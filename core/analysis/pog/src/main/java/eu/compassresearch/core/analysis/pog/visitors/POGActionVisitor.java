package eu.compassresearch.core.analysis.pog.visitors;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.ADefPatternBind;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.statements.AExternalClause;
import org.overture.pog.obligation.POContextStack;
import org.overture.pog.obligation.ProofObligationList;
import org.overture.pog.util.POException;

import eu.compassresearch.ast.actions.AAlphabetisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AAssignmentCallStatementAction;
import eu.compassresearch.ast.actions.ABlockStatementAction;
import eu.compassresearch.ast.actions.ACallStatementAction;
import eu.compassresearch.ast.actions.ACaseAlternativeAction;
import eu.compassresearch.ast.actions.ACasesStatementAction;
import eu.compassresearch.ast.actions.AChannelRenamingAction;
import eu.compassresearch.ast.actions.AChaosAction;
import eu.compassresearch.ast.actions.ACommonInterleavingReplicatedAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.ADeclarationInstantiatedAction;
import eu.compassresearch.ast.actions.ADeclareStatementAction;
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
import eu.compassresearch.ast.actions.AParametrisedAction;
import eu.compassresearch.ast.actions.AParametrisedInstantiatedAction;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.AResParametrisation;
import eu.compassresearch.ast.actions.AReturnStatementAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASequentialCompositionReplicatedAction;
import eu.compassresearch.ast.actions.ASingleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.ASpecificationStatementAction;
import eu.compassresearch.ast.actions.AStartDeadlineAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.actions.ASubclassResponsibilityAction;
import eu.compassresearch.ast.actions.ASynchronousParallelismParallelAction;
import eu.compassresearch.ast.actions.ASynchronousParallelismReplicatedAction;
import eu.compassresearch.ast.actions.ATimedInterruptAction;
import eu.compassresearch.ast.actions.ATimeoutAction;
import eu.compassresearch.ast.actions.AUntimedTimeoutAction;
import eu.compassresearch.ast.actions.AValParametrisation;
import eu.compassresearch.ast.actions.AVresParametrisation;
import eu.compassresearch.ast.actions.AWaitAction;
import eu.compassresearch.ast.actions.AWhileStatementAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.ACmlClassDefinition;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.core.analysis.pog.obligations.CMLWhileLoopObligation;
import eu.compassresearch.core.analysis.pog.obligations.NonZeroTimeObligation;

