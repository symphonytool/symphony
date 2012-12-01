package eu.compassresearch.core.interpreter;

import java.util.List;

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.InterpreterException;

public final class VanillaInterpreterFactory {

	
	/**
	 * create an instance of the Vanilla interpreter.
	 * 
	 * @param cmlSources
	 *            - List of parsed and typechecked CML source to interpret
	 * @throws InterpreterException 
	 */
	public static CmlInterpreter newInterpreter(List<PSource> cmlSources) throws InterpreterException {
		return new VanillaCmlInterpreter(cmlSources);
	}
	
	/**
	 * create an instance of the Vanilla interpreter.
	 * 
	 * @param cmlSource
	 *            - A single parsed and typechecked CML source to interpret
	 * @throws InterpreterException 
	 */
	public static CmlInterpreter newInterpreter(PSource cmlSource) throws InterpreterException {
		return new VanillaCmlInterpreter(cmlSource);
	}
}
