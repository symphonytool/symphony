package eu.compassresearch.core.interpreter;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.types.PType;
import org.overture.ast.util.definitions.ClassList;
import org.overture.interpreter.runtime.ClassInterpreter;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.scheduler.BasicSchedulableThread;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.lex.CmlLexNameToken;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.api.CmlInterpreterState;
import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.SelectionStrategy;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.behaviour.CmlTrace;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.transitions.ObservableTransition;
import eu.compassresearch.core.interpreter.api.transitions.TimedTransition;
import eu.compassresearch.core.interpreter.api.values.ProcessObjectValue;
import eu.compassresearch.core.interpreter.debug.Breakpoint;
import eu.compassresearch.core.interpreter.debug.DebugContext;
import eu.compassresearch.core.interpreter.utility.CmlInitThread;
import eu.compassresearch.core.interpreter.utility.LocationExtractor;
import eu.compassresearch.core.parser.ParserUtil;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.ICmlTypeChecker;

class VanillaCmlInterpreter extends AbstractCmlInterpreter
{
	static
	{
		BasicSchedulableThread.setInitialThread(new CmlInitThread(Thread.currentThread()));
	}

	/**
	 * 
	 */
	protected Context globalContext;
	protected String defaultName = null;
	protected AProcessDefinition topProcess;

	/**
	 * Sync object used to suspend the execution
	 */
	private Object suspendObject = new Object();
	private boolean stepping = false;
	private Breakpoint activeBP = null;
	private CmlTransition selectedEvent;

	/**
	 * Construct a CmlInterpreter with a list of PSources. These source may refer to each other.
	 * 
	 * @param definitions
	 *            - Source containing CML Paragraphs for type checking.
	 */
	public VanillaCmlInterpreter(List<PDefinition> definitions, Config config)
	{
		super(config);
		this.sourceForest = definitions;
		instance = this;
	}

	/**
	 * Initializes the interpreter by making a global context and setting the last defined process as the top process
	 * 
	 * @throws AnalysisException
	 */
	public void initialize() throws AnalysisException
	{
		super.initialize();
		GlobalEnvironmentBuilder envBuilder = new GlobalEnvironmentBuilder(sourceForest);
		// Build the global context
		globalContext = envBuilder.getGlobalContext();
		// set the last defined process as the top process
		topProcess = envBuilder.getLastDefinedProcess();
		setNewState(CmlInterpreterState.INITIALIZED);

		ClassList classes = new ClassList();
		for (PDefinition def : sourceForest)
		{
			if (def instanceof SClassDefinition)
			{
				classes.add((SClassDefinition) def);
			}
		}
		try
		{
			new ClassInterpreter(classes);// this stores an internal static reference needed later
											// Interpreter.getInstance()
		} catch (Exception e)
		{
			throw new AnalysisException("Faild to initialize class interpreter", e);
		}
	}

	@Override
	public String getDefaultName()
	{
		return defaultName;
	}

	@Override
	public void setDefaultName(String name)
	{
		defaultName = name;
	}

	@Override
	public Value execute(SelectionStrategy env) throws AnalysisException
	{
		if (this.getState() == null)
		{
			setNewState(CmlInterpreterState.FAILED);
			throw new CmlInterpreterException("The interpreter has not been initialized, please call the initialize method before invoking the start method");
		}

		if (null == env)
		{
			setNewState(CmlInterpreterState.FAILED);
			throw new CmlInterpreterException("The SelectionStrategy must not be set to null in the cml scheduler");
		}

		if (null == topProcess)
		{
			setNewState(CmlInterpreterState.FAILED);
			throw new CmlInterpreterException("No process is defined");
		}

		environment = env;

		// Find and initialize the top process value
		ProcessObjectValue pov = InitializeTopProcess();
		// Create the initial context with the global definitions
		Context topContext = getInitialContext(null);
		// Create a CmlBehaviour for the top process
		runningTopProcess = config.cmlBehaviorFactory.newCmlBehaviour(topProcess.getProcess(), topContext, new CmlBehaviour.BehaviourName(topProcess.getName().getName()),null);

		// Fire the interpreter running event before we start
		setNewState(CmlInterpreterState.RUNNING);
		// start the execution of the top process
		try
		{
			executeTopProcess(runningTopProcess);
		} catch (AnalysisException e)
		{
			setNewState(CmlInterpreterState.FAILED);
			throw e;
		} catch (InterruptedException ex)
		{
			ex.printStackTrace();
		} catch (Exception ex)
		{
			setNewState(CmlInterpreterState.FAILED);
			throw ex;
		}

		// Finally we return the top process value
		return pov;
	}

