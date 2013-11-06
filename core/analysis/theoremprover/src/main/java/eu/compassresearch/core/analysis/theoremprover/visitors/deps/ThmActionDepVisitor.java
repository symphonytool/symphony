package eu.compassresearch.core.analysis.theoremprover.visitors.deps;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.statements.AAssignmentStm;
import org.overture.ast.statements.ACallStm;
import org.overture.ast.statements.AElseIfStm;
import org.overture.ast.statements.AIfStm;
import org.overture.ast.statements.ALetStm;
import org.overture.ast.statements.AWhileStm;
import org.overture.ast.statements.PStateDesignator;

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
import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASequentialCompositionReplicatedAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AStartDeadlineAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.actions.ASynchronousParallelismParallelAction;
import eu.compassresearch.ast.actions.ASynchronousParallelismReplicatedAction;
import eu.compassresearch.ast.actions.ATimedInterruptAction;
import eu.compassresearch.ast.actions.ATimeoutAction;
import eu.compassresearch.ast.actions.AUntimedTimeoutAction;
import eu.compassresearch.ast.actions.AWaitAction;
import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.core.analysis.theoremprover.thms.NodeNameList;

@SuppressWarnings("serial")
public class ThmActionDepVisitor  extends
QuestionAnswerCMLAdaptor<NodeNameList, NodeNameList>{
	
	final private QuestionAnswerCMLAdaptor<NodeNameList, NodeNameList> thmDepVisitor;
	
	public ThmActionDepVisitor(ThmDepVisitor thmDepVisitor) {
		this.thmDepVisitor = thmDepVisitor;
	}
	
	public NodeNameList caseASkipAction(ASkipAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		
		return nodeDeps;
	}
	
	public NodeNameList caseAStopAction(AStopAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		
		return nodeDeps;
	}
	
	public NodeNameList caseAChaosAction(AChaosAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		
		return nodeDeps;
	}
	
	public NodeNameList caseADivAction(ADivAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
	
		return nodeDeps;
	}
	
	public NodeNameList caseAWaitAction(AWaitAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getExpression().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	
	public NodeNameList caseACommunicationAction(ACommunicationAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
						
		for( PCommunicationParameter p: a.getCommunicationParameters())
		{
			if(p instanceof AReadCommunicationParameter)
			{
				AReadCommunicationParameter cp = (AReadCommunicationParameter) p;
				PPattern patt = cp.getPattern();
				if(patt instanceof AIdentifierPattern)
				{
					AIdentifierPattern ip = (AIdentifierPattern) patt;
					for (PDefinition def : ip.getDefinitions())
					{
						nodeDeps.addAll(def.getType().apply(thmDepVisitor, bvars));
					}
					bvars.add(ip.getName());
				}
			}
			else if (p instanceof AWriteCommunicationParameter)
			{
				AWriteCommunicationParameter cp = (AWriteCommunicationParameter) p;
				nodeDeps.addAll(cp.getExpression().apply(thmDepVisitor, bvars));
				
			}else
			{
				nodeDeps.addAll(p.getExpression().apply(thmDepVisitor, bvars));
			}
		}
		nodeDeps.addAll(a.getAction().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	
	public NodeNameList caseAGuardedAction(AGuardedAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getExpression().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getAction().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	
	public NodeNameList caseASequentialCompositionAction(ASequentialCompositionAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getRight().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	
	public NodeNameList caseAExternalChoiceAction(AExternalChoiceAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getRight().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	
	public NodeNameList caseAInternalChoiceAction(AInternalChoiceAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getRight().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	
	public NodeNameList caseAInterruptAction(AInterruptAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getRight().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	
	public NodeNameList caseATimedInterruptAction(ATimedInterruptAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getRight().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getTimeExpression().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
		
	public NodeNameList caseAUntimedTimeoutAction(AUntimedTimeoutAction a, NodeNameList bvars)
				throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		
		nodeDeps.addAll(a.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getRight().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	
	public NodeNameList caseATimeoutAction(ATimeoutAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getRight().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getTimeoutExpression().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	
	public NodeNameList caseAHidingAction(AHidingAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getChansetExpression().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	
	public NodeNameList caseAStartDeadlineAction(AStartDeadlineAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getExpression().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	
	public NodeNameList caseAEndDeadlineAction(AEndDeadlineAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getExpression().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	
	public NodeNameList caseAChannelRenamingAction(AChannelRenamingAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		
		nodeDeps.addAll(a.getAction().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getRenameExpression().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	
	public NodeNameList caseAMuAction(AMuAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		
	//	for (ILexIdentifierToken id : a.getIdentifiers()) 
	//	{
	//		
	//		nodeDeps.add(new LexNameToken("", id.getName().toString(), a.getLocation()));
	//	}
		
	//	for (PAction pa : a.getActions())
	//	{
	//		nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(pa, bvars));
	//	}

		return nodeDeps;
	}
	
	public NodeNameList caseAReferenceAction(AReferenceAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		LinkedList<PExp> args = a.getArgs();
		if (args.size() != 0)
		{
			for (PExp e : a.getArgs()) 
			{
				nodeDeps.addAll(e.apply(thmDepVisitor, bvars));
			}
		}
		nodeDeps.add(a.getName());

		return nodeDeps;
	}
	
	public NodeNameList caseASequentialCompositionReplicatedAction(ASequentialCompositionReplicatedAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

//		a.getReplicatedAction();
//		a.getReplicationDeclaration();
		//TODO: NOT YET HANDLED

		return nodeDeps;
	}
	
	public NodeNameList caseAExternalChoiceReplicatedAction(AExternalChoiceReplicatedAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		//TODO: NOT YET HANDLED

		return nodeDeps;
	}
	
	public NodeNameList caseAInternalChoiceReplicatedAction(AInternalChoiceReplicatedAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		//TODO: NOT YET HANDLED

		return nodeDeps;
	}
	
	public NodeNameList caseACommonInterleavingReplicatedAction(ACommonInterleavingReplicatedAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		
//      [namesetExpression]:VarsetExpression
		//TODO: NOT YET HANDLED

		return nodeDeps;
	}
	
	public NodeNameList caseAInterleavingReplicatedAction(AInterleavingReplicatedAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
//      [namesetExpression]:VarsetExpression
		//TODO: NOT YET HANDLED

		return nodeDeps;
	}
	
	public NodeNameList caseAGeneralisedParallelismReplicatedAction(AGeneralisedParallelismReplicatedAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		
//      [chansetExpression]:VarsetExpression
//      [namesetExpression]:VarsetExpression
		//TODO: NOT YET HANDLED

		return nodeDeps;
	}
	
	public NodeNameList caseAAlphabetisedParallelismReplicatedAction(AAlphabetisedParallelismReplicatedAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
//      [namesetExpression]:VarsetExpression
//      [chansetExpression]:VarsetExpression
		//TODO: NOT YET HANDLED

		return nodeDeps;
	}
	
	public NodeNameList caseASynchronousParallelismReplicatedAction(ASynchronousParallelismReplicatedAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
//      [namesetExpression]:VarsetExpression
		//TODO: NOT YET HANDLED

		return nodeDeps;
	}
	
	public NodeNameList caseAInterleavingParallelAction(AInterleavingParallelAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getLeftAction().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getRightAction().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	
	public NodeNameList caseASynchronousParallelismParallelAction(ASynchronousParallelismParallelAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getLeftAction().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getRightAction().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	
	public NodeNameList caseAGeneralisedParallelismParallelAction(AGeneralisedParallelismParallelAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getLeftAction().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getRightAction().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getChansetExpression().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	
	public NodeNameList caseAAlphabetisedParallelismParallelAction(AAlphabetisedParallelismParallelAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getLeftAction().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getRightAction().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getLeftChansetExpression().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getRightChansetExpression().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	
	public NodeNameList caseACallStm(ACallStm a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		for (PExp e: a.getArgs())
		{
			nodeDeps.addAll(e.apply(thmDepVisitor, bvars));
		}
		nodeDeps.add(a.getName());

		return nodeDeps;
	}
	
	public NodeNameList caseALetStm(ALetStm a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		
		LinkedList<PDefinition> defs = a.getLocalDefs();
		if(! defs.isEmpty()) 
		{
			for (PDefinition pdef : a.getLocalDefs())
			{
				AAssignmentDefinition aDef = (AAssignmentDefinition) pdef;
				bvars.add(aDef.getName());
				if (aDef.getExpression()!=null)
				{
					nodeDeps.addAll(aDef.getExpression().apply(thmDepVisitor, bvars));
				}
			}
		}

		nodeDeps.addAll(a.getStatement().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	
	public NodeNameList caseAIfStm(AIfStm a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getIfExp().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getThenStm().apply(thmDepVisitor, bvars));

		if(a.getElseStm() != null)
		{
			nodeDeps.addAll(a.getElseStm().apply(thmDepVisitor, bvars));
		}
		LinkedList<AElseIfStm> elseIf = a.getElseIf();
		for(AElseIfStm e : elseIf)
		{
			nodeDeps.addAll(e.apply(thmDepVisitor, bvars));

		}

		return nodeDeps;
	}
	
	public NodeNameList caseAElseIfStm(AElseIfStm a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getElseIf().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getThenStm().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	
	public NodeNameList caseAAssignmentStm(AAssignmentStm a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		PStateDesignator designator = a.getTarget();
//NOT SURE HERE... NEED TO ADD DEPENDENCY ON TARGET
	//	nodeDeps.addAll(a.getStateDesignator().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getExp().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	//DON'T THINK IS HANDLED IN ISABELLE YET
//	public NodeNameList caseAAssignmentCallStatementAction(AAssignmentCallStatementAction a, NodeNameList bvars)
//			throws AnalysisException {
//		NodeNameList nodeDeps = new NodeNameList();
////       [designator]:exp
////       [call]:action.#Statement.call
//
//			return nodeDeps;
//		}
		
	public NodeNameList caseAWhileStm(AWhileStm a, NodeNameList bvars)
				throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getExp().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getStatement().apply(thmDepVisitor, bvars));
//        | {declare}  [assignmentDefs]:definition*

		return nodeDeps;
	}
	
//	public NodeNameList caseANonDeterministicDoStatementAction(ANonDeterministicDoStatementAction a, NodeNameList bvars)
//			throws AnalysisException {
//		NodeNameList nodeDeps = new NodeNameList();
//
////        [alternatives]:action.#Statement.nonDeterministicAlt*
//		//TODO: NOT HANDLED
//
//		return nodeDeps;
//	}
//	
//	public NodeNameList caseANonDeterministicIfStatementAction(ANonDeterministicIfStatementAction a, NodeNameList bvars)
//			throws AnalysisException {
//		NodeNameList nodeDeps = new NodeNameList();
//		
////       [alternatives]:action.#Statement.nonDeterministicAlt*
//		//TODO: NOT HANDLED
//
//		return nodeDeps;
//	}
//	
//	public NodeNameList caseANotYetSpecifiedStatementAction(ANotYetSpecifiedStatementAction a, NodeNameList bvars)
//			throws AnalysisException {
//		NodeNameList nodeDeps = new NodeNameList();
//		
////       [opname]:LexNameToken
////       [args]:exp*
//		//TODO: NOT HANDLED
//
//		return nodeDeps;
//	}
//	
//	public NodeNameList caseALetStatementAction(ALetStatementAction a, NodeNameList bvars)
//			throws AnalysisException {
//		NodeNameList nodeDeps = new NodeNameList();
//		
//		//[action]:action [localDefinitions]:definition*
//		//TODO: NOT YET HANDLED
//
//		return nodeDeps;
//	}
//	
//	public NodeNameList caseANonDeterministicAltStatementAction(ANonDeterministicAltStatementAction a, NodeNameList bvars)
//			throws AnalysisException {
//		NodeNameList nodeDeps = new NodeNameList();
////       [guard]:exp
////       [action]:action
//		//TODO: NOT YET HANDLED
//
//		return nodeDeps;
//	}
//	
//	public NodeNameList caseACasesStatementAction(ACasesStatementAction a, NodeNameList bvars)
//			throws AnalysisException {
//		NodeNameList nodeDeps = new NodeNameList();
//
////       [exp]:exp
////       [cases]:alternativeAction.case*
////       [others]:action
//		//TODO: NOT YET HANDLED
//
//		return nodeDeps;
//	}
//	
//	public NodeNameList caseAMultipleGeneralAssignmentStatementAction(AMultipleGeneralAssignmentStatementAction a, NodeNameList bvars)
//			throws AnalysisException {
//		NodeNameList nodeDeps = new NodeNameList();
//		//TODO: NOT YET HANDLED
//
//		return nodeDeps;
//	}
//	
//	public NodeNameList caseASpecificationStatementAction(ASpecificationStatementAction a, NodeNameList bvars)
//			throws AnalysisException {
//		NodeNameList nodeDeps = new NodeNameList();
////       [externals]:clause.external*
////       [precondition]:exp
////       [postcondition]:exp
//		//MAY GEN LEMMA TO PROVE STATING FRAME NOT VIOLATED
//		//TODO: NOT YET HANDLED
//
//		return nodeDeps;
//	}
//	
//	public NodeNameList caseAReturnStatementAction(AReturnStatementAction a, NodeNameList bvars)
//			throws AnalysisException {
//		NodeNameList nodeDeps = new NodeNameList();
////       [exp]:exp
//			//TODO: NOT YET HANDLED
//
//		return nodeDeps;
//	}
//	
//	public NodeNameList caseANewStatementAction(ANewStatementAction a, NodeNameList bvars)
//			throws AnalysisException {
//		NodeNameList nodeDeps = new NodeNameList();
////        [destination]:exp
////        [className]:LexNameToken
////        [args]:exp*
////        (classdef):definition.#class
////        (ctorDefinition):definition
//		//TODO: NOT YET HANDLED
//
//		return nodeDeps;
//	}
//	
//	public NodeNameList caseAForSetStatementAction(AForSetStatementAction a, NodeNameList bvars)
//			throws AnalysisException {
//		NodeNameList nodeDeps = new NodeNameList();
////        [pattern]:pattern
////        [set]:exp
////        [action]:action
//		//TODO: NOT YET HANDLED
//
//		return nodeDeps;
//	}
//	
//	public NodeNameList caseAForIndexStatementAction(AForIndexStatementAction a, NodeNameList bvars)
//			throws AnalysisException {
//		NodeNameList nodeDeps = new NodeNameList();
////        [var]:LexNameToken
////        [from]:exp
////        [to]:exp
////        [by]:exp
////        [action]:action
//		//TODO: NOT YET HANDLED
//
//		return nodeDeps;
//	}
//	
//	public NodeNameList caseAForSequenceStatementAction(AForSequenceStatementAction a, NodeNameList bvars)
//			throws AnalysisException {
//		NodeNameList nodeDeps = new NodeNameList();
////        [patternBind]:patternBind.def
////        [exp]:exp
////        [action]:action
////        (seqType):type.#seq
//		//TODO: NOT YET HANDLED
//	
//		return nodeDeps;
//	
//	}
	
	
	@Override
	public NodeNameList createNewReturnValue(INode arg0, NodeNameList arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeNameList createNewReturnValue(Object arg0, NodeNameList arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}
}
