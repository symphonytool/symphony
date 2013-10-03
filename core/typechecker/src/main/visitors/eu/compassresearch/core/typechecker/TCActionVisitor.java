package eu.compassresearch.core.typechecker;

import static eu.compassresearch.core.typechecker.CmlTCUtil.successfulType;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.APrivateAccess;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.AApplyExp;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.ADefPatternBind;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.ATuplePattern;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.statements.AExternalClause;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.typechecker.Pass;
import org.overture.ast.types.AAccessSpecifierAccessSpecifier;
import org.overture.ast.types.ABooleanBasicType;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.ANatNumericBasicType;
import org.overture.ast.types.AOperationType;
import org.overture.ast.types.AProductType;
import org.overture.ast.types.ASetType;
import org.overture.ast.types.AUnknownType;
import org.overture.ast.types.AUnresolvedType;
import org.overture.ast.types.AVoidType;
import org.overture.ast.types.PType;
import org.overture.ast.types.SSeqType;
import org.overture.typechecker.Environment;
import org.overture.typechecker.TypeCheckInfo;

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
import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.AResParametrisation;
import eu.compassresearch.ast.actions.AReturnStatementAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASequentialCompositionReplicatedAction;
import eu.compassresearch.ast.actions.ASignalCommunicationParameter;
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
import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.AExpressionSingleDeclaration;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.ACmlClassDefinition;
import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AOperationsDefinition;
import eu.compassresearch.ast.definitions.SCmlOperationDefinition;
import eu.compassresearch.ast.expressions.AUnresolvedPathExp;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.expressions.SRenameChannelExp;
import eu.compassresearch.ast.lex.LexIdentifierToken;
import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.ast.types.AActionType;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.ast.types.AChansetType;
import eu.compassresearch.ast.types.AErrorType;
import eu.compassresearch.ast.types.ANamesetsType;
import eu.compassresearch.ast.types.AProcessType;
import eu.compassresearch.ast.types.AStatementType;
import eu.compassresearch.core.typechecker.api.TypeComparator;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;
import eu.compassresearch.core.typechecker.api.TypeWarningMessages;

/**
 * @author rwl
 * 
 */
