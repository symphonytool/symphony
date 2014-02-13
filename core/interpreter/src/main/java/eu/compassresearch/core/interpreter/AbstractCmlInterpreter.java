package eu.compassresearch.core.interpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.lex.Dialect;
import org.overture.ast.node.INode;
import org.overture.ast.statements.PStm;
import org.overture.config.Release;
import org.overture.config.Settings;
import org.overture.interpreter.debug.BreakpointManager;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.Stoppoint;
import org.overture.interpreter.scheduler.BasicSchedulableThread;
import org.overture.parser.lex.LexException;
import org.overture.parser.syntax.ParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.CmlInterpreterState;
import eu.compassresearch.core.interpreter.api.SelectionStrategy;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.events.CmlInterpreterStateObserver;
import eu.compassresearch.core.interpreter.api.events.EventFireMediator;
import eu.compassresearch.core.interpreter.api.events.EventSource;
import eu.compassresearch.core.interpreter.api.events.EventSourceHandler;
import eu.compassresearch.core.interpreter.api.events.InterpreterStateChangedEvent;
import eu.compassresearch.core.interpreter.assistant.CmlInterpreterAssistantFactory;
import eu.compassresearch.core.interpreter.debug.Breakpoint;
import eu.compassresearch.core.interpreter.debug.DebugContext;
import eu.compassresearch.core.interpreter.utility.CmlInitThread;
import eu.compassresearch.core.interpreter.utility.FirstLineMatchSearcher;

public abstract class AbstractCmlInterpreter implements CmlInterpreter
{

	/**
	 * Event handler for notifying when the interpreter status changes
	 */
	protected EventSourceHandler<CmlInterpreterStateObserver, InterpreterStateChangedEvent> stateChangedEventHandler = new EventSourceHandler<CmlInterpreterStateObserver, InterpreterStateChangedEvent>(this, new EventFireMediator<CmlInterpreterStateObserver, InterpreterStateChangedEvent>()
	{

		@Override
		public void fireEvent(CmlInterpreterStateObserver observer,
				Object source, InterpreterStateChangedEvent event)
		{

			observer.onStateChanged(source, event);

		}
	});

	final static Logger logger = LoggerFactory.getLogger("cml-interpreter");

	/**
	 * A map of the active breakpoints where the key has the following format "<filepath>:<linenumber>"
	 */
	protected Map<String, Breakpoint> breakpoints = new HashMap<>();
	protected CmlBehaviour runningTopProcess = null;
	protected SelectionStrategy environment;
	private boolean suspendBeforeTermination = false;

	/** A static instance pointer to the interpreter. */
	protected static CmlInterpreter instance = null;
	/**
	 * The current state of the interpreter
	 */
	private CmlInterpreterState currentState = null;

	/**
	 * Configuration of the interpreter
	 */
	protected final Config config;

	/**
	 * The AST that the interpretation is performed on
	 */
	protected List<PDefinition> sourceForest;

	/**
	 * The active debug contexts for all processes.
	 */
	private Map<Integer, DebugContext> debugContexts = new HashMap<Integer, DebugContext>();

	public AbstractCmlInterpreter(Config config)
	{
		this.config = config;

	}

	/**
	 * Set the new state of the interpreter
	 */
	protected void setNewState(CmlInterpreterState newState)
	{
		if (currentState != newState)
		{
			currentState = newState;
			stateChangedEventHandler.fireEvent(new InterpreterStateChangedEvent(this));
		}
	}

	/**
	 * Retrieves the current state of the interpreter
	 * 
	 * @return The current state of the interpreter
	 */
	@Override
	public CmlInterpreterState getState()
	{
		return currentState;
	}

	@Override
	public SelectionStrategy getEnvironment()
	{
		return environment;
	}

	@Override
	public EventSource<CmlInterpreterStateObserver> onStateChanged()
	{

		return stateChangedEventHandler;
	}

	@Override
	public void initialize() throws AnalysisException
	{
		Settings.dialect = Dialect.VDM_PP;
		Settings.release = Release.VDM_10;
		// enable debugging in VDM source code
		Settings.usingDBGP = true;
		/**
		 * configure the thread management in the overture interpreter
		 */
		BasicSchedulableThread.setInitialThread(new CmlInitThread(Thread.currentThread()));
		CmlInterpreterAssistantFactory.init(CmlContextFactory.factory);
	}

	// Breakpoints
	@Override
	public boolean addBreakpoint(Breakpoint bp)
	{

		String key = bp.getFile() + ":" + bp.getLine();

		addOvertureBreakpoint(bp);

		if (breakpoints.containsKey(key))
		{
			return false;
		} else
		{
			breakpoints.put(key, bp);
			return true;
		}
	}

	protected void addOvertureBreakpoint(Breakpoint bp)
	{
		INode node = FirstLineMatchSearcher.search(sourceForest, bp.getFile(), bp.getLine());
		if (node != null)
		{
			try
			{
				if (node instanceof PStm)
				{
					PStm stmt = (PStm) node;
					BreakpointManager.setBreakpoint(stmt, new Stoppoint(stmt.getLocation(), bp.getId(), null));

				} else if (node instanceof PExp)
				{

					PExp exp = (PExp) node;
					BreakpointManager.setBreakpoint(exp, new Stoppoint(exp.getLocation(), bp.getId(), null));
				}

			} catch (ParserException | LexException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean removeBreakpoint(Breakpoint bp)
	{
		String key = bp.getFile() + ":" + bp.getLine();

		if (!breakpoints.containsKey(key))
		{
			return false;
		} else
		{
			breakpoints.remove(key);
			return true;
		}
	}

	@Override
	public CmlBehaviour findBehaviorById(int id)
	{

		return findBehaviorById(this.runningTopProcess, id);
	}

	private CmlBehaviour findBehaviorById(CmlBehaviour behavior, int id)
	{

		CmlBehaviour foundBehavior = null;

		if (behavior.getId() == id)
		{
			foundBehavior = behavior;
		}

		if (behavior.getLeftChild() != null && foundBehavior == null)
		{
			foundBehavior = findBehaviorById(behavior.getLeftChild(), id);
		}

		if (behavior.getRightChild() != null && foundBehavior == null)
		{
			foundBehavior = findBehaviorById(behavior.getRightChild(), id);
		}

		return foundBehavior;
	}

	@Override
	public boolean suspendBeforeTermination()
	{
		return suspendBeforeTermination;
	}

	@Override
	public void setSuspendBeforeTermination(boolean suspendBeforeTermination)
	{
		this.suspendBeforeTermination = suspendBeforeTermination;
	}

	/**
	 * @return The Interpreter instance.
	 */
	public static CmlInterpreter getInstance()
	{
		return instance; // NB. last one created
	}

	@Override
	public DebugContext getDebugContext(int id)
	{
		return debugContexts.get(id);
	}

	@Override
	public void setDebugContext(int id, Context context, ILexLocation location)
	{
		debugContexts.put(id, new DebugContext(location, context));
	}

}
