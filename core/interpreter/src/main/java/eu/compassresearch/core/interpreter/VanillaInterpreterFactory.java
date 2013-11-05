package eu.compassresearch.core.interpreter;

import java.util.List;

import org.overture.ast.definitions.PDefinition;

import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;

public final class VanillaInterpreterFactory
{

	/**
	 * create an instance of the Vanilla interpreter.
	 * 
	 * @param definitions
	 *            - List of parsed and type-checked CML source to interpret
	 * @throws CmlInterpreterException
	 */
	public static CmlInterpreter newInterpreter(List<PDefinition> definitions)
			throws CmlInterpreterException
	{
		return new VanillaCmlInterpreter(definitions);
	}

	// /**
	// * create an instance of the Vanilla interpreter.
	// *
	// * @param cmlSource
	// * - A single parsed and type-checked CML source to interpret
	// * @throws CmlInterpreterException
	// */
	// public static CmlInterpreter newInterpreter(PSource cmlSource)
	// throws CmlInterpreterException
	// {
	// return new VanillaCmlInterpreter(cmlSource);
	// }

}
