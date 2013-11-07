package eu.compassresearch.core.analysis.theoremprover.visitors.string;

import java.util.Iterator;
import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.process.AAlphabetisedParallelismProcess;
import eu.compassresearch.ast.process.AEndDeadlineProcess;
import eu.compassresearch.ast.process.AExternalChoiceProcess;
import eu.compassresearch.ast.process.AGeneralisedParallelismProcess;
import eu.compassresearch.ast.process.AHidingProcess;
import eu.compassresearch.ast.process.AInterleavingProcess;
import eu.compassresearch.ast.process.AInternalChoiceProcess;
import eu.compassresearch.ast.process.AInterruptProcess;
import eu.compassresearch.ast.process.AReferenceProcess;
import eu.compassresearch.ast.process.ASequentialCompositionProcess;
import eu.compassresearch.ast.process.AStartDeadlineProcess;
import eu.compassresearch.ast.process.ASynchronousParallelismProcess;
import eu.compassresearch.ast.process.ATimedInterruptProcess;
import eu.compassresearch.ast.process.ATimeoutProcess;
import eu.compassresearch.ast.process.AUntimedTimeoutProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.analysis.theoremprover.utils.ThmProcessUtil;

@SuppressWarnings("serial")
public class ThmProcessStringVisitor extends
QuestionAnswerCMLAdaptor<ThmVarsContext, String> {

	final private QuestionAnswerCMLAdaptor<ThmVarsContext, String> thmStringVisitor;
	
	public ThmProcessStringVisitor(ThmStringVisitor thmStringVisitor) {
		this.thmStringVisitor = thmStringVisitor;
	}
	
	public String caseASequentialCompositionProcess(ASequentialCompositionProcess p, ThmVarsContext vars) throws AnalysisException{

		return p.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.seqComp + p.getRight().apply(thmStringVisitor, vars);
	}
	
	public String caseAExternalChoiceProcess(AExternalChoiceProcess p, ThmVarsContext vars) throws AnalysisException{
		
		return p.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.extChoice + p.getRight().apply(thmStringVisitor, vars);
	}
	
	public String caseAInternalChoiceProcess(AInternalChoiceProcess p, ThmVarsContext vars) throws AnalysisException{
		
		return p.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.intChoice + p.getRight().apply(thmStringVisitor, vars);
	}
	
	public String caseAInterleavingProcess(AInterleavingProcess p, ThmVarsContext vars) throws AnalysisException{

		return p.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.interleave  + p.getRight().apply(thmStringVisitor, vars);
	}
	
	public String caseASynchronousParallelismProcess(ASynchronousParallelismProcess p, ThmVarsContext vars) throws AnalysisException{

		return p.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.syncParallel  + p.getRight().apply(thmStringVisitor, vars);
	}
	
	public String caseAGeneralisedParallelismProcess(AGeneralisedParallelismProcess p, ThmVarsContext vars) throws AnalysisException{

		String left = p.getLeft().apply(thmStringVisitor, vars);
		String right = p.getRight().apply(thmStringVisitor, vars);
		String chExp = p.getChansetExpression().apply(thmStringVisitor, vars);
		
		return left + "[|" + chExp +"|]" + right;
	}
	
//	public String caseAAlphabetisedParallelismProcess(AAlphabetisedParallelismProcess p, ThmVarsContext vars) throws AnalysisException{
//
//		String left = p.getLeft().apply(thmStringVisitor, vars);
//		String right = p.getRight().apply(thmStringVisitor, vars);
//		String leftChExp = p.getLeftChansetExpression().apply(thmStringVisitor, vars);
//		String rightChExp = p.getRightChansetExpression().apply(thmStringVisitor, vars);
//		
//		return left + "[" + leftChExp + "||" + rightChExp +"]" + right;
//	}
	
//	public String caseAInterruptProcess(AInterruptProcess p, ThmVarsContext vars) throws AnalysisException{
//
//		return p.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.interrupt + p.getRight().apply(thmStringVisitor, vars);
//	}
//	
//	public String caseATimedInterruptProcess(ATimedInterruptProcess p, ThmVarsContext vars) throws AnalysisException{
//		
//		String left = p.getLeft().apply(thmStringVisitor, vars);
//		String expr = p.getTimeExpression().apply(thmStringVisitor, vars);
//		String right = p.getRight().apply(thmStringVisitor, vars);
//
//		return left + ThmProcessUtil.timeIntLeft + expr + ThmProcessUtil.timeIntRight + right;
//	}
//	
//	public String caseAUntimedTimeoutProcess(AUntimedTimeoutProcess p, ThmVarsContext vars) throws AnalysisException{
//		
//		String left =  p.getLeft().apply(thmStringVisitor, vars);
//		String right = p.getRight().apply(thmStringVisitor, vars);
//
//		return left + ThmProcessUtil.timeout + right;
//	}
//	
//	public String caseATimeoutProcess(ATimeoutProcess p, ThmVarsContext vars) throws AnalysisException{
//
//		String left = p.getLeft().apply(thmStringVisitor, vars);
//		String expr = p.getTimeoutExpression().apply(thmStringVisitor, vars);
//		String right = p.getRight().apply(thmStringVisitor, vars);
//
//		return left + ThmProcessUtil.timeoutLeft +  expr + ThmProcessUtil.timeoutRight + right;
//	}
	
	public String caseAHidingProcess(AHidingProcess p, ThmVarsContext vars) throws AnalysisException{

		String actStr = p.getLeft().apply(thmStringVisitor, vars);
		String chanStr = p.getChansetExpression().apply(thmStringVisitor, vars);
		return actStr + ThmProcessUtil.hiding + chanStr;
	}
	
//	public String caseAStartDeadlineProcess(AStartDeadlineProcess p, ThmVarsContext vars) throws AnalysisException{
//
//		String left = p.getLeft().apply(thmStringVisitor, vars);
//		String expr = p.getExpression().apply(thmStringVisitor, vars);		
//		return left + ThmProcessUtil.startsby + expr;
//	}
//	
//	public String caseAEndDeadlineProcess(AEndDeadlineProcess p, ThmVarsContext vars) throws AnalysisException{
//
//		String left = p.getLeft().apply(thmStringVisitor, vars);
//		String expr = p.getExpression().apply(thmStringVisitor, vars);	
//		return left + ThmProcessUtil.endsby + expr;	
//	}
	
	public String caseAReferenceProcess(AReferenceProcess p, ThmVarsContext vars) throws AnalysisException{

		StringBuilder argStr = new StringBuilder();
		LinkedList<PExp> args = p.getArgs();
		if (args.size() != 0)
		{
			argStr.append("(");
			for (Iterator<PExp> itr = p.getArgs().listIterator(); itr.hasNext(); ) {
				PExp e = itr.next();
				
				argStr.append(e.apply(thmStringVisitor, vars));
				//If there are remaining expressions, add a ","
				if(itr.hasNext()){	
					argStr.append(", ");
				}
			}
			argStr.append(")");
		}
		return p.getProcessName().toString() + argStr.toString();
	}
	
	public String casePProcess(PProcess p, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.procNotHandled;
	}
	
	@Override
	public String createNewReturnValue(INode arg0, ThmVarsContext arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createNewReturnValue(Object arg0, ThmVarsContext arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}
}
