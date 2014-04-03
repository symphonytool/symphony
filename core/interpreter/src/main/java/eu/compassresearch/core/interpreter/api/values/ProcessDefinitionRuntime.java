package eu.compassresearch.core.interpreter.api.values;

import org.overture.ast.definitions.SClassDefinition;
import org.overture.interpreter.assistant.IInterpreterAssistantFactory;
import org.overture.interpreter.runtime.state.SClassDefinitionRuntime;
import org.overture.interpreter.util.Delegate;

class ProcessDefinitionRuntime extends SClassDefinitionRuntime
{

	public ProcessDefinitionRuntime(IInterpreterAssistantFactory af,
			SClassDefinition def, String name)
	{
		super(af, def);
		delegate = new Delegate(name, af.createPDefinitionAssistant().getDefinitions(def));
	}

}
