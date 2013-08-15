package eu.compassresearch.core.interpreter.api;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexLocation;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.events.CmlInterpreterStatusObserver;
import eu.compassresearch.core.interpreter.debug.Breakpoint;
import eu.compassresearch.core.interpreter.utility.events.EventSource;
/**
 * The CML interpreter interface. This has the responsibility of simulating/animating
 * a given AST representing a CML model. 
 */

public interface CmlInterpreter 
{
	
	/**
	 * Get a string version of the environment.
	 */

	public Context getInitialContext(LexLocation location);

	/**
	 * Get the name of the default process. Symbols in the default
	 * process do not have to have their names qualified when being
	 * referred to on the command line.
	 *
	 * @return The default name.
	 */

	public String getDefaultName();
	
	/**
	 * Set the default process.
	 *
	 * @param name The default name.
	 * @throws Exception
	 */

	public void setDefaultName(String name);
	
	/**
	 * The top level supervisor for the interpreter
	 * @return
	 */
	public CmlSupervisorEnvironment getCurrentSupervisor();

	/**
	 * Initializes the interpreter by making a global context and setting the 
	 * last defined process as the top process
	 * @throws InterpreterException
	 */
	public void initialize() throws AnalysisException;
	
	/**
	 * Executes the defined default process from the given sourceForest, with the given selection strategy
	 * @param selectionStrategy
	 * @return
	 * @throws InterpreterException
	 */
	public Value execute(CmlSupervisorEnvironment sve) throws AnalysisException;
	
	public CmlInterpreterState getStatus();
	
	public CmlInterpretationStatus getCurrentState();
	
	public CmlBehaviour getTopLevelCmlBehaviour();

	public EventSource<CmlInterpreterStatusObserver> onStatusChanged();
	
	//Breakpoints
	
	public boolean addBreakpoint(Breakpoint bp);
	
	public void resume();
	
	public void step();
	
}