@SuppressWarnings("serial")
class TCActionVisitor extends
		QuestionAnswerCMLAdaptor<org.overture.typechecker.TypeCheckInfo, PType> {

	@Override
	public PType caseAElseIfStatementAction(AElseIfStatementAction node,
			TypeCheckInfo question) throws AnalysisException {

		PAction thenAction = node.getThenStm();
		PExp elseIfExp = node.getElseIf();

		PType elseIfExpType = elseIfExp.apply(parentChecker, question);
		if (!successfulType(elseIfExpType)) {
			node.setType(issueHandler.addTypeError(elseIfExp,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(elseIfExp + "")));
			return node.getType();
		}

		if (!typeComparator.isSubType(elseIfExpType,
				new ABooleanBasicType(node.getLocation(), true))) {
			node.setType(issueHandler.addTypeError(node,
					TypeErrorMessages.INCOMPATIBLE_TYPE.customizeMessage(
							"bool", "" + elseIfExpType)));
			return node.getType();
		}

		PType thenActionType = thenAction.apply(parentChecker, question);
		if (!successfulType(thenActionType)) {
			node.setType(issueHandler.addTypeError(node,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + thenAction)));
			return node.getType();
		}

		node.setType(new AActionType(node.getLocation(), true));
		return node.getType();
	}

	@Override
	public PType caseAValParametrisation(AValParametrisation node,
			TypeCheckInfo question) throws AnalysisException {

		ATypeSingleDeclaration decl = node.getDeclaration();
		List<PDefinition> defs = new LinkedList<PDefinition>();

		for (ILexIdentifierToken id : decl.getIdentifiers()) {
			ILexNameToken name = new LexNameToken("", id);
			ALocalDefinition localDef = AstFactory.newALocalDefinition(
					node.getLocation(), name, NameScope.LOCAL, decl.getType());
			defs.add(localDef);
		}

		PType res = new AActionType(node.getLocation(), true);
		res.setDefinitions(defs);
		return res;
	}

	@Override
	public PType caseAResParametrisation(AResParametrisation node,
			TypeCheckInfo question) throws AnalysisException {

		ATypeSingleDeclaration decl = node.getDeclaration();
		List<PDefinition> defs = new LinkedList<PDefinition>();

		for (ILexIdentifierToken id : decl.getIdentifiers()) {
			ILexNameToken name = new LexNameToken("", id);
			ALocalDefinition localDef = AstFactory.newALocalDefinition(
					node.getLocation(), name, NameScope.LOCAL, decl.getType());
			defs.add(localDef);
		}
		PType res = new AActionType(node.getLocation(), true);
		res.setDefinitions(defs);
		return res;
	}

	@Override
	public PType caseAIfStatementAction(AIfStatementAction node,
			TypeCheckInfo question) throws AnalysisException {

		PAction thenAction = node.getThenStm();
		PAction elseAction = node.getElseStm();
		PExp testExp = node.getIfExp();
		LinkedList<AElseIfStatementAction> elseIfs = node.getElseIf();

		PType testExpType = testExp.apply(parentChecker, question);
		if (!successfulType(testExpType)) {
			node.setType(issueHandler.addTypeError(testExp,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(testExp + "")));
			return node.getType();
		}

		if (!typeComparator.compatible(new ABooleanBasicType(), testExpType)) {
			node.setType(issueHandler.addTypeError(testExp,
					TypeErrorMessages.INCOMPATIBLE_TYPE.customizeMessage(
							"Boolean", "" + testExpType)));
			return node.getType();
		}

		PType thenActionType = thenAction.apply(parentChecker, question);
		if (!successfulType(thenActionType)) {
			node.setType(issueHandler.addTypeError(thenAction,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(thenAction + "")));
			return node.getType();

		}

		// AKM: The else case is optional
		if (elseAction != null) {
			PType elseActionType = elseAction.apply(parentChecker, question);
			if (!successfulType(elseActionType)) {
				node.setType(issueHandler.addTypeError(elseAction,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage(elseAction + "")));
				return node.getType();

			}
		}

		for (AElseIfStatementAction elseIf : elseIfs) {
			PType elseIfType = elseIf.apply(parentChecker, question);
			if (!successfulType(elseIfType)) {
				node.setType(issueHandler.addTypeError(elseIf,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage(elseIf + "")));
				return node.getType();
			}
		}

		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseAUntimedTimeoutAction(AUntimedTimeoutAction node,
			TypeCheckInfo question) throws AnalysisException {

		PAction left = node.getLeft();
		PAction right = node.getRight();

		PType leftType = left.apply(parentChecker, question);
		if (!successfulType(leftType)) {
			node.setType(issueHandler.addTypeError(left,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + left)));
			return node.getType();
		}

		PType rightType = right.apply(parentChecker, question);
		if (!successfulType(rightType)) {
			node.setType(issueHandler.addTypeError(right,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(right + "")));
			return node.getType();
		}

		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseATimeoutAction(ATimeoutAction node, TypeCheckInfo question)
			throws AnalysisException {

		PAction left = node.getLeft();
		PAction right = node.getRight();
		PExp timedExp = node.getTimeoutExpression();

		PType leftType = left.apply(parentChecker, question);
		if (!successfulType(leftType)) {
			node.setType(issueHandler.addTypeError(left,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(left + "")));
			return node.getType();
		}

		PType rightType = right.apply(parentChecker, question);
		if (!successfulType(rightType)) {
			node.setType(issueHandler.addTypeError(right,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(right + "")));
			return node.getType();
		}

		PType timedExpType = timedExp.apply(parentChecker, question);
		if (!successfulType(timedExpType)) {
			node.setType(issueHandler.addTypeError(timedExp,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(timedExp + "")));
			return node.getType();
		}

		if (!typeComparator.isSubType(timedExpType, new AIntNumericBasicType())) {
			node.setType(issueHandler
					.addTypeError(timedExp,
							TypeErrorMessages.TIME_UNIT_EXPRESSION_MUST_BE_NAT
									.customizeMessage(timedExp + "",
											timedExpType + "")));
			return node.getType();
		}

		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseAExternalClause(AExternalClause node,
			TypeCheckInfo question) throws AnalysisException {

		LinkedList<ILexNameToken> ids = node.getIdentifiers();
		for (ILexNameToken id : ids) {
			PDefinition def = CmlTCUtil.findDefByAllMeans(question, id);
			if (def == null) {
				node.setType(issueHandler.addTypeError(
						node,
						TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage(""
								+ id)));
				return node.getType();
			}

			issueHandler.addTypeWarning(def,
					TypeWarningMessages.INCOMPLETE_TYPE_CHECKING
							.customizeMessage(def + ""));
		}

		return new AActionType();
	}

	@Override
	public PType caseASpecificationStatementAction(
			ASpecificationStatementAction node, TypeCheckInfo question)
			throws AnalysisException {

		CmlTypeCheckInfo cmlEnv = getTypeCheckInfo(question);
		if (cmlEnv == null) {
			node.setType(issueHandler.addTypeError(
					node,
					TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(node
							+ "")));
			return node.getType();
		}
		CmlTypeCheckInfo prePostEnv = cmlEnv.newScope();
		LinkedList<AExternalClause> externals = node.getExternals();
		for (AExternalClause extClause : externals) {
			PType extClauseType = extClause.apply(parentChecker, question);
			if (!successfulType(extClauseType)) {
				node.setType(issueHandler.addTypeError(extClause,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage(extClause + "")));
				return node.getType();
			}
			LinkedList<ILexNameToken> ids = extClause.getIdentifiers();
			/*
			 * for (ILexIdentifierToken id : ids) { LexNameToken localName = new
			 * LexNameToken("", id); ALocalDefinition localDef =
			 * AstFactory.newALocalDefinition( node.getLocation(), localName,
			 * NameScope.LOCAL, extClauseType); prePostEnv.addVariable(id,
			 * localDef); }
			 */
		}

		PExp preCond = node.getPrecondition();
		PType preCondType = preCond.apply(parentChecker, prePostEnv);
		if (!successfulType(preCondType)) {
			node.setType(issueHandler.addTypeError(preCond,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(preCond + "")));
			return node.getType();
		}

		PExp postCond = node.getPostcondition();
		PType postCondType = postCond.apply(parentChecker, prePostEnv);
		if (successfulType(postCondType))

			node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseAInternalChoiceReplicatedAction(
			AInternalChoiceReplicatedAction node, TypeCheckInfo question)
			throws AnalysisException {

		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);

		CmlTypeCheckInfo actionEnv = cmlEnv.newScope();

		PAction repAction = node.getReplicatedAction();
		LinkedList<PSingleDeclaration> repDecl = node
				.getReplicationDeclaration();
		for (PSingleDeclaration d : repDecl) {
			PType type = d.apply(parentChecker, question);
			if (!successfulType(type)) {
				node.setType(issueHandler.addTypeError(d,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage(d + "")));
				return node.getType();
			}

			for (PDefinition newDef : type.getDefinitions()) {
				actionEnv.addVariable(newDef.getName(), newDef);
			}

		}

		PType actionType = repAction.apply(parentChecker, actionEnv);
		if (!successfulType(actionType)) {
			node.setType(issueHandler.addTypeError(repAction,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + repAction)));
			return node.getType();
		}

		node.setType(new AActionType());
		return node.getType();

	}

	@Override
	public PType caseAGeneralisedParallelismReplicatedAction(
			AGeneralisedParallelismReplicatedAction node, TypeCheckInfo question)
			throws AnalysisException {

		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);
		if (cmlEnv == null) {
			node.setType(issueHandler.addTypeError(
					node,
					TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(""
							+ node)));
			return node.getType();
		}

		//that it can't access the class/process state is needed
		CmlTypeCheckInfo actionEnv = cmlEnv.emptyScope();
		
		//CmlTypeCheckInfo actionEnv = cmlEnv.newScope();
		actionEnv.scope = NameScope.NAMESANDANYSTATE;
		// TODO RWL: What is the semantics of this?
		PVarsetExpression csexp = node.getChansetExpression();
		PType csexpType = csexp.apply(parentChecker, question);
		if (!successfulType(csexpType)) {
			node.setType(issueHandler.addTypeError(node,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + node)));
			return node.getType();
		}

		if (csexpType instanceof AUnknownType) {
			csexpType = new AChansetType(node.getLocation(), true);
		}

		if (!(csexpType instanceof AChansetType)) {
			node.setType(issueHandler.addTypeError(
					node,
					TypeErrorMessages.EXPECTED_A_CHANNELSET.customizeMessage(""
							+ csexpType)));
			return node.getType();
		}

		for (PDefinition chanDef : csexpType.getDefinitions()) {
			if (!(chanDef instanceof AChannelNameDefinition)) {
				node.setType(issueHandler
						.addTypeError(
								node,
								TypeErrorMessages.TYPE_CHECK_INTERNAL_FAILURE
										.customizeMessage("Expected a Channel and got something of type AChannelType, however it is not AChannelNameDefinition.")));
				return node.getType();
			}
			AChannelNameDefinition chanNameDef = (AChannelNameDefinition) chanDef;
			for (ILexIdentifierToken id : chanNameDef.getSingleType()
					.getIdentifiers()) {
				actionEnv.addChannel(id, chanDef);
			}
		}

		PVarsetExpression sexp = node.getNamesetExpression();
		PType sexpType = sexp.apply(parentChecker, question);
		if (!successfulType(sexpType)) {
			node.setType(issueHandler.addTypeError(node,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + node)));
			return node.getType();
		}

		if (sexpType instanceof AUnknownType) {
			sexpType = new ANamesetsType(node.getLocation(), true);
		}

		if (!(sexpType instanceof ANamesetsType)) {
			node.setType(issueHandler.addTypeError(
					node,
					TypeErrorMessages.EXPECTED_A_NAMESET.customizeMessage(""
							+ sexpType)));
			return node.getType();
		}

		for (PDefinition stateDef : sexpType.getDefinitions()) {
			actionEnv.addVariable(stateDef.getName(), stateDef);
		}

		PAction repAction = node.getReplicatedAction();

		LinkedList<PSingleDeclaration> repDecls = node
				.getReplicationDeclaration();

		for (PSingleDeclaration decl : repDecls) {

			if (decl instanceof AExpressionSingleDeclaration) {
				AExpressionSingleDeclaration singleDecl = (AExpressionSingleDeclaration) decl;
				PExp exp = singleDecl.getExpression();
				PType expType = exp.apply(parentChecker, question);
				if (!successfulType(expType)) {
					node.setType(issueHandler.addTypeError(exp,
							TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
									.customizeMessage("" + exp)));
					return node.getType();
				}

				if (expType instanceof ASetType) {
					ASetType st = (ASetType) expType;
					expType = st.getSetof();
				}

				LinkedList<ILexIdentifierToken> ids = singleDecl
						.getIdentifiers();
				for (ILexIdentifierToken id : ids) {
					LexNameToken name = new LexNameToken("", id);

					ALocalDefinition def = AstFactory.newALocalDefinition(
							id.getLocation(), name, NameScope.LOCAL, expType);
					actionEnv.addVariable(name, def);
				}
			}

			if (decl instanceof ATypeSingleDeclaration) {
				ATypeSingleDeclaration singleDecl = (ATypeSingleDeclaration) decl;
				LinkedList<ILexIdentifierToken> ids = singleDecl
						.getIdentifiers();
				for (ILexIdentifierToken id : ids) {
					LexNameToken name = new LexNameToken("", id);
					ALocalDefinition def = AstFactory.newALocalDefinition(
							id.getLocation(), name, singleDecl.getNameScope(),
							singleDecl.getType());
					actionEnv.addVariable(name, def);
				}
			}
		}

		PType repActionType = repAction.apply(parentChecker, actionEnv);
		if (!successfulType(repActionType)) {
			node.setType(issueHandler.addTypeError(repAction,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + repAction)));
			return node.getType();
		}

		issueHandler.addTypeWarning(node,
				TypeWarningMessages.INCOMPLETE_TYPE_CHECKING
						.customizeMessage("" + node));

		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseAExternalChoiceReplicatedAction(
			AExternalChoiceReplicatedAction node, TypeCheckInfo question)
			throws AnalysisException {

		PAction action = node.getReplicatedAction();
		LinkedList<PSingleDeclaration> decl = node.getReplicationDeclaration();

		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);

		CmlTypeCheckInfo actionEnv = cmlEnv.newScope();

		for (PSingleDeclaration d : decl) {
			PType declType = d.apply(parentChecker, question);
			if (!successfulType(declType)) {
				node.setType(issueHandler.addTypeError(d,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage(d + "")));
				return node.getType();
			}
			for (PDefinition def : declType.getDefinitions()) {
				actionEnv.addVariable(def.getName(), def);
			}
		}

		PType actionType = action.apply(parentChecker, actionEnv);
		if (!successfulType(actionType)) {
			node.setType(issueHandler.addTypeError(action,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + action)));
			return node.getType();
		}

		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseANonDeterministicIfStatementAction(
			ANonDeterministicIfStatementAction node, TypeCheckInfo question)
			throws AnalysisException {

		LinkedList<ANonDeterministicAltStatementAction> alternatives = node
				.getAlternatives();
		for (ANonDeterministicAltStatementAction alt : alternatives) {
			PType altType = alt.apply(parentChecker, question);
			if (!successfulType(altType)) {
				node.setType(issueHandler.addTypeError(alt,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage(alt + "")));
				return node.getType();
			}
		}

		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseANewStatementAction(ANewStatementAction node,
			TypeCheckInfo question) throws AnalysisException {

		List<PType> argtypes = new LinkedList<PType>();

		CmlTypeCheckInfo cmlEnv = getTypeCheckInfo(question);

		// lookup variable
		PExp destVar = node.getDestination();

		if (!(destVar instanceof AVariableExp)) {
			node.setType(issueHandler.addTypeError(
					node,
					TypeErrorMessages.EXPECTED_LVALUE.customizeMessage(""
							+ destVar)));
			return node.getType();
		}

		AVariableExp destVarExp = (AVariableExp) destVar;
		PType destVarExpType = destVarExp.apply(parentChecker, question);
		if (!successfulType(destVarExpType)) {
			node.setType(issueHandler.addTypeError(destVarExp,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(destVarExp + "")));
			return node.getType();
		}

		PType classType = cmlEnv.lookupType(node.getClassName());
		if (classType == null) {
			node.setType(issueHandler.addTypeError(
					node,
					TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage(node
							.getClassName() + "")));
			return node.getType();
		}

		// make sure they match
		if (!typeComparator.isSubType(classType, destVarExpType)) {
			node.setType(issueHandler.addTypeError(node,
					TypeErrorMessages.EXPECTED_SUBTYPE_RELATION
							.customizeMessage(classType.toString(),
									destVarExpType.toString())));
			return node.getType();
		}

		// // typecheck arguments
		for (PExp arg : node.getArgs()) {
			PType pt = arg.apply(parentChecker, cmlEnv);
			if (!successfulType(pt)) {
				node.setType(issueHandler.addTypeError(
						arg,
						TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage(""
								+ arg)));
				return node.getType();
			}
			argtypes.add(pt);
		}

		// find the constructor
		CmlTypeCheckInfo ctorEnv = cmlEnv.newScope();
		for (PDefinition clzDef : classType.getDefinitions()) {
			if (clzDef instanceof AOperationsDefinition) {
				AOperationsDefinition odefs = (AOperationsDefinition) clzDef;
				for (SCmlOperationDefinition cmlOdef : odefs.getOperations()) {
					if (cmlOdef instanceof AExplicitCmlOperationDefinition) {
						AExplicitCmlOperationDefinition ctorcand = (AExplicitCmlOperationDefinition) cmlOdef;
						if (ctorcand.getIsConstructor()) {
							ctorEnv.addVariable(ctorcand.getName(), ctorcand);
						}
					}
				}
			}
			else if (clzDef instanceof AExplicitCmlOperationDefinition) {
				AExplicitCmlOperationDefinition ctorcand = (AExplicitCmlOperationDefinition) clzDef;
				if (ctorcand.getIsConstructor()) {
					ctorEnv.addVariable(ctorcand.getName(), ctorcand);
				}
			}
		}

		ACallStatementAction callStm = new ACallStatementAction(node
				.getClassName().getLocation(), node.getClassName().clone(),
				node.getArgs());
		PType applyCtorExpType = callStm.apply(parentChecker, ctorEnv);
		if (!successfulType(applyCtorExpType)) {
			node.setType(issueHandler.addTypeError(node,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(node + "")));
			return node.getType();
		}

		// set the class definition
		node.setClassdef((ACmlClassDefinition) cmlEnv.lookup(node.getClassName(),
				ACmlClassDefinition.class));

		// All done!
		node.setType(new AActionType());
		return node.getType();

	}

	@Override
	public PType caseAMultipleGeneralAssignmentStatementAction(
			AMultipleGeneralAssignmentStatementAction node,
			TypeCheckInfo question) throws AnalysisException {

		// extract sub-stuff
		LinkedList<ASingleGeneralAssignmentStatementAction> assigns = node
				.getAssignments();

		CmlTypeCheckInfo cmlEnv = getTypeCheckInfo(question);

		if (cmlEnv == null) {
			node.setType(issueHandler.addTypeError(
					node,
					TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(""
							+ node)));
			return node.getType();
		}

		// type-check the "let" definitions
		CmlTypeCheckInfo newCmlEnv = cmlEnv.newScope();

		for (ASingleGeneralAssignmentStatementAction assign : assigns) {
			PType pt = assign.apply(parentChecker, newCmlEnv);
			if (!successfulType(pt)) {
				node.setType(issueHandler.addTypeError(assign,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage("" + assign)));
				return node.getType();
			}
		}

		// All done!
		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseALetStatementAction(ALetStatementAction node,
			TypeCheckInfo question) throws AnalysisException {
		// Extract sub-stuff
		PAction action = node.getAction();
		LinkedList<PDefinition> localDefs = node.getLocalDefinitions();
		CmlTypeCheckInfo cmlEnv = getTypeCheckInfo(question);

		if (cmlEnv == null) {
			node.setType(issueHandler.addTypeError(
					node,
					TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(""
							+ node)));
			return node.getType();
		}

		// type-check the "let" definitions
		CmlTypeCheckInfo newCmlEnv = cmlEnv.newScope();

		for (PDefinition pd : localDefs) {
			PType pt = pd.apply(parentChecker, newCmlEnv);
			if (!successfulType(pt)) {
				node.setType(issueHandler.addTypeError(pd,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage("" + pd)));
				return node.getType();
			}

			for (PDefinition d : pt.getDefinitions()) {
				newCmlEnv.addVariable(d.getName(), d);
			}
		}

		// type check sub-action
		PType actionType = action.apply(parentChecker, newCmlEnv);
		if (!successfulType(actionType)) {
			node.setType(issueHandler.addTypeError(action,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + action)));
			return node.getType();
		}

		// All done!
		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseAInterruptAction(AInterruptAction node,
			TypeCheckInfo question) throws AnalysisException {
		// extract sub-stuff
		PAction leftAction = node.getLeft();
		PAction rightAction = node.getRight();

		// type-check sub-actions
		PType leftActionType = leftAction.apply(parentChecker, question);
		if (!successfulType(leftActionType)) {
			node.setType(issueHandler.addTypeError(leftAction,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + leftAction)));
			return node.getType();
		}

		PType rightActionType = rightAction.apply(parentChecker, question);
		if (!successfulType(rightActionType)) {
			node.setType(issueHandler.addTypeError(rightAction,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + rightAction)));
			return node.getType();
		}

		// All done!
		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseAInterleavingParallelAction(
			AInterleavingParallelAction node, TypeCheckInfo question)
			throws AnalysisException {
		// extract sub-stuff
		PAction leftAction = node.getLeftAction();
		PVarsetExpression leftNamesetExp = node.getLeftNamesetExpression();
		PAction rightAction = node.getRightAction();
		PVarsetExpression rightnamesetExp = node.getRightNamesetExpression();

		// type-check sub-actions
		PType leftActionType = leftAction.apply(parentChecker, question);
		if (!successfulType(leftActionType)) {
			node.setType(issueHandler.addTypeError(leftAction,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + leftAction)));
			return node.getType();
		}

		PType rightActionType = rightAction.apply(parentChecker, question);
		if (!successfulType(rightActionType)) {
			node.setType(issueHandler.addTypeError(rightAction,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + rightAction)));
			return node.getType();
		}

		// type-check the namesets
		if (leftNamesetExp != null) {
			PType leftNameSetType = leftNamesetExp.apply(parentChecker,
					question);

			if (!successfulType(leftNameSetType))
				return issueHandler.addTypeError(leftNamesetExp,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage(leftNamesetExp + ""));
		}

		if (rightnamesetExp != null) {
			PType rightNameSetType = rightnamesetExp.apply(parentChecker,
					question);
			if (!successfulType(rightNameSetType))
				return issueHandler.addTypeError(rightnamesetExp,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage(rightnamesetExp + ""));
		}

		// All done!
		node.setType(new AActionType(node.getLocation(), true));
		return node.getType();
	}

	@Override
	public PType caseADeclarationInstantiatedAction(
			ADeclarationInstantiatedAction node, TypeCheckInfo question)
			throws AnalysisException {

		// Extract sub-stuff
		PAction action = node.getAction();
		LinkedList<ATypeSingleDeclaration> declarations = node.getDeclaration();

		// type check sub-action
		PType actionType = action.apply(parentChecker, question);
		if (!successfulType(actionType)) {
			node.setType(issueHandler.addTypeError(action,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + action)));
			return node.getType();
		}

		for (ATypeSingleDeclaration declr : declarations) {
			PType declType = declr.apply(parentChecker, question);
			if (!successfulType(declType))
				return issueHandler.addTypeError(declr,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage(declr + ""));
			issueHandler.addTypeWarning(declr,
					"This declaration should expand the environment: " + declr);
		}

		// All done!
		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseAGeneralisedParallelismParallelAction(
			AGeneralisedParallelismParallelAction node, TypeCheckInfo question)
			throws AnalysisException {

		// Extract sub-stuff
		PVarsetExpression chansetExp = node.getChansetExpression();
		PAction leftAction = node.getLeftAction();
		PVarsetExpression leftNamesetExp = node.getLeftNamesetExpression();
		PAction rightAction = node.getRightAction();
		PVarsetExpression rightnamesetExp = node.getLeftNamesetExpression();

		// type-check sub-actions
		PType leftActionType = leftAction.apply(parentChecker, question);
		if (!successfulType(leftActionType)) {
			node.setType(issueHandler.addTypeError(leftAction,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + leftAction)));
			return node.getType();
		}

		PType rightActionType = rightAction.apply(parentChecker, question);
		if (!successfulType(rightActionType)) {
			node.setType(issueHandler.addTypeError(rightAction,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + rightAction)));
			return node.getType();
		}

		// type-check the chanset
		PType chanSetType = chansetExp.apply(parentChecker, question);
		if (!successfulType(chanSetType))
			return issueHandler.addTypeError(chansetExp,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(chansetExp + ""));

		// type-check the namesets
		if (leftNamesetExp != null) {
			PType leftNameSetType = leftNamesetExp.apply(parentChecker,
					question);
			if (!successfulType(leftNameSetType))
				return issueHandler.addTypeError(leftNamesetExp,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage(leftNamesetExp + ""));
		}

		if (rightnamesetExp != null) {
			PType rightNameSetType = rightnamesetExp.apply(parentChecker,
					question);
			if (!successfulType(rightNameSetType))
				return issueHandler.addTypeError(rightnamesetExp,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage(rightnamesetExp + ""));
		}
		// All done!
		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseAForSetStatementAction(AForSetStatementAction node,
			TypeCheckInfo question) throws AnalysisException {
		// TODO RWL Working on it !

		// extract sub-trees
		PAction action = node.getAction();
		PPattern pattern = node.getPattern();
		PExp set = node.getSet();
		PType patternType = null;

		PType setType = set.apply(parentChecker, question);
		if (!successfulType(setType)) {
			node.setType(issueHandler.addTypeError(set,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + set)));
			return node.getType();
		}

		if (!(setType instanceof ASetType)) {
			node.setType(issueHandler.addTypeError(
					set,
					TypeErrorMessages.SET_TYPE_EXPECTED.customizeMessage(""
							+ set, "" + setType)));
			return node.getType();
		} else
			patternType = setType;

		CmlTypeCheckInfo cmlEnv = getTypeCheckInfo(question);
		CmlTypeCheckInfo localEnv = cmlEnv.newScope();

		PType patternUnknownType = pattern.apply(parentChecker, question);
		if (!successfulType(patternUnknownType)) {
			node.setType(issueHandler.addTypeError(pattern,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(pattern + "")));
			return node.getType();
		}

		if (!(patternUnknownType instanceof AUnresolvedType)) {
			node.setType(issueHandler.addTypeError(pattern,
					TypeErrorMessages.INCOMPATIBLE_TYPE.customizeMessage(
							"Unresolved Type", patternUnknownType + "")));
			return node.getType();
		} else {
			for (PDefinition def : patternUnknownType.getDefinitions()) {
				ASetType asettype = (ASetType) setType;
				def.setType(asettype.getSetof());
				localEnv.addVariable(def.getName(), def);
			}

			PType actionType = action.apply(parentChecker, localEnv);
			if (!successfulType(actionType)) {
				node.setType(issueHandler.addTypeError(node,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage("" + node)));
				return node.getType();
			}
		}

		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseAForSequenceStatementAction(
			AForSequenceStatementAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		PAction action = node.getAction();
		PExp exp = node.getExp();
		ADefPatternBind pattern = node.getPatternBind();
		PType patternType = null;

		// Get an CML environment
		CmlTypeCheckInfo cmlQuestion = getTypeCheckInfo(question);
		if (cmlQuestion == null) {
			node.setType(issueHandler.addTypeError(exp,
					TypeErrorMessages.ILLEGAL_CONTEXT
							.customizeMessage(exp + "")));
			return node.getType();
		}

		// Type check the expression ...
		PType expType = exp.apply(parentChecker, question);
		if (!successfulType(expType)) {
			node.setType(issueHandler.addTypeError(exp,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + exp)));
			return node.getType();
		}

		// ... it has to be a sequence
		if (!(expType instanceof SSeqType)) {
			node.setType(issueHandler.addTypeError(
					exp,
					TypeErrorMessages.SEQ_TYPE_EXPECTED.customizeMessage(exp
							+ "", expType + "")));
			return node.getType();
		} else
			patternType = ((SSeqType) expType).getSeqof();

		// Create an extended local environment
		CmlTypeCheckInfo newEnv = cmlQuestion.newScope();

		pattern.setType(patternType);

		PType patType = pattern.apply(parentChecker, question);
		List<PDefinition> defs = patType.getDefinitions();
		for (PDefinition d : defs) {
			ALocalDefinition localDef = AstFactory.newALocalDefinition(
					pattern.getLocation(), d.getName(), NameScope.LOCAL,
					patternType);
			newEnv.addVariable(d.getName(), localDef);
		}

		// In this new environment lets check the given action
		PType actionType = action.apply(parentChecker, newEnv);
		if (!successfulType(actionType)) {
			node.setType(issueHandler.addTypeError(action,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + action)));
			return node.getType();
		}

		// Interesting stuff goes here !

		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseAForIndexStatementAction(AForIndexStatementAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		CmlTypeCheckInfo cmlEnv = getTypeCheckInfo(question);
		if (cmlEnv == null) {
			node.setType(issueHandler.addTypeError(
					node,
					TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(""
							+ node)));
			return node.getType();
		}

		PAction action = node.getAction();
		PExp byExp = node.getBy();
		PExp toExp = node.getTo();
		ILexNameToken var = node.getVar();

		// Get the type of the by expression
		PType byExpType = null;
		if (byExp != null) {
			byExpType = byExp.apply(parentChecker, question);
			if (!successfulType(byExpType)) {
				node.setType(issueHandler.addTypeError(byExp,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage(byExp + "")));
				return node.getType();
			}
		}

		// Get the type of the to expression
		PType toExpType = toExp.apply(parentChecker, question);
		if (!successfulType(toExpType)) {
			node.setType(issueHandler.addTypeError(toExp,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(toExp + "")));
			return node.getType();
		}

		// Add the local variable
		ALocalDefinition localVar = AstFactory.newALocalDefinition(
				var.getLocation(), var, NameScope.LOCAL, toExpType);
		CmlTypeCheckInfo newQuestion = (CmlTypeCheckInfo) cmlEnv.newScope(
				question, question.env.getEnclosingDefinition());
		newQuestion.addVariable(localVar.getName(), localVar);

		// Type chec the action in this new environment
		PType actionType = action.apply(parentChecker, newQuestion);
		if (!successfulType(actionType)) {
			node.setType(issueHandler.addTypeError(byExp,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(action + "")));
			return node.getType();
		}

		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseAChannelRenamingAction(AChannelRenamingAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		PAction action = node.getAction();

		SRenameChannelExp renameExp = node.getRenameExpression();

		return new AActionType(node.getLocation(), true);
	}

	@Override
	public PType caseAWaitAction(AWaitAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		PExp timedExp = node.getExpression();
		PType timedExpType = timedExp.apply(parentChecker, question);

		if (!successfulType(timedExpType)) {
			node.setType(issueHandler.addTypeError(timedExp,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + timedExp)));
			return node.getType();
		}

		if (!typeComparator.isSubType(timedExpType, new AIntNumericBasicType())) {
			node.setType(issueHandler.addTypeError(timedExpType,
					TypeErrorMessages.TIME_UNIT_EXPRESSION_MUST_BE_NAT
							.customizeMessage(timedExp + "", timedExp + " (" +timedExpType + ")")));
			return node.getType();
		}

		node.setType(new AActionType(node.getLocation(), true));
		return node.getType();
	}

	@Override
	public PType caseACaseAlternativeAction(ACaseAlternativeAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		LinkedList<PDefinition> defs = node.getDefs();
		LinkedList<PPattern> ptrns = node.getPattern();
		PAction res = node.getResult();

		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);
		if (cmlEnv == null)
			return issueHandler.addTypeError(
					node,
					TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(""
							+ node));

		CmlTypeCheckInfo local = cmlEnv.newScope();

		for (PPattern ptrn : ptrns) {
			PType patternType = ptrn.apply(parentChecker, question);
			if (!successfulType(patternType))
				return issueHandler.addTypeError(ptrn,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage("" + ptrn));
			for (PDefinition d : patternType.getDefinitions()) {
				local.addVariable(d.getName(), d);
			}
		}

		// if (node.getDefs().size() == 0) {
		// PPattern pattern = ptrn.get(0);
		// node.setDefs(new LinkedList<PDefinition>());
		// PPatternAssistantTC
		// .typeResolve(
		// pattern,
		// (QuestionAnswerAdaptor<org.overture.typechecker.TypeCheckInfo,
		// PType>) parentChecker,
		// question);
		//
		// if (ptrn.get(0) instanceof AExpressionPattern) {
		// // Only expression patterns need type checking...
		// AExpressionPattern ep = (AExpressionPattern) pattern;
		// ep.getExp().apply(parentChecker, question);
		// }
		//
		// PPatternAssistantTC
		// .typeResolve(
		// pattern,
		// (QuestionAnswerAdaptor<org.overture.typechecker.TypeCheckInfo,
		// PType>) parentChecker,
		// question);
		//
		// ACasesStatementAction stm = (ACasesStatementAction) node.parent();
		// node.getDefs().addAll(
		// PPatternAssistantTC.getDefinitions(pattern, stm.getExp()
		// .getType(), NameScope.LOCAL));
		// }
		//
		// PDefinitionListAssistantTC
		// .typeCheck(
		// node.getDefs(),
		// (QuestionAnswerAdaptor<org.overture.typechecker.TypeCheckInfo,
		// PType>) parentChecker,
		// question);

		question.contextSet(CmlTypeCheckInfo.class, getTypeCheckInfo(question));
		PType r = node.getResult().apply(parentChecker, local);
		question.contextRem(CmlTypeCheckInfo.class);

		return r;
	}

	@Override
	public PType caseACasesStatementAction(ACasesStatementAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		LinkedList<ACaseAlternativeAction> cases = node.getCases();
		for (ACaseAlternativeAction altAction : cases) {
			PType caseType = altAction.apply(parentChecker, question);
			if (!successfulType(caseType)) {
				node.setType(issueHandler.addTypeError(node,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage(altAction + "")));
				return node.getType();
			}
		}
		PExp exp = node.getExp();

		PType expType = exp.apply(parentChecker, question);
		if (!successfulType(expType)) {
			node.setType(issueHandler.addTypeError(node,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(exp + "")));
			return node.getType();

		}

		node.setType(new AActionType(node.getLocation(), true));
		return node.getType();
	}

	/*
	 * Get the type check info object (context) for a CML context given a
	 * Overture one.
	 */
	static CmlTypeCheckInfo getTypeCheckInfo(
			org.overture.typechecker.TypeCheckInfo question) {
		if (question instanceof CmlTypeCheckInfo)
			return (CmlTypeCheckInfo) question;

		return question.contextGet(CmlTypeCheckInfo.class);
	}

	@Override
	public PType caseAMuAction(AMuAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		// extract elements from the node
		LinkedList<ILexIdentifierToken> ids = node.getIdentifiers();
		LinkedList<PAction> acts = node.getActions();

		// get the enclosing definition if any
		PDefinition enclosingDef = question.env.getEnclosingDefinition();

		// get the CML context we are in
		CmlTypeCheckInfo info = getTypeCheckInfo(question);

		//
		CmlTypeCheckInfo newQuestion = (CmlTypeCheckInfo) info.newScope(info,
				enclosingDef);

		// add IDs to the environment
		for (ILexIdentifierToken id : ids) {
			newQuestion.addType(id, new ATypeDefinition(node.getLocation(),
					NameScope.LOCAL, false, null,
					new AAccessSpecifierAccessSpecifier(new APrivateAccess(),
							null, null), new AActionType(node.getLocation(),
							true), Pass.DEFS, null, null, null, null, false,
					new LexNameToken("", id)));
		}

		// check the actions
		for (PAction act : acts) {
			PType actType = act.apply(parentChecker, newQuestion);
			if (!successfulType(actType)) {
				// node.setType(issueHandler.addTypeError(act,
				// TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(act+"")));
				issueHandler.addTypeWarning(node,
						"Not all identifiers are checked.");
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
		node.setType(new AActionType(node.getLocation(), true));
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
		if (!successfulType(leftType))
			return issueHandler.addTypeError(left,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(left + ""));

		PType rightType = right.apply(parentChecker, question);
		if (!successfulType(rightType))
			return issueHandler.addTypeError(right,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(right + ""));

		PType timeExpType = timeExp.apply(parentChecker, question);
		if (!successfulType(timeExpType))
			return issueHandler.addTypeError(timeExp,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(timeExp + ""));
		if (!typeComparator.isSubType(timeExpType, new ANatNumericBasicType()))
			return issueHandler.addTypeError(timeExp,
					TypeErrorMessages.TIME_UNIT_EXPRESSION_MUST_BE_NAT
							.customizeMessage(timeExp + "", timeExpType + ""));

		return new AActionType(node.getLocation(), true);
	}

	@SuppressWarnings("deprecation")
	@Override
	public PType caseASequentialCompositionReplicatedAction(
			ASequentialCompositionReplicatedAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		PAction replicatedAction = node.getReplicatedAction();
		LinkedList<PSingleDeclaration> decls = node.getReplicationDeclaration();

		// get CML environment
		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);
		if (cmlEnv == null) {
			node.setType(issueHandler.addTypeError(
					node,
					TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(node
							+ "")));
			return node.getType();
		}
		
		CmlTypeCheckInfo repActionEnv = cmlEnv.newScope();
		for (PSingleDeclaration decl : decls) {
			PType declType = decl.apply(parentChecker, question);
			if (!successfulType(declType))
				return issueHandler.addTypeError(decl,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage(decl + ""));

			if(declType instanceof ASetType){
				return issueHandler.addTypeError(declType, 
						TypeErrorMessages.SEQ_TYPE_EXPECTED.customizeMessage(decl + "", declType + ""));
			}
			
			issueHandler.addTypeWarning(decl,
					"This declaration should expand the environment: " + decl);
			
			for (PDefinition def : declType.getDefinitions())
				repActionEnv.addVariable(def.getName(), def);
		}

		PType replicatedActionType = replicatedAction.apply(parentChecker,
				repActionEnv);
		if (!successfulType(replicatedActionType))
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

		PExp designator = node.getDesignator();
		ACallStatementAction call = node.getCall();

		PType callType = call.apply(parentChecker, question);
		if (!successfulType(callType)) {
			node.setType(issueHandler.addTypeError(call,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + call)));
			return node.getType();
		}

		PType designatorType = designator.apply(parentChecker, question);
		if (!successfulType(designatorType)) {
			node.setType(issueHandler.addTypeError(designator,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(designator + "")));
			return node.getType();
		}

		node.setType(new AActionType());
		return node.getType();
	}

	@SuppressWarnings("deprecation")
	@Override
	public PType caseAWhileStatementAction(AWhileStatementAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		PExp condExp = node.getCondition();
		PAction actionBody = node.getAction();

		PType condExpType = condExp.apply(parentChecker, question);
		if (!successfulType(condExpType))
			return issueHandler.addTypeError(condExp,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(condExp + ""));

		PType actionBodyType = actionBody.apply(parentChecker, question);
		if (!successfulType(actionBodyType))
			return issueHandler.addTypeError(actionBodyType,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(actionBody + ""));

		return new AActionType(node.getLocation(), true);
	}

	private final eu.compassresearch.core.typechecker.api.CmlRootVisitor parentChecker;
	private final TypeIssueHandler issueHandler;
	private final TypeComparator typeComparator;

	public TCActionVisitor(
			eu.compassresearch.core.typechecker.api.CmlRootVisitor parentChecker,
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
		PVarsetExpression leftChanSet = node.getLeftChansetExpression();
		PVarsetExpression leftNameSet = node.getLeftNamesetExpression();

		PAction rightAction = node.getRightAction();
		PVarsetExpression rightChanSet = node.getRightChansetExpression();
		PVarsetExpression rightNameSet = node.getLeftNamesetExpression();

		PType leftActionType = leftAction.apply(parentChecker, question);
		if (!successfulType(leftActionType))
			return issueHandler.addTypeError(leftAction,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(leftAction + ""));

		PType leftChanSetType = leftChanSet.apply(parentChecker, question);
		if (!successfulType(leftChanSetType))
			return issueHandler.addTypeError(leftChanSet,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(leftChanSet + ""));

		PType leftNameSetType = leftNameSet.apply(parentChecker, question);
		if (!successfulType(leftNameSetType))
			return issueHandler.addTypeError(leftNameSet,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(leftNameSet + ""));

		PType rightActionType = rightAction.apply(parentChecker, question);
		if (!successfulType(rightActionType))
			return issueHandler.addTypeError(rightAction,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(rightAction + ""));

		PType rightChanSetType = rightChanSet.apply(parentChecker, question);
		if (!successfulType(rightChanSetType))
			return issueHandler.addTypeError(rightChanSet,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(rightChanSet + ""));

		PType rightNameSetType = rightNameSet.apply(parentChecker, question);
		if (!successfulType(rightNameSetType))
			return issueHandler.addTypeError(rightNameSet,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(rightNameSet + ""));

		return new AProcessType(node.getLocation(), true);
	}

	@Override
	public PType caseAReturnStatementAction(AReturnStatementAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {
		AExplicitCmlOperationDefinition operation = node
				.getAncestor(AExplicitCmlOperationDefinition.class);
		if (operation == null)
			throw new AnalysisException(
					"Return Statement Action does not have explicit operation as parent.");

		PExp exp = node.getExp();
		PType type = exp.apply(parentChecker, question);
		if (!successfulType(type)) {
			node.setType(issueHandler.addTypeError(node,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + exp)));
			return node.getType();
		}
		// check return type of parent function and the expression
		AOperationType operType = (AOperationType) operation.getType();
		if (!typeComparator.isSubType(type, operType.getResult())) {
			node.setType(issueHandler.addTypeError(
					node,
					TypeErrorMessages.INCOMPATIBLE_TYPE.customizeMessage(""
							+ operType.getResult(), "" + type)));
			return node.getType();
		}

		node.setType(new AVoidType(node.getLocation(), true));
		return node.getType();
	}

	@Override
	public PType caseAGuardedAction(AGuardedAction node, TypeCheckInfo question)
			throws AnalysisException {

		PExp exp = node.getExpression();
		PAction action = node.getAction();

		PType expType = exp.apply(parentChecker, question);
		if (!successfulType(expType)) {
			node.setType(issueHandler.addTypeError(exp,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(exp + "")));
			return node.getType();
		}

		if (!typeComparator.isSubType(expType,
				AstFactory.newABooleanBasicType(node.getLocation()))) {
			node.setType(issueHandler.addTypeError(exp,
					TypeErrorMessages.INCOMPATIBLE_TYPE.customizeMessage(
							"bool", "" + expType)));
			return node.getType();
		}

		PType actionType = action.apply(parentChecker, question);
		if (!successfulType(actionType)) {
			node.setType(issueHandler.addTypeError(action,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + action)));
			return node.getType();
		}

		if (!(actionType instanceof AActionType || actionType instanceof AStatementType || actionType instanceof AVoidType)) {
			node.setType(issueHandler.addTypeError(action,
					TypeErrorMessages.EXPECTED_AN_ACTION_OR_OPERATION
							.customizeMessage("" + action)));
			return node.getType();
		}

		node.setType(new AActionType(node.getLocation(), true));
		return node.getType();

	}

	@Override
	public PType caseADivAction(ADivAction node, TypeCheckInfo question)
			throws AnalysisException {

		node.setType(new AActionType(node.getLocation(), true));
		return node.getType();
	}

	@Override
	public PType caseASubclassResponsibilityAction(
			ASubclassResponsibilityAction node, TypeCheckInfo question)
			throws AnalysisException {

		PType type = AstFactory.newAUnknownType(node.getLocation());
		node.setType(type);
		return node.getType();
	}

	@Override
	public PType caseACommonInterleavingReplicatedAction(
			ACommonInterleavingReplicatedAction node, TypeCheckInfo question)
			throws AnalysisException {

		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);

		PVarsetExpression namesetExp = node.getNamesetExpression();
		PAction repAction = node.getReplicatedAction();
		LinkedList<PSingleDeclaration> decls = node.getReplicationDeclaration();

		PType namesetExpType = namesetExp.apply(parentChecker, question);
		if (!successfulType(namesetExpType)) {
			node.setType(issueHandler.addTypeError(node,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + node)));
			return node.getType();
		}

		CmlTypeCheckInfo repActionEnv = cmlEnv.newScope();
		for (PSingleDeclaration decl : decls) {
			PType declType = decl.apply(parentChecker, question);
			if (!successfulType(declType)) {
				node.setType(issueHandler.addTypeError(decl,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage("" + decl)));
				return node.getType();
			}

			for (PDefinition def : declType.getDefinitions())
				repActionEnv.addVariable(def.getName(), def);
		}

		PType repActionType = repAction.apply(parentChecker, question);
		if (!successfulType(repActionType)) {
			node.setType(issueHandler.addTypeError(repAction,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + repAction)));
			return node.getType();
		}

		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseAInterleavingReplicatedAction(
			AInterleavingReplicatedAction node, TypeCheckInfo question)
			throws AnalysisException {
		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);

		PVarsetExpression namesetExp = node.getNamesetExpression();
		PAction repAction = node.getReplicatedAction();
		LinkedList<PSingleDeclaration> decls = node.getReplicationDeclaration();

		PType namesetExpType = namesetExp.apply(parentChecker, question);
		if (!successfulType(namesetExpType)) {
			node.setType(issueHandler.addTypeError(node,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + node)));
			return node.getType();
		}

		CmlTypeCheckInfo repActionEnv = cmlEnv.newScope();
		for (PSingleDeclaration decl : decls) {
			PType declType = decl.apply(parentChecker, question);
			if (!successfulType(declType)) {
				node.setType(issueHandler.addTypeError(decl,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage("" + decl)));
				return node.getType();
			}

			for (PDefinition def : declType.getDefinitions())
				repActionEnv.addVariable(def.getName(), def);
		}

		PType repActionType = repAction.apply(parentChecker, repActionEnv);
		if (!successfulType(repActionType)) {
			node.setType(issueHandler.addTypeError(repAction,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + repAction)));
			return node.getType();
		}

		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseASynchronousParallelismReplicatedAction(
			ASynchronousParallelismReplicatedAction node, TypeCheckInfo question)
			throws AnalysisException {
		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);

		PVarsetExpression namesetExp = node.getNamesetExpression();
		PAction repAction = node.getReplicatedAction();
		LinkedList<PSingleDeclaration> decls = node.getReplicationDeclaration();

		PType namesetExpType = namesetExp.apply(parentChecker, question);
		if (!successfulType(namesetExpType)) {
			node.setType(issueHandler.addTypeError(node,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + node)));
			return node.getType();
		}

		CmlTypeCheckInfo repActionEnv = cmlEnv.newScope();
		for (PSingleDeclaration decl : decls) {
			PType declType = decl.apply(parentChecker, question);
			if (!successfulType(declType)) {
				node.setType(issueHandler.addTypeError(decl,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage("" + decl)));
				return node.getType();
			}

			for (PDefinition def : declType.getDefinitions())
				repActionEnv.addVariable(def.getName(), def);
		}

		PType repActionType = repAction.apply(parentChecker, repActionEnv);
		if (!successfulType(repActionType)) {
			node.setType(issueHandler.addTypeError(repAction,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + repAction)));
			return node.getType();
		}

		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseANotYetSpecifiedStatementAction(
			ANotYetSpecifiedStatementAction node, TypeCheckInfo question)
			throws AnalysisException {
		node.setType(AstFactory.newAUnknownType(node.getLocation()));
		return node.getType();

	}

	@Override
	public PType caseADeclareStatementAction(ADeclareStatementAction node,
			TypeCheckInfo question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseADeclareStatementAction(node, question);
	}

	@Override
	public PType caseABlockStatementAction(ABlockStatementAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		// get CML environment
		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);
		if (cmlEnv == null) {
			node.setType(issueHandler.addTypeError(
					node,
					TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(node
							+ "")));
			return node.getType();
		}

		NameScope oldScope = question.scope;
		question.scope = NameScope.NAMESANDANYSTATE;

		// Create a new environment for this block
		CmlTypeCheckInfo blockEnv = cmlEnv.newScope();

		// extend the environment with optional declarations
		ADeclareStatementAction declared = node.getDeclareStatement();
		if (declared != null) {
			LinkedList<PDefinition> freshDefinitions = declared
					.getAssignmentDefs();
			for (PDefinition def : freshDefinitions) {
				PType freshDefType = def.apply(parentChecker, question);
				if (!successfulType(freshDefType)) {
					node.setType(issueHandler.addTypeError(def,
							TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
									.customizeMessage("" + def)));
					return node.getType();
				}
				blockEnv.addVariable(def.getName(), def);
			}
		}

		// check the action.
		PAction action = node.getAction();
		PType actionType = action.apply(parentChecker, blockEnv);
		question.scope = oldScope;
		if (!successfulType(actionType))
			issueHandler.addTypeError(action,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(action.toString()));

		// alright this block is okay.
		node.setType(new AActionType(node.getLocation(), true));
		return node.getType();
	}

	@Override
	public PType caseASingleGeneralAssignmentStatementAction(
			ASingleGeneralAssignmentStatementAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		PExp state = node.getStateDesignator();
		PExp exp = node.getExpression();
		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);

		PType stateType = state.apply(parentChecker, question);
		if (!successfulType(stateType)) {
			node.setType(issueHandler.addTypeError(state,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(state + "")));
			return node.getType();
		}
		NameScope oldScope = question.scope;
		question.scope = NameScope.NAMESANDANYSTATE;
		PType expType = exp.apply(parentChecker, question);
		if (!successfulType(expType)) {
			node.setType(issueHandler.addTypeError(exp,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + exp)));
			return node.getType();
		}
		question.scope = oldScope;
		do {
			// are we in this special Cml Operation invocation in an AGASA
			// situation (don't ask)
			if (!(exp instanceof AApplyExp && ((AApplyExp) exp).getRoot()
					.getType() instanceof AOperationType))
				break;

			// extract the call root/target
			AApplyExp applyExp = (AApplyExp) exp;
			
			ILexNameToken callRootName;
			
			if(applyExp.getRoot() instanceof AUnresolvedPathExp){
				AUnresolvedPathExp aa = (AUnresolvedPathExp) applyExp.getRoot();
				LinkedList<ILexIdentifierToken> identifiers = aa.getIdentifiers();
				callRootName = new LexNameToken("", identifiers.get(0));
			 } else {
				AVariableExp callRoot = (AVariableExp) applyExp.getRoot();
				callRootName = callRoot.getName();
			 }

			// find the callRoot (again) in the environment
			PDefinition operationDefinition = cmlEnv.lookupVariable(callRootName);
			if (!(operationDefinition instanceof SCmlOperationDefinition))
				break;

			// we have an CML operation, transform that into a call statement
			SCmlOperationDefinition cmlOperation = (SCmlOperationDefinition) operationDefinition;
			ILexLocation newCallLocation = node.getExpression().getLocation();
			ILexNameToken newCallName = cmlOperation.getName();
			List<? extends PExp> newCallargs = applyExp.getArgs();
			ACallStatementAction newCall = new ACallStatementAction(
					newCallLocation, newCallName, newCallargs);

			// compute my replacement, that's right i'm going to replace my self
			ILexLocation replacementLocation = node.getLocation();
			PExp replacementDesignator = node.getStateDesignator().clone();
			ACallStatementAction replacementCall = newCall;
			AAssignmentCallStatementAction replacement = new AAssignmentCallStatementAction(
					replacementLocation, replacementDesignator, replacementCall);

			// replace me in parent with replacement
			INode parent = node.parent();
			Map<String, Object> children = parent.getChildren(true);
			String toRemove = null;
			for (Entry<String, Object> childEntry : children.entrySet()) {
				if (childEntry.getValue() == node) {
					toRemove = childEntry.getKey();
				}
			}
			if (toRemove == null) {
				node.setType(issueHandler
						.addTypeError(
								node,
								TypeErrorMessages.TYPE_CHECK_INTERNAL_FAILURE
										.customizeMessage("Could replace ASGASA as it is not a child of it's parent. I know that is a faulty AST !")));
				return node.getType();
			}
			parent.replaceChild(node, replacement);
			replacement.parent(parent);

			// type check the replacement node
			PType replacementType = replacement.apply(parentChecker, question);
			if (!successfulType(replacementType)) {
				node.setType(issueHandler.addTypeError(replacement,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage(replacement + "")));
				return node.getType();
			}

			replacement.setType(replacementType);
			return replacement.getType();

		} while (false);

		if (!typeComparator.isSubType(expType, stateType)) {
			node.setType(issueHandler.addTypeError(
					exp,
					TypeErrorMessages.INCOMPATIBLE_TYPE.customizeMessage(""
							+ expType, "" + stateType)));
			return node.getType();
		}
		
		//if the expression is an apply expression with of operation type, 
		//then we have an assignment call instead. So we replace the AST nodes with this
		if(node.getExpression() instanceof AApplyExp && ((AApplyExp)node.getExpression()).getRoot().getType() instanceof AOperationType)
		{
			AApplyExp applyExp = (AApplyExp)node.getExpression();
			
			ILexNameToken name = null;
			if(applyExp.getRoot() instanceof AUnresolvedPathExp)
			{
				List<ILexIdentifierToken> ids = ((AUnresolvedPathExp)applyExp.getRoot()).getIdentifiers(); 
				StringBuilder strBuilder = new StringBuilder();
				for(Iterator<ILexIdentifierToken> iter = ids.iterator();iter.hasNext();)
					strBuilder.append(iter.next() + (iter.hasNext() ? "." + iter.next() : ""));
				name = new LexNameToken("",strBuilder.toString(),applyExp.getRoot().getLocation());
			}
			else if(applyExp.getRoot() instanceof AVariableExp)
			{
				name = ((AVariableExp)applyExp.getRoot()).getName();
			}
			//ILexNameToken name = new LexNameToken("", ids.get(0) + "." + ids.get(1),applyExp.getRoot().getLocation());
			//AUnresolvedPathExp prev = new AUnresolvedPathExp(ids.get(0).getLocation(), ids.subList(0, ids.size()-1));
			//PObjectDesignator designator = convertUnresolvedPathExpToObjectdesignator(
			//		new AUnresolvedPathExp(ids.get(0).getLocation(), ids.subList(0, ids.size()-1)), question);
			//PType newType = prev.apply(parentChecker,question);
			//PDefinition newDef = question.env.findName(new LexNameToken("", prev.getIdentifiers().get(0)), question.scope);
			
			//AVariableExp varExp = new AVariableExp(location_, name_, original_)
			
			//ILexNameToken name = new LexNameToken("",ids.get(ids.size()-1) + "",applyExp.getRoot().getLocation());
			ACallStatementAction cstm = new ACallStatementAction(applyExp.getLocation(),new AStatementType(), name, applyExp.getArgs());
			
			AAssignmentCallStatementAction acstm = new AAssignmentCallStatementAction(node.getLocation(),new AStatementType(),node.getStateDesignator(),cstm);
			acstm.parent(node.parent());
			acstm.parent().replaceChild(node, acstm);
			node.parent(null);
			return acstm.getType();
		}
		else{
			node.setType(new AVoidType());	
			return node.getType();
		}
	}
	
