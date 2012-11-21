package eu.compassresearch.core.interpreter.eval;

import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.Value;


import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;

public class CmlExpressionEvaluator extends QuestionAnswerCMLAdaptor<Context, Value>
{
	//private ExpressionEvaluator vdmExpressionEvaluator;
	//private CML2VDMExpressionEvaluator vdmExpressionEvaluator = new CML2VDMExpressionEvaluator();
	
	
	
    
//    CmlAstToOvertureAst                               transform  = new CmlAstToOvertureAst(
//                                                                     null, null);
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