	/**
	 * Finds and initializes the top process
	 * 
	 * @return
	 * @throws AnalysisException
	 */
	private ProcessObjectValue InitializeTopProcess() throws AnalysisException
	{
		if (defaultName != null && !defaultName.equals(""))
		{
			CmlLexNameToken name = new CmlLexNameToken("", getDefaultName(), null);
			ProcessObjectValue pov = (ProcessObjectValue) globalContext.check(name);

			if (pov == null)
			{
				throw new CmlInterpreterException(InterpretationErrorMessages.NO_PROCESS_WITH_DEFINED_NAME_FOUND.customizeMessage(getDefaultName()));
			}

			topProcess = pov.getProcessDefinition();

			return pov;
		}

		return null;
	}

	/**
	 * Main loop for executing the top process
	 * 
	 * @param behaviour
	 * @throws AnalysisException
	 * @throws InterruptedException
	 */
	protected void executeTopProcess(CmlBehaviour behaviour)
			throws AnalysisException, InterruptedException
	{
		// continue until the top process is not finished and not deadlocked
		while (!behaviour.finished() && !behaviour.deadlocked())
		{
			CmlTransitionSet availableEvents = inspect(behaviour);

			selectedEvent = resolveChoice(availableEvents);

			// if its null we terminate and assume that this happened because of a user interrupt
			if (selectedEvent == null)
			{
				break;
			}

			// Handle the breakpoints if any
			handleBreakpoints(selectedEvent);

			// if we get here it means that it in a running state again
			setNewState(CmlInterpreterState.RUNNING);

			executeBehaviour(behaviour);
			CmlTrace trace = behaviour.getTraceModel();

			logTransition(behaviour, trace);

		}

		if (behaviour.deadlocked())
		{
			setNewState(CmlInterpreterState.DEADLOCKED);
			Console.err.println("DEADLOCKED");
			if (suspendBeforeTermination())
			{
				synchronized (suspendObject)
				{
					this.suspendObject.wait();
				}
			}
		} else if (!behaviour.finished())
		{
			setNewState(CmlInterpreterState.TERMINATED_BY_USER);
		} else
		{
			setNewState(CmlInterpreterState.FINISHED);
		}

	}

	protected CmlTransitionSet inspect(CmlBehaviour behaviour)
			throws AnalysisException
	{
		// inspect the top process to get the next possible trace element
		CmlTransitionSet topAlphabet = behaviour.inspect();
		// expand what's possible in the alphabet
		CmlTransitionSet availableEvents = topAlphabet.expandAlphabet();
		return availableEvents;
	}

	protected void executeBehaviour(CmlBehaviour behaviour)
			throws AnalysisException
	{
		behaviour.execute(selectedEvent);
	}

	public CmlTransition resolveChoice(CmlTransitionSet availableEvents)
	{
		CmlRuntime.logger().fine("Waiting for environment on : "
				+ availableEvents.getAllEvents());

		logState(availableEvents);

		SelectionStrategy env = getEnvironment();
		// set the state of the interpreter to be waiting for the environment
		env.choices(filterEvents(availableEvents));
		setNewState(CmlInterpreterState.WAITING_FOR_ENVIRONMENT);
		// Get the environment to select the next transition.
		// this is potentially a blocking call!!
		return env.resolveChoice();
	}

	public void logState(CmlTransitionSet availableEvents)
	{
		for (CmlTransition event : availableEvents.getAllEvents())
		{
			// TODO this should be handled differently
			Context context = event.getEventSources().iterator().next().getNextState().second;

			String state;

			if (context.getSelf() != null)
			{
				state = context.getSelf().toString();
			} else if (context.outer != null)
			{
				state = context.getRoot().toString();
			} else
			{
				state = context.toString();
			}

			CmlRuntime.logger().finer("State for " + event + " : " + state);
		}
	}

