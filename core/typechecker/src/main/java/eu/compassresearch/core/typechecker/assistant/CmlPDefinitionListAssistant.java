package eu.compassresearch.core.typechecker.assistant;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.overture.ast.definitions.PDefinition;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;
import org.overture.typechecker.assistant.definition.PDefinitionAssistantTC;
import org.overture.typechecker.assistant.definition.PDefinitionListAssistantTC;

import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;

public class CmlPDefinitionListAssistant extends PDefinitionListAssistantTC
{

	public CmlPDefinitionListAssistant(ITypeCheckerAssistantFactory af)
	{
		super(af);
	}
	

	public  Set<PDefinition> findMatches(List<PDefinition> definitions,
			ILexNameToken name)
	{

		Set<PDefinition> set = new HashSet<PDefinition>();

		for (PDefinition d : singleDefinitions(definitions))
		{
			if ((PDefinitionAssistantTC.isFunctionOrOperation(d)|| d instanceof AChannelNameDefinition || d instanceof AChansetDefinition || d instanceof AActionDefinition)
					&& d.getName().matches(name))
			{
				set.add(d);
			}
		}

		return set;
	}
}
