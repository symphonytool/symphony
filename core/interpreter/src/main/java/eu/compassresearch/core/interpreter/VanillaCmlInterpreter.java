package eu.compassresearch.core.interpreter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.lex.Dialect;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.util.definitions.ClassList;
import org.overture.interpreter.runtime.ClassInterpreter;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.scheduler.BasicSchedulableThread;
import org.overture.interpreter.scheduler.InitThread;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.lex.CmlLexNameToken;
import eu.compassresearch.core.interpreter.api.AnnimationStrategy;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.api.CmlInterpreterState;
import eu.compassresearch.core.interpreter.api.ConsoleSelectionStrategy;
import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.RandomSelectionStrategy;
import eu.compassresearch.core.interpreter.api.SelectionStrategy;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.behaviour.CmlTrace;
import eu.compassresearch.core.interpreter.api.events.CmlInterpreterStateObserver;
import eu.compassresearch.core.interpreter.api.events.InterpreterStateChangedEvent;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.transitions.ObservableTransition;
import eu.compassresearch.core.interpreter.api.values.ProcessObjectValue;
import eu.compassresearch.core.interpreter.debug.Breakpoint;
import eu.compassresearch.core.interpreter.utility.LocationExtractor;
import eu.compassresearch.core.parser.ParserUtil;
import eu.compassresearch.core.parser.ParserUtil.ParserResult;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.ICmlTypeChecker;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;

class VanillaCmlInterpreter extends AbstractCmlInterpreter
{
	static
	{
		BasicSchedulableThread.setInitialThread(new InitThread(Thread.currentThread()));
	}

	/**
	 * 
	 */
	protected List<PDefinition> sourceForest;
	protected Context globalContext;
	protected String defaultName = null;
	protected AProcessDefinition topProcess;

	/**
	 * Sync object used to suspend the execution
	 */
	private Object suspendObject = new Object();
	private boolean stepping = false;
	private Breakpoint activeBP = null;

	/**
	 * Construct a CmlInterpreter with a list of PSources. These source may refer to each other.
	 * 
	 * @param definitions
	 *            - Source containing CML Paragraphs for type checking.
	 */
	public VanillaCmlInterpreter(List<PDefinition> definitions)
	{
		this.sourceForest = definitions;
	}

