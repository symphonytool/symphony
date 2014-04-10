package eu.compassresearch.core.analysis.refinementtool.visitors;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AUntypedDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.expressions.AUnaryMinusUnaryExp;
import org.overture.ast.expressions.AUnaryPlusUnaryExp;
import org.overture.ast.expressions.AUndefinedExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AUnionPattern;
import org.overture.ast.statements.AWhileStm;
import org.overture.ast.types.AUndefinedType;
import org.overture.ast.types.AUnionType;
import org.overture.ast.types.AUnknownType;
import org.overture.ast.types.AUnresolvedType;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.actions.AUntimedTimeoutAction;
import eu.compassresearch.ast.actions.AValParametrisation;
import eu.compassresearch.ast.actions.AVresParametrisation;
import eu.compassresearch.ast.actions.AWaitAction;
import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.ABracketedExp;
import eu.compassresearch.ast.expressions.AUnionVOpVarsetExpression;
import eu.compassresearch.ast.expressions.AUnresolvedPathExp;
import eu.compassresearch.ast.process.AUntimedTimeoutProcess;
import eu.compassresearch.ast.statements.AUnresolvedObjectDesignator;
import eu.compassresearch.ast.statements.AUnresolvedStateDesignator;

public class RefinePrettyPrinter extends QuestionAnswerCMLAdaptor<Integer, String> {
	public static String tabs(Integer n) {
		String s = "";
		for (int i = 0; i < n; i++) {
			s+="\t";
		}
		return s;
	}
	@Override
	public String createNewReturnValue(INode arg0, Integer arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createNewReturnValue(Object arg0, Integer arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String caseABracketedExp(ABracketedExp node, Integer question)
			throws AnalysisException {
		return "(" + node.getExpression().apply(this, question) + ")";
	}

	@Override
	public String caseAWriteCommunicationParameter(
			AWriteCommunicationParameter node, Integer question)
			throws AnalysisException {
		
		return "!("+node.getExpression().apply(this,0)+")";
	}
	/* (non-Javadoc)
	 * @see eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor#caseAWaitAction(eu.compassresearch.ast.actions.AWaitAction, java.lang.Object)
	 */
	@Override
	public String caseAWaitAction(AWaitAction node, Integer question)
			throws AnalysisException {
		return tabs(question)+"Wait "+node.getExpression().apply(this,0);
	}
	/* (non-Javadoc)
	 * @see eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor#caseAVresParametrisation(eu.compassresearch.ast.actions.AVresParametrisation, java.lang.Object)
	 */
	@Override
	public String caseAVresParametrisation(AVresParametrisation node,
			Integer question) throws AnalysisException {
		return "vres "+node.getDeclaration().apply(this,0);
	}
	/* (non-Javadoc)
	 * @see org.overture.ast.analysis.QuestionAnswerAdaptor#caseAWhileStm(org.overture.ast.statements.AWhileStm, java.lang.Object)
	 */
	@Override
	public String caseAWhileStm(AWhileStm node, Integer question)
			throws AnalysisException {
		return tabs(question)+"while "+node.getExp().apply(this,0)+" do \n"+node.getStatement().apply(this,question+1);
	}

	/* (non-Javadoc)
	 * @see eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor#caseAUntimedTimeoutProcess(eu.compassresearch.ast.process.AUntimedTimeoutProcess, java.lang.Object)
	 */
	@Override
	public String caseAUntimedTimeoutProcess(AUntimedTimeoutProcess node,
			Integer question) throws AnalysisException {
		return tabs(question)+node.getLeft().apply(this,0)+" [_> "+node.getRight().apply(this,0);
	}
	/* (non-Javadoc)
	 * @see eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor#caseAUntimedTimeoutAction(eu.compassresearch.ast.actions.AUntimedTimeoutAction, java.lang.Object)
	 */
	@Override
	public String caseAUntimedTimeoutAction(AUntimedTimeoutAction node,
			Integer question) throws AnalysisException {
		return tabs(question)+node.getLeft().apply(this,0)+" [_> "+node.getRight().apply(this,0);
	}
	/* (non-Javadoc)
	 * @see eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor#caseAValParametrisation(eu.compassresearch.ast.actions.AValParametrisation, java.lang.Object)
	 */
	@Override
	public String caseAValParametrisation(AValParametrisation node,
			Integer question) throws AnalysisException {
		return "val "+node.getDeclaration().apply(this,0);
	}

	/* (non-Javadoc)
	 * @see org.overture.ast.analysis.QuestionAnswerAdaptor#caseAUnionType(org.overture.ast.types.AUnionType, java.lang.Object)
	 */
	@Override
	public String caseAUnionType(AUnionType node, Integer question)
			throws AnalysisException {
		LinkedList<PType> types = node.getTypes();
		if (types.size() > 0) {
			String s = types.get(0).apply(this,0);
			for (int i = 1; i < types.size(); i++) {
				s += " | " + types.get(i).apply(this,0);
			}
			return s;
		} else {
			throw new AnalysisException("Union type of size 0");
		}
	}
	

	/* (non-Javadoc)
	 * @see org.overture.ast.analysis.QuestionAnswerAdaptor#caseAUnionPattern(org.overture.ast.patterns.AUnionPattern, java.lang.Object)
	 */
	@Override
	public String caseAUnionPattern(AUnionPattern node, Integer question)
			throws AnalysisException {
		return tabs(question) + node.toString();
	}

	/* (non-Javadoc)
	 * @see org.overture.ast.analysis.QuestionAnswerAdaptor#caseAValueDefinition(org.overture.ast.definitions.AValueDefinition, java.lang.Object)
	 */
	@Override
	public String caseAValueDefinition(AValueDefinition node, Integer question)
			throws AnalysisException {
		String s = tabs(question);
		if (node.getAccess() != null)
			s += node.getAccess().apply(this,0)+" ";
		
		s += node.getPattern().apply(this,0);
		
		if (node.getType() != null)
			s += ": "+node.getType().apply(this,0);
		
		s += " = "+node.getExpression().apply(this,0);
		return s;
	}
	/* (non-Javadoc)
	 * @see org.overture.ast.analysis.QuestionAnswerAdaptor#defaultPExp(org.overture.ast.expressions.PExp, java.lang.Object)
	 */
	@Override
	public String defaultPExp(PExp node, Integer question)
			throws AnalysisException {
		return tabs(question)+node.toString();
	}
	
}
