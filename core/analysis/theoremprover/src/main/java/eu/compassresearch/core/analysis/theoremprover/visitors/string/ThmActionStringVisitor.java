package eu.compassresearch.core.analysis.theoremprover.visitors.string;

import java.util.Iterator;
import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.PPattern;

import eu.compassresearch.ast.actions.AAlphabetisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AAlphabetisedParallelismReplicatedAction;
import eu.compassresearch.ast.actions.AChannelRenamingAction;
import eu.compassresearch.ast.actions.AChaosAction;
import eu.compassresearch.ast.actions.ACommonInterleavingReplicatedAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.ADivAction;
import eu.compassresearch.ast.actions.AEndDeadlineAction;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AExternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismReplicatedAction;
import eu.compassresearch.ast.actions.AGuardedAction;
import eu.compassresearch.ast.actions.AHidingAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.AInterleavingReplicatedAction;
import eu.compassresearch.ast.actions.AInternalChoiceAction;
import eu.compassresearch.ast.actions.AInternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AInterruptAction;
import eu.compassresearch.ast.actions.AMuAction;
import eu.compassresearch.ast.actions.AParametrisedAction;
import eu.compassresearch.ast.actions.AParametrisedInstantiatedAction;
import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASequentialCompositionReplicatedAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AStartDeadlineAction;
import eu.compassresearch.ast.actions.AStmAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.actions.ATimedInterruptAction;
import eu.compassresearch.ast.actions.ATimeoutAction;
import eu.compassresearch.ast.actions.AUntimedTimeoutAction;
import eu.compassresearch.ast.actions.AWaitAction;
import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.core.analysis.theoremprover.utils.ThmProcessUtil;

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
	
	public String caseAInterleavingParallelAction(AInterleavingParallelAction a, ThmVarsContext vars) throws AnalysisException{
		return a.getLeftAction().apply(thmStringVisitor, vars) + ThmProcessUtil.interleave  + a.getRightAction().apply(thmStringVisitor, vars);
	}
	
	public String caseAGeneralisedParallelismParallelAction(AGeneralisedParallelismParallelAction a, ThmVarsContext vars) throws AnalysisException{
		String left = a.getLeftAction().apply(thmStringVisitor, vars);
		String right = a.getRightAction().apply(thmStringVisitor, vars);
		String chExp = a.getChansetExpression().apply(thmStringVisitor, vars);
		
		return left + "[|" + chExp +"|]" + right;
	}

	public String caseAAlphabetisedParallelismParallelAction(
			AAlphabetisedParallelismParallelAction node, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.undefined;
	}
	
	public String caseAAlphabetisedParallelismReplicatedAction(
			AAlphabetisedParallelismReplicatedAction node, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.undefined;
	}

	public String caseAChannelRenamingAction(AChannelRenamingAction node, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.undefined;
	}
	
	public String caseACommonInterleavingReplicatedAction(
			ACommonInterleavingReplicatedAction node, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.undefined;
	}

	public String caseAEndDeadlineAction(AEndDeadlineAction node, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.undefined;
	}
	
	public String caseAExternalChoiceReplicatedAction(
			AExternalChoiceReplicatedAction node, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.undefined;
	}
	
	public String caseAGeneralisedParallelismReplicatedAction(
			AGeneralisedParallelismReplicatedAction node, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.undefined;
	}

	public String caseAInterleavingReplicatedAction(
			AInterleavingReplicatedAction node, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.undefined;
	}
	
	public String caseAInternalChoiceReplicatedAction(
			AInternalChoiceReplicatedAction node, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.undefined;
	}

	public String caseAInterruptAction(AInterruptAction node, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.undefined;
	}
	
	public String caseAParametrisedAction(AParametrisedAction node, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.undefined;
	}

	public String caseAParametrisedInstantiatedAction(
			AParametrisedInstantiatedAction node, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.undefined;
	}
	
	public String caseASequentialCompositionReplicatedAction(
			ASequentialCompositionReplicatedAction node, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.undefined;
	}

	public String caseAStartDeadlineAction(AStartDeadlineAction node, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.undefined;
	}
	
	public String caseATimedInterruptAction(ATimedInterruptAction node, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.undefined;
	}

	public String caseATimeoutAction(ATimeoutAction node, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.undefined;
	}
	
	public String caseAUntimedTimeoutAction(AUntimedTimeoutAction node, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.undefined;
	}

	public String caseAWaitAction(AWaitAction a, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.undefined;
	}
	
	public String caseAHidingAction(AHidingAction a, ThmVarsContext vars) throws AnalysisException{
		String actStr = a.getLeft().apply(thmStringVisitor, vars);
		String chanStr = a.getChansetExpression().apply(thmStringVisitor, vars);
		return actStr + ThmProcessUtil.hiding + chanStr;
	}
	
	
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
