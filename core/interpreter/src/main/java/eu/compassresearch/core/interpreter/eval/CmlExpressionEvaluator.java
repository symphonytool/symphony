package eu.compassresearch.core.interpreter.eval;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.interpreter.eval.ExpressionEvaluator;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.scheduler.BasicSchedulableThread;
import org.overture.interpreter.scheduler.InitThread;
import org.overture.interpreter.values.CPUValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.PCMLExp;

public class CmlExpressionEvaluator extends QuestionAnswerCMLAdaptor<Context, Value>
{
	class VdmExpressionEvaluator extends ExpressionEvaluator {
		
		@Override
		public Value defaultPExp(PExp node, Context question)
				throws AnalysisException {
		
			if(node instanceof PCMLExp)
				return defaultPCMLExp((PCMLExp)node,question);
			else
				return node.apply(this,question);
		}
	}
	
	private VdmExpressionEvaluator vdmExpEvaluator =  new VdmExpressionEvaluator();

	public CmlExpressionEvaluator()
	{
		InitThread initThread = new InitThread(Thread.currentThread());
        BasicSchedulableThread.setInitialThread(initThread);
        
     // TODO For now we diable VDM typechecking, since something is not
//      // working
//      Settings.dynamictypechecks = false;
	}
	
	@Override
	public Value defaultPExp(PExp node, Context question)
			throws AnalysisException {
		question.setThreadState(null, CPUValue.vCPU);
		return vdmExpEvaluator.defaultPExp(node,question);
	}
	
//	@Override
//	public Value defaultPCMLExp(PCMLExp node, Context question)
//			throws AnalysisException {
//		
//		if(node instanceof PExp)
//		{
//			question.setThreadState(null, CPUValue.vCPU);
//			return vdmExpEvaluator.defaultPExp(node,question);
//		}
//		else
//			return super.defaultPCMLExp(node, question);
//	}
	
//    org.overture.interpreter.eval.ExpressionEvaluator ovtExpEval = new org.overture.interpreter.eval.ExpressionEvaluator();
//    
//    @Override
//    public Value defaultPExp(PExp node, CMLContext question)
//        throws AnalysisException
//      {
//        
//        INode ovtNode = transform.defaultINode(node);
//        
//        /** The initial execution context. */
//        // RootContext initialContext = new StateContext(
//        // new LexLocation(), "global environment");
//        // initialContext.setThreadState(null, CPUValue.vCPU);
//        
//        // TODO For now we diable VDM typechecking, since something is not
//        // working
//        Settings.dynamictypechecks = false;
//        
//        Value value = null;
//        
//        try
//          {
//            InitThread initThread = new InitThread(Thread.currentThread());
//            BasicSchedulableThread.setInitialThread(initThread);
//            
//            org.overture.interpreter.runtime.Context evalContext = question
//                .getOvertureContext();
//            // new org.overture.interpreter.runtime.Context(location,
//            // "process expression", initialContext);
//            evalContext.setThreadState(null, CPUValue.vCPU);
//            // LexNameToken dName = new LexNameToken("Default", "d", location);
//            // evalContext.putNew(new NameValuePair(dName, new
//            // NaturalValue(10)));
//            
//            value = ovtNode.apply(ovtExpEval, evalContext);
//          } catch (org.overture.ast.analysis.AnalysisException e)
//          {
//            throw new AnalysisException(e.getMessage());
//          }
//        
//        return value;
//      }

}
