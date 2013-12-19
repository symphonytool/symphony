package eu.compassresearch.core.typechecker.assistant;

import org.overture.ast.definitions.PDefinition;
import org.overture.ast.types.PType;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;
import org.overture.typechecker.assistant.definition.PDefinitionAssistantTC;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.SBlockDefinition;

public class CmlPDefinitionAssistantTC extends PDefinitionAssistantTC
{

	public CmlPDefinitionAssistantTC(ITypeCheckerAssistantFactory af)
	{
		super(af);
	}

	@Override
	public PType getType(PDefinition def)
	{
		if (def instanceof AProcessDefinition)
		{
			return null;// skip
		}
		else if (def instanceof SBlockDefinition) {
			return null;
		}

		return super.getType(def);
	}

}
