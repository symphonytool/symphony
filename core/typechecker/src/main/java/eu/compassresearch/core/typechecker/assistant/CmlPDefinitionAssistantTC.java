package eu.compassresearch.core.typechecker.assistant;

import org.overture.ast.definitions.PDefinition;
import org.overture.ast.types.PType;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;
import org.overture.typechecker.assistant.definition.PDefinitionAssistantTC;

public class CmlPDefinitionAssistantTC extends PDefinitionAssistantTC
{

	public CmlPDefinitionAssistantTC(ITypeCheckerAssistantFactory af)
	{
		super(af);
	}

	@Override
	public PType getType(PDefinition def)
	{
		//FIXME: handle CML type lookup
		PType t =  super.getType(def);
		if(t==null)
		{
			System.out.println("----------------------------------CML should be handled!!!");
		}
		return t;
	}

}
