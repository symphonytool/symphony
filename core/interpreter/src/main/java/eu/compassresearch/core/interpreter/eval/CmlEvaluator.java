package eu.compassresearch.core.interpreter.eval;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.analysis.intf.ICMLQuestionAnswer;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.api.CMLContext;
import eu.compassresearch.core.interpreter.scheduler.CMLProcess;
import eu.compassresearch.core.interpreter.values.ProcessValue;

@SuppressWarnings("serial")
public class CmlEvaluator extends QuestionAnswerCMLAdaptor<CMLContext, Value> {

	private ICMLQuestionAnswer<CMLContext, Value> exp;
	private ICMLQuestionAnswer<CMLContext, CMLProcess> prc; 
	private ICMLQuestionAnswer<CMLContext, Value> act;
	private ICMLQuestionAnswer<CMLContext, Value> def;
			
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
