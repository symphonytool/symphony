package eu.compassresearch.core.analysis.pog.visitors;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.pog.pub.IPOContextStack;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.AInterVOpVarsetExpression;
import eu.compassresearch.ast.expressions.ASubVOpVarsetExpression;
import eu.compassresearch.ast.expressions.AUnionVOpVarsetExpression;
import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligationList;

public abstract class VarSetVisitor extends
		QuestionAnswerCMLAdaptor<IPOContextStack, CmlProofObligationList>
{

	/*
	 * IProofObligationList obligations = new ProofObligationList();
	 * obligations.addAll(node.getLeft().apply(mainVisitor, question));
	 * obligations.addAll(node.getRight().apply(mainVisitor, question)); return obligations;
	 */

	public VarSetVisitor(ProofObligationGenerator parentPog)
	{
	}

	@Override
	public CmlProofObligationList caseAInterVOpVarsetExpression(
			AInterVOpVarsetExpression node, IPOContextStack question)
			throws AnalysisException
	{

		CmlProofObligationList obligations = new CmlProofObligationList();
		obligations.addAll(node.getLeft().apply(this, question));
		obligations.addAll(node.getRight().apply(this, question));

		return obligations;
	}

	@Override
	public CmlProofObligationList caseASubVOpVarsetExpression(
			ASubVOpVarsetExpression node, IPOContextStack question)
			throws AnalysisException
	{

		CmlProofObligationList obligations = new CmlProofObligationList();
		obligations.addAll(node.getLeft().apply(this, question));
		obligations.addAll(node.getRight().apply(this, question));

		return obligations;
	}

	@Override
	public CmlProofObligationList caseAUnionVOpVarsetExpression(
			AUnionVOpVarsetExpression node, IPOContextStack question)
			throws AnalysisException
	{

		CmlProofObligationList obligations = new CmlProofObligationList();
		obligations.addAll(node.getLeft().apply(this, question));
		obligations.addAll(node.getRight().apply(this, question));

		return obligations;
	}

	@Override
	public CmlProofObligationList createNewReturnValue(INode node,
			IPOContextStack question)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CmlProofObligationList createNewReturnValue(Object node,
			IPOContextStack question)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
