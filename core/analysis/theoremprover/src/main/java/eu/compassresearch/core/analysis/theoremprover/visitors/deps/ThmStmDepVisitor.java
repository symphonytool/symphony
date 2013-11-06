package eu.compassresearch.core.analysis.theoremprover.visitors.deps;

import java.util.LinkedList;
import eu.compassresearch.ast.statements.AActionStm;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.ast.statements.AAssignmentStm;
import org.overture.ast.statements.ABlockSimpleBlockStm;
import org.overture.ast.statements.ACallStm;
import org.overture.ast.statements.AElseIfStm;
import org.overture.ast.statements.AIfStm;
import org.overture.ast.statements.ALetStm;
import org.overture.ast.statements.AWhileStm;
import org.overture.ast.statements.PStateDesignator;
import org.overture.ast.statements.PStm;

import eu.compassresearch.ast.statements.AActionStm;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.core.analysis.theoremprover.thms.NodeNameList;

@SuppressWarnings("serial")
public class ThmStmDepVisitor   extends
QuestionAnswerCMLAdaptor<NodeNameList, NodeNameList>{
	
	final private QuestionAnswerCMLAdaptor<NodeNameList, NodeNameList> thmDepVisitor;
	
	public ThmStmDepVisitor(ThmDepVisitor thmDepVisitor) {
		this.thmDepVisitor = thmDepVisitor;
	}
	
	public NodeNameList caseAActionStm(AActionStm a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getAction().apply(thmDepVisitor, bvars));
		
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

	public NodeNameList caseABlockSimpleBlockStm(ABlockSimpleBlockStm a, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();
		
		for (AAssignmentDefinition aDef : a.getAssignmentDefs())
		{
			bvars.add(aDef.getName());
			if (aDef.getExpression()!=null)
			{
				nodeDeps.addAll(aDef.getExpression().apply(thmDepVisitor, bvars));
			}
		}
		
		for (PStm s : a.getStatements())
		{
			nodeDeps.addAll(s.apply(thmDepVisitor, bvars));
		}

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

