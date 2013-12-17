package eu.compassresearch.core.interpreter.api;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.types.PType;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.events.CmlInterpreterStateObserver;
import eu.compassresearch.core.interpreter.api.events.EventSource;
import eu.compassresearch.core.interpreter.debug.Breakpoint;
import eu.compassresearch.core.interpreter.debug.DebugContext;

/**
 * The CML interpreter interface. This has the responsibility of simulating/animating a given AST representing a CML
 * model.
 */

public interface CmlInterpreter
{

	/**
	 * Get a string version of the environment.
	 */

	public Context getInitialContext(LexLocation location);

	/**
	 * Get the name of the default process. Symbols in the default process do not have to have their names qualified
	 * when being referred to on the command line.
	 * 
	 * @return The default name.
	 */

	public String getDefaultName();

	/**
	 * Set the default process.
	 * 
	 * @param name
	 *            The default name.
	 * @throws Exception
	 */

	public void setDefaultName(String name);

	/**
	 * The top level supervisor for the interpreter
	 * 
	 * @return
	 */
	public SelectionStrategy getEnvironment();

	/**
	 * Initializes the interpreter by making a global context and setting the last defined process as the top process
	 * 
	 * @throws CmlInterpreterException
	 */
	public void initialize() throws AnalysisException;

	/**
	 * Executes the defined default process from the given sourceForest
	 * 
	 * @param sve
	 *            the top supervisor environment
	 * @return Ifthe process value of the top process if
	 * @throws AnalysisException
	 */
	public Value execute(SelectionStrategy env) throws AnalysisException;

	public CmlInterpreterState getState();

	public CmlBehaviour getTopLevelProcess();

	public CmlBehaviour findBehaviorById(int id);
	
	/**
	 * Gets the active debug context of a given process by its id
	 * @param id a process id
	 * @return a the current debug context
	 */
	public DebugContext getDebugContext(int id);
	
	/**
	 * Sets the current debug context for a given process
	 * @param id the process id
	 * @param context the current context
	 * @param location the current location
	 */
	public void setDebugContext(int id, Context context, ILexLocation location);
	
	/**
	 * Sets the current debug context for the first active process
	 * @param id the process id
	 * @param context the current context
	 * @param location the current location
	 */
	public void setCurrentDebugContext(Context context, ILexLocation location);

	public EventSource<CmlInterpreterStateObserver> onStateChanged();

	// Debugging control methods

	public boolean addBreakpoint(Breakpoint bp);

	public boolean removeBreakpoint(Breakpoint bp);

	public Breakpoint getActiveBreakpoint();

	public void setSuspendBeforeTermination(boolean suspend);

	public boolean suspendBeforeTermination();

	public void resume();
	
	public void suspend() throws InterruptedException;

	public void step();

	// public CmlInterpreter getInstance();
	PExp parseExpression(String line, String module) throws Exception;

	public PType typeCheck(PExp expr) throws Exception;

}
