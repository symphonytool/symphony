package eu.compassresearch.core.typechecker.visitors;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.actions.AAlphabetisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.core.typechecker.analysis.AssignmentCollector;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;

/**
 * Does a depth first search and finds all parallel actions and checks that the assignments made by the sub actions are
 * mentioned in the namesets. If not then a warning is issued.
 * 
 * @author kel
 */
public class ParallelNamesetChecker extends DepthFirstAnalysisCMLAdaptor
{

	private final ITypeIssueHandler issueHandler;

	public ParallelNamesetChecker(ITypeIssueHandler issueHandler)
	{
		this.issueHandler = issueHandler;
	}

	@Override
	public void caseAInterleavingParallelAction(AInterleavingParallelAction node)
			throws AnalysisException
	{
		// extract sub-stuff
		PAction leftAction = node.getLeftAction();
		PVarsetExpression leftNamesetExp = node.getLeftNamesetExpression();
		PAction rightAction = node.getRightAction();
		PVarsetExpression rightnamesetExp = node.getRightNamesetExpression();

		// type-check the namesets
		if (leftNamesetExp != null)
		{
			PType nameSetType = leftNamesetExp.getType();
			AssignmentCollector.checkAssignments(nameSetType, leftAction, issueHandler);
		}

		if (rightnamesetExp != null)
		{
			PType nameSetType = rightnamesetExp.getType();
			AssignmentCollector.checkAssignments(nameSetType, rightAction, issueHandler);
		}

	}

	@Override
	public void caseAGeneralisedParallelismParallelAction(
			AGeneralisedParallelismParallelAction node)
			throws AnalysisException
	{

		// Extract sub-stuff
		PAction leftAction = node.getLeftAction();
		PVarsetExpression leftNamesetExp = node.getLeftNamesetExpression();
		PAction rightAction = node.getRightAction();
		PVarsetExpression rightnamesetExp = node.getRightNamesetExpression();

		PType leftName = null;
		PType rightName = null;
		// type-check the namesets
		if (leftNamesetExp != null)
		{
			leftName = leftNamesetExp.getType();
			AssignmentCollector.checkAssignments(leftName, leftAction, issueHandler);
		}

		if (rightnamesetExp != null)
		{
			rightName = rightnamesetExp.getType();
			AssignmentCollector.checkAssignments(rightName, rightAction, issueHandler);
		}

	}

	@Override
	public void caseAAlphabetisedParallelismParallelAction(
			AAlphabetisedParallelismParallelAction node)
			throws AnalysisException
	{

		PAction leftAction = node.getLeftAction();
		PVarsetExpression leftNameSet = node.getLeftNamesetExpression();

		PAction rightAction = node.getRightAction();
		PVarsetExpression rightNameSet = node.getLeftNamesetExpression();

		if (leftNameSet != null)
		{
			PType leftNameType = leftNameSet.getType();
			AssignmentCollector.checkAssignments(leftNameType, leftAction, issueHandler);
		}

		if (rightNameSet != null)
		{
			PType rightNameType = rightNameSet.getType();
			AssignmentCollector.checkAssignments(rightNameType, rightAction, issueHandler);
		}

	}

}
