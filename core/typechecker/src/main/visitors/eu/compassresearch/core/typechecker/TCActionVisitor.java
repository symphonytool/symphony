package eu.compassresearch.core.typechecker;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.types.ANatNumericBasicType;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.actions.ABlockStatementAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.AEndDeadlineAction;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AHidingAction;
import eu.compassresearch.ast.actions.AInternalChoiceAction;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.AReturnStatementAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASingleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AStartDeadlineAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AExplicitOperationDefinition;
import eu.compassresearch.ast.types.AActionType;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.ast.types.AErrorType;
import eu.compassresearch.ast.types.AStatementType;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeComparator;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

/**
 * @author rwl
 * 
 */
@SuppressWarnings("serial")
class TCActionVisitor extends
		QuestionAnswerCMLAdaptor<org.overture.typechecker.TypeCheckInfo, PType> {

	private final CmlTypeChecker parentChecker;
	private final TypeIssueHandler issueHandler;
	private final TypeComparator typeComparator;

	public TCActionVisitor(CmlTypeChecker parentChecker,
			TypeIssueHandler issueHandler, TypeComparator typeComparator) {
		this.parentChecker = parentChecker;
		this.issueHandler = issueHandler;
		this.typeComparator = typeComparator;
	}

	@Override
	public PType caseAReturnStatementAction(AReturnStatementAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {
		AExplicitOperationDefinition operation = node
				.getAncestor(AExplicitOperationDefinition.class);
		if (operation == null)
			throw new AnalysisException(
					"Return Statement Action does not have explicit operation as parent.");

		PExp exp = node.getExp();
		PType type = exp.apply(parentChecker, question);
		if (type == null)
			throw new AnalysisException("Unable to type check expression \""
					+ exp + "\" in return statement action of "
					+ operation.getName());

		node.setType(new AStatementType());
		return node.getType();
	}

	@Override
	public PType caseABlockStatementAction(ABlockStatementAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {
		// extend the environment

		//
		PAction action = node.getAction();
		PType actionType = action.apply(parentChecker, question);
		if (actionType == null)
			issueHandler.addTypeError(action,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(action.toString()));
		node.setType(new AStatementType());

		return node.getType();
	}

	@Override
	public PType caseASingleGeneralAssignmentStatementAction(
			ASingleGeneralAssignmentStatementAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		// FIXME Some scope stuff is not correct when typechecking let exp
		// PType expType = node.getExpression().apply(parentChecker, question);
		// if (expType == null)
		// throw new AnalysisException(
		// "Unable to type check expression in assignment action.");

		// PType stateDesignatorType = node.getStateDesignator().apply(
		// parentChecker, question);
		// TODO This is not implemented yet
		// if (stateDesignatorType == null)
		// throw new AnalysisException(
		// "Unable to type check state designator in assignment action.");

		node.setType(new AStatementType());

		return node.getType();
	}

	@Override
	public PType caseAInternalChoiceAction(AInternalChoiceAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		PAction left = node.getLeft();
		PAction right = node.getRight();

		PType leftType = left.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(leftType))
			return new AErrorType();

		PType rightType = right.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(rightType))
			return new AErrorType();

		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseAReferenceAction(AReferenceAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		TypeCheckInfo newQ = (TypeCheckInfo) question;

		PDefinition actionDef = newQ.lookupVariable(node.getName());

		if (actionDef == null) {
			issueHandler.addTypeError(
					node,
					TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage(node
							.getName() + ""));
			node.setType(new AErrorType());
			return node.getType();
		}

		if (!(actionDef instanceof AActionDefinition)) {
			issueHandler.addTypeError(
					node,
					TypeErrorMessages.EXPECTED_AN_ACTION.customizeMessage(node
							.getName() + ""));
			node.setType(new AErrorType());
			return node.getType();

		}

		node.setActionDefinition(((AActionDefinition) actionDef));
		node.setType(new AStatementType());
		return node.getType();
	}

	@SuppressWarnings("deprecation")
	@Override
	public PType caseACommunicationAction(ACommunicationAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		if (question instanceof TypeCheckInfo) {
			TypeCheckInfo cmlQuestion = (TypeCheckInfo) question;

			// There should be a channel defined with this name
			if (null == cmlQuestion.lookupChannel(node.getIdentifier())) {
				issueHandler.addTypeError(node,
						TypeErrorMessages.NAMED_TYPE_UNDEFINED
								.customizeMessage(node.getIdentifier().name));
				return new AErrorType(node.getLocation(), true);
			}
			node.getAction().apply(this, question);

			node.setType(new AStatementType());
		}
		return node.getType();
	}

	@Override
	public PType caseASequentialCompositionAction(
			ASequentialCompositionAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		node.getLeft().apply(parentChecker, question);
		node.getRight().apply(parentChecker, question);

		node.setType(new AStatementType());
		return node.getType();
	}

	@SuppressWarnings("deprecation")
	@Override
	public PType caseASkipAction(ASkipAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {
		node.setType(new AActionType(node.getLocation(), true));
		return node.getType();
	}

	@SuppressWarnings("deprecation")
	@Override
	public PType caseAExternalChoiceAction(AExternalChoiceAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		PAction left = node.getLeft();
		PAction right = node.getRight();

		PType leftType = left.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(leftType))
			return new AErrorType();

		PType rightType = right.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(rightType))
			return new AErrorType();

		node.setType(new AActionType(node.getLocation(), true));
		return node.getType();
	}

	@SuppressWarnings("deprecation")
	@Override
	public PType caseAHidingAction(AHidingAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		PAction action = node.getLeft();
		PExp chanSet = node.getChansetExpression();

		PType actionType = action.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(actionType))
			return new AErrorType();

		PType chanSetType = chanSet.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(chanSetType))
			return new AErrorType();

		if (!(chanSetType instanceof AChannelType)) {
			issueHandler.addTypeError(chanSet,
					TypeErrorMessages.EXPECTED_A_CHANNELSET
							.customizeMessage(chanSet.toString()));
			return new AErrorType();
		}

		node.setType(new AActionType(node.getLocation(), true));

		return node.getType();
	}

	@SuppressWarnings("deprecation")
	@Override
	public PType caseAStartDeadlineAction(AStartDeadlineAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		PAction event = node.getLeft();
		PExp timeExp = node.getExpression();

		PType eventType = event.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(eventType))
			return new AErrorType(node.getLocation(), true);

		PType expType = timeExp.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(expType))
			return new AErrorType(node.getLocation(), true);

		if (!(typeComparator.isSubType(expType, new ANatNumericBasicType()))) {
			TypeErrorMessages.TIME_UNIT_EXPRESSION_MUST_BE_NAT
					.customizeMessage(timeExp + "", expType + "");
			node.setType(new AErrorType(timeExp.getLocation(), true));
			return node.getType();
		}
		node.setType(new AActionType(node.getLocation(), true));
		return node.getType();
	}

	@SuppressWarnings("deprecation")
	@Override
	public PType caseAEndDeadlineAction(AEndDeadlineAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		PAction event = node.getLeft();
		PExp timeExp = node.getExpression();

		PType eventType = event.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(eventType))
			return new AErrorType(node.getLocation(), true);

		PType timeExpType = timeExp.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(timeExpType))
			return new AErrorType(node.getLocation(), true);

		if (!(typeComparator.isSubType(timeExpType, new ANatNumericBasicType()))) {
			TypeErrorMessages.TIME_UNIT_EXPRESSION_MUST_BE_NAT
					.customizeMessage(timeExp + "", timeExpType + "");
			node.setType(new AErrorType(timeExp.getLocation(), true));
			return node.getType();

		}

		node.setType(new AActionType());

		return super.caseAEndDeadlineAction(node, question);
	}

}