@SuppressWarnings("serial")
public class POGActionVisitor extends
		QuestionAnswerCMLAdaptor<POContextStack, ProofObligationList> {

	private ProofObligationGenerator parentPOG;

	/**
	 * Constructor - simply initialise parent POG
	 * 
	 * @param parent
	 */
	public POGActionVisitor(ProofObligationGenerator parent) {
		this.parentPOG = parent;
	}

	// Default action
	@Override
	public ProofObligationList defaultPAction(PAction node,
			POContextStack question) throws AnalysisException {
		ProofObligationList pol = new ProofObligationList();

		System.out.println("----------***----------");
		System.out.println("defaultPAction");
		System.out.println(node.toString());
		System.out.println("----------***----------");

		return pol;
	}

	// Call the main pog when it's not a statement
	@Override
	public ProofObligationList defaultINode(INode node, POContextStack question)
			throws AnalysisException {

		ProofObligationList pol = new ProofObligationList();
		pol.addAll(node.apply(parentPOG, question));
		return pol;
	}

	/**
	 * Block Statement. Currently, get the action and handle
	 */
	@Override
	public ProofObligationList caseABlockStatementAction(
			ABlockStatementAction node, POContextStack question)
			throws AnalysisException {
		try {
			System.out.println("A ABlockStatementAction: " + node.toString());
			ProofObligationList pol = new ProofObligationList();

			// Get subparts
			PAction action = node.getAction();
			ADeclareStatementAction decl = node.getDeclareStatement();

			pol.addAll(action.apply(parentPOG, question));

			// TODO: Any ABlockStatementAction POs?
			return pol;
		} catch (Exception e) {
			throw new POException(node, e.getMessage());
		}
	}

	/**
	 * Single assignment. Possibly naive - just handle the identifiers and
	 * expressions May need more detail on identifiers?
	 */
	@Override
	public ProofObligationList caseASingleGeneralAssignmentStatementAction(
			ASingleGeneralAssignmentStatementAction node,
			POContextStack question) throws AnalysisException {
		try{
		System.out.println("A ASingleGeneralAssignmentStatementAction: "
				+ node.toString());
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PExp id = node.getStateDesignator();
		PExp expr = node.getExpression();

		pol.addAll(id.apply(parentPOG, question));
		pol.addAll(expr.apply(parentPOG, question));

		// TODO: Any ASingleGeneralAssignmentStatementAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	/**
	 * Composition action. Process left part, then right.
	 */
	@Override
	public ProofObligationList caseASequentialCompositionAction(
			ASequentialCompositionAction node, POContextStack question)
			throws AnalysisException {
		try{
		System.out
				.println("A ASequentialCompositionAction: " + node.toString());
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PAction left = node.getLeft();
		PAction right = node.getRight();

		pol.addAll(left.apply(parentPOG, question));
		pol.addAll(right.apply(parentPOG, question));

		// TODO: Any ASequentialCompositionAction POs?
		return pol;} catch (Exception e) {
			throw new POException(node, e.getMessage());
		}
	}

	/**
	 * If statement action. process expression, then 'then' part. Optionally
	 * process 'else' and 'elseif'
	 */
	@Override
	public ProofObligationList caseAIfStatementAction(AIfStatementAction node,
			POContextStack question) throws AnalysisException {
		try{
		System.out.println("A caseAIfStatementAction: " + node.toString());
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PExp ifexp = node.getIfExp();
		PAction then = node.getThenStm();

		pol.addAll(ifexp.apply(parentPOG, question));
		pol.addAll(then.apply(parentPOG, question));

		for (AElseIfStatementAction stmt : node.getElseIf()) {
			pol.addAll(stmt.apply(this, question));
		}

		if (node.getElseStm() != null) {
			pol.addAll(node.getElseStm().apply(this, question));
		}

		// TODO: Any AIfStatementAction POs?
		return pol;} catch (Exception e) {
			throw new POException(node, e.getMessage());
		}
	}

	@Override
	public ProofObligationList caseAElseIfStatementAction(
			AElseIfStatementAction node, POContextStack question)
			throws AnalysisException {
		try{
		System.out.println("A caseAElseIfStatementAction: " + node.toString());
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		pol.addAll(node.getElseIf().apply(parentPOG, question));
		pol.addAll(node.getThenStm().apply(parentPOG, question));

		// TODO: Any AElseIfStatementAction POs?
		return pol;} catch (Exception e) {
			throw new POException(node, e.getMessage());
		}
	}

	@Override
	public ProofObligationList caseAWhileStatementAction(
			AWhileStatementAction node, POContextStack question)
			throws AnalysisException {
		try{
		System.out.println("A caseAWhileStatementAction: " + node.toString());
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		pol.add(new CMLWhileLoopObligation(node, question));
		pol.addAll(node.getCondition().apply(parentPOG, question));
		pol.addAll(node.getAction().apply(this, question));

		// TODO: Any AWhileStatementAction POs?
		return pol;} catch (Exception e) {
			throw new POException(node, e.getMessage());
		}
	}

	@Override
	public ProofObligationList caseATimedInterruptAction(
			ATimedInterruptAction node, POContextStack question)
			throws AnalysisException {
		try{
		System.out.println("A ATimedInterruptAction: " + node.toString());
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PAction left = node.getLeft();
		PAction right = node.getRight();
		PExp timeExp = node.getTimeExpression();

		// Send left-hand side
		pol.addAll(left.apply(this, question));
		// check for Non-Zero time obligation and dispatch exp for POG checking
		// Changing format of CML POs... pol.add(new
		// CMLNonZeroTimeObligation(timeExp, question));
		pol.add(new NonZeroTimeObligation(timeExp, question));
		pol.addAll(timeExp.apply(this, question));
		// Send right-hand side
		pol.addAll(right.apply(this, question));

		// TODO: Any ATimedInterruptAction POs?
		return pol;} catch (Exception e) {
			throw new POException(node, e.getMessage());
		}
	}

	@Override
	public ProofObligationList caseAValParametrisation(
			AValParametrisation node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		ATypeSingleDeclaration decl = node.getDeclaration();

		// TODO: Any AValParametrisation POs?
		return pol;} catch (Exception e) {
			throw new POException(node, e.getMessage());
		}
	}

	@Override
	public ProofObligationList caseAResParametrisation(
			AResParametrisation node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		ATypeSingleDeclaration decl = node.getDeclaration();

		// TODO: Any AResParametrisation POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseAUntimedTimeoutAction(
			AUntimedTimeoutAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PAction left = node.getLeft();
		PAction right = node.getRight();

		pol.addAll(left.apply(parentPOG, question));
		pol.addAll(right.apply(parentPOG, question));

		// TODO: Any AUntimedTimeoutAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseATimeoutAction(ATimeoutAction node,
			POContextStack question) throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();
		System.out.println("A ATimeoutAction: " + node.toString());

		// Get subparts
		PAction left = node.getLeft();
		PAction right = node.getRight();
		PExp timedExp = node.getTimeoutExpression();

		pol.addAll(left.apply(parentPOG, question));
		pol.addAll(timedExp.apply(parentPOG, question));
		// check for Non-Zero time obligation and dispatch exp for POG checking
		pol.add(new NonZeroTimeObligation(timedExp, question));
		pol.addAll(right.apply(parentPOG, question));

		// TODO: Any ATimeoutAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseAExternalClause(AExternalClause node,
			POContextStack question) throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		LinkedList<ILexNameToken> ids = node.getIdentifiers();
		for (ILexNameToken id : ids) {
			// TODO anything to do w/ ids?
		}
		// TODO: Any AExternalClause POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseASpecificationStatementAction(
			ASpecificationStatementAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PExp post = node.getPostcondition();
		PExp pre = node.getPrecondition();

		// TODO: Any ASpecificationStatementAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseAInternalChoiceReplicatedAction(
			AInternalChoiceReplicatedAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PAction repAction = node.getReplicatedAction();
		LinkedList<PSingleDeclaration> repDecl = node
				.getReplicationDeclaration();
		for (PSingleDeclaration d : repDecl) {
			// TODO anything to do w/ decls?
		}
		// TODO: Any AInternalChoiceReplicatedAction POs?
		return pol;
} catch (Exception e) {
	throw new POException(node, e.getMessage());
}
	}

	@Override
	public ProofObligationList caseAGeneralisedParallelismReplicatedAction(
			AGeneralisedParallelismReplicatedAction node,
			POContextStack question) throws AnalysisException {
		try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PVarsetExpression csexp = node.getChansetExpression();
		PVarsetExpression sexp = node.getNamesetExpression();
		PAction repAction = node.getReplicatedAction();
		LinkedList<PSingleDeclaration> repDecl = node
				.getReplicationDeclaration();

		// TODO: Any AGeneralisedParallelismReplicatedAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseAExternalChoiceReplicatedAction(
			AExternalChoiceReplicatedAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PAction action = node.getReplicatedAction();
		LinkedList<PSingleDeclaration> decl = node.getReplicationDeclaration();
		for (PSingleDeclaration d : decl) {
		}

		pol.addAll(action.apply(parentPOG, question));

		// TODO: Any AExternalChoiceReplicatedAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseANonDeterministicIfStatementAction(
			ANonDeterministicIfStatementAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		LinkedList<ANonDeterministicAltStatementAction> alternatives = node
				.getAlternatives();
		for (ANonDeterministicAltStatementAction alt : alternatives) {
			pol.addAll(alt.apply(parentPOG, question));
		}

		// TODO: Any ANonDeterministicIfStatementAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseANewStatementAction(
			ANewStatementAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		LinkedList<PExp> args = node.getArgs();
		ACmlClassDefinition classdef = (ACmlClassDefinition) node.getClassdef();
		ILexNameToken classname = node.getClassName();
		PDefinition ctor = node.getCtorDefinition();
		PExp dest = node.getDestination();

		// TODO: Any ANewStatementAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseAMultipleGeneralAssignmentStatementAction(
			AMultipleGeneralAssignmentStatementAction node,
			POContextStack question) throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		LinkedList<ASingleGeneralAssignmentStatementAction> assigns = node
				.getAssignments();

		// TODO: Any AMultipleGeneralAssignmentStatementAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseALetStatementAction(
			ALetStatementAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PAction action = node.getAction();
		LinkedList<PDefinition> localDefs = node.getLocalDefinitions();

		// TODO: Any ALetStatementAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseAInterruptAction(AInterruptAction node,
			POContextStack question) throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PAction left = node.getLeft();
		PAction right = node.getRight();

		pol.addAll(left.apply(parentPOG, question));
		pol.addAll(right.apply(parentPOG, question));

		// TODO: Any AInterruptAction POs?

		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseAInterleavingParallelAction(
			AInterleavingParallelAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PAction leftAction = node.getLeftAction();
		PAction rightAction = node.getRightAction();
		PVarsetExpression leftNamesetExp = node.getLeftNamesetExpression();
		PVarsetExpression rightnamesetExp = node.getRightNamesetExpression();

		pol.addAll(leftAction.apply(parentPOG, question));
		pol.addAll(rightAction.apply(parentPOG, question));
		pol.addAll(leftNamesetExp.apply(parentPOG, question));
		pol.addAll(rightnamesetExp.apply(parentPOG, question));

		// TODO: Consider AInterleavingParallelAction POs

		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseADeclarationInstantiatedAction(
			ADeclarationInstantiatedAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PAction action = node.getAction();
		LinkedList<ATypeSingleDeclaration> declarations = node.getDeclaration();

		pol.addAll(action.apply(parentPOG, question));

		for (ATypeSingleDeclaration declr : declarations) {
			// TODO: Any decl POs?
		}

		// TODO: Any ADeclarationInstantiatedAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseAGeneralisedParallelismParallelAction(
			AGeneralisedParallelismParallelAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PAction leftAction = node.getLeftAction();
		PAction rightAction = node.getRightAction();
		PVarsetExpression leftNamesetExp = node.getLeftNamesetExpression();
		PVarsetExpression rightnamesetExp = node.getRightNamesetExpression();

		pol.addAll(leftAction.apply(parentPOG, question));
		pol.addAll(rightAction.apply(parentPOG, question));
		if (leftNamesetExp != null)
			pol.addAll(leftNamesetExp.apply(parentPOG, question));
		if (rightnamesetExp != null)
			pol.addAll(rightnamesetExp.apply(parentPOG, question));

		// TODO: Consider AGeneralisedParallelismParallelAction POs

		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseAForSetStatementAction(
			AForSetStatementAction node, POContextStack question)
			throws AnalysisException {
	try{
		// TODO RWL Working on it !

		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PAction action = node.getAction();
		PPattern pattern = node.getPattern();
		PExp set = node.getSet();

		// TODO: Any AForSetStatementAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseAForSequenceStatementAction(
			AForSequenceStatementAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PAction action = node.getAction();
		PExp exp = node.getExp();
		ADefPatternBind pattern = node.getPatternBind();

		// TODO: Any AForSequenceStatementAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseAForIndexStatementAction(
			AForIndexStatementAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PAction act = node.getAction();
		PExp by = node.getBy();
		PExp frm = node.getFrom();
		PExp to = node.getTo();
		ILexNameToken var = node.getVar();

		// TODO: Any AForIndexStatementAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseAChannelRenamingAction(
			AChannelRenamingAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PAction act = node.getAction();
		PExp exp = node.getRenameExpression();

		// TODO: Any AChannelRenamingAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseAWaitAction(AWaitAction node,
			POContextStack question) throws AnalysisException {
		try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PExp timedExp = node.getExpression();

		pol.addAll(timedExp.apply(parentPOG, question));

		// TODO: Any AWaitAction POs?

		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseACaseAlternativeAction(
			ACaseAlternativeAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		LinkedList<PDefinition> defs = node.getDefs();
		LinkedList<PPattern> ptrn = node.getPattern();
		PAction res = node.getResult();

		// TODO: Any ACaseAlternativeAction POs?

		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseACasesStatementAction(
			ACasesStatementAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		LinkedList<ACaseAlternativeAction> cases = node.getCases();
		PExp exp = node.getExp();
		PAction others = node.getOthers();

		// TODO: Any ACasesStatementAction POs?

		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseAMuAction(AMuAction node,
			POContextStack question) throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		LinkedList<ILexIdentifierToken> ids = node.getIdentifiers();
		LinkedList<PAction> acts = node.getActions();

		// TODO: Any AMuAction POs?

		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@SuppressWarnings("deprecation")
	@Override
	public ProofObligationList caseAChaosAction(AChaosAction node,
			POContextStack question) throws AnalysisException {
		try{
		ProofObligationList pol = new ProofObligationList();

		// TODO: Any AChaosAction POs?

		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@SuppressWarnings("deprecation")
	@Override
	public ProofObligationList caseASequentialCompositionReplicatedAction(
			ASequentialCompositionReplicatedAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PAction replicatedAction = node.getReplicatedAction();
		LinkedList<PSingleDeclaration> decls = node.getReplicationDeclaration();

		// TODO: Any ASequentialCompositionReplicatedAction POs?

		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseAAssignmentCallStatementAction(
			AAssignmentCallStatementAction node, POContextStack question)
			throws AnalysisException {
try{
		// Get subparts
		PExp designator = node.getDesignator();
		ACallStatementAction call = node.getCall();

		ProofObligationList pol = new ProofObligationList();

		// TODO: Any AAssignmentCallStatementAction POs?

		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@SuppressWarnings("deprecation")
	@Override
	public ProofObligationList caseAAlphabetisedParallelismParallelAction(
			AAlphabetisedParallelismParallelAction node, POContextStack question)
			throws AnalysisException {
		try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PAction leftAction = node.getLeftAction();
		PVarsetExpression leftChanSet = node.getLeftChansetExpression();
		PVarsetExpression leftNameSet = node.getLeftNamesetExpression();
		PAction rightAction = node.getRightAction();
		PVarsetExpression rightChanSet = node.getRightChansetExpression();
		PVarsetExpression rightNameSet = node.getLeftNamesetExpression();

		pol.addAll(leftAction.apply(parentPOG, question));
		pol.addAll(leftChanSet.apply(parentPOG, question));
		pol.addAll(leftNameSet.apply(parentPOG, question));
		pol.addAll(rightAction.apply(parentPOG, question));
		pol.addAll(rightChanSet.apply(parentPOG, question));
		pol.addAll(rightNameSet.apply(parentPOG, question));

		// TODO: Any AAlphabetisedParallelismParallelAction POs?

		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseAReturnStatementAction(
			AReturnStatementAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PExp exp = node.getExp();
		pol.addAll(exp.apply(parentPOG, question));
		// TODO: Any AReturnStatementAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseAGuardedAction(AGuardedAction node,
			POContextStack question) throws AnalysisException {
try{
		// Get subparts
		PExp exp = node.getExpression();
		PAction action = node.getAction();

		ProofObligationList pol = new ProofObligationList();

		pol.addAll(exp.apply(parentPOG, question));
		pol.addAll(action.apply(parentPOG, question));

		// TODO: Any AGuardedAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseADivAction(ADivAction node,
			POContextStack question) throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// TODO: Any ADivAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseASubclassResponsibilityAction(
			ASubclassResponsibilityAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// TODO: Any ASubclassResponsibilityAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseACommonInterleavingReplicatedAction(
			ACommonInterleavingReplicatedAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PAction acts = node.getReplicatedAction();
		LinkedList<PSingleDeclaration> decls = node.getReplicationDeclaration();
		PVarsetExpression namesetExp = node.getNamesetExpression();

		// TODO: Any ACommonInterleavingReplicatedAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseAInterleavingReplicatedAction(
			AInterleavingReplicatedAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PVarsetExpression namesetExp = node.getNamesetExpression();
		PAction repAction = node.getReplicatedAction();
		LinkedList<PSingleDeclaration> decls = node.getReplicationDeclaration();

		// TODO: Any AInterleavingReplicatedAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseASynchronousParallelismReplicatedAction(
			ASynchronousParallelismReplicatedAction node,
			POContextStack question) throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PVarsetExpression namesetExp = node.getNamesetExpression();
		PAction repAction = node.getReplicatedAction();
		LinkedList<PSingleDeclaration> decls = node.getReplicationDeclaration();

		// TODO Any ASynchronousParallelismReplicatedAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseANotYetSpecifiedStatementAction(
			ANotYetSpecifiedStatementAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		LinkedList<PExp> args = node.getArgs();
		ILexNameToken opname = node.getOpname();

		// TODO Any ANotYetSpecifiedStatementAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseAInternalChoiceAction(
			AInternalChoiceAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PAction left = node.getLeft();
		PAction right = node.getRight();

		pol.addAll(left.apply(parentPOG, question));
		pol.addAll(right.apply(parentPOG, question));

		// TODO Any AInternalChoiceAction POs?

		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseAReferenceAction(AReferenceAction node,
			POContextStack question) throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		LinkedList<PExp> args = node.getArgs();
		ILexNameToken name = node.getName();

		// TODO Any AReferenceAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseACommunicationAction(
			ACommunicationAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PAction act = node.getAction();
		LinkedList<PCommunicationParameter> commparam = node
				.getCommunicationParameters();
		ILexIdentifierToken ident = node.getIdentifier();

		// TODO Any ACommunicationAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@SuppressWarnings("deprecation")
	@Override
	public ProofObligationList caseASkipAction(ASkipAction node,
			POContextStack question) throws AnalysisException {
		try{
		ProofObligationList pol = new ProofObligationList();

		// TODO Any ASkipAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@SuppressWarnings("deprecation")
	@Override
	public ProofObligationList caseAExternalChoiceAction(
			AExternalChoiceAction node, POContextStack question)
			throws AnalysisException {
try{
		System.out.println("A AExternalChoiceAction: " + node.toString());
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PAction left = node.getLeft();
		PAction right = node.getRight();

		pol.addAll(left.apply(parentPOG, question));
		pol.addAll(right.apply(parentPOG, question));

		// TODO Any AInternalChoiceAction POs?

		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseAHidingAction(AHidingAction node,
			POContextStack question) throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PAction action = node.getLeft();
		PVarsetExpression chanSet = node.getChansetExpression();

		pol.addAll(action.apply(parentPOG, question));
		pol.addAll(chanSet.apply(parentPOG, question));

		// TODO Any AHidingAction POs?

		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseAVresParametrisation(
			AVresParametrisation node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		ATypeSingleDeclaration decl = node.getDeclaration();
		pol.addAll(decl.apply(parentPOG, question));

		// TODO Any AVresParametrisation POs?

		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseAParametrisedInstantiatedAction(
			AParametrisedInstantiatedAction node, POContextStack question)
			throws AnalysisException {
		try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		AParametrisedAction action = node.getAction();
		LinkedList<PExp> args = node.getArgs();
		LinkedList<PParametrisation> parameterNames = node.getAction()
				.getParametrisations();

		pol.addAll(action.apply(parentPOG, question));

		// TODO Any AParametrisedInstantiatedAction POs?

		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@SuppressWarnings("deprecation")
	@Override
	public ProofObligationList caseAStartDeadlineAction(
			AStartDeadlineAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PAction event = node.getLeft();
		PExp timeExp = node.getExpression();

		pol.addAll(event.apply(parentPOG, question));
		pol.addAll(timeExp.apply(parentPOG, question));

		// TODO Any AStartDeadlineAction POs?

		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@SuppressWarnings("deprecation")
	@Override
	public ProofObligationList caseAEndDeadlineAction(AEndDeadlineAction node,
			POContextStack question) throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PAction event = node.getLeft();
		PExp timeExp = node.getExpression();

		pol.addAll(event.apply(parentPOG, question));
		pol.addAll(timeExp.apply(parentPOG, question));

		// TODO: Any AEndDeadlineAction POs?

		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseAStopAction(AStopAction node,
			POContextStack question) throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// TODO: any AStopAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@SuppressWarnings("deprecation")
	@Override
	public ProofObligationList caseACallStatementAction(
			ACallStatementAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		LinkedList<PExp> args = node.getArgs();
		ILexNameToken name = node.getName();

		// TODO: any ACallStatementAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@SuppressWarnings("deprecation")
	@Override
	public ProofObligationList caseASynchronousParallelismParallelAction(
			ASynchronousParallelismParallelAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PAction leftAction = node.getLeftAction();
		PVarsetExpression leftNameSet = node.getLeftNamesetExpression();
		PAction rightAction = node.getRightAction();
		PVarsetExpression rightNameSet = node.getLeftNamesetExpression();

		pol.addAll(leftAction.apply(parentPOG, question));
		pol.addAll(leftNameSet.apply(parentPOG, question));
		pol.addAll(rightAction.apply(parentPOG, question));
		pol.addAll(rightNameSet.apply(parentPOG, question));

		// TODO: Any ASynchronousParallelismParallelAction POs?

		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseANonDeterministicDoStatementAction(
			ANonDeterministicDoStatementAction node, POContextStack question)
			throws AnalysisException {
		try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		LinkedList<ANonDeterministicAltStatementAction> alternatives = node
				.getAlternatives();
		for (ANonDeterministicAltStatementAction act : alternatives) {
		}

		// TODO: Any ANonDeterministicDoStatementAction POs?

		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseANonDeterministicAltStatementAction(
			ANonDeterministicAltStatementAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PExp guard = node.getGuard();
		PAction action = node.getAction();

		pol.addAll(guard.apply(parentPOG, question));
		pol.addAll(action.apply(parentPOG, question));

		// TODO: Any ANonDeterministicAltStatementAction POs?
		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}

	@Override
	public ProofObligationList caseAParametrisedAction(
			AParametrisedAction node, POContextStack question)
			throws AnalysisException {
try{
		ProofObligationList pol = new ProofObligationList();

		// Get subparts
		PAction action = node.getAction();
		LinkedList<PParametrisation> params = node.getParametrisations();

		// TODO: Consider any AParametrisedAction POs
		for (PParametrisation p : params) {
		}

		pol.addAll(action.apply(parentPOG, question));

		return pol;
	} catch (Exception e) {
		throw new POException(node, e.getMessage());
	}}
	
}