package eu.compassresearch.core.analysis.theoremprover.visitors.string;

import java.util.Iterator;
import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.PPattern;

import eu.compassresearch.ast.actions.AAlphabetisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AAlphabetisedParallelismReplicatedAction;
import eu.compassresearch.ast.actions.AAssignmentCallStatementAction;
import eu.compassresearch.ast.actions.ABlockStatementAction;
import eu.compassresearch.ast.actions.ACallStatementAction;
import eu.compassresearch.ast.actions.ACasesStatementAction;
import eu.compassresearch.ast.actions.AChannelRenamingAction;
import eu.compassresearch.ast.actions.AChaosAction;
import eu.compassresearch.ast.actions.ACommonInterleavingReplicatedAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.ADivAction;
import eu.compassresearch.ast.actions.AElseIfStatementAction;
import eu.compassresearch.ast.actions.AEndDeadlineAction;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AExternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AForIndexStatementAction;
import eu.compassresearch.ast.actions.AForSequenceStatementAction;
import eu.compassresearch.ast.actions.AForSetStatementAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismReplicatedAction;
import eu.compassresearch.ast.actions.AGuardedAction;
import eu.compassresearch.ast.actions.AHidingAction;
import eu.compassresearch.ast.actions.AIfStatementAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.AInterleavingReplicatedAction;
import eu.compassresearch.ast.actions.AInternalChoiceAction;
import eu.compassresearch.ast.actions.AInternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AInterruptAction;
import eu.compassresearch.ast.actions.ALetStatementAction;
import eu.compassresearch.ast.actions.AMuAction;
import eu.compassresearch.ast.actions.AMultipleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.ANewStatementAction;
import eu.compassresearch.ast.actions.ANonDeterministicAltStatementAction;
import eu.compassresearch.ast.actions.ANonDeterministicDoStatementAction;
import eu.compassresearch.ast.actions.ANonDeterministicIfStatementAction;
import eu.compassresearch.ast.actions.ANotYetSpecifiedStatementAction;
import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.AReturnStatementAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASequentialCompositionReplicatedAction;
import eu.compassresearch.ast.actions.ASingleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.ASpecificationStatementAction;
import eu.compassresearch.ast.actions.AStartDeadlineAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.actions.ASynchronousParallelismParallelAction;
import eu.compassresearch.ast.actions.ASynchronousParallelismReplicatedAction;
import eu.compassresearch.ast.actions.ATimedInterruptAction;
import eu.compassresearch.ast.actions.ATimeoutAction;
import eu.compassresearch.ast.actions.AUntimedTimeoutAction;
import eu.compassresearch.ast.actions.AWaitAction;
import eu.compassresearch.ast.actions.AWhileStatementAction;
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
		
	public String caseASequentialCompositionProcess(AStopAction a, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.stop;
	}
		
	public String caseAChaosAction(AChaosAction a, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.chaos;
	}
		
	public String caseADivAction(ADivAction a, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.div;
	}
		
	public String caseAWaitAction(AWaitAction a, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.wait + a.getExpression().apply(thmStringVisitor, vars);
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
		
	public String caseAInterruptAction(AInterruptAction a, ThmVarsContext vars) throws AnalysisException{
		return a.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.interrupt + a.getRight().apply(thmStringVisitor, vars);
	}
		
	public String caseATimedInterruptAction(ATimedInterruptAction a, ThmVarsContext vars) throws AnalysisException{
		return a.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.timeIntLeft +  a.getTimeExpression().apply(thmStringVisitor, vars) + ThmProcessUtil.timeIntRight + a.getRight().apply(thmStringVisitor, vars);
	}
		
	public String caseAUntimedTimeoutAction(AUntimedTimeoutAction a, ThmVarsContext vars) throws AnalysisException{
		return a.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.timeout + a.getRight().apply(thmStringVisitor, vars);
	}
	
	public String caseATimeoutAction(ATimeoutAction a, ThmVarsContext vars) throws AnalysisException{
		return a.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.timeoutLeft +  a.getTimeoutExpression().apply(thmStringVisitor, vars) + ThmProcessUtil.timeoutRight + a.getRight().apply(thmStringVisitor, vars);
	}
	
	public String caseAHidingAction(AHidingAction a, ThmVarsContext vars) throws AnalysisException{
		String actStr = a.getLeft().apply(thmStringVisitor, vars);
		String chanStr = a.getChansetExpression().apply(thmStringVisitor, vars);
		return actStr + ThmProcessUtil.hiding + chanStr;
	}
	
	public String caseAStartDeadlineAction(AStartDeadlineAction a, ThmVarsContext vars) throws AnalysisException{
		return a.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.startsby + a.getExpression().apply(thmStringVisitor, vars);
	}
	
	public String caseAEndDeadlineAction(AEndDeadlineAction a, ThmVarsContext vars) throws AnalysisException{
		return a.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.endsby + a.getExpression().apply(thmStringVisitor, vars);	
	}
	
	public String caseAChannelRenamingAction(AChannelRenamingAction a, ThmVarsContext vars) throws AnalysisException{
		return a.getAction().apply(thmStringVisitor, vars) + a.getRenameExpression().apply(thmStringVisitor, vars);
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
	
	
	public String caseAAction(PAction a, ThmVarsContext vars) throws AnalysisException{
		return "(*unknown action not handled*)";	
	}

	public String caseASequentialCompositionReplicatedAction(ASequentialCompositionReplicatedAction a, ThmVarsContext vars) throws AnalysisException{
//			a.getReplicatedAction();
//			a.getReplicationDeclaration();
		//TODO: NOT YET HANDLED
		return ThmProcessUtil.repActNotHandled;
	}
	
	public String caseAExternalChoiceReplicatedAction(AExternalChoiceReplicatedAction a, ThmVarsContext vars) throws AnalysisException{
		//TODO: NOT YET HANDLED
		return ThmProcessUtil.repActNotHandled;
	}
	
	public String caseAInternalChoiceReplicatedAction(AInternalChoiceReplicatedAction a, ThmVarsContext vars) throws AnalysisException{
		//TODO: NOT YET HANDLED
		return ThmProcessUtil.repActNotHandled;
	}
	
	public String caseACommonInterleavingReplicatedAction(ACommonInterleavingReplicatedAction a, ThmVarsContext vars) throws AnalysisException{
//          [namesetExpression]:VarsetExpression
		//TODO: NOT YET HANDLED
		return ThmProcessUtil.repActNotHandled;
	}
	
	public String caseAInterleavingReplicatedAction(AInterleavingReplicatedAction a, ThmVarsContext vars) throws AnalysisException{
//          [namesetExpression]:VarsetExpression
		//TODO: NOT YET HANDLED
		return ThmProcessUtil.repActNotHandled;
	}
	
	public String caseAGeneralisedParallelismReplicatedAction(AGeneralisedParallelismReplicatedAction a, ThmVarsContext vars) throws AnalysisException{
//          [chansetExpression]:VarsetExpression
//          [namesetExpression]:VarsetExpression
		//TODO: NOT YET HANDLED
		return ThmProcessUtil.repActNotHandled;
	}
	
	public String caseAAlphabetisedParallelismReplicatedAction(AAlphabetisedParallelismReplicatedAction a, ThmVarsContext vars) throws AnalysisException{
//          [namesetExpression]:VarsetExpression
//          [chansetExpression]:VarsetExpression
		//TODO: NOT YET HANDLED
		return ThmProcessUtil.repActNotHandled;
	}
	
	public String caseASynchronousParallelismReplicatedAction(ASynchronousParallelismReplicatedAction a, ThmVarsContext vars) throws AnalysisException{
	//          [namesetExpression]:VarsetExpression
		//TODO: NOT YET HANDLED
		return ThmProcessUtil.repActNotHandled;
	}
	
	public String caseAInterleavingParallelAction(AInterleavingParallelAction a, ThmVarsContext vars) throws AnalysisException{
		return a.getLeftAction().apply(thmStringVisitor, vars) + ThmProcessUtil.interleave  + a.getRightAction().apply(thmStringVisitor, vars);
	}
	
	public String caseASynchronousParallelismParallelAction(ASynchronousParallelismParallelAction a, ThmVarsContext vars) throws AnalysisException{
		return a.getLeftAction().apply(thmStringVisitor, vars) + ThmProcessUtil.syncParallel  + a.getRightAction().apply(thmStringVisitor, vars);
	}
	
	public String caseAGeneralisedParallelismParallelAction(AGeneralisedParallelismParallelAction a, ThmVarsContext vars) throws AnalysisException{
		String left = a.getLeftAction().apply(thmStringVisitor, vars);
		String right = a.getRightAction().apply(thmStringVisitor, vars);
		String chExp = a.getChansetExpression().apply(thmStringVisitor, vars);
		
		return left + "[|" + chExp +"|]" + right;
	}
	
	public String caseAAlphabetisedParallelismParallelAction(AAlphabetisedParallelismParallelAction a, ThmVarsContext vars) throws AnalysisException{
	
		String left = a.getLeftAction().apply(thmStringVisitor, vars);
		String right = a.getRightAction().apply(thmStringVisitor, vars);
		String leftChExp = a.getLeftChansetExpression().apply(thmStringVisitor, vars);
		String rightChExp = a.getRightChansetExpression().apply(thmStringVisitor, vars);
		
		return left + "[" + leftChExp + "||" + rightChExp +"]" + right;
	}
	
	public String caseACallStatementAction(ACallStatementAction a, ThmVarsContext vars) throws AnalysisException{
		StringBuilder args = new StringBuilder();
		
		for (Iterator<PExp> itr = a.getArgs().listIterator(); itr.hasNext(); ) {
			PExp e = itr.next();
			
			args.append(e.apply(thmStringVisitor, vars));
			//If there are remaining expressions, add a ","
			if(itr.hasNext()){	
				args.append(", ");
			}
		}
		return a.getName().toString() + "(" + args.toString() + ")";		
		
	}
	
	public String caseABlockStatementActionion(ABlockStatementAction a, ThmVarsContext vars) throws AnalysisException{
	
		LinkedList<String> varsStr = new LinkedList<String>();
		StringBuilder assignStr = new StringBuilder();
		LinkedList<PDefinition> assigns = new LinkedList<PDefinition>();

		String blockStr = a.getAction().apply(thmStringVisitor, vars);
		if(a.getDeclareStatement() != null) 
		{
			assigns = a.getDeclareStatement().getAssignmentDefs();
			
			for (PDefinition pdef : assigns)
			{
				AAssignmentDefinition aDef = (AAssignmentDefinition) pdef;
				varsStr.add(aDef.getName().toString());
				if(aDef.getExpression() != null)
				{
					assignStr.append(aDef.getName().toString() + ":=" + aDef.getExpression().apply(thmStringVisitor, vars) + "; ");
				}
			}

			blockStr = assignStr.toString() + blockStr;
			for(String as : varsStr)
			{
				blockStr = "var " + as + "; " + blockStr + "; end " + as; 
			}
		}
		
		return "(" + blockStr + ")"; 
	}
	
	public String caseAIfStatementAction(AIfStatementAction a, ThmVarsContext vars) throws AnalysisException{
		String ifStr = a.getIfExp().apply(thmStringVisitor, vars);
		String thenStr = a.getThenStm().apply(thmStringVisitor, vars);	
		String elseStr = "Skip";
		if(a.getElseStm() != null)
		{
			elseStr = a.getElseStm().apply(thmStringVisitor, vars);	
		}
		String elseIfStr = "";
		String closingIf = "";
		LinkedList<AElseIfStatementAction> elseIf = a.getElseIf();
		for(AElseIfStatementAction e : elseIf)
		{
			elseIfStr = elseIfStr + "("+ e.apply(thmStringVisitor, vars);	
			closingIf = ")";
		}
		
		return thenStr + ThmProcessUtil.ifLeft + ifStr + ThmProcessUtil.ifRight + elseIfStr + elseStr + closingIf;
	}
	
	public String caseAElseIfStatementAction(AElseIfStatementAction a, ThmVarsContext vars) throws AnalysisException{
		String elseIfStr = a.getElseIf().apply(thmStringVisitor, vars);
		String thenStr = a.getThenStm().apply(thmStringVisitor, vars);		

		return thenStr + ThmProcessUtil.ifLeft + elseIfStr+ ThmProcessUtil.ifRight;
	}
	
	public String caseASingleGeneralAssignmentStatementAction(ASingleGeneralAssignmentStatementAction a, ThmVarsContext vars) throws AnalysisException{
	
		String aExp = a.getStateDesignator().apply(thmStringVisitor, vars);
		String exp = a.getExpression().apply(thmStringVisitor, vars);
		
		//Isabelle doesn't want state variables to have a $ on the lhs of an assignment.
		//TODO: This may be too harsh
		aExp = aExp.replace("$", "");
		
		return aExp + ThmProcessUtil.assign + exp;
	}
	
	public String caseAAssignmentCallStatementAction(AAssignmentCallStatementAction a, ThmVarsContext vars) throws AnalysisException{
		 String aExp = a.getDesignator().apply(thmStringVisitor, vars);
		 String callExp = a.getCall().apply(thmStringVisitor, vars);
//           
		//Isabelle doesn't want state variables to have a $ on the lhs of an assignment.
		//TODO: This may be too harsh
		aExp = aExp.replace("$", "");
		return aExp + ThmProcessUtil.assign + callExp;
	}
	
	public String caseAWhileStatementAction(AWhileStatementAction a, ThmVarsContext vars) throws AnalysisException{
		String cond = a.getCondition().apply(thmStringVisitor, vars);
		String actStr = a.getAction().apply(thmStringVisitor, vars);
//	        | {declare}  [assignmentDefs]:definition*
		
		return ThmProcessUtil.isaWhile  + ThmProcessUtil.opExpLeft + cond + ThmProcessUtil.isaDo  + ThmProcessUtil.opExpRight + actStr + ThmProcessUtil.isaOd;
	}
	
	public String caseANonDeterministicDoStatementAction(ANonDeterministicDoStatementAction a, ThmVarsContext vars) throws AnalysisException{
//	        [alternatives]:action.#Statement.nonDeterministicAlt*
		//TODO: NOT HANDLED
		return ThmProcessUtil.stmtNotHandled;
	}
	
	public String caseANonDeterministicIfStatementAction(ANonDeterministicIfStatementAction a, ThmVarsContext vars) throws AnalysisException{
//           [alternatives]:action.#Statement.nonDeterministicAlt*
		//TODO: NOT HANDLED
		return ThmProcessUtil.stmtNotHandled;
	}
	
	public String caseANotYetSpecifiedStatementAction(ANotYetSpecifiedStatementAction a, ThmVarsContext vars) throws AnalysisException{
//           [opname]:LexNameToken
//           [args]:exp*
		//TODO: NOT HANDLED
		return ThmProcessUtil.stmtNotHandled;
	}
	
	public String caseALetStatementAction(ALetStatementAction a, ThmVarsContext vars) throws AnalysisException{
		//[action]:action [localDefinitions]:definition*
		//TODO: NOT YET HANDLED
		return ThmProcessUtil.stmtNotHandled;
	}
	
	public String caseANonDeterministicAltStatementAction(ANonDeterministicAltStatementAction a, ThmVarsContext vars) throws AnalysisException{
//           [guard]:exp
//           [action]:action
		//TODO: NOT YET HANDLED
		return ThmProcessUtil.stmtNotHandled;
	}
	
	public String caseACasesStatementAction(ACasesStatementAction a, ThmVarsContext vars) throws AnalysisException{
//           [exp]:exp
//           [cases]:alternativeAction.case*
//           [others]:action
		//TODO: NOT YET HANDLED
		return ThmProcessUtil.stmtNotHandled;
	}
	
	public String caseAMultipleGeneralAssignmentStatementAction(AMultipleGeneralAssignmentStatementAction a, ThmVarsContext vars) throws AnalysisException{
		//TODO: NOT YET HANDLED
		return ThmProcessUtil.stmtNotHandled;
	}
	
	public String caseASpecificationStatementAction(ASpecificationStatementAction a, ThmVarsContext vars) throws AnalysisException{
//           [externals]:clause.external*
//           [precondition]:exp
//           [postcondition]:exp
		//MAY GEN LEMMA TO PROVE STATING FRAME NOT VIOLATED
		//TODO: NOT YET HANDLED
		return ThmProcessUtil.stmtNotHandled;
	}
	
	public String caseAReturnStatementAction(AReturnStatementAction a, ThmVarsContext vars) throws AnalysisException{
//           [exp]:exp
			//TODO: NOT YET HANDLED
		return ThmProcessUtil.stmtNotHandled;
	}
	
	public String caseANewStatementAction(ANewStatementAction a, ThmVarsContext vars) throws AnalysisException{
//	        [destination]:exp
//	        [className]:LexNameToken
//	        [args]:exp*
//	        (classdef):definition.#class
//	        (ctorDefinition):definition
		//TODO: NOT YET HANDLED
		return ThmProcessUtil.stmtNotHandled;
	}
	
	public String caseAForSetStatementAction(AForSetStatementAction a, ThmVarsContext vars) throws AnalysisException{
//	        [pattern]:pattern
//	        [set]:exp
//	        [action]:action
		//TODO: NOT YET HANDLED
		return ThmProcessUtil.stmtNotHandled;
	}
	
	public String caseAForIndexStatementAction(AForIndexStatementAction a, ThmVarsContext vars) throws AnalysisException{
//	        [var]:LexNameToken
//	        [from]:exp
//	        [to]:exp
//	        [by]:exp
//	        [action]:action
		//TODO: NOT YET HANDLED
		return ThmProcessUtil.stmtNotHandled;
	}
	
	public String caseAForSequenceStatementAction(AForSequenceStatementAction a, ThmVarsContext vars) throws AnalysisException{
//	        [patternBind]:patternBind.def
//	        [exp]:exp
//	        [action]:action
//	        (seqType):type.#seq
		//TODO: NOT YET HANDLED
		return ThmProcessUtil.stmtNotHandled;
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
