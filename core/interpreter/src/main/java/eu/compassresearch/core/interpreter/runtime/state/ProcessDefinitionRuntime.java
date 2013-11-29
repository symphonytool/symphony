package eu.compassresearch.core.interpreter.runtime.state;

import org.overture.ast.definitions.SClassDefinition;
import org.overture.interpreter.runtime.state.SClassDefinitionRuntime;
import org.overture.interpreter.util.Delegate;
import org.overture.typechecker.assistant.definition.PDefinitionAssistantTC;

public class ProcessDefinitionRuntime extends SClassDefinitionRuntime
{

	public ProcessDefinitionRuntime(SClassDefinition def,String name)
	{
		super(def);
		delegate = new Delegate(name, PDefinitionAssistantTC.getDefinitions(def));
	}

}
