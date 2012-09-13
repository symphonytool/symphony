package eu.compassresearch.core.interpreter.eval;

import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.analysis.QuestionAnswerAdaptor;
import eu.compassresearch.ast.analysis.intf.IQuestionAnswer;
import eu.compassresearch.ast.definitions.PDefinition;
import eu.compassresearch.ast.expressions.PExp;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.runtime.CMLContext;
import eu.compassresearch.core.interpreter.scheduler.CMLProcess;
import eu.compassresearch.core.interpreter.values.ProcessValue;


@SuppressWarnings("serial")
public class CmlEvaluator extends QuestionAnswerAdaptor<CMLContext, Value> {

	private IQuestionAnswer<CMLContext, Value> exp;
	private IQuestionAnswer<CMLContext, CMLProcess> prc; 
	private IQuestionAnswer<CMLContext, Value> act;
	private IQuestionAnswer<CMLContext, Value> def;
			
	private void initialize()
	{
		prc = new ProcessEvaluator(this);
		act = new ActionEvaluator(this);
		exp = new CmlExpressionEvaluator();
		def = new CmlDeclAndDefEvaluator(this);
	}
		
	public CmlEvaluator()
	{
		initialize();
	}
				
	@Override
	public Value defaultPAction(PAction node, CMLContext question)
			throws AnalysisException {
		return node.apply(act,question);
	}
	
	@Override
	public Value defaultPExp(PExp node, CMLContext question)
			throws AnalysisException {
		
		return node.apply(exp,question);
	}
		
	@Override
	public Value defaultPProcess(PProcess node, CMLContext question)
			throws AnalysisException {
		
		CMLProcess process = node.apply(prc,question);
		
		return new ProcessValue(process,question);
	}
	
	@Override
	public Value defaultPDefinition(PDefinition node, CMLContext question)
			throws AnalysisException {
		
		return node.apply(this.def,question);
	}
		
}
