package eu.compassresearch.core.interpreter.eval;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.PVarsetExpression;

@SuppressWarnings("serial")
public class CmlEvaluator extends QuestionAnswerCMLAdaptor<Context, Value> {

	private QuestionAnswerCMLAdaptor<Context, Value> exp;
	private QuestionAnswerCMLAdaptor<Context, Value> def;
			
	private void initialize()
	{
		exp = new CmlExpressionEvaluator();
		def = new CmlDeclAndDefEvaluator(this);
	}
		
	public CmlEvaluator()
	{
		initialize();
	}
				
	@Override
	public Value defaultPExp(PExp node, Context question)
			throws AnalysisException {
		
		return node.apply(exp,question);
	}
	
	@Override
	public Value defaultPVarsetExpression(PVarsetExpression node,
			Context question) throws AnalysisException {

		return node.apply(exp,question);
	}
		
	@Override
	public Value defaultPDefinition(PDefinition node, Context question)
			throws AnalysisException {
		
		return node.apply(this.def,question);
	}
		
}
