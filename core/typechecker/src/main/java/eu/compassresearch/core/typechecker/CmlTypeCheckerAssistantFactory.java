package eu.compassresearch.core.typechecker;

import org.overture.typechecker.assistant.TypeCheckerAssistantFactory;
import org.overture.typechecker.assistant.definition.PDefinitionAssistantTC;

import eu.compassresearch.core.typechecker.assistant.CmlPDefinitionAssistantTC;

public class CmlTypeCheckerAssistantFactory extends TypeCheckerAssistantFactory
{
	static
	{
		// FIXME: remove this when conversion to factory obtained assistants are completed.
		//init(new AstAssistantFactory());
		init(new CmlTypeCheckerAssistantFactory());
	}

	@Override
	public PDefinitionAssistantTC createPDefinitionAssistantTC()
	{
		return new CmlPDefinitionAssistantTC(this);
	}
}
