package eu.compassresearch.core.typechecker.analysis;

import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.typechecker.NameScope;
import org.overture.typechecker.Environment;

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
@SuppressWarnings("serial")
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
		this.env = new FlatCheckedGlobalEnvironment(AF, lp, NameScope.NAMESANDSTATE);
	}

	@Override
	public void caseAActionClassDefinition(AActionClassDefinition node)
			throws AnalysisException
	{
		new FlatStrictCheckEnvironment(AF, node.getDefinitions(), this.env, NameScope.NAMESANDSTATE);
	}

}