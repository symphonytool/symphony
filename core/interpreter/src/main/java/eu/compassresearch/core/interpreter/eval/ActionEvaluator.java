package eu.compassresearch.core.interpreter.eval;

import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.analysis.QuestionAnswerAdaptor;
import eu.compassresearch.core.interpreter.runtime.Context;
import eu.compassresearch.core.interpreter.values.ProcessValue;

public class ActionEvaluator extends QuestionAnswerAdaptor<Context, Value> {

	private CmlEvaluator parentInterpreter; 
	
	public ActionEvaluator(CmlEvaluator parentInterpreter)
	{
		this.parentInterpreter = parentInterpreter;
	}
	
	
	@Override
	public Value caseACommunicationAction(ACommunicationAction node,
			Context question) throws AnalysisException {
	
		
		System.out.println(node.getIdentifier());
		
		return node.getAction().apply(parentInterpreter,question);
	}
	
	@Override
	public Value caseASkipAction(ASkipAction node, Context question)
			throws AnalysisException {
		
		System.out.println("Skip");
		
		return new ProcessValue();
	}
	
}
