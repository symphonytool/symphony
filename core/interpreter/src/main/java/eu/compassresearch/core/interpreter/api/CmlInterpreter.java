package eu.compassresearch.core.interpreter.api;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexLocation;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.events.CmlInterpreterStateObserver;
import eu.compassresearch.core.interpreter.api.events.EventSource;
import eu.compassresearch.core.interpreter.debug.Breakpoint;

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

	public EventSource<CmlInterpreterStateObserver> onStateChanged();

	// Debugging control methods

	public boolean addBreakpoint(Breakpoint bp);

	public Breakpoint getActiveBreakpoint();

	public void resume();

	public void step();

}
