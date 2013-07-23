package eu.compassresearch.core.analysis.modelchecker.visitors;

import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AActionDefinition;

@SuppressWarnings("serial")
public class NewCMLModelcheckerVisitor extends
		QuestionAnswerCMLAdaptor<String, StringBuilder> {

	@Override
	public StringBuilder caseAActionDefinition(AActionDefinition node,
			String question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAActionDefinition(node, question);
	}

}
