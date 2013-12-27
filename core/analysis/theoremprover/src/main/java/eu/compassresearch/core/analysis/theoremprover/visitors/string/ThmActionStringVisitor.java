package eu.compassresearch.core.analysis.theoremprover.visitors.string;

import java.util.Iterator;
import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.PPattern;

import eu.compassresearch.ast.actions.AChaosAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.ADivAction;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AGuardedAction;
import eu.compassresearch.ast.actions.AHidingAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.AInternalChoiceAction;
import eu.compassresearch.ast.actions.AMuAction;
import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AStmAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.core.analysis.theoremprover.utils.ThmProcessUtil;

@SuppressWarnings("serial")
public class ThmActionStringVisitor extends
QuestionAnswerCMLAdaptor<ThmVarsContext, String> {

	final private QuestionAnswerCMLAdaptor<ThmVarsContext, String> thmStringVisitor;
	
	public ThmActionStringVisitor(ThmStringVisitor thmStringVisitor) {
		this.thmStringVisitor = thmStringVisitor;
	}
	
	
	public String caseASkipAction(ASkipAction a, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.skip;
	}
		
	public String caseAStopAction(AStopAction a, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.stop;
	}
		
	public String caseAChaosAction(AChaosAction a, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.chaos;
	}
		
	public String caseADivAction(ADivAction a, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.div;
	}
		
//	public String caseAWaitAction(AWaitAction a, ThmVarsContext vars) throws AnalysisException{
//		return ThmProcessUtil.wait + a.getExpression().apply(thmStringVisitor, vars);
//	}
		
	public String caseACommunicationAction(ACommunicationAction a, ThmVarsContext vars) throws AnalysisException{
		
			
		StringBuilder params = new StringBuilder();
		for( PCommunicationParameter p: a.getCommunicationParameters())
		{
			if(p instanceof AReadCommunicationParameter)
			{
				AReadCommunicationParameter cp = (AReadCommunicationParameter) p;
				PPattern patt = cp.getPattern();
				if(patt instanceof AIdentifierPattern)
				{
					AIdentifierPattern ip = (AIdentifierPattern) patt;
					params.append("?(");
					params.append(ip.getName().toString());
					vars.addBVar(ip.getName());
					params.append(")");
				}
				patt.getDefinitions();
			}
			else if (p instanceof AWriteCommunicationParameter)
			{
				AWriteCommunicationParameter cp = (AWriteCommunicationParameter) p;
				params.append("!");
				params.append(cp.getExpression().apply(thmStringVisitor, vars));
				
			}else
			{
				params.append(".(");
				params.append(p.getExpression().apply(thmStringVisitor, vars));
				params.append(")");
			}
		}
			
		return "(" + a.getIdentifier().toString() + params.toString() + ThmProcessUtil.comm + a.getAction().apply(thmStringVisitor, vars) + ")";
	}
		
	public String caseAGuardedAction(AGuardedAction a, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.guardLeftBrack + a.getExpression().apply(thmStringVisitor, vars) + ThmProcessUtil.guardRightBrack + ThmProcessUtil.guardSep + a.getAction().apply(thmStringVisitor, vars);
	}
		
	public String caseASequentialCompositionAction(ASequentialCompositionAction a, ThmVarsContext vars) throws AnalysisException{
		return a.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.seqComp + a.getRight().apply(thmStringVisitor, vars);
	}
		
	public String caseAExternalChoiceAction(AExternalChoiceAction a, ThmVarsContext vars) throws AnalysisException{
		return a.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.extChoice + a.getRight().apply(thmStringVisitor, vars);
	}
		
	public String caseAInternalChoiceAction(AInternalChoiceAction a, ThmVarsContext vars) throws AnalysisException{
		return a.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.intChoice + a.getRight().apply(thmStringVisitor, vars);
	}
		
//	public String caseAInterruptAction(AInterruptAction a, ThmVarsContext vars) throws AnalysisException{
//		return a.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.interrupt + a.getRight().apply(thmStringVisitor, vars);
//	}
//		
//	public String caseATimedInterruptAction(ATimedInterruptAction a, ThmVarsContext vars) throws AnalysisException{
//		return a.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.timeIntLeft +  a.getTimeExpression().apply(thmStringVisitor, vars) + ThmProcessUtil.timeIntRight + a.getRight().apply(thmStringVisitor, vars);
//	}
//		
//	public String caseAUntimedTimeoutAction(AUntimedTimeoutAction a, ThmVarsContext vars) throws AnalysisException{
//		return a.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.timeout + a.getRight().apply(thmStringVisitor, vars);
//	}
//	
//	public String caseATimeoutAction(ATimeoutAction a, ThmVarsContext vars) throws AnalysisException{
//		return a.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.timeoutLeft +  a.getTimeoutExpression().apply(thmStringVisitor, vars) + ThmProcessUtil.timeoutRight + a.getRight().apply(thmStringVisitor, vars);
//	}
//	
	public String caseAHidingAction(AHidingAction a, ThmVarsContext vars) throws AnalysisException{
		String actStr = a.getLeft().apply(thmStringVisitor, vars);
		String chanStr = a.getChansetExpression().apply(thmStringVisitor, vars);
		return actStr + ThmProcessUtil.hiding + chanStr;
	}
	
//	public String caseAStartDeadlineAction(AStartDeadlineAction a, ThmVarsContext vars) throws AnalysisException{
//		return a.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.startsby + a.getExpression().apply(thmStringVisitor, vars);
//	}
//	
//	public String caseAEndDeadlineAction(AEndDeadlineAction a, ThmVarsContext vars) throws AnalysisException{
//		return a.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.endsby + a.getExpression().apply(thmStringVisitor, vars);	
//	}
//	
//	public String caseAChannelRenamingAction(AChannelRenamingAction a, ThmVarsContext vars) throws AnalysisException{
//		return a.getAction().apply(thmStringVisitor, vars) + a.getRenameExpression().apply(thmStringVisitor, vars);
//	}

	public String caseAMuAction(AMuAction a, ThmVarsContext vars) throws AnalysisException{

		StringBuilder idStr = new StringBuilder();
		for (Iterator<ILexIdentifierToken> itr = a.getIdentifiers().listIterator(); itr.hasNext(); ) {
			ILexIdentifierToken id = itr.next();
			
			idStr.append(id.getName().toString());
			//If there are remaining expressions, add a ","
			if(itr.hasNext()){	
				idStr.append(" ");
			}
		}
		
		StringBuilder actStr = new StringBuilder();
		for (PAction pa : a.getActions())
		{
			actStr.append(pa.apply(thmStringVisitor, vars));
		}
		return ThmProcessUtil.isaMu + idStr + ". " + actStr;
	}
	
	public String caseAReferenceAction(AReferenceAction a, ThmVarsContext vars) throws AnalysisException{

		StringBuilder argStr = new StringBuilder();
		LinkedList<PExp> args = a.getArgs();
		if (args.size() != 0)
		{
			argStr.append("(");
			for (Iterator<PExp> itr = a.getArgs().listIterator(); itr.hasNext(); ) {
				PExp e = itr.next();
				
				argStr.append(e.apply(thmStringVisitor, vars));
				//If there are remaining expressions, add a ","
				if(itr.hasNext()){	
					argStr.append(", ");
				}
			}
			argStr.append(")");
		}
		
		return a.getName().toString() + argStr.toString();
	}
	
	
//	public String caseAAction(PAction a, ThmVarsContext vars) throws AnalysisException{
//		return "(*unknown action not handled*)";	
//	}

//	public String caseASequentialCompositionReplicatedAction(ASequentialCompositionReplicatedAction a, ThmVarsContext vars) throws AnalysisException{
////			a.getReplicatedAction();
////			a.getReplicationDeclaration();
//		//TODO: NOT YET HANDLED
//		return ThmProcessUtil.repActNotHandled;
//	}
//	
//	public String caseAExternalChoiceReplicatedAction(AExternalChoiceReplicatedAction a, ThmVarsContext vars) throws AnalysisException{
//		//TODO: NOT YET HANDLED
//		return ThmProcessUtil.repActNotHandled;
//	}
//	
//	public String caseAInternalChoiceReplicatedAction(AInternalChoiceReplicatedAction a, ThmVarsContext vars) throws AnalysisException{
//		//TODO: NOT YET HANDLED
//		return ThmProcessUtil.repActNotHandled;
//	}
//	
//	public String caseACommonInterleavingReplicatedAction(ACommonInterleavingReplicatedAction a, ThmVarsContext vars) throws AnalysisException{
////          [namesetExpression]:VarsetExpression
//		//TODO: NOT YET HANDLED
//		return ThmProcessUtil.repActNotHandled;
//	}
//	
//	public String caseAInterleavingReplicatedAction(AInterleavingReplicatedAction a, ThmVarsContext vars) throws AnalysisException{
////          [namesetExpression]:VarsetExpression
//		//TODO: NOT YET HANDLED
//		return ThmProcessUtil.repActNotHandled;
//	}
//	
//	public String caseAGeneralisedParallelismReplicatedAction(AGeneralisedParallelismReplicatedAction a, ThmVarsContext vars) throws AnalysisException{
////          [chansetExpression]:VarsetExpression
////          [namesetExpression]:VarsetExpression
//		//TODO: NOT YET HANDLED
//		return ThmProcessUtil.repActNotHandled;
//	}
//	
//	public String caseAAlphabetisedParallelismReplicatedAction(AAlphabetisedParallelismReplicatedAction a, ThmVarsContext vars) throws AnalysisException{
////          [namesetExpression]:VarsetExpression
////          [chansetExpression]:VarsetExpression
//		//TODO: NOT YET HANDLED
//		return ThmProcessUtil.repActNotHandled;
//	}
//	
//	public String caseASynchronousParallelismReplicatedAction(ASynchronousParallelismReplicatedAction a, ThmVarsContext vars) throws AnalysisException{
//	//          [namesetExpression]:VarsetExpression
//		//TODO: NOT YET HANDLED
//		return ThmProcessUtil.repActNotHandled;
//	}
	
	public String caseAInterleavingParallelAction(AInterleavingParallelAction a, ThmVarsContext vars) throws AnalysisException{
		return a.getLeftAction().apply(thmStringVisitor, vars) + ThmProcessUtil.interleave  + a.getRightAction().apply(thmStringVisitor, vars);
	}
	
	public String caseAGeneralisedParallelismParallelAction(AGeneralisedParallelismParallelAction a, ThmVarsContext vars) throws AnalysisException{
		String left = a.getLeftAction().apply(thmStringVisitor, vars);
		String right = a.getRightAction().apply(thmStringVisitor, vars);
		String chExp = a.getChansetExpression().apply(thmStringVisitor, vars);
		
		return left + "[|" + chExp +"|]" + right;
	}
	
//	public String caseAAlphabetisedParallelismParallelAction(AAlphabetisedParallelismParallelAction a, ThmVarsContext vars) throws AnalysisException{
//	
//		String left = a.getLeftAction().apply(thmStringVisitor, vars);
//		String right = a.getRightAction().apply(thmStringVisitor, vars);
//		String leftChExp = a.getLeftChansetExpression().apply(thmStringVisitor, vars);
//		String rightChExp = a.getRightChansetExpression().apply(thmStringVisitor, vars);
//		
//		return left + "[" + leftChExp + "||" + rightChExp +"]" + right;
//	}
	
	
	public String caseAStmAction(AStmAction a, ThmVarsContext vars) throws AnalysisException{
		return a.getStatement().apply(thmStringVisitor, vars);
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
