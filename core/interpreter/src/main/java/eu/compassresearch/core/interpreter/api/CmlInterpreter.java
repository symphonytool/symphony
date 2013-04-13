package eu.compassresearch.core.interpreter.api;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.types.PType;
import org.overture.interpreter.runtime.Breakpoint;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.SourceFile;
import org.overture.interpreter.values.Value;
import org.overture.parser.lex.LexException;
import org.overture.parser.syntax.ParserException;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.events.CmlInterpreterStatusObserver;
import eu.compassresearch.core.interpreter.events.EventSource;
import eu.compassresearch.core.interpreter.scheduler.CmlScheduler;
/**
 * The CML interpreter interface.
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
	 * Get the filename that contains the default process.
	 *
	 * @return The default file name.
	 */

	public File getDefaultFile();
	
	/**
	 * The top level supervisor for the interpreter
	 * @return
	 */
	public CmlSupervisorEnvironment getCurrentSupervisor();

//	/**
//	 * Initialize the initial context. This means that all definition
//	 * initializers are re-run to put the global environment back into its
//	 * original state. This is run implicitly when the interpreter starts,
//	 * but it can also be invoked explicitly via the "init" command.
//	 *
//	 * @throws Exception
//	 */
//
//	public void init(DBGPReader dbgp);
//
//	/**
//	 * Initialize the context between trace sequences. This is less
//	 * thorough than the full init, since it does not reset the scheduler
//	 * for example.
//	 */
//
//	public void traceInit(DBGPReader dbgp);
//
//	/**
//	 * Parse the line passed, type check it and evaluate it as an expression
//	 * in the initial context.
//	 *
//	 * @param line A CML expression.
//	 * @param dbgp The DBGPReader, if any
//	 * @return The value of the expression.
//	 * @throws Exception Parser, type checking or runtime errors.
//	 */
//
//	public Value execute(String line, DBGPReader dbgp) throws Exception;

//	/**
//	 * Executes the defined default process from the given sourceForest  
//	 * 
//	 * @return The value of the expression.
//	 * @throws Exception Parser, type checking or runtime errors.
//	 */
//
//	public Value execute() throws InterpreterException;
	
	
	
	/**
	 * Executes the defined default process from the given sourceForest, with the given selection strategy
	 * @param selectionStrategy
	 * @return
	 * @throws InterpreterException
	 */
	public Value execute(CmlSupervisorEnvironment sve, CmlScheduler cmlScheduler) throws InterpreterException;

	/**
	 * Parse the line passed, and evaluate it as an expression in the context
	 * passed. Note that this does not type check the expression.
	 *
	 * @param line A CML expression.
	 * @param ctxt The context in which to evaluate the expression.
	 * @return The value of the expression.
	 * @throws Exception Parser or runtime errors.
	 */

	abstract public Value evaluate(String line, Context ctxt) throws Exception;

	/**
	 * @return The list of breakpoints currently set.
	 */

	public Map<Integer, Breakpoint> getBreakpoints();

	/**
	 * Get a line of a source file.
	 */

	public String getSourceLine(LexLocation src);

	/**
	 * Get a line of a source file by its location.
	 */

	public String getSourceLine(File file, int line);

	/**
	 * Get a line of a source file by its location.
	 */

	public String getSourceLine(File file, int line, String sep);

	/**
	 * Get an entire source file object.
	 * @throws IOException
	 */

	public SourceFile getSourceFile(File file) throws IOException;

	/**
	 * Get a list of all source files.
	 */
	public Set<File> getSourceFiles();
	
	/**
	 * Find a statement by file name and line number.
	 *
	 * @param file The name of the class/module
	 * @param lineno The line number
	 * @return A Statement object if found, else null.
	 */

	public PAction findStatement(File file, int lineno);

	/**
	 * Find an expression by file name and line number.
	 *
	 * @param file The name of the file
	 * @param lineno The line number
	 * @return An Expression object if found, else null.
	 */

	public PExp findExpression(File file, int lineno);

	/**
	 * Find a global environment value by name.
	 *
	 * @param name The name of the variable
	 * @return A Value object if found, else null.
	 */

	public Value findGlobal(LexNameToken name);

	/**
	 * Set a statement tracepoint. A tracepoint does not stop execution, but
	 * evaluates and displays an expression before continuing.
	 *
	 * @param stmt The statement to trace.
	 * @param trace The expression to evaluate.
	 * @return The Breakpoint object created.
	 *
	 * @throws Exception Expression is not valid.
	 */

	public Breakpoint setTracepoint(PAction stmt, String trace) throws Exception;

	/**
	 * Set an expression tracepoint. A tracepoint does not stop execution, but
	 * evaluates an expression before continuing.
	 *
	 * @param exp The expression to trace.
	 * @param trace The expression to evaluate.
	 * @return The Breakpoint object created.
	 *
	 * @throws LexException
	 * @throws ParserException
	 */

	public Breakpoint setTracepoint(PExp exp, String trace)
		throws LexException;

	/**
	 * Set a statement breakpoint. A breakpoint stops execution and allows
	 * the user to query the environment.
	 *
	 * @param stmt The statement at which to stop.
	 * @param condition The condition when to stop.
	 * @return The Breakpoint object created.
	 *
	 * @throws LexException
	 * @throws ParserException
	 */

	public Breakpoint setBreakpoint(PAction stmt, String condition)
		throws LexException;

	/**
	 * Set an expression breakpoint. A breakpoint stops execution and allows
	 * the user to query the environment.
	 *
	 * @param exp The expression at which to stop.
	 * @param condition The condition when to stop.
	 * @return The Breakpoint object created.
	 * @throws LexException
	 * @throws ParserException
	 *
	 */

	public Breakpoint setBreakpoint(PExp exp, String condition)
		throws LexException;

	/**
	 * Clear the breakpoint given by the number.
	 *
	 * @param bpno The breakpoint number to remove.
	 * @return The breakpoint object removed, or null.
	 */

	public Breakpoint clearBreakpoint(int bpno);

	public void clearBreakpointHits();
	
	public PType findType(String typename);
	
	public InterpreterStatus getStatus();

	public EventSource<CmlInterpreterStatusObserver> onStatusChanged();
}
