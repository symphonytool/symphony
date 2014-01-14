package eu.compassresearch.core.interpreter;

import java.util.List;

import org.overture.ast.definitions.PDefinition;

import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviorFactory;

public class VanillaInterpreterFactory implements InterpreterFactory 
{
	
	 CmlBehaviorFactory cmlBehaviorFactory = new DefaultCmlBehaviorFactory();
	
	/* (non-Javadoc)
	 * @see eu.compassresearch.core.interpreter.InterpreterFactory#setDefaultCmlBehaviourFactory(eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviorFactory)
	 */
	@Override
	public  void setDefaultCmlBehaviourFactory(CmlBehaviorFactory factory)
	{
		cmlBehaviorFactory = factory;
	}

	/* (non-Javadoc)
	 * @see eu.compassresearch.core.interpreter.InterpreterFactory#newInterpreter(java.util.List)
	 */
	@Override
	public  CmlInterpreter newInterpreter(List<PDefinition> definitions)
			throws CmlInterpreterException
	{
		VanillaCmlInterpreter interpreter = new VanillaCmlInterpreter(definitions, newDefaultConfig());
		CmlContextFactory.configureDBGPReader(interpreter);
		return interpreter;
	}

	/* (non-Javadoc)
	 * @see eu.compassresearch.core.interpreter.InterpreterFactory#newInterpreter(java.util.List, eu.compassresearch.core.interpreter.Config)
	 */
	@Override
	public  CmlInterpreter newInterpreter(List<PDefinition> definitions,
			Config config) throws CmlInterpreterException
	{
		VanillaCmlInterpreter interpreter = new VanillaCmlInterpreter(definitions, config);
		CmlContextFactory.configureDBGPReader(interpreter);
		return interpreter;
	}

	/* (non-Javadoc)
	 * @see eu.compassresearch.core.interpreter.InterpreterFactory#newDefaultConfig()
	 */
	@Override
	public  Config newDefaultConfig()
	{
		return new Config(false, cmlBehaviorFactory);
	}

	/* (non-Javadoc)
	 * @see eu.compassresearch.core.interpreter.InterpreterFactory#newDefaultConfig(boolean)
	 */
	@Override
	public  Config newDefaultConfig(boolean filterTockEvents)
	{
		return new Config(filterTockEvents,cmlBehaviorFactory);
	}
}
