package eu.compassresearch.core.typechecker.weeding;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.SOperationDefinition;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.typechecker.DefinitionList;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;

/**
 * @author kel & cb
 */
@SuppressWarnings("serial")
public class OperationBodyValidater extends DepthFirstAnalysisCMLAdaptor
{
	public static boolean apply(DefinitionList lp,
			ITypeIssueHandler issueHandler)
	{

		OperationBodyValidater lv = new OperationBodyValidater(issueHandler);
		// for (PSource s : lp)
		// {
		// if (s != null)
		try
		{
			lp.apply(lv);
		} catch (AnalysisException e)
		{
		}
		// }
		return lv.ok;
	}

	private ITypeIssueHandler issueHandler;
	boolean ok = true;

	private OperationBodyValidater(ITypeIssueHandler issueHandler)
	{
		this.issueHandler = issueHandler;
	}

	@Override
	public void defaultInPAction(PAction node) throws AnalysisException
	{
		if (node.getAncestor(SOperationDefinition.class) != null)
		{
			issueHandler.addTypeError(node, "Actions not allowed in operations");
			ok = false;
		}
	}

	@Override
	public void defaultInPProcess(PProcess node) throws AnalysisException
	{
		if (node.getAncestor(SOperationDefinition.class) != null)
		{
			issueHandler.addTypeError(node, "Processes not allowed in operations");
			ok = false;
		}
	}

}