package eu.compassresearch.core.interpreter.eval;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.lex.LexNameToken;
import org.overture.interpreter.values.UndefinedValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AStateParagraphDefinition;
import eu.compassresearch.core.interpreter.api.CMLContext;

public class CmlDeclAndDefEvaluator extends
		QuestionAnswerCMLAdaptor<CMLContext, Value> {

	private CmlEvaluator parentInterpreter; 

	public CmlDeclAndDefEvaluator(CmlEvaluator parentInterpreter)
	{
		this.parentInterpreter = parentInterpreter;
	}
	
	@Override
	public Value caseAStateParagraphDefinition(AStateParagraphDefinition node,
			CMLContext question) throws AnalysisException {
		
		for(PDefinition def : node.getStateDefs())
		{
			def.apply(this,question);
		}
		
		return null;
	}

	@Override
	public Value caseAAssignmentDefinition(AAssignmentDefinition node,
			CMLContext question) throws AnalysisException {
		
		Value expValue = null;
		if(node.getExpression() != null)
			expValue = node.getExpression().apply(parentInterpreter,question);
		else
			expValue = new UndefinedValue();
		LexNameToken nt = new LexNameToken("Default",node.getName());
		question.put(nt, expValue);
		return expValue;
	}

}
