package eu.compassresearch.core.analysis.pog.visitors;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.pog.visitors.IVariableSubVisitor;
import org.overture.pog.visitors.Substitution;
import org.overture.pog.visitors.VariableSubVisitor;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.ABracketedExp;

public class CmlVarSubVisitor extends QuestionAnswerCMLAdaptor<Substitution, PExp> implements IVariableSubVisitor {

	VariableSubVisitor ovtVisitor = new VariableSubVisitor(this);
	
	@Override
	public PExp caseABracketedExp(ABracketedExp node, Substitution question)
			throws AnalysisException {
		PExp r = node.getExpression().apply(this, question);
		node.setExpression(r);
		return node;

	}
	
	@Override
	public PExp createNewReturnValue(INode node, Substitution question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PExp createNewReturnValue(Object node, Substitution question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public PExp defaultINode(INode node, Substitution question)
			throws AnalysisException {
		return node.apply(ovtVisitor,question);

	}

}
