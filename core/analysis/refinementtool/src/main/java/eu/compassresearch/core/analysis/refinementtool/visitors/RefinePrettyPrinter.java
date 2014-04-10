package eu.compassresearch.core.analysis.refinementtool.visitors;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.ABracketedExp;

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
		
		return "!("+node.getExpression().toString()+")";
	}

}
