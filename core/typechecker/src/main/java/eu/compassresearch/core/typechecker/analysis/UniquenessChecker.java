package eu.compassresearch.core.typechecker.analysis;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AClassInvariantDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexNameList;
import org.overture.ast.statements.PStm;
import org.overture.ast.typechecker.NameScope;
import org.overture.typechecker.Environment;
import org.overture.typechecker.assistant.definition.PDefinitionListAssistantTC;

import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.definitions.AActionClassDefinition;
import eu.compassresearch.core.typechecker.CmlTypeCheckerAssistantFactory;
import eu.compassresearch.core.typechecker.DefinitionList;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;
import eu.compassresearch.core.typechecker.environment.FlatCheckedGlobalEnvironment;
import eu.compassresearch.core.typechecker.environment.FlatStrictCheckEnvironment;

/**
 * @author kel & cb
 */
public class UniquenessChecker extends DepthFirstAnalysisCMLAdaptor
{
	private static final CmlTypeCheckerAssistantFactory AF = new CmlTypeCheckerAssistantFactory();

	public static boolean apply(DefinitionList lp,
			ITypeIssueHandler issueHandler)
	{

		UniquenessChecker lv = new UniquenessChecker(lp);
		try
		{
			lp.apply(lv);
		} catch (AnalysisException e)
		{
		}
		return lv.ok;
	}

	boolean ok = true;
	private Environment env;

	private UniquenessChecker(List<PDefinition> lp)
	{
		this.env = new FlatCheckedGlobalEnvironment(AF, lp, NameScope.NAMESANDSTATE){
			protected void dupHideCheck(List<PDefinition> list, NameScope scope)
			{
				List<PDefinition> filteredList= new LinkedList<PDefinition>(list);
				for (PDefinition def : list)
				{
					if(def instanceof AClassInvariantDefinition)
					{
						filteredList.remove(def);
					}
				}
				LexNameList allnamesDeepSearch = PDefinitionListAssistantTC.getVariableNames(filteredList);
				
				LexNameList	allnames = new LexNameList(allnamesDeepSearch);
				
				for (ILexNameToken n : allnamesDeepSearch)
				{
					if(n.parent().parent() instanceof SClassDefinition)
					{
						allnames.remove(n);
					}
				}
				
				//FIXME: this list ends up only having all global definitions + all process names. Is this what have to be unique. Class uniqueness is enforced elsewhere

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
		;
	}

	@Override
	public void caseAActionClassDefinition(AActionClassDefinition node)
			throws AnalysisException
	{
		new FlatStrictCheckEnvironment(AF, node.getDefinitions(), this.env, NameScope.NAMESANDSTATE);
	}

	@Override
	public void defaultPExp(PExp node) throws AnalysisException
	{
	}

	@Override
	public void defaultPStm(PStm node) throws AnalysisException
	{
	}

	@Override
	public void defaultSClassDefinition(SClassDefinition node)
			throws AnalysisException
	{
	}

}