package eu.compassresearch.core.typechecker;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.APrivateAccess;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.patterns.ADefPatternBind;
import org.overture.ast.patterns.AExpressionPattern;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.typechecker.Pass;
import org.overture.ast.types.AAccessSpecifierAccessSpecifier;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.ANatNumericBasicType;
import org.overture.ast.types.PType;
import org.overture.ast.types.SSeqType;
import org.overture.typechecker.Environment;
import org.overture.typechecker.FlatCheckedEnvironment;
import org.overture.typechecker.assistant.definition.PDefinitionListAssistantTC;
import org.overture.typechecker.assistant.pattern.PPatternAssistantTC;

import eu.compassresearch.ast.actions.AAlphabetisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AAssignmentCallStatementAction;
import eu.compassresearch.ast.actions.ABlockStatementAction;
import eu.compassresearch.ast.actions.ACallStatementAction;
import eu.compassresearch.ast.actions.ACaseAlternativeAction;
import eu.compassresearch.ast.actions.ACasesStatementAction;
import eu.compassresearch.ast.actions.AChannelRenamingAction;
import eu.compassresearch.ast.actions.AChaosAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.AEndDeadlineAction;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AForIndexStatementAction;
import eu.compassresearch.ast.actions.AForSequenceStatementAction;
import eu.compassresearch.ast.actions.AHidingAction;
import eu.compassresearch.ast.actions.AInternalChoiceAction;
import eu.compassresearch.ast.actions.AMuAction;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.AReturnStatementAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASequentialCompositionReplicatedAction;
import eu.compassresearch.ast.actions.ASingleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AStartDeadlineAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.actions.ASynchronousParallelismParallelAction;
import eu.compassresearch.ast.actions.ATimedInterruptAction;
import eu.compassresearch.ast.actions.AWaitAction;
import eu.compassresearch.ast.actions.AWhileStatementAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.SSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AExplicitOperationDefinition;
import eu.compassresearch.ast.expressions.SRenameChannelExp;
import eu.compassresearch.ast.types.AActionType;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.ast.types.AErrorType;
import eu.compassresearch.ast.types.AProcessType;
import eu.compassresearch.ast.types.AStatementType;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeCheckQuestion;
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



	@Override
	public PType caseAForSequenceStatementAction(
			AForSequenceStatementAction node, org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		PAction action = node.getAction();
		PExp exp = node.getExp();
		ADefPatternBind pattern = node.getPatternBind();
		SSeqType seqType = node.getSeqType();
		
		node.setType(new AActionType());
		return node.getType();
	}


	@Override
	public PType caseAForIndexStatementAction(AForIndexStatementAction node,
			org.overture.typechecker.TypeCheckInfo question) throws AnalysisException {

		CmlTypeCheckInfo cmlEnv = getTypeCheckInfo(question);
		if (cmlEnv == null)
		{
			node.setType(issueHandler.addTypeError(node, TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(""+node)));
			return node.getType();
		}

		PAction action = node.getAction();
		PExp byExp = node.getBy();
		PExp toExp = node.getTo();
		LexNameToken var = node.getVar();



		// Get the type of the by expression
		PType byExpType = null;
		if (byExp != null)
		{
			byExpType = byExp.apply(parentChecker,question);
			if (!TCDeclAndDefVisitor.successfulType(byExpType)){
				node.setType(issueHandler.addTypeError(byExp, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(byExp+"")));
				return node.getType();
			}
		}

		// Get the type of the to expression
		PType toExpType = toExp.apply(parentChecker,question);
		if (!TCDeclAndDefVisitor.successfulType(toExpType)){
			node.setType(issueHandler.addTypeError(toExp, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(toExp+"")));
			return node.getType();
		}


		// Add the local variable 
		ALocalDefinition localVar = AstFactory.newALocalDefinition(var.getLocation(), var, NameScope.LOCAL, toExpType);
		CmlTypeCheckInfo newQuestion = (CmlTypeCheckInfo) cmlEnv.newScope(question, question.env.getEnclosingDefinition());
		newQuestion.addVariable(localVar.getName(), localVar);

		// Type chec the action in this new environment
		PType actionType = action.apply(parentChecker,newQuestion);
		if (!TCDeclAndDefVisitor.successfulType(actionType)){
			node.setType(issueHandler.addTypeError(byExp, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(action+"")));
			return node.getType();
		}

		node.setType(new AActionType());
		return node.getType();
	}


	@Override
	public PType caseAChannelRenamingAction(AChannelRenamingAction node,
			org.overture.typechecker.TypeCheckInfo question) throws AnalysisException {

		PAction action = node.getAction();

		SRenameChannelExp renameExp = node.getRenameExpression();

		return new AActionType(node.getLocation(), true);
	}


	@Override
	public PType caseAWaitAction(AWaitAction node, org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		PExp timedExp = node.getExpression();
		PType timedExpType = timedExp.apply(parentChecker,question);

		if (!TCDeclAndDefVisitor.successfulType(timedExpType))
		{
			node.setType(issueHandler.addTypeError(timedExp, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(""+timedExp)));
			return node.getType();
		}


		if (!typeComparator.isSubType(timedExpType, new AIntNumericBasicType()))
		{
			node.setType(issueHandler.addTypeError(timedExpType, TypeErrorMessages.TIME_UNIT_EXPRESSION_MUST_BE_NAT.customizeMessage(timedExp+"")));
			return node.getType();
		}

		node.setType(new AActionType(node.getLocation(), true));
		return node.getType();
	}


	@Override
	public PType caseACaseAlternativeAction(ACaseAlternativeAction node,
			org.overture.typechecker.TypeCheckInfo question) throws AnalysisException {


		LinkedList<PDefinition> defs = node.getDefs();
		LinkedList<PPattern> ptrn = node.getPattern();
		PAction res = node.getResult();



		if (node.getDefs().size() == 0) {
			PPattern pattern = ptrn.get(0);
			node.setDefs(new LinkedList<PDefinition>());
			PPatternAssistantTC.typeResolve(pattern, (QuestionAnswerAdaptor<org.overture.typechecker.TypeCheckInfo, PType>) parentChecker,
					question);

			if (ptrn.get(0) instanceof AExpressionPattern) {
				// Only expression patterns need type checking...
				AExpressionPattern ep = (AExpressionPattern) pattern;
				ep.getExp().apply(parentChecker, question);
			}

			PPatternAssistantTC.typeResolve(pattern, (QuestionAnswerAdaptor<org.overture.typechecker.TypeCheckInfo, PType>) parentChecker,
					question);

			ACasesStatementAction stm = (ACasesStatementAction) node.parent();
			node.getDefs().addAll(
					PPatternAssistantTC.getDefinitions(pattern, stm
							.getExp().getType(), NameScope.LOCAL));
		}

		PDefinitionListAssistantTC.typeCheck(node.getDefs(), (QuestionAnswerAdaptor<org.overture.typechecker.TypeCheckInfo, PType>)  parentChecker,
				question);


		question.contextSet(CmlTypeCheckInfo.class, getTypeCheckInfo (question) );
		Environment local = new FlatCheckedEnvironment(node.getDefs(),
				question.env, question.scope);
		PType r = node.getResult().apply(parentChecker,
				new org.overture.typechecker.TypeCheckInfo(local, question.scope));
		question.contextRem(CmlTypeCheckInfo.class);
		local.unusedCheck();


		return r;
	}


	@Override
	public PType caseACasesStatementAction(ACasesStatementAction node,
			org.overture.typechecker.TypeCheckInfo question) throws AnalysisException {

		LinkedList<ACaseAlternativeAction> cases = node.getCases();
		for(ACaseAlternativeAction altAction : cases)
		{
			PType caseType = altAction.apply(parentChecker,question);
			if (!TCDeclAndDefVisitor.successfulType(caseType))
			{
				node.setType(issueHandler.addTypeError(node, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(altAction+"")));
				return node.getType();
			}
		}
		PExp exp = node.getExp();

		PType expType = exp.apply(parentChecker,question);
		if (TCDeclAndDefVisitor.successfulType(expType))
		{
			node.setType(issueHandler.addTypeError(node, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(exp+"")));
			return node.getType();

		}

		node.setType(new AStatementType(node.getLocation(), true));
		return node.getType();
	}


	/*
	 * Get the type check info object (context) for a CML context
	 * given a Overture one.
	 */
	private static CmlTypeCheckInfo getTypeCheckInfo(org.overture.typechecker.TypeCheckInfo question)
	{
		if (question instanceof CmlTypeCheckInfo)
			return (CmlTypeCheckInfo)question;

		return question.contextGet(CmlTypeCheckInfo.class);
	}


	@Override
	public PType caseAMuAction(AMuAction node, org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {


		// extract elements from the node
		LinkedList<LexIdentifierToken> ids = node.getIdentifiers();
		LinkedList<PAction> acts = node.getActions();

		// get the enclosing definition if any
		PDefinition enclosingDef = question.env.getEnclosingDefinition();

		// get the CML context we are in
		CmlTypeCheckInfo info = getTypeCheckInfo(question);

		// 
		CmlTypeCheckInfo newQuestion = (CmlTypeCheckInfo)info.newScope(info, enclosingDef);

		// add IDs to the environment
		for(LexIdentifierToken id : ids)
		{
			newQuestion.addType(id, new ATypeDefinition(node.getLocation(), NameScope.LOCAL, false, null, new AAccessSpecifierAccessSpecifier(new APrivateAccess(), null,null), new AActionType(node.getLocation(),true), Pass.DEFS, null, null, null, null, false,new LexNameToken("", id)));			
		}

		// check the actions
		for(PAction act : acts)
		{
			PType actType = act.apply(parentChecker,newQuestion);
			if (!TCDeclAndDefVisitor.successfulType(actType))
			{
				//	node.setType(issueHandler.addTypeError(act, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(act+"")));
				issueHandler.addTypeWarning(node, "Not all identifiers are checked.");
			}

		}

		node.setType(new AActionType());
		return node.getType();
	}


	@SuppressWarnings("deprecation")
	@Override
	public PType caseAChaosAction(AChaosAction node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {
		node.setType( new AActionType(node.getLocation(), true));
		return node.getType();
	}

	@SuppressWarnings("deprecation")
	@Override
	public PType caseATimedInterruptAction(ATimedInterruptAction node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {

		PAction left = node.getLeft();
		PAction right = node.getRight();
		PExp timeExp = node.getTimeExpression();

		PType leftType = left.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(leftType))
			return issueHandler.addTypeError(left,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
					.customizeMessage(left + ""));

		PType rightType = right.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(rightType))
			return issueHandler.addTypeError(right,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
					.customizeMessage(right + ""));

		PType timeExpType = timeExp.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(timeExpType))
			return issueHandler.addTypeError(timeExp,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
					.customizeMessage(timeExp + ""));
		if (!typeComparator.isSubType(timeExpType, new ANatNumericBasicType()))
			return issueHandler.addTypeError(timeExp,
					TypeErrorMessages.TIME_UNIT_EXPRESSION_MUST_BE_NAT
					.customizeMessage(node + "", timeExpType + ""));

		return new AActionType(node.getLocation(), true);
	}

	@SuppressWarnings("deprecation")
	@Override
	public PType caseASequentialCompositionReplicatedAction(
			ASequentialCompositionReplicatedAction node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {

		PAction replicatedAction = node.getReplicatedAction();
		LinkedList<SSingleDeclaration> decls = node.getReplicationDeclaration();

		for (SSingleDeclaration decl : decls) {
			PType declType = decl.apply(parentChecker, question);
			if (!TCDeclAndDefVisitor.successfulType(declType))
				return issueHandler.addTypeError(decl,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
						.customizeMessage(decl + ""));

			issueHandler.addTypeWarning(decl,
					"This declaration should expand the environment: " + decl);
		}

		PType replicatedActionType = replicatedAction.apply(parentChecker,
				question);
		if (!TCDeclAndDefVisitor.successfulType(replicatedActionType))
			return issueHandler.addTypeError(replicatedAction,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
					.customizeMessage(replicatedAction + ""));

		return new AActionType(node.getLocation(), true);
	}

	@Override
	public PType caseAAssignmentCallStatementAction(
			AAssignmentCallStatementAction node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {
		return super.caseAAssignmentCallStatementAction(node, question);
	}

	@SuppressWarnings("deprecation")
	@Override
	public PType caseAWhileStatementAction(AWhileStatementAction node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {

		PExp condExp = node.getCondition();
		PAction actionBody = node.getAction();

		PType condExpType = condExp.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(condExpType))
			return issueHandler.addTypeError(condExp,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
					.customizeMessage(condExp + ""));

		PType actionBodyType = actionBody.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(actionBodyType))
			return issueHandler.addTypeError(actionBodyType,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
					.customizeMessage(actionBody + ""));

		return new AActionType(node.getLocation(), true);
	}

	private final CmlTypeChecker parentChecker;
	private final TypeIssueHandler issueHandler;
	private final TypeComparator typeComparator;

	public TCActionVisitor(CmlTypeChecker parentChecker,
			TypeIssueHandler issueHandler, TypeComparator typeComparator) {
		this.parentChecker = parentChecker;
		this.issueHandler = issueHandler;
		this.typeComparator = typeComparator;
	}

	@SuppressWarnings("deprecation")
	@Override
	public PType caseAAlphabetisedParallelismParallelAction(
			AAlphabetisedParallelismParallelAction node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {

		PAction leftAction = node.getLeftAction();
		PExp leftChanSet = node.getLeftChanSetExpression();
		PExp leftNameSet = node.getLeftNameSetExpression();

		PAction rightAction = node.getRightAction();
		PExp rightChanSet = node.getRightChanSetExpression();
		PExp rightNameSet = node.getRightNameSetExpression();

		PType leftActionType = leftAction.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(leftActionType))
			return issueHandler.addTypeError(leftAction,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
					.customizeMessage(leftAction + ""));

		PType leftChanSetType = leftChanSet.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(leftChanSetType))
			return issueHandler.addTypeError(leftChanSet,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
					.customizeMessage(leftChanSet + ""));

		PType leftNameSetType = leftNameSet.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(leftNameSetType))
			return issueHandler.addTypeError(leftNameSet,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
					.customizeMessage(leftNameSet + ""));

		PType rightActionType = rightAction.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(rightActionType))
			return issueHandler.addTypeError(rightAction,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
					.customizeMessage(rightAction + ""));

		PType rightChanSetType = rightChanSet.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(rightChanSetType))
			return issueHandler.addTypeError(rightChanSet,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
					.customizeMessage(rightChanSet + ""));

		PType rightNameSetType = rightNameSet.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(rightNameSetType))
			return issueHandler.addTypeError(rightNameSet,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
					.customizeMessage(rightNameSet + ""));

		return new AProcessType(node.getLocation(), true);
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

		CmlTypeCheckInfo newQ = getTypeCheckInfo(question);


		PDefinition actionDef = newQ.lookupVariable(node.getName());

		PType type = newQ.lookupType(node.getName());
		if (type != null) return type;

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

		if (question instanceof CmlTypeCheckInfo) {
			CmlTypeCheckInfo cmlQuestion = (CmlTypeCheckInfo) question;

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

		return node.getType();
	}



	@Override
	public PType caseAStopAction(AStopAction node, org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {
		return new AActionType(node.getLocation(), true);
	}


	@SuppressWarnings("deprecation")
	@Override
	public PType caseACallStatementAction(ACallStatementAction node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {

		LexNameToken name = node.getName();
		PDefinition callee = question.env.findName(name, NameScope.GLOBAL);

		if (callee == null)
			return issueHandler.addTypeError(
					node,
					TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage(name
							+ ""));

		LinkedList<PExp> args = node.getArgs();
		List<PType> argTypes = new LinkedList<PType>();
		for (PExp arg : args) {
			PType argType = arg.apply(parentChecker, question);
			if (!(TCDeclAndDefVisitor.successfulType(argType)))
				return issueHandler.addTypeError(arg,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
						.customizeMessage(arg + ""));
			argTypes.add(argType);
		}

		// TODO check actual arg types agains the function or operation
		// parameter types in callee.getType
		issueHandler.addTypeWarning(node,
				"Incomplete type checking arguments may be wrong.");

		// TODO Auto-generated method stub
		return new AActionType(node.getLocation(), true);
	}

	@SuppressWarnings("deprecation")
	@Override
	public PType caseASynchronousParallelismParallelAction(
			ASynchronousParallelismParallelAction node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {

		PAction leftAction = node.getLeftAction();
		PExp leftNameSet = node.getLeftNameSetExpression();

		PAction rightAction = node.getRightAction();
		PExp rightNameSet = node.getRightNameSetExpression();

		PType leftActionType = leftAction.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(leftActionType))
			return issueHandler.addTypeError(leftActionType,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
					.customizeMessage(leftAction + ""));

		if (leftNameSet != null)
		{
			PType leftNameSetType = leftNameSet.apply(parentChecker, question);
			if (!TCDeclAndDefVisitor.successfulType(leftNameSetType))
				return issueHandler.addTypeError(leftNameSet,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
						.customizeMessage(leftNameSet + ""));
		}

		PType rightActionType = rightAction.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(rightActionType))
			return issueHandler.addTypeError(leftActionType,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
					.customizeMessage(rightAction + ""));

		if (rightNameSet != null)
		{
			PType rightNameSetType = rightNameSet.apply(parentChecker, question);
			if (!TCDeclAndDefVisitor.successfulType(rightNameSetType))
				return issueHandler.addTypeError(rightNameSet,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
						.customizeMessage(rightNameSet + ""));
		}
		return new AActionType(node.getLocation(), true);
	}

}
