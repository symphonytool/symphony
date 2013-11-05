package eu.compassresearch.core.typechecker.environment;

import java.util.List;
import java.util.Vector;

import org.overture.ast.definitions.AClassInvariantDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexNameList;
import org.overture.ast.typechecker.NameScope;
import org.overture.typechecker.Environment;
import org.overture.typechecker.FlatCheckedEnvironment;
import org.overture.typechecker.TypeChecker;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;
import org.overture.typechecker.assistant.definition.PDefinitionListAssistantTC;

public class FlatStrictCheckEnvironment extends FlatCheckedEnvironment
{

	public FlatStrictCheckEnvironment(ITypeCheckerAssistantFactory af,
			List<PDefinition> definitions, Environment env, NameScope scope)
	{
		super(af, definitions, env, scope);
	}

	public FlatStrictCheckEnvironment(ITypeCheckerAssistantFactory af,
			List<PDefinition> definitions, NameScope scope)
	{
		super(af, definitions, scope);
	}

	protected void dubError(ILexNameToken n1)
	{
		TypeChecker.report(5007, "Duplicate definition: " + n1.getName(), n1.getLocation());
	}

	private List<PDefinition> filterClassInvs(List<PDefinition> list)
	{
		List<PDefinition> filtrered = new Vector<PDefinition>(list);
		for (PDefinition d : list)
		{
			if (d instanceof AClassInvariantDefinition)
			{
				filtrered.remove(d);
			}
		}
		return filtrered;
	}

	protected void dupHideCheck(List<PDefinition> list, NameScope scope)
	{
		LexNameList allnames = PDefinitionListAssistantTC.getVariableNames(filterClassInvs(list));

		for (ILexNameToken n1 : allnames)
		{
			LexNameList done = new LexNameList();

			for (ILexNameToken n2 : allnames)
			{
				if (n1 != n2 && n1.equals(n2) && !done.contains(n1))
				{
					dubError(n1);
					done.add(n1);
				}
			}

			if (outer != null)
			{
				// We search for any scoped name (ie. the first), but then check
				// the scope matches what we can see. If we pass scope to findName
				// it throws errors if the name does not match the scope.

				if (!outer.findMatches(n1).isEmpty())
				{
					dubError(n1);
				}

			}
		}
	}

}