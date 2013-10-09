package eu.compassresearch.core.typechecker;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.ASubclassResponsibilityAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.SStatementAction;
import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

public class OperationBodyActionChecker extends AnswerCMLAdaptor<Boolean>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TypeIssueHandler issueHandler;

	public OperationBodyActionChecker(TypeIssueHandler issueHandler)
	{
		this.issueHandler = issueHandler;
	}

	@Override
	public Boolean createNewReturnValue(INode node) throws AnalysisException
	{
		return true;
	}

	@Override
	public Boolean createNewReturnValue(Object node) throws AnalysisException
	{
		return true;
	}

	@Override
	public Boolean defaultPAction(PAction node) throws AnalysisException
	{
		issueHandler.addTypeError(node, TypeErrorMessages.REACTIVE_CONSTRUCTS_IN_OP_NOT_ALLOWED.customizeMessage(""
				+ node));
		return false;
	}

	@Override
	public Boolean caseASequentialCompositionAction(
			ASequentialCompositionAction node) throws AnalysisException
	{
		return true;
	}

	@Override
	public Boolean caseASkipAction(ASkipAction node) throws AnalysisException
	{
		return true;
	}

	@Override
	public Boolean caseASubclassResponsibilityAction(
			ASubclassResponsibilityAction node) throws AnalysisException
	{
		return true;
	}

	@Override
	public Boolean defaultSStatementAction(SStatementAction node)
			throws AnalysisException
	{
		return true;
	}

}