	public void logTransition(CmlBehaviour behaviour, CmlTrace trace)
	{
		if (trace.getLastTransition() instanceof ObservableTransition)
		{
			CmlRuntime.logger().fine("----------------observable step by '"
					+ behaviour + "'----------------------");
			CmlRuntime.logger().fine("Observable trace of '" + behaviour
					+ "': " + trace.getObservableTrace());
			CmlRuntime.logger().fine("Eval. Status={ "
					+ behaviour.nextStepToString() + " }");
			CmlRuntime.logger().fine("-----------------------------------------------------------------");
		} else
		{
			CmlRuntime.logger().finer("----------------Silent step by '"
					+ behaviour + "'--------------------");
			CmlRuntime.logger().finer("Trace of '" + behaviour + "': "
					+ trace);
			CmlRuntime.logger().finer("Eval. Status={ "
					+ behaviour.nextStepToString() + " }");
			CmlRuntime.logger().finer("-----------------------------------------------------------------");
		}
	}

	@Override
	public DebugContext getDebugContext(int id)
	{
		DebugContext context = super.getDebugContext(id);
		if (context == null)
		{
			CmlBehaviour behaviour = findBehaviorById(id);
			if (behaviour != null)
			{
				ILexLocation location = LocationExtractor.extractLocation(behaviour.getNextState().first);
				context = new DebugContext(location, behaviour.getNextState().second);
			}
		}

		return context;
	}

	@Override
	public void setCurrentDebugContext(Context context, ILexLocation location)
	{
		setDebugContext(selectedEvent.getEventSources().iterator().next().getId(), context, location);
	}

	private CmlTransitionSet filterEvents(CmlTransitionSet availableEvents)
	{
		if (!config.filterTockEvents)
		{
			return availableEvents;
		}

		SortedSet<CmlTransition> events = new TreeSet<CmlTransition>();
		for (CmlTransition event : availableEvents.getAllEvents())
		{
			if (!(event instanceof TimedTransition))
			{
				events.add(event);
			}
		}
		return new CmlTransitionSet(events);
	}

	@Override
	public void resume()
	{
		synchronized (suspendObject)
		{
			stepping = false;
			this.suspendObject.notifyAll();
		}
	}

	@Override
	public void suspend() throws InterruptedException
	{
		setNewState(CmlInterpreterState.SUSPENDED);

		synchronized (suspendObject)
		{
			this.suspendObject.wait();
		}
	}

	public void step()
	{
		synchronized (suspendObject)
		{
			stepping = true;
			this.suspendObject.notifyAll();
		}
	}

	private void handleBreakpoints(CmlTransition selectedEvent)
			throws InterruptedException
	{
		activeBP = findActiveBreakpoint(selectedEvent);
		if (activeBP != null || stepping)
		{
			suspend();

		}
	}

	private Breakpoint findActiveBreakpoint(CmlTransition selectedEvent)
	{
		Breakpoint bp = null;

		// see if any of the next executing processes/actions are hitting any breakpoints
		for (CmlBehaviour b : selectedEvent.getEventSources())
		{
			ILexLocation loc = LocationExtractor.extractLocation(b.getNextState().first);
			if (loc == null)
			{
				continue;
			}
			String key = loc.getFile().toURI().toString() + ":"
					+ loc.getStartLine();
			if (this.breakpoints.containsKey(key))
			{
				bp = this.breakpoints.get(key);
			}
		}

		return bp;
	}

	@Override
	public Breakpoint getActiveBreakpoint()
	{
		return activeBP;
	}

	@Override
	public Context getInitialContext(LexLocation location)
	{
		return globalContext;
	}

	@Override
	public CmlBehaviour getTopLevelProcess()
	{
		return runningTopProcess;
	}

	@Override
	public PType typeCheck(PExp expr) throws Exception
	{
		ICmlTypeChecker typeChecker = VanillaFactory.newTypeChecker(sourceForest, null);
		return typeChecker.typeCheck(expr);

	}

	@Override
	public PExp parseExpression(String line, String module) throws Exception
	{
		return ParserUtil.parseExpression(new File("Console"), ParserUtil.getCharStream(line, StandardCharsets.UTF_8.name())).exp;
	}

}
