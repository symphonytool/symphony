package eu.compassresearch.core.analysis.refinementtool.visitors;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.ast.statements.AAssignmentStm;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.expressions.ABracketedExp;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.statements.AActionStm;

public class RefinePrettyPrinter extends QuestionAnswerCMLAdaptor<Integer, String> {

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
	public String caseAActionDefinition(AActionDefinition node, Integer question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAActionDefinition(node, question);
	}

	@Override
	public String caseAActionProcess(AActionProcess node, Integer question)
			throws AnalysisException {
		return node.getAction().apply(this, question);
	}

	@Override
	public String caseAActionStm(AActionStm node, Integer question)
			throws AnalysisException {
		return node.getAction().apply(this, question);
	}

	@Override
	public String caseAAssignmentStm(AAssignmentStm node, Integer question)
			throws AnalysisException {
		return node.getTarget().toString() + " := " + node.getExp().toString();
	}

	

}
