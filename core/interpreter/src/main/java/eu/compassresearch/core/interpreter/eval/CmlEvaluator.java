package eu.compassresearch.core.interpreter.eval;

import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.analysis.QuestionAnswerAdaptor;
import eu.compassresearch.ast.analysis.intf.IQuestionAnswer;
import eu.compassresearch.ast.expressions.PExp;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.runtime.Context;
import eu.compassresearch.core.interpreter.scheduler.CMLProcess;
import eu.compassresearch.core.interpreter.values.ProcessValue;


@SuppressWarnings("serial")
public class CmlEvaluator extends QuestionAnswerAdaptor<Context, Value> {

	private IQuestionAnswer<Context, Value> exp;
	private IQuestionAnswer<Context, CMLProcess> prc; 
	private IQuestionAnswer<Context, Value> act;
	
			
	private void initialize()
	{
		prc = new ProcessEvaluator();
		act = new ActionEvaluator(this);
		exp = new ExpressionEvaluator();
	}
		
	public CmlEvaluator()
	{
		initialize();
	}
				
	@Override
	public Value defaultPAction(PAction node, Context question)
			throws AnalysisException {
		return node.apply(act,question);
	}
	
	@Override
	public Value defaultPExp(PExp node, Context question)
			throws AnalysisException {
		
		return node.apply(exp,question);
	}
		
	@Override
	public Value defaultPProcess(PProcess node, Context question)
			throws AnalysisException {
		
		CMLProcess process = node.apply(prc,question);
		
		return new ProcessValue(process,question);
	}
		
}
