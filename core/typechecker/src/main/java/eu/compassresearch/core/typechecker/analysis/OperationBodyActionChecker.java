package eu.compassresearch.core.typechecker.analysis;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.ast.statements.ASubclassResponsibilityStm;

import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AStmAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;

public class OperationBodyActionChecker extends AnswerCMLAdaptor<Boolean>
{

	private ITypeIssueHandler issueHandler;

	public OperationBodyActionChecker(ITypeIssueHandler issueHandler)
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
		issueHandler.addTypeError(node, TypeErrorMessages.REACTIVE_CONSTRUCTS_IN_OP_NOT_ALLOWED,""
				+ node);
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
	public Boolean caseASubclassResponsibilityStm(
			ASubclassResponsibilityStm node) throws AnalysisException
	{
		return true;
	}

	@Override
	public Boolean caseAStmAction(AStmAction node) throws AnalysisException
	{
		return true;// FIXME we should properly check the inner statement stuff for action wrappers
	}

}
