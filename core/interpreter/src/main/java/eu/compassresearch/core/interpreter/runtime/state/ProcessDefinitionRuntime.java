package eu.compassresearch.core.interpreter.runtime.state;

import org.overture.ast.definitions.SClassDefinition;
import org.overture.interpreter.assistant.IInterpreterAssistantFactory;
import org.overture.interpreter.runtime.state.SClassDefinitionRuntime;
import org.overture.interpreter.util.Delegate;

public class ProcessDefinitionRuntime extends SClassDefinitionRuntime
{

	public ProcessDefinitionRuntime(IInterpreterAssistantFactory af,
			SClassDefinition def, String name)
	{
		super(af, def);
		delegate = new Delegate(name, af.createPDefinitionAssistant().getDefinitions(def));
	}

}
