package eu.compassresearch.core.interpreter.eval;

import org.overture.interpreter.values.NaturalValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.analysis.QuestionAnswerAdaptor;
import eu.compassresearch.ast.definitions.AAssignmentDefinition;
import eu.compassresearch.ast.definitions.AStateParagraphDefinition;
import eu.compassresearch.ast.definitions.PDefinition;
import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.core.interpreter.runtime.CMLContext;

public class CmlDeclAndDefEvaluator extends
		QuestionAnswerAdaptor<CMLContext, Value> {

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
		
		LexNameToken nt = new LexNameToken("Default",node.getName());
		question.put(nt, expValue);
		return expValue;
	}

}
