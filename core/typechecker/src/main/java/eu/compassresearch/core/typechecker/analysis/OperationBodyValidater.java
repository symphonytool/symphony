package eu.compassresearch.core.typechecker.analysis;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.SOperationDefinition;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.typechecker.DefinitionList;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;

/**
 * @author kel & cb
 */
public class OperationBodyValidater extends DepthFirstAnalysisCMLAdaptor
{
	public static boolean apply(DefinitionList lp,
			ITypeIssueHandler issueHandler)
	{

		OperationBodyValidater lv = new OperationBodyValidater(issueHandler);
		try
		{
			lp.apply(lv);
		} catch (AnalysisException e)
		{
		}
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
			issueHandler.addTypeError(node,TypeErrorMessages.ACTIONS_NOT_ALLOWED_IN_OPERATION);
			ok = false;
		}
	}

	@Override
	public void defaultInPProcess(PProcess node) throws AnalysisException
	{
		if (node.getAncestor(SOperationDefinition.class) != null)
		{
			issueHandler.addTypeError(node,TypeErrorMessages.PROCESSES_NOT_ALLOWED_IN_OPERATION,"");
			ok = false;
		}
	}

}