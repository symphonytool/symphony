package eu.compassresearch.core.interpreter.eval;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.UndefinedValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.core.interpreter.runtime.CmlContext;

@SuppressWarnings("serial")
public class CmlDeclAndDefEvaluator extends
		QuestionAnswerCMLAdaptor<CmlContext, Value> {

	private CmlEvaluator parentInterpreter; 

	public CmlDeclAndDefEvaluator(CmlEvaluator parentInterpreter)
	{
		this.parentInterpreter = parentInterpreter;
	}
	
	@Override
	public Value caseAStateDefinition(AStateDefinition node,
			CmlContext question) throws AnalysisException {
		
		for(PDefinition def : node.getStateDefs())
		{
			def.apply(this,question);
		}
		
		return null;
	}

	@Override
	public Value caseAAssignmentDefinition(AAssignmentDefinition node,
			CmlContext question) throws AnalysisException {
		
		Value expValue = null;
		if(node.getExpression() != null)
			expValue = node.getExpression().apply(parentInterpreter,question);
		else
			expValue = new UndefinedValue();
		
		question.put(node.getName(), expValue);
		return expValue;
	}

}