	// /**
	// * Construct a CmlTypeInterpreter with the intension of checking a single source.
	// *
	// * @param singleSource
	// */
	// public VanillaCmlInterpreter(PSource singleSource)
	// {
	// this.sourceForest = new LinkedList<PDefinition>();
	// this.sourceForest.add(singleSource);
	// }

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
		// FIXME When there are multiple files there are no way to determine which one it will be!
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
		runningTopProcess = new ConcreteCmlBehaviour(topProcess.getProcess(), topContext, topProcess.getName());

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
	private void executeTopProcess(CmlBehaviour behaviour)
			throws AnalysisException, InterruptedException
	{
		// continue until the top process is not finished and not deadlocked
		while (!behaviour.finished() && !behaviour.deadlocked())
		{
			// inspect the top process to get the next possible trace element
			CmlTransitionSet topAlphabet = behaviour.inspect();
			// expand what's possible in the alphabet
			CmlTransitionSet availableEvents = topAlphabet.expandAlphabet();

			CmlRuntime.logger().fine("Waiting for environment on : "
					+ availableEvents.getAllEvents());

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

			// set the state of the interpreter to be waiting for the environment
			getEnvironment().choices(availableEvents);
			setNewState(CmlInterpreterState.WAITING_FOR_ENVIRONMENT);
			// Get the environment to select the next transition.
			// this is potentially a blocking call!!
			CmlTransition selectedEvent = getEnvironment().resolveChoice();

			// if its null we terminate and assume that this happended because of a user interrupt
			if (selectedEvent == null)
			{
				break;
			}

			// Handle the breakpoints if any
			handleBreakpoints(selectedEvent);

			if (getState() == CmlInterpreterState.SUSPENDED)
			{
				synchronized (suspendObject)
				{
					this.suspendObject.wait();
				}
			}

			// if we get here it means that it in a running state again
			setNewState(CmlInterpreterState.RUNNING);

			behaviour.execute(selectedEvent);
			CmlTrace trace = behaviour.getTraceModel();

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

	@Override
	public void resume()
	{
		synchronized (suspendObject)
		{
			stepping = false;
			this.suspendObject.notifyAll();
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
	{
		activeBP = findActiveBreakpoint(selectedEvent);
		if (activeBP != null || stepping)
		{
			setNewState(CmlInterpreterState.SUSPENDED);
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

	public static void main(String[] args) throws IOException,
			CmlInterpreterException
	{
		List<String> largs = Arrays.asList(args);
		List<File> filenames = new Vector<File>();
		String processName = null;
		String remoteClass = null;
		SelectionStrategy selectionStrategy = new ConsoleSelectionStrategy();

		for (Iterator<String> i = largs.iterator(); i.hasNext();)
		{
			String arg = i.next();
			if (arg.equals("-process"))
			{
				if (i.hasNext())
				{
					processName = i.next();
				} else
				{
					usage("-process option requires a process name");
				}
			} else if (arg.equals("-animate"))
			{
				selectionStrategy = new AnnimationStrategy();
			} else if (arg.equals("-simulate"))
			{
				selectionStrategy = new RandomSelectionStrategy();
			} else if (arg.equals("-remote"))
			{
				if (i.hasNext())
				{
					remoteClass = i.next();
				} else
				{
					usage("-remote option requires a fully qualified java class name");
				}
			} else if (arg.startsWith("-"))
			{
				usage("Unknown option " + arg);
			} else
			{
				// It's a file or a directory
				File file = new File(arg);

				if (file.isDirectory())
				{
					for (File subFile : file.listFiles(Dialect.CML.getFilter()))
					{
						if (subFile.isFile())
						{
							filenames.add(subFile);
						}
					}
				} else
				{
					if (file.exists())
					{
						filenames.add(file);
					} else
					{
						usage("Cannot find file " + file);

					}
				}
			}
		}
		
		if(remoteClass!=null)
		{
			//TODO
		}

		execute(selectionStrategy, processName, filenames.toArray(new File[filenames.size()]));

	}

	private static void execute(SelectionStrategy selectionStrategy,
			String processName, File... f) throws IOException,
			CmlInterpreterException
	{
		ParserResult res = ParserUtil.parse(f);

		if (res.errors.size() > 0)
		{
			res.printErrors(System.err);
			return;
		}

		ITypeIssueHandler issueHandler = VanillaFactory.newCollectingIssueHandle();

		// Type check
		ICmlTypeChecker cmlTC = VanillaFactory.newTypeChecker(res.definitions, issueHandler);

		// Print result and report errors if any
		if (!cmlTC.typeCheck())
		{
			System.out.println("Failed to type check: " + f.toString());
			System.out.println(issueHandler.getTypeErrors());
			return;
		}

		// interpret
		VanillaCmlInterpreter interpreter = new VanillaCmlInterpreter(res.definitions);
		if (processName != null)
		{
			interpreter.setDefaultName(processName);
		}
		interpreter.onStateChanged().registerObserver(new CmlInterpreterStateObserver()
		{

			@Override
			public void onStateChanged(Object source,
					InterpreterStateChangedEvent event)
			{
				System.out.println("Simulator status event : "
						+ event.getStatus());

			}
		});

		try
		{
			CmlRuntime.logger().setLevel(Level.FINEST);
			interpreter.initialize();
			interpreter.execute(selectionStrategy);
		} catch (ValueException e)
		{
			System.out.println("With Error : " + e);
			System.out.println(e.ctxt.location);
			System.out.println("With stack trace : ");
			e.printStackTrace();
		} catch (Exception ex)
		{
			System.out.println("Failed to interpret: " + f.toString());
			System.out.println("With Error : " + ex.getMessage());
			System.out.println("With stack trace : ");
			ex.printStackTrace();
			return;
		}

		// Report success
		System.out.println("The given CML Program is done simulating.");
	}

	private static void usage(String msg)
	{
		System.err.println("COMPASS: " + msg + "\n");
		System.err.println("Usage: COMPASS <-process> [<options>] [<files or dirs>]");
		System.err.println("-animate: use the animation strategy");
		System.err.println("-simulate: use the simulation strategy that autoselects events");
		System.err.println("-remote: use a remote interpreter as selection strategy");
		// System.err.println("-path: search path for files");
		// System.err.println("-r <release>: VDM language release");
		// System.err.println("-w: suppress warning messages");
		// System.err.println("-q: suppress information messages");
		// System.err.println("-i: run the interpreter if successfully type checked");
		// System.err.println("-p: generate proof obligations and stop");
		// System.err.println("-e <exp>: evaluate <exp> and stop");
		// System.err.println("-c <charset>: select a file charset");
		// System.err.println("-t <charset>: select a console charset");
		// System.err.println("-o <filename>: saved type checked specification");
		// System.err.println("-default <name>: set the default module/class");
		// System.err.println("-pre: disable precondition checks");
		// System.err.println("-post: disable postcondition checks");
		// System.err.println("-inv: disable type/state invariant checks");
		// System.err.println("-dtc: disable all dynamic type checking");
		// System.err.println("-measures: disable recursive measure checking");
		// System.err.println("-log: enable real-time event logging");
		// System.err.println("-remote <class>: enable remote control");

		System.exit(1);
	}

}