//	private PObjectDesignator convertUnresolvedPathExpToObjectdesignator(AUnresolvedPathExp node, org.overture.typechecker.TypeCheckInfo question)
//	{
//		// So we are going to look up a path of the form <class>.<member> or
//		// <identifier>.<member>
//		// To find that class there must be a CML Environment as Classes are
//		// top-level and CML Specific.
//		//
//		PObjectDesignator designator = null;
//		
//		CmlTypeCheckInfo cmlQuestion = CmlTCUtil.getCmlEnv(question);
//		if (cmlQuestion == null) {
//			return designator;
//		}
//
//		// All right lets get all the identifiers used in this path
//		LinkedList<ILexIdentifierToken> identifiers = node.getIdentifiers();
//
//		// Get parent identifier
//		LexNameToken rootName = new LexNameToken("", identifiers.get(0));
//
//		// is it a type like a class or global type this is not a type
//		// as we would be in the UnresolvedType case
//		// PDefinition root = question.env.findType(rootName, "");
//		PDefinition root = null;
//
//		// no then it may be a variable
//		if (root == null)
//			root = question.env.findName(rootName, question.scope);
//
//		// Use Cml environment to determine what rootName is
//		if (root == null)
//			root = cmlQuestion.lookup(rootName, PDefinition.class);
//
//		// last option it is not in something else then in must be in this class
//		//				if (root == null) {
//		//					root = question.env.getEnclosingDefinition();
//		//					if (root != null)
//		//						root = assist.findMemberName(root, rootName, cmlQuestion);
//		//				}
//
//		// did we find the top-level
//		if (root == null) {
//			return designator;
//		}
//
//		// Now the root identifier is resolved, lets look for the first member
//		// We assume the identifiers are given in order with the outer most
//		// definitions coming first
//		PType leafType = null;
//		PDefinition prevRoot = null;
//		List<PDefinition> defs = new LinkedList<PDefinition>();
//		defs.add(root);
//		PDefinition def = root;
//
//		if(identifiers.size() == 1)
//		{
//			ILexIdentifierToken id = identifiers.get(0); 
//			//it must be a variable exp
//			AVariableExp varExp = new AVariableExp(root.getType(), id.getLocation(),new LexNameToken("", id.clone()),id.getName(), root);
//			designator = new AIdentifierObjectDesignator(varExp.getLocation(),new LexNameToken("", id.clone()),varExp);
//		}
//
//		
//		
//		return designator;
//	}

	@Override
	public PType caseAInternalChoiceAction(AInternalChoiceAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		PAction left = node.getLeft();
		PAction right = node.getRight();

		PType leftType = left.apply(parentChecker, question);
		if (!successfulType(leftType))
			return new AErrorType();

		PType rightType = right.apply(parentChecker, question);
		if (!successfulType(rightType))
			return new AErrorType();

		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseAReferenceAction(AReferenceAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		CmlTypeCheckInfo newQ = getTypeCheckInfo(question);

		PDefinition actionDef = newQ.lookup(node.getName(), PDefinition.class);

		PType type = newQ.lookupType(node.getName());
		if (type != null) {
			if (!(type instanceof AActionType)) {
				node.setType(issueHandler.addTypeError(node,
						TypeErrorMessages.EXPECTED_AN_ACTION
								.customizeMessage("" + node.getName())));
				return node.getType();
			}
			node.setType(type.clone());
			return node.getType();
		}

		if (type == null) {

			if (actionDef == null) {
				issueHandler.addTypeError(node,
						TypeErrorMessages.UNDEFINED_SYMBOL
								.customizeMessage(node.getName() + ""));
				node.setType(new AErrorType());
				return node.getType();
			}

			if (!(actionDef instanceof AActionDefinition)) {
				issueHandler.addTypeError(node,
						TypeErrorMessages.EXPECTED_AN_ACTION_OR_OPERATION
								.customizeMessage(node.getName() + ""));
				node.setType(new AErrorType());
				return node.getType();

			}
		}
		node.setActionDefinition(((AActionDefinition) actionDef));
		node.setType(new AActionType());
		return node.getType();
	}

	@SuppressWarnings("deprecation")
	@Override
	public PType caseACommunicationAction(ACommunicationAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);
		if (cmlEnv == null) {
			node.setType(issueHandler.addTypeError(
					node,
					TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(node
							+ "")));
			return node.getType();
		}

		PDefinition channel = cmlEnv.lookupChannel(node.getIdentifier());
		AChannelNameDefinition channelNameDefinition = null;

		// There should be a channel defined with this name
		if (null == channel) {
			node.setType(issueHandler.addTypeError(node,
					TypeErrorMessages.NAMED_TYPE_UNDEFINED
							.customizeMessage(node.getIdentifier().getName())));
			return node.getType();
		}

		if (!(channel instanceof AChannelNameDefinition)) {
			node.setType(issueHandler.addTypeError(channel,
					TypeErrorMessages.EXPECTED_A_CHANNEL
							.customizeMessage(channel + "")));
			return node.getType();
		}

		channelNameDefinition = (AChannelNameDefinition) channel;

		CmlTypeCheckInfo commEnv = cmlEnv.newScope();
		int paramIndex = 0;
		LinkedList<PCommunicationParameter> commParams = node
				.getCommunicationParameters();
		for (PCommunicationParameter commParam : commParams) {

			PPattern commPattern = null;
			// If a read communication is encountered
			// //
			// // Add all identifiers in pattern to environment with
			// // the types in the declared type for the channel
			// //
			// //
			ATypeSingleDeclaration typeDecl = channelNameDefinition
					.getSingleType();
			
			if (commParam instanceof AReadCommunicationParameter) {

				AReadCommunicationParameter readParam = (AReadCommunicationParameter) commParam;
				commPattern = readParam.getPattern();

				PType commPatternType = commPattern.apply(parentChecker,
						question);
				if (!successfulType(commPatternType)) {
					node.setType(issueHandler.addTypeError(commPattern,
							TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
									.customizeMessage("" + commPattern)));
					return node.getType();
				}

				if (typeDecl.getType() == null)
					typeDecl.setType(new AChannelType(commParam.getLocation(),
							true));

				if (commPattern instanceof AIdentifierPattern) {
					AIdentifierPattern id = (AIdentifierPattern) commPattern;
					AChannelType type = (AChannelType) typeDecl.getType();
					PType theType = null;
					if (type.getType() instanceof AProductType) {
						AProductType pType = (AProductType) type.getType();
						if (paramIndex >= pType.getTypes().size()) {
							node.setType(issueHandler.addTypeError(commPattern,
									TypeErrorMessages.PATTERN_MISMATCH
											.customizeMessage(pType + "",
													commParams + "")));
							return node.getType();
						}
						theType = pType.getTypes().get(paramIndex);
						paramIndex++;
					} else
						theType = type.getType();
					ALocalDefinition readVariable = AstFactory
							.newALocalDefinition(commPattern.getLocation(),
									id.getName(), NameScope.LOCAL, theType);
					readVariable.parent(commParam);
					readVariable.setLocation(commPattern.getLocation().clone());
					commEnv.addVariable(readVariable.getName(), readVariable);

					//Typecheck the constraint expression if it exists
					//TODO AKM: I think RWL has to check that this is correct
					if(commParam.getExpression() != null)
					{
						PExp constraintExp = commParam.getExpression();
						PType constraintType = constraintExp.apply(parentChecker,
							commEnv);
						
						if (!(constraintType instanceof ABooleanBasicType)) {
							constraintExp.setType(issueHandler.addTypeError(commPattern,
									TypeErrorMessages.INCOMPATIBLE_TYPE
											.customizeMessage("Boolean", ""
													+ constraintType)));
						}
					}
				}

				if (commPattern instanceof ATuplePattern) {
					PType type = typeDecl.getType();
					if (!(type instanceof AChannelType)) {
						node.setType(issueHandler.addTypeError(commPattern,
								TypeErrorMessages.INCOMPATIBLE_TYPE
										.customizeMessage("Channel type", ""
												+ type)));
						return node.getType();
					}

					AChannelType chanType = (AChannelType) type;
					if (!(chanType.getType() instanceof AProductType)) {
						node.setType(issueHandler.addTypeError(commPattern,
								TypeErrorMessages.INCOMPATIBLE_TYPE
										.customizeMessage(typeDecl.getType()
												+ "", chanType.getType() + "")));
						return node.getType();
					}

					AProductType r = (AProductType) chanType.getType();

					if (commPatternType.getDefinitions().size() != r.getTypes()
							.size()) {
						node.setType(issueHandler.addTypeError(commPattern,
								TypeErrorMessages.PATTERN_MISMATCH
										.customizeMessage(r + "", commPattern
												+ "")));
						return node.getType();
					}

					List<PDefinition> defs = commPatternType.getDefinitions();
					for (int i = 0; i < r.getTypes().size(); i++) {
						PDefinition def = defs.get(i);
						PType componentType = r.getTypes().get(i);
						def.setType(componentType);
						commEnv.addVariable(def.getName(), def);
					}
				}

			}

			if (commParam instanceof AWriteCommunicationParameter
					|| commParam instanceof ASignalCommunicationParameter) {
				PExp writeExp = null;
				PType writeExpType = null;

				if (commParam instanceof AWriteCommunicationParameter) {
					AWriteCommunicationParameter writeParam = (AWriteCommunicationParameter) commParam;
					writeExp = writeParam.getExpression();
				}

				if (commParam instanceof ASignalCommunicationParameter) {
					ASignalCommunicationParameter signalParam = (ASignalCommunicationParameter) commParam;
					writeExp = signalParam.getExpression();
				}

				writeExpType = writeExp.apply(parentChecker, commEnv);

				if (!successfulType(writeExpType)) {
					node.setType(issueHandler.addTypeError(writeExp,
							TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
									.customizeMessage(writeExp + "")));
					return node.getType();
				}

				PType thisType = null;
				PType type = typeDecl.getType();

				//Type check channel type definitions, if not checked.  
				if(type.getDefinitions().isEmpty()){
					type.apply(parentChecker, cmlEnv);
				}
				
				if (!(type instanceof AChannelType)) {
					node.setType(issueHandler.addTypeError(node,
							TypeErrorMessages.EXPECTED_A_CHANNEL
									.customizeMessage(node + "")));
					return node.getType();
				}

				AChannelType cType = (AChannelType) type;

				if (cType.getType() instanceof AProductType) {

					AProductType pType = (AProductType) cType.getType();
					if (paramIndex > pType.getTypes().size()) {
						node.setType(issueHandler.addTypeError(node,
								TypeErrorMessages.WRONG_NUMBER_OF_ARGUMENTS
										.customizeMessage(pType.getTypes()
												.size() + "", "" + paramIndex)));
						return node.getType();
					}
					thisType = pType.getTypes().get(paramIndex);
					paramIndex++;
				} else
					thisType = cType.getType();

				if (thisType == null) {
					node.setType(issueHandler.addTypeError(node,
							TypeErrorMessages.PATTERN_MISMATCH
									.customizeMessage("untyped channel",
											writeExp + "")));
					return node.getType();

				}

				if (!typeComparator.isSubType(writeExpType, thisType)) {
					node.setType(issueHandler.addTypeError(commParam,
							TypeErrorMessages.INCOMPATIBLE_TYPE
									.customizeMessage("" + thisType, ""
											+ writeExpType)));
					return node.getType();
				}

			}
		}

		PType commType = node.getAction().apply(this, commEnv);
		if (!successfulType(commType)) {
			node.setType(issueHandler.addTypeError(node.getAction(),
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(node.getAction() + "")));
			return node.getType();
		}

		node.setType(new AActionType(node.getLocation(), true));
		return node.getType();
	}

	@Override
	public PType caseASequentialCompositionAction(
			ASequentialCompositionAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		PType leftType = node.getLeft().apply(parentChecker, question);
		if (!successfulType(leftType)) {
			node.setType(issueHandler.addTypeError(node.getLeft(),
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(node.getLeft() + "")));
			return node.getType();
		}
		PType rightType = node.getRight().apply(parentChecker, question);
		if (!successfulType(rightType)) {
			node.setType(issueHandler.addTypeError(node.getRight(),
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(node.getRight() + "")));
			return node.getType();
		}

		node.setType(new AActionType());
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
		if (!successfulType(leftType))
			return new AErrorType();

		PType rightType = right.apply(parentChecker, question);
		if (!successfulType(rightType))
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
		PVarsetExpression chanSet = node.getChansetExpression();

		PType actionType = action.apply(parentChecker, question);
		if (!successfulType(actionType))
			return new AErrorType();

		PType chanSetType = chanSet.apply(parentChecker, question);
		if (!successfulType(chanSetType))
			return new AErrorType();

		if (!(chanSetType instanceof AChansetType)) {
			PType errorType = issueHandler.addTypeError(chanSet,
					TypeErrorMessages.EXPECTED_A_CHANNELSET
							.customizeMessage(chanSet.toString()));
			return errorType;
		}

		node.setType(new AActionType(node.getLocation(), true));

		return node.getType();
	}

	@Override
	public PType caseAVresParametrisation(AVresParametrisation node,
			TypeCheckInfo question) throws AnalysisException {

		ATypeSingleDeclaration decl = node.getDeclaration();
		PType declType = decl.apply(parentChecker, question);
		if (!successfulType(declType)) {
			return new AErrorType(node.getLocation(), true);
		}

		return new AActionType();
	}

	@Override
	public PType caseAParametrisedInstantiatedAction(
			AParametrisedInstantiatedAction node, TypeCheckInfo question)
			throws AnalysisException {

		AParametrisedAction action = node.getAction();
		LinkedList<PExp> args = node.getArgs();

		CmlTypeCheckInfo cmlEnv = getTypeCheckInfo(question);
		if (cmlEnv == null) {
			node.setType(issueHandler.addTypeError(
					node,
					TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(node
							+ "")));
			return node.getType();
		}

		CmlTypeCheckInfo newCmlEnv = cmlEnv.newScope();

		LinkedList<PParametrisation> parameterNames = node.getAction()
				.getParametrisations();
		int i = 0;
		for (PExp exp : args) {
			PType expType = exp.apply(parentChecker, question);
			if (!successfulType(expType)) {
				node.setType(issueHandler.addTypeError(exp,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage(exp + "")));
				return node.getType();
			}

			if (i > parameterNames.size())
				continue;
			PParametrisation pa = parameterNames.get(i++);
			ATypeSingleDeclaration decl = pa.getDeclaration();
			PType declType = decl.apply(parentChecker, question);
			if (!successfulType(declType)) {
				node.setType(issueHandler.addTypeError(decl,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage("" + decl)));
				return node.getType();
			}

			LinkedList<ILexIdentifierToken> ids = decl.getIdentifiers();
			for (ILexIdentifierToken id : ids) {
				LexNameToken idName = new LexNameToken("", id);
				ALocalDefinition localDef = AstFactory.newALocalDefinition(
						id.getLocation(), idName, NameScope.LOCAL, declType);
				newCmlEnv.addVariable(id, localDef);
			}
		}
		int a;
		PType actionType = action.apply(parentChecker, newCmlEnv);
		if (!successfulType(actionType)) {
			node.setType(issueHandler.addTypeError(action,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(action + "")));
			return node.getType();
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
		if (!successfulType(eventType))
			return new AErrorType(node.getLocation(), true);

		PType expType = timeExp.apply(parentChecker, question);
		if (!successfulType(expType))
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
		if (!successfulType(eventType))
			return new AErrorType(node.getLocation(), true);

		PType timeExpType = timeExp.apply(parentChecker, question);
		if (!successfulType(timeExpType))
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
	public PType caseAStopAction(AStopAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {
		return new AActionType(node.getLocation(), true);
	}

	@SuppressWarnings("deprecation")
	@Override
	public PType caseACallStatementAction(ACallStatementAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		ILexNameToken name = node.getName().clone();

		PDefinition callee = question.env.findName(name, NameScope.GLOBAL);

		LinkedList<PExp> args = node.getArgs();
		List<PType> argTypes = new LinkedList<PType>();

		for (PExp e : args) {
			PType eType = e.apply(parentChecker, question);
			if (!successfulType(eType)) {
				node.setType(issueHandler.addTypeError(node,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage("" + node)));
				return node.getType();
			}
			argTypes.add(eType);

		}

		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);
		if (cmlEnv != null) {
			if (callee == null)
				callee = cmlEnv.lookup(name, PDefinition.class);
			if (callee == null && !argTypes.isEmpty()) {
				name.setTypeQualifier(argTypes);
				callee = cmlEnv.lookup(name, PDefinition.class);
			}
		}

		CmlAssistant assistant = new CmlAssistant();
		String[] ids = new String[0];
		if (!"".equals(name.getModule())) {
			ids = name.getModule().split(".");
			if (ids.length == 0)
				ids = new String[] { name.getModule() };
		}

		String[] tmp = new String[ids.length + 1];
		System.arraycopy(ids, 0, tmp, 0, ids.length);
		tmp[tmp.length - 1] = name.getName();
		ids = tmp;
		LexNameToken nameid = new LexNameToken("", new LexIdentifierToken(
				ids[0], false, node.getLocation()));
		callee = cmlEnv.lookup(nameid, PDefinition.class);
		Environment env = question.env;
		while (callee == null && env != null
				&& env.getEnclosingDefinition() != null) {
			callee = assistant.findMemberName(env.getEnclosingDefinition(),
					nameid, question);
			env = env.getOuter();
		}

		for (int i = 1; i < ids.length; i++) {
			nameid = new LexNameToken("", new LexIdentifierToken(ids[i], false,
					node.getLocation()));
			callee = assistant.findMemberName(callee, nameid, question);
			if (callee == null) {
				node.setType(issueHandler.addTypeError(name,
						TypeErrorMessages.UNDEFINED_SYMBOL
								.customizeMessage(ids[i])));
				return node.getType();
			}
		}

		if (callee == null)
			callee = cmlEnv.lookup(name, PDefinition.class);
		if (callee == null)
			return issueHandler.addTypeError(
					node,
					TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage(name
							+ ""));

		if (!(callee instanceof AActionDefinition || callee instanceof PAction
				|| callee instanceof SCmlOperationDefinition
				|| callee instanceof AExplicitFunctionDefinition || callee instanceof AImplicitFunctionDefinition)) {
			return issueHandler.addTypeError(node,
					TypeErrorMessages.EXPECTED_AN_ACTION_OR_OPERATION
							.customizeMessage("" + callee));
		}

		if (callee.getType() == null) {
			callee.setType(new AActionType());
		}

		// Action can only call actions.
		if (callee.getType() == null) {
			node.setType(issueHandler.addTypeError(
					callee,
					TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage(callee
							+ "")));
			return node.getType();
		}

		if (callee.getType() instanceof AOperationType) {
			AOperationType ot = (AOperationType) callee.getType();
			List<PType> params = ot.getParameters();
			if (params.size() != argTypes.size()) {
				node.setType(issueHandler.addTypeError(node,
						TypeErrorMessages.WRONG_NUMBER_OF_ARGUMENTS
								.customizeMessage(params.size() + "", ""
										+ argTypes.size())));
				return node.getType();
			}

			for (int i = 0; i < params.size(); i++) {
				if (!typeComparator.isSubType(argTypes.get(i), params.get(i))) {
					node.setType(issueHandler.addTypeError(node,
							TypeErrorMessages.INCOMPATIBLE_TYPE
									.customizeMessage(params.get(i) + "",
											argTypes.get(i) + "")));
					return node.getType();
				}
			}
		}

		if (callee.getType() instanceof AActionType) {
			AActionDefinition ad = (AActionDefinition) callee;
			LinkedList<PParametrisation> ps = ad.getDeclarations();
			if (ps.size() != args.size()) {
				node.setType(issueHandler.addTypeError(node,
						TypeErrorMessages.WRONG_NUMBER_OF_ARGUMENTS
								.customizeMessage(ps.size() + "", args.size()
										+ "")));
				return node.getType();
			}
		}

		node.setType(new AActionType(node.getLocation(), true));
		return node.getType();
	}

	@SuppressWarnings("deprecation")
	@Override
	public PType caseASynchronousParallelismParallelAction(
			ASynchronousParallelismParallelAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		PAction leftAction = node.getLeftAction();
		PVarsetExpression leftNameSet = node.getLeftNamesetExpression();

		PAction rightAction = node.getRightAction();
		PVarsetExpression rightNameSet = node.getLeftNamesetExpression();

		PType leftActionType = leftAction.apply(parentChecker, question);
		if (!successfulType(leftActionType))
			return issueHandler.addTypeError(leftActionType,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(leftAction + ""));

		if (leftNameSet != null) {
			PType leftNameSetType = leftNameSet.apply(parentChecker, question);
			if (!successfulType(leftNameSetType))
				return issueHandler.addTypeError(leftNameSet,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage(leftNameSet + ""));
		}

		PType rightActionType = rightAction.apply(parentChecker, question);
		if (!successfulType(rightActionType))
			return issueHandler.addTypeError(leftActionType,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(rightAction + ""));

		if (rightNameSet != null) {
			PType rightNameSetType = rightNameSet
					.apply(parentChecker, question);
			if (!successfulType(rightNameSetType))
				return issueHandler.addTypeError(rightNameSet,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage(rightNameSet + ""));
		}
		return new AActionType(node.getLocation(), true);
	}

	@Override
	public PType caseANonDeterministicDoStatementAction(
			ANonDeterministicDoStatementAction node, TypeCheckInfo question)
			throws AnalysisException {

		LinkedList<ANonDeterministicAltStatementAction> alternatives = node
				.getAlternatives();
		for (ANonDeterministicAltStatementAction act : alternatives) {
			PType actType = act.apply(parentChecker, question);
			if (!successfulType(actType)) {
				node.setType(issueHandler.addTypeError(act,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage(act + "")));
				return node.getType();
			}
		}

		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseANonDeterministicAltStatementAction(
			ANonDeterministicAltStatementAction node, TypeCheckInfo question)
			throws AnalysisException {

		PExp guard = node.getGuard();
		PType guardType = guard.apply(parentChecker, question);
		if (!successfulType(guardType)) {
			node.setType(issueHandler.addTypeError(guard,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(guard + "")));
			return node.getType();
		}
		
		if (!(typeComparator.isSubType(guardType, new ABooleanBasicType()))) {
			TypeErrorMessages.INCOMPATIBLE_TYPE
					.customizeMessage("Boolean", guardType + "");
			node.setType(issueHandler.addTypeError(guard,
					TypeErrorMessages.INCOMPATIBLE_TYPE
					.customizeMessage("Boolean", "a guard of type " + guardType )));
			return node.getType();
		}

		PAction action = node.getAction();
		PType actionType = action.apply(parentChecker, question);
		if (!successfulType(actionType)) {
			node.setType(issueHandler.addTypeError(action,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + action)));
			return node.getType();
		}

		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseAParametrisedAction(AParametrisedAction node,
			TypeCheckInfo question) throws AnalysisException {

		PAction action = node.getAction();

		// Params are already added to the environment above as we have the
		// defining expressions there !
		// at least in the case of caseAParametrisedInstantiatedAction. See how
		// it is done there if your are in trouble
		// with this guy.
		LinkedList<PParametrisation> params = node.getParametrisations();

		PType actionType = action.apply(parentChecker, question);
		if (!successfulType(actionType)) {
			node.setType(issueHandler.addTypeError(node,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(action + "")));
			return node.getType();
		}

		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType createNewReturnValue(INode node, TypeCheckInfo question)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PType createNewReturnValue(Object node, TypeCheckInfo question)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
