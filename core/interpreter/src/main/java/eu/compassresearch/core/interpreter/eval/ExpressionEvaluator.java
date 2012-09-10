package eu.compassresearch.core.interpreter.eval;


import org.overture.ast.node.INode;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.analysis.QuestionAnswerAdaptor;
import eu.compassresearch.ast.expressions.PExp;
import eu.compassresearch.core.interpreter.runtime.Context;
import eu.compassresearch.transformation.CmlAstToOvertureAst;

public class ExpressionEvaluator extends QuestionAnswerAdaptor<Context, Value> {

	@Override
	public Value defaultPExp(PExp node, Context question)
			throws AnalysisException {
		
		CmlAstToOvertureAst transform = new CmlAstToOvertureAst();
        INode ovtNode = transform.defaultINode(node);
        
        org.overture.interpreter.eval.ExpressionEvaluator ovtExpEval = 
        		new org.overture.interpreter.eval.ExpressionEvaluator();  
        
        org.overture.interpreter.runtime.Context quest = 
        		new org.overture.interpreter.runtime.Context(null,"",null);
        quest.setThreadState(null, null);
        
//        org.overture.typechecker.TypeCheckInfo quest = new org.overture.typechecker.TypeCheckInfo(
//            question.env.getOvertureEnv());
        
        Value value = null; 
        
        try
        {
        	value = ovtNode.apply(ovtExpEval, quest);	
        } catch (org.overture.ast.analysis.AnalysisException e)
        {
        	throw new AnalysisException(e.getMessage());
        }
        
        return value;
	}
	
}
