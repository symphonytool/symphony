package eu.compassresearch.core.typechecker.version2;

import java.util.ArrayList;
import java.util.List;

import org.overture.ast.definitions.PDefinition;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexNameList;
import org.overture.ast.typechecker.NameScope;
import org.overture.typechecker.Environment;
import org.overture.typechecker.EnvironmentSearchStrategy;
import org.overture.typechecker.FlatCheckedEnvironment;
import org.overture.typechecker.TypeChecker;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;
import org.overture.typechecker.assistant.definition.PDefinitionListAssistantTC;

import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;


public class CmlFlatCheckedEnvironment extends FlatCheckedEnvironment
{
	private List<AChannelDefinition> channelDef ;
	
	public CmlFlatCheckedEnvironment(ITypeCheckerAssistantFactory af,
			PDefinition one, Environment env, NameScope scope)
	{
		super(af, one, env, scope); 
	}

	public CmlFlatCheckedEnvironment(ITypeCheckerAssistantFactory af,
			List<PDefinition> definitions, NameScope scope,
			EnvironmentSearchStrategy ess)
	{
		super(af, definitions, scope, ess);
	}

	public CmlFlatCheckedEnvironment(ITypeCheckerAssistantFactory af,
			List<PDefinition> definitions, Environment env, NameScope scope)
	{
		super(af, definitions, env, scope);
	}
	
	@Override
	protected void dupHideCheck(List<PDefinition> list, NameScope scope)
	{	
		ArrayList<PDefinition> channelDefs = new ArrayList<PDefinition>();
		
		for (PDefinition def : list)
		{
			if(def instanceof AChannelDefinition){
				channelDefs.add(def);
			}
		}
		
		LexNameList chanNames = PDefinitionListAssistantTC.getVariableNames(channelDefs);
		LexNameList names = PDefinitionListAssistantTC.getVariableNames(list);
		names.removeAll(chanNames);
		
		findDuplicates(scope, chanNames);
		findDuplicates(scope, names);
	}

	private void findDuplicates(NameScope scope, LexNameList allnames)
	{
		for (ILexNameToken n1: allnames)
		{
			LexNameList done = new LexNameList();

			for (ILexNameToken n2: allnames)
			{
				if (n1 != n2 && n1.equals(n2) && !done.contains(n1))
				{
					TypeChecker.report(3426, TypeErrorMessages.DUPLICATE_DEFINITION.customizeMessage(n1
							+ " "
							+  n1.getLocation(), n2
							+ "  "
							+ n2.getLocation()), n1.getLocation());
					
					done.add(n1);
				}
			}

			if (outer != null)
			{
				// We search for any scoped name (ie. the first), but then check
				// the scope matches what we can see. If we pass scope to findName
				// it throws errors if the name does not match the scope.

				PDefinition def = outer.findName(n1, NameScope.NAMESANDSTATE);

				// TODO: RWL: This is not sound, however the behaviour below is not sound 
				// in case def.getNameScope is null.
				if (def != null && def.getNameScope() == null) def.setNameScope(NameScope.GLOBAL);
				
				if (def != null && def.getLocation() != n1.getLocation() &&
					def.getNameScope().matches(scope))
				{
					// Reduce clutter for names in the same module/class
					String message = null;

					if (def.getLocation().getFile().equals(n1.getLocation().getFile()))
					{
						message = def.getName() + " " + def.getLocation().toShortString() +
							" hidden by " +	n1.getFullName();
					}
					else
					{
						message = def.getName() + " " + def.getLocation() +
							" hidden by " + n1.getFullName();
					}

					TypeChecker.warning(5008, message, n1.getLocation());
				}
			}
		}
	}

	public List<AChannelDefinition> getChannelDef()
	{
		return channelDef;
	}
}
