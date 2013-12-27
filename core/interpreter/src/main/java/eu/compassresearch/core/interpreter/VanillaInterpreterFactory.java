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
		VanillaCmlInterpreter interpreter = new VanillaCmlInterpreter(definitions, newDefaultConfig());
		CmlContextFactory.configureDBGPReader(interpreter);
		return interpreter;
	}

	/**
	 * create an instance of the Vanilla interpreter.
	 * 
	 * @param definitions
	 *            - List of parsed and type-checked CML source to interpret
	 * @param config
	 *            the configuration that the interpreter should use
	 * @throws CmlInterpreterException
	 */
	public static CmlInterpreter newInterpreter(List<PDefinition> definitions,
			Config config) throws CmlInterpreterException
	{
		VanillaCmlInterpreter interpreter = new VanillaCmlInterpreter(definitions, config);
		CmlContextFactory.configureDBGPReader(interpreter);
		return interpreter;
	}

	public static Config newDefaultConfig()
	{
		return new Config(false);
	}

	public static Config newDefaultConfig(boolean filterTockEvents)
	{
		return new Config(filterTockEvents);
	}
}
