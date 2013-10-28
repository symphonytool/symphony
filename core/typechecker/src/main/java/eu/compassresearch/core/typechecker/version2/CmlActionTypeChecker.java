package eu.compassresearch.core.typechecker.version2;

//import static eu.compassresearch.core.typechecker.util.CmlTCUtil.successfulType;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.analysis.intf.IQuestionAnswer;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.APrivateAccess;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.ATuplePattern;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.statements.PStm;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.typechecker.Pass;
import org.overture.ast.types.AAccessSpecifierAccessSpecifier;
import org.overture.ast.types.ABooleanBasicType;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.ANatNumericBasicType;
import org.overture.ast.types.AProductType;
import org.overture.ast.types.ASetType;
import org.overture.ast.types.AUnknownType;
import org.overture.ast.types.AVoidType;
import org.overture.ast.types.PType;
import org.overture.typechecker.Environment;
import org.overture.typechecker.FlatCheckedEnvironment;
import org.overture.typechecker.TypeCheckInfo;
import org.overture.typechecker.TypeComparator;
import org.overture.typechecker.assistant.pattern.PPatternAssistantTC;

import eu.compassresearch.ast.actions.AAlphabetisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AChannelRenamingAction;
import eu.compassresearch.ast.actions.AChaosAction;
import eu.compassresearch.ast.actions.ACommonInterleavingReplicatedAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.ADeclarationInstantiatedAction;
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
import eu.compassresearch.ast.actions.ASignalCommunicationParameter;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AStartDeadlineAction;
import eu.compassresearch.ast.actions.AStmAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.actions.ASynchronousParallelismParallelAction;
import eu.compassresearch.ast.actions.ASynchronousParallelismReplicatedAction;
import eu.compassresearch.ast.actions.ATimedInterruptAction;
import eu.compassresearch.ast.actions.ATimeoutAction;
import eu.compassresearch.ast.actions.AUntimedTimeoutAction;
import eu.compassresearch.ast.actions.AVresParametrisation;
import eu.compassresearch.ast.actions.AWaitAction;
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
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.expressions.SRenameChannelExp;
import eu.compassresearch.ast.types.AActionType;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.ast.types.AChansetType;
import eu.compassresearch.ast.types.AErrorType;
import eu.compassresearch.ast.types.ANamesetsType;
import eu.compassresearch.ast.types.AProcessType;
import eu.compassresearch.ast.types.AStatementType;
import eu.compassresearch.core.typechecker.CmlTypeCheckInfo;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.api.TypeWarningMessages;
import eu.compassresearch.core.typechecker.util.CmlTCUtil;

public class CmlActionTypeChecker extends
		QuestionAnswerCMLAdaptor<TypeCheckInfo, PType>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private QuestionAnswerAdaptor<TypeCheckInfo, PType> tc;
	private final ITypeIssueHandler issueHandler;// = VanillaFactory.newCollectingIssueHandle();

	@SuppressWarnings("deprecation")
	public CmlActionTypeChecker(
			QuestionAnswerAdaptor<TypeCheckInfo, PType> tc2,
			IQuestionAnswer<TypeCheckInfo, PType> root,
			ITypeIssueHandler issueHandler)
	{
		super(root);
		this.tc = tc2;
		this.issueHandler = issueHandler;
	}

	/**
	 * Case to handle statements embedded in actions
	 */
	public PType caseAStmAction(AStmAction node, TypeCheckInfo question)
			throws AnalysisException
	{
		PStm stm = node.getStatement();
		node.setType(stm.apply(tc, question));
		return node.getType();
	}

	@Override
	public PType caseAUntimedTimeoutAction(AUntimedTimeoutAction node,
			TypeCheckInfo question) throws AnalysisException
	{

		PAction left = node.getLeft();
		PAction right = node.getRight();

		PType leftType = left.apply(THIS, question);

		PType rightType = right.apply(THIS, question);

		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseATimeoutAction(ATimeoutAction node, TypeCheckInfo question)
			throws AnalysisException
	{

		PAction left = node.getLeft();
		PAction right = node.getRight();
		PExp timedExp = node.getTimeoutExpression();

		PType leftType = left.apply(THIS, question);

		PType rightType = right.apply(THIS, question);

		PType timedExpType = timedExp.apply(THIS, question);

		if (!TypeComparator.isSubType(timedExpType, new AIntNumericBasicType()))
		{
			node.setType(issueHandler.addTypeError(timedExp, TypeErrorMessages.TIME_UNIT_EXPRESSION_MUST_BE_NAT.customizeMessage(timedExp
					+ "", timedExpType + "")));
			return node.getType();
		}

		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseAInternalChoiceReplicatedAction(
			AInternalChoiceReplicatedAction node, TypeCheckInfo question)
			throws AnalysisException
	{

		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);

		CmlTypeCheckInfo actionEnv = cmlEnv.newScope();

		PAction repAction = node.getReplicatedAction();
		LinkedList<PSingleDeclaration> repDecl = node.getReplicationDeclaration();
		for (PSingleDeclaration d : repDecl)
		{
			PType type = d.apply(THIS, question);

			for (PDefinition newDef : type.getDefinitions())
			{
				actionEnv.addVariable(newDef.getName(), newDef);
			}

		}

		PType actionType = repAction.apply(THIS, actionEnv);

		node.setType(new AActionType());
		return node.getType();

	}

	@Override
	public PType caseAGeneralisedParallelismReplicatedAction(
			AGeneralisedParallelismReplicatedAction node, TypeCheckInfo question)
			throws AnalysisException
	{

		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);
		if (cmlEnv == null)
		{
			node.setType(issueHandler.addTypeError(node, TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(""
					+ node)));
			return node.getType();
		}

		// that it can't access the class/process state is needed
		CmlTypeCheckInfo actionEnv = cmlEnv.emptyScope();

		// CmlTypeCheckInfo actionEnv = cmlEnv.newScope();
		actionEnv.scope = NameScope.NAMESANDANYSTATE;
		// TODO RWL: What is the semantics of this?
		PVarsetExpression csexp = node.getChansetExpression();
		PType csexpType = csexp.apply(THIS, question);

		if (csexpType instanceof AUnknownType)
		{
			csexpType = new AChansetType(node.getLocation(), true);
		}

		if (!(csexpType instanceof AChansetType))
		{
			node.setType(issueHandler.addTypeError(node, TypeErrorMessages.EXPECTED_A_CHANNELSET.customizeMessage(""
					+ csexpType)));
			return node.getType();
		}

		for (PDefinition chanDef : csexpType.getDefinitions())
		{
			if (!(chanDef instanceof AChannelNameDefinition))
			{
				node.setType(issueHandler.addTypeError(node, TypeErrorMessages.TYPE_CHECK_INTERNAL_FAILURE.customizeMessage("Expected a Channel and got something of type AChannelType, however it is not AChannelNameDefinition.")));
				return node.getType();
			}
			AChannelNameDefinition chanNameDef = (AChannelNameDefinition) chanDef;
			for (ILexIdentifierToken id : chanNameDef.getSingleType().getIdentifiers())
			{
				actionEnv.addChannel(id, chanDef);
			}
		}

		PVarsetExpression sexp = node.getNamesetExpression();
		PType sexpType = sexp.apply(THIS, question);

		if (sexpType instanceof AUnknownType)
		{
			sexpType = new ANamesetsType(node.getLocation(), true);
		}

		if (!(sexpType instanceof ANamesetsType))
		{
			node.setType(issueHandler.addTypeError(node, TypeErrorMessages.EXPECTED_A_NAMESET.customizeMessage(""
					+ sexpType)));
			return node.getType();
		}

		for (PDefinition stateDef : sexpType.getDefinitions())
		{
			actionEnv.addVariable(stateDef.getName(), stateDef);
		}

		PAction repAction = node.getReplicatedAction();

		LinkedList<PSingleDeclaration> repDecls = node.getReplicationDeclaration();

		for (PSingleDeclaration decl : repDecls)
		{

			if (decl instanceof AExpressionSingleDeclaration)
			{
				AExpressionSingleDeclaration singleDecl = (AExpressionSingleDeclaration) decl;
				PExp exp = singleDecl.getExpression();
				PType expType = exp.apply(THIS, question);

				if (expType instanceof ASetType)
				{
					ASetType st = (ASetType) expType;
					expType = st.getSetof();
				}

				LinkedList<ILexIdentifierToken> ids = singleDecl.getIdentifiers();
				for (ILexIdentifierToken id : ids)
				{
					LexNameToken name = new LexNameToken("", id);

					ALocalDefinition def = AstFactory.newALocalDefinition(id.getLocation(), name, NameScope.LOCAL, expType);
					actionEnv.addVariable(name, def);
				}
			}

			if (decl instanceof ATypeSingleDeclaration)
			{
				ATypeSingleDeclaration singleDecl = (ATypeSingleDeclaration) decl;
				LinkedList<ILexIdentifierToken> ids = singleDecl.getIdentifiers();
				for (ILexIdentifierToken id : ids)
				{
					LexNameToken name = new LexNameToken("", id);
					ALocalDefinition def = AstFactory.newALocalDefinition(id.getLocation(), name, singleDecl.getNameScope(), singleDecl.getType());
					actionEnv.addVariable(name, def);
				}
			}
		}

		PType repActionType = repAction.apply(THIS, actionEnv);

		issueHandler.addTypeWarning(node, TypeWarningMessages.INCOMPLETE_TYPE_CHECKING.customizeMessage(""
				+ node));

		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseAExternalChoiceReplicatedAction(
			AExternalChoiceReplicatedAction node, TypeCheckInfo question)
			throws AnalysisException
	{

		PAction action = node.getReplicatedAction();
		LinkedList<PSingleDeclaration> decl = node.getReplicationDeclaration();

		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);

		CmlTypeCheckInfo actionEnv = cmlEnv.newScope();

		for (PSingleDeclaration d : decl)
		{
			PType declType = d.apply(THIS, question);
			for (PDefinition def : declType.getDefinitions())
			{
				actionEnv.addVariable(def.getName(), def);
			}
		}

		PType actionType = action.apply(THIS, actionEnv);

		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseAInterruptAction(AInterruptAction node,
			TypeCheckInfo question) throws AnalysisException
	{
		// extract sub-stuff
		PAction leftAction = node.getLeft();
		PAction rightAction = node.getRight();

		// type-check sub-actions
		PType leftActionType = leftAction.apply(THIS, question);

		PType rightActionType = rightAction.apply(THIS, question);

		// All done!
		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseAInterleavingParallelAction(
			AInterleavingParallelAction node, TypeCheckInfo question)
			throws AnalysisException
	{
		// extract sub-stuff
		PAction leftAction = node.getLeftAction();
		PVarsetExpression leftNamesetExp = node.getLeftNamesetExpression();
		PAction rightAction = node.getRightAction();
		PVarsetExpression rightnamesetExp = node.getRightNamesetExpression();

		// type-check sub-actions
		PType leftActionType = leftAction.apply(THIS, question);

		PType rightActionType = rightAction.apply(THIS, question);

		// type-check the namesets
		if (leftNamesetExp != null)
		{
			PType leftNameSetType = leftNamesetExp.apply(THIS, question);

		}

		if (rightnamesetExp != null)
		{
			PType rightNameSetType = rightnamesetExp.apply(THIS, question);
		}

		// All done!
		node.setType(new AActionType(node.getLocation(), true));
		return node.getType();
	}

	@Override
	public PType caseADeclarationInstantiatedAction(
			ADeclarationInstantiatedAction node, TypeCheckInfo question)
			throws AnalysisException
	{

		// Extract sub-stuff
		PAction action = node.getAction();
		LinkedList<ATypeSingleDeclaration> declarations = node.getDeclaration();

		// type check sub-action
		PType actionType = action.apply(THIS, question);

		for (ATypeSingleDeclaration declr : declarations)
		{
			PType declType = declr.apply(THIS, question);
			issueHandler.addTypeWarning(declr, "This declaration should expand the environment: "
					+ declr);
		}

		// All done!
		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseAGeneralisedParallelismParallelAction(
			AGeneralisedParallelismParallelAction node, TypeCheckInfo question)
			throws AnalysisException
	{

		// Extract sub-stuff
		PVarsetExpression chansetExp = node.getChansetExpression();
		PAction leftAction = node.getLeftAction();
		PVarsetExpression leftNamesetExp = node.getLeftNamesetExpression();
		PAction rightAction = node.getRightAction();
		PVarsetExpression rightnamesetExp = node.getLeftNamesetExpression();

		// type-check sub-actions
		PType leftActionType = leftAction.apply(THIS, question);

		PType rightActionType = rightAction.apply(THIS, question);

		// type-check the chanset
		PType chanSetType = chansetExp.apply(THIS, question);

		// type-check the namesets
		if (leftNamesetExp != null)
		{
			PType leftNameSetType = leftNamesetExp.apply(THIS, question);
		}

		if (rightnamesetExp != null)
		{
			PType rightNameSetType = rightnamesetExp.apply(THIS, question);
		}
		// All done!
		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseAChannelRenamingAction(AChannelRenamingAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		PAction action = node.getAction();

		SRenameChannelExp renameExp = node.getRenameExpression();

		return new AActionType(node.getLocation(), true);
	}

	@Override
	public PType caseAWaitAction(AWaitAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		PExp timedExp = node.getExpression();
		PType timedExpType = timedExp.apply(THIS, question);

		if (!TypeComparator.isSubType(timedExpType, new AIntNumericBasicType()))
		{
			node.setType(issueHandler.addTypeError(timedExpType, TypeErrorMessages.TIME_UNIT_EXPRESSION_MUST_BE_NAT.customizeMessage(timedExp
					+ "", timedExp + " (" + timedExpType + ")")));
			return node.getType();
		}

		node.setType(new AActionType(node.getLocation(), true));
		return node.getType();
	}

	@Override
	public PType caseAMuAction(AMuAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		// extract elements from the node
		LinkedList<ILexIdentifierToken> ids = node.getIdentifiers();
		LinkedList<PAction> acts = node.getActions();

		// get the enclosing definition if any
		PDefinition enclosingDef = question.env.getEnclosingDefinition();

		// get the CML context we are in
		CmlTypeCheckInfo info = getTypeCheckInfo(question);

		//
		CmlTypeCheckInfo newQuestion = (CmlTypeCheckInfo) info.newScope(info, enclosingDef);

		// add IDs to the environment
		for (ILexIdentifierToken id : ids)
		{
			newQuestion.addType(id, new ATypeDefinition(node.getLocation(), NameScope.LOCAL, false, null, new AAccessSpecifierAccessSpecifier(new APrivateAccess(), null, null), new AActionType(node.getLocation(), true), Pass.DEFS, null, null, null, null, false, new LexNameToken("", id)));
		}

		// check the actions
		for (PAction act : acts)
		{
			PType actType = act.apply(THIS, newQuestion);

		}

		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseAChaosAction(AChaosAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{
		node.setType(new AActionType(node.getLocation(), true));
		return node.getType();
	}

	@Override
	public PType caseATimedInterruptAction(ATimedInterruptAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		PAction left = node.getLeft();
		PAction right = node.getRight();
		PExp timeExp = node.getTimeExpression();

		PType leftType = left.apply(THIS, question);

		PType rightType = right.apply(THIS, question);

		PType timeExpType = timeExp.apply(THIS, question);

		if (!TypeComparator.isSubType(timeExpType, new ANatNumericBasicType()))
			return issueHandler.addTypeError(timeExp, TypeErrorMessages.TIME_UNIT_EXPRESSION_MUST_BE_NAT.customizeMessage(timeExp
					+ "", timeExpType + ""));

		return new AActionType(node.getLocation(), true);
	}

	@Override
	public PType caseASequentialCompositionReplicatedAction(
			ASequentialCompositionReplicatedAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		PAction replicatedAction = node.getReplicatedAction();
		LinkedList<PSingleDeclaration> decls = node.getReplicationDeclaration();

		// get CML environment
		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);
		if (cmlEnv == null)
		{
			node.setType(issueHandler.addTypeError(node, TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(node
					+ "")));
			return node.getType();
		}

		CmlTypeCheckInfo repActionEnv = cmlEnv.newScope();
		for (PSingleDeclaration decl : decls)
		{
			PType declType = decl.apply(THIS, question);

			if (declType instanceof ASetType)
			{
				return issueHandler.addTypeError(declType, TypeErrorMessages.SEQ_TYPE_EXPECTED.customizeMessage(decl
						+ "", declType + ""));
			}

			issueHandler.addTypeWarning(decl, "This declaration should expand the environment: "
					+ decl);

			for (PDefinition def : declType.getDefinitions())
				repActionEnv.addVariable(def.getName(), def);
		}

		PType replicatedActionType = replicatedAction.apply(THIS, repActionEnv);

		return new AActionType(node.getLocation(), true);
	}

	@Override
	public PType caseAAlphabetisedParallelismParallelAction(
			AAlphabetisedParallelismParallelAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		PAction leftAction = node.getLeftAction();
		PVarsetExpression leftChanSet = node.getLeftChansetExpression();
		PVarsetExpression leftNameSet = node.getLeftNamesetExpression();

		PAction rightAction = node.getRightAction();
		PVarsetExpression rightChanSet = node.getRightChansetExpression();
		PVarsetExpression rightNameSet = node.getLeftNamesetExpression();

		PType leftActionType = leftAction.apply(THIS, question);

		PType leftChanSetType = leftChanSet.apply(THIS, question);

		PType leftNameSetType = leftNameSet.apply(THIS, question);

		PType rightActionType = rightAction.apply(THIS, question);

		PType rightChanSetType = rightChanSet.apply(THIS, question);

		PType rightNameSetType = rightNameSet.apply(THIS, question);

		return new AProcessType(node.getLocation(), true);
	}

	@Override
	public PType caseAGuardedAction(AGuardedAction node, TypeCheckInfo question)
			throws AnalysisException
	{

		PExp exp = node.getExpression();
		PAction action = node.getAction();

		PType expType = exp.apply(tc, question);

		if (!TypeComparator.isSubType(expType, AstFactory.newABooleanBasicType(node.getLocation())))
		{
			node.setType(issueHandler.addTypeError(exp, TypeErrorMessages.INCOMPATIBLE_TYPE.customizeMessage("bool", ""
					+ expType)));
			return node.getType();
		}

		PType actionType = action.apply(THIS, question);

		if (!(actionType instanceof AActionType
				|| actionType instanceof AStatementType || actionType instanceof AVoidType))
		{
			node.setType(issueHandler.addTypeError(action, TypeErrorMessages.EXPECTED_AN_ACTION_OR_OPERATION.customizeMessage(""
					+ action)));
			return node.getType();
		}

		node.setType(new AActionType(node.getLocation(), true));
		return node.getType();

	}

	@Override
	public PType caseADivAction(ADivAction node, TypeCheckInfo question)
			throws AnalysisException
	{

		node.setType(new AActionType(node.getLocation(), true));
		return node.getType();
	}

	@Override
	public PType caseACommonInterleavingReplicatedAction(
			ACommonInterleavingReplicatedAction node, TypeCheckInfo question)
			throws AnalysisException
	{

		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);

		PVarsetExpression namesetExp = node.getNamesetExpression();
		PAction repAction = node.getReplicatedAction();
		LinkedList<PSingleDeclaration> decls = node.getReplicationDeclaration();

		PType namesetExpType = namesetExp.apply(THIS, question);

		CmlTypeCheckInfo repActionEnv = cmlEnv.newScope();
		for (PSingleDeclaration decl : decls)
		{
			PType declType = decl.apply(THIS, question);

			for (PDefinition def : declType.getDefinitions())
				repActionEnv.addVariable(def.getName(), def);
		}

		PType repActionType = repAction.apply(THIS, question);

		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseAInterleavingReplicatedAction(
			AInterleavingReplicatedAction node, TypeCheckInfo question)
			throws AnalysisException
	{
		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);

		PVarsetExpression namesetExp = node.getNamesetExpression();
		PAction repAction = node.getReplicatedAction();
		LinkedList<PSingleDeclaration> decls = node.getReplicationDeclaration();

		PType namesetExpType = namesetExp.apply(THIS, question);

		CmlTypeCheckInfo repActionEnv = cmlEnv.newScope();
		for (PSingleDeclaration decl : decls)
		{
			PType declType = decl.apply(THIS, question);

			for (PDefinition def : declType.getDefinitions())
				repActionEnv.addVariable(def.getName(), def);
		}

		PType repActionType = repAction.apply(THIS, repActionEnv);

		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseASynchronousParallelismReplicatedAction(
			ASynchronousParallelismReplicatedAction node, TypeCheckInfo question)
			throws AnalysisException
	{
		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);

		PVarsetExpression namesetExp = node.getNamesetExpression();
		PAction repAction = node.getReplicatedAction();
		LinkedList<PSingleDeclaration> decls = node.getReplicationDeclaration();

		PType namesetExpType = namesetExp.apply(THIS, question);

		CmlTypeCheckInfo repActionEnv = cmlEnv.newScope();
		for (PSingleDeclaration decl : decls)
		{
			PType declType = decl.apply(THIS, question);

			for (PDefinition def : declType.getDefinitions())
				repActionEnv.addVariable(def.getName(), def);
		}

		PType repActionType = repAction.apply(THIS, repActionEnv);

		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseAInternalChoiceAction(AInternalChoiceAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		PAction left = node.getLeft();
		PAction right = node.getRight();

		PType leftType = left.apply(THIS, question);
		// if (!successfulType(leftType))
		// return new AErrorType();

		PType rightType = right.apply(THIS, question);
		// if (!successfulType(rightType))
		// return new AErrorType();

		node.setType(new AActionType());
		return node.getType();
	}

	@Override
	public PType caseAReferenceAction(AReferenceAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		// CmlTypeCheckInfo newQ = getTypeCheckInfo(question);
		Environment env = question.env;

		// FIXME
		PDefinition actionDef = env.findMatches(node.getName()).iterator().next();// newQ.lookup(node.getName(),
																					// PDefinition.class);

		PType type = actionDef.getType();// newQ.lookupType(node.getName());
		if (type != null)
		{
			if (!(type instanceof AActionType))
			{
				node.setType(issueHandler.addTypeError(node, TypeErrorMessages.EXPECTED_AN_ACTION.customizeMessage(""
						+ node.getName())));
				return node.getType();
			}
			node.setType(type.clone());
			return node.getType();
		}

		if (type == null)
		{

			if (actionDef == null)
			{
				issueHandler.addTypeError(node, TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage(node.getName()
						+ ""));
				node.setType(new AErrorType());
				return node.getType();
			}

			if (!(actionDef instanceof AActionDefinition))
			{
				issueHandler.addTypeError(node, TypeErrorMessages.EXPECTED_AN_ACTION_OR_OPERATION.customizeMessage(node.getName()
						+ ""));
				node.setType(new AErrorType());
				return node.getType();

			}
		}
		node.setActionDefinition(((AActionDefinition) actionDef));
		node.setType(new AActionType());
		return node.getType();
	}

	private CmlTypeCheckInfo getTypeCheckInfo(TypeCheckInfo question)
	{
		// CmlTypeCheckInfo info = new CmlActionTypeChecker(tc2, root)
		return null;
	}

	@Override
	public PType caseACommunicationAction(ACommunicationAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		Set<PDefinition> deff = question.env.findMatches(new eu.compassresearch.ast.lex.LexNameToken("", node.getIdentifier()));

		PDefinition channel = deff.iterator().next();// FIXME
		AChannelNameDefinition channelNameDefinition = null;

		// There should be a channel defined with this name
		if (null == channel)
		{
			node.setType(issueHandler.addTypeError(node, TypeErrorMessages.NAMED_TYPE_UNDEFINED.customizeMessage(node.getIdentifier().getName())));
			return node.getType();
		}

		if (!(channel instanceof AChannelNameDefinition))
		{
			node.setType(issueHandler.addTypeError(channel, TypeErrorMessages.EXPECTED_A_CHANNEL.customizeMessage(channel
					+ "")));
			return node.getType();
		}

		channelNameDefinition = (AChannelNameDefinition) channel;

		// CmlTypeCheckInfo commEnv = cmlEnv.newScope();
		List<PDefinition> localDefinitions = new Vector<PDefinition>();
		Environment local = new FlatCheckedEnvironment(question.assistantFactory, localDefinitions, question.env, NameScope.NAMES);
		TypeCheckInfo info = new TypeCheckInfo(question.assistantFactory, local, NameScope.NAMES);

		int paramIndex = 0;
		LinkedList<PCommunicationParameter> commParams = node.getCommunicationParameters();
		for (PCommunicationParameter commParam : commParams)
		{

			PPattern commPattern = null;
			// If a read communication is encountered
			// //
			// // Add all identifiers in pattern to environment with
			// // the types in the declared type for the channel
			// //
			// //
			ATypeSingleDeclaration typeDecl = channelNameDefinition.getSingleType();

			if (commParam instanceof AReadCommunicationParameter)
			{

				AReadCommunicationParameter readParam = (AReadCommunicationParameter) commParam;
				commPattern = readParam.getPattern();

				PPatternAssistantTC.typeResolve(commPattern, tc, question);

				PType commPatternType = PPatternAssistantTC.getPossibleType(commPattern);// commPattern.apply(tc,
																							// question);

				if (typeDecl.getType() == null)
					typeDecl.setType(new AChannelType(commParam.getLocation(), true));

				if (commPattern instanceof AIdentifierPattern)
				{
					AIdentifierPattern id = (AIdentifierPattern) commPattern;
					AChannelType type = (AChannelType) typeDecl.getType();
					PType theType = null;
					if (type.getType() instanceof AProductType)
					{
						AProductType pType = (AProductType) type.getType();
						if (paramIndex >= pType.getTypes().size())
						{
							node.setType(issueHandler.addTypeError(commPattern, TypeErrorMessages.PATTERN_MISMATCH.customizeMessage(pType
									+ "", commParams + "")));
							return node.getType();
						}
						theType = pType.getTypes().get(paramIndex);
						paramIndex++;
					} else
					{
						theType = type.getType();
					}

					ALocalDefinition readVariable = AstFactory.newALocalDefinition(commPattern.getLocation(), id.getName(), NameScope.LOCAL, theType);
					readVariable.parent(commParam);
					readVariable.setLocation(commPattern.getLocation().clone());
					// commEnv.addVariable(readVariable.getName(), readVariable);
					localDefinitions.add(readVariable);

					// Typecheck the constraint expression if it exists
					// TODO AKM: I think RWL has to check that this is correct
					if (commParam.getExpression() != null)
					{
						PExp constraintExp = commParam.getExpression();

						PType constraintType = constraintExp.apply(this.tc, new TypeCheckInfo(question.assistantFactory, local, NameScope.NAMES));

						if (!(constraintType instanceof ABooleanBasicType))
						{
							constraintExp.setType(issueHandler.addTypeError(commPattern, TypeErrorMessages.INCOMPATIBLE_TYPE.customizeMessage("Boolean", ""
									+ constraintType)));
						}
					}
				}

				if (commPattern instanceof ATuplePattern)
				{
					PType type = typeDecl.getType();
					if (!(type instanceof AChannelType))
					{
						node.setType(issueHandler.addTypeError(commPattern, TypeErrorMessages.INCOMPATIBLE_TYPE.customizeMessage("Channel type", ""
								+ type)));
						return node.getType();
					}

					AChannelType chanType = (AChannelType) type;
					if (!(chanType.getType() instanceof AProductType))
					{
						node.setType(issueHandler.addTypeError(commPattern, TypeErrorMessages.INCOMPATIBLE_TYPE.customizeMessage(typeDecl.getType()
								+ "", chanType.getType() + "")));
						return node.getType();
					}

					AProductType r = (AProductType) chanType.getType();

					if (commPatternType.getDefinitions().size() != r.getTypes().size())
					{
						node.setType(issueHandler.addTypeError(commPattern, TypeErrorMessages.PATTERN_MISMATCH.customizeMessage(r
								+ "", commPattern + "")));
						return node.getType();
					}

					List<PDefinition> defs = commPatternType.getDefinitions();
					for (int i = 0; i < r.getTypes().size(); i++)
					{
						PDefinition def = defs.get(i);
						PType componentType = r.getTypes().get(i);
						def.setType(componentType);
						// commEnv.addVariable(def.getName(), def);
						localDefinitions.add(def);
					}
				}

			}

			if (commParam instanceof AWriteCommunicationParameter
					|| commParam instanceof ASignalCommunicationParameter)
			{
				PExp writeExp = null;
				PType writeExpType = null;

				if (commParam instanceof AWriteCommunicationParameter)
				{
					AWriteCommunicationParameter writeParam = (AWriteCommunicationParameter) commParam;
					writeExp = writeParam.getExpression();
				}

				if (commParam instanceof ASignalCommunicationParameter)
				{
					ASignalCommunicationParameter signalParam = (ASignalCommunicationParameter) commParam;
					writeExp = signalParam.getExpression();
				}

				writeExpType = writeExp.apply(tc, info);

				PType thisType = null;
				PType type = typeDecl.getType();

				// Type check channel type definitions, if not checked.
				if (type.getDefinitions().isEmpty())
				{
					type.apply(THIS, info);
				}

				if (!(type instanceof AChannelType))
				{
					node.setType(issueHandler.addTypeError(node, TypeErrorMessages.EXPECTED_A_CHANNEL.customizeMessage(node
							+ "")));
					return node.getType();
				}

				AChannelType cType = (AChannelType) type;

				if (cType.getType() instanceof AProductType)
				{

					AProductType pType = (AProductType) cType.getType();
					if (paramIndex > pType.getTypes().size())
					{
						node.setType(issueHandler.addTypeError(node, TypeErrorMessages.WRONG_NUMBER_OF_ARGUMENTS.customizeMessage(pType.getTypes().size()
								+ "", "" + paramIndex)));
						return node.getType();
					}
					thisType = pType.getTypes().get(paramIndex);
					paramIndex++;
				} else
					thisType = cType.getType();

				if (thisType == null)
				{
					node.setType(issueHandler.addTypeError(node, TypeErrorMessages.PATTERN_MISMATCH.customizeMessage("untyped channel", writeExp
							+ "")));
					return node.getType();

				}

				if (!TypeComparator.isSubType(writeExpType, thisType))
				{
					node.setType(issueHandler.addTypeError(commParam, TypeErrorMessages.INCOMPATIBLE_TYPE.customizeMessage(""
							+ thisType, "" + writeExpType)));
					return node.getType();
				}

			}
		}

		PType commType = node.getAction().apply(this, info);

		node.setType(commType);
		return node.getType();
	}

	@Override
	public PType caseASequentialCompositionAction(
			ASequentialCompositionAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		PType leftType = node.getLeft().apply(THIS, question);
		PType rightType = node.getRight().apply(THIS, question);

		node.setType(new AVoidType());
		return node.getType();
	}

	@Override
	public PType caseASkipAction(ASkipAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{
		node.setType(new AActionType(node.getLocation(), true));
		return node.getType();
	}

	@Override
	public PType caseAExternalChoiceAction(AExternalChoiceAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		PAction left = node.getLeft();
		PAction right = node.getRight();

		PType leftType = left.apply(THIS, question);
		// if (!successfulType(leftType))
		// return new AErrorType();

		PType rightType = right.apply(THIS, question);
		// if (!successfulType(rightType))
		// return new AErrorType();

		node.setType(new AActionType(node.getLocation(), true));
		return node.getType();
	}

	@Override
	public PType caseAHidingAction(AHidingAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		PAction action = node.getLeft();
		PVarsetExpression chanSet = node.getChansetExpression();

		PType actionType = action.apply(THIS, question);
		// if (!successfulType(actionType))
		// return new AErrorType();

		PType chanSetType = chanSet.apply(THIS, question);
		// if (!successfulType(chanSetType))
		// return new AErrorType();

		if (!(chanSetType instanceof AChansetType))
		{
			PType errorType = issueHandler.addTypeError(chanSet, TypeErrorMessages.EXPECTED_A_CHANNELSET.customizeMessage(chanSet.toString()));
			return errorType;
		}

		node.setType(new AActionType(node.getLocation(), true));

		return node.getType();
	}

	@Override
	public PType caseAVresParametrisation(AVresParametrisation node,
			TypeCheckInfo question) throws AnalysisException
	{

		ATypeSingleDeclaration decl = node.getDeclaration();
		PType declType = decl.apply(THIS, question);
		// if (!successfulType(declType))
		// {
		// return new AErrorType(node.getLocation(), true);
		// }

		return new AActionType();
	}

	@Override
	public PType caseAParametrisedInstantiatedAction(
			AParametrisedInstantiatedAction node, TypeCheckInfo question)
			throws AnalysisException
	{

		AParametrisedAction action = node.getAction();
		LinkedList<PExp> args = node.getArgs();

		CmlTypeCheckInfo cmlEnv = getTypeCheckInfo(question);
		if (cmlEnv == null)
		{
			node.setType(issueHandler.addTypeError(node, TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(node
					+ "")));
			return node.getType();
		}

		CmlTypeCheckInfo newCmlEnv = cmlEnv.newScope();

		LinkedList<PParametrisation> parameterNames = node.getAction().getParametrisations();
		int i = 0;
		for (PExp exp : args)
		{
			PType expType = exp.apply(THIS, question);

			if (i > parameterNames.size())
				continue;
			PParametrisation pa = parameterNames.get(i++);
			ATypeSingleDeclaration decl = pa.getDeclaration();
			PType declType = decl.apply(THIS, question);

			LinkedList<ILexIdentifierToken> ids = decl.getIdentifiers();
			for (ILexIdentifierToken id : ids)
			{
				LexNameToken idName = new LexNameToken("", id);
				ALocalDefinition localDef = AstFactory.newALocalDefinition(id.getLocation(), idName, NameScope.LOCAL, declType);
				newCmlEnv.addVariable(id, localDef);
			}
		}
		int a;
		PType actionType = action.apply(THIS, newCmlEnv);

		node.setType(new AActionType(node.getLocation(), true));
		return node.getType();
	}

	@Override
	public PType caseAStartDeadlineAction(AStartDeadlineAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		PAction event = node.getLeft();
		PExp timeExp = node.getExpression();

		PType eventType = event.apply(THIS, question);
		// if (!successfulType(eventType))
		// return new AErrorType(node.getLocation(), true);

		PType expType = timeExp.apply(THIS, question);
		// if (!successfulType(expType))
		// return new AErrorType(node.getLocation(), true);

		if (!(TypeComparator.isSubType(expType, new ANatNumericBasicType())))
		{
			TypeErrorMessages.TIME_UNIT_EXPRESSION_MUST_BE_NAT.customizeMessage(timeExp
					+ "", expType + "");
			node.setType(new AErrorType(timeExp.getLocation(), true));
			return node.getType();
		}
		node.setType(new AActionType(node.getLocation(), true));
		return node.getType();
	}

	@Override
	public PType caseAEndDeadlineAction(AEndDeadlineAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		PAction event = node.getLeft();
		PExp timeExp = node.getExpression();

		PType eventType = event.apply(THIS, question);
		// if (!successfulType(eventType))
		// return new AErrorType(node.getLocation(), true);

		PType timeExpType = timeExp.apply(THIS, question);
		// if (!successfulType(timeExpType))
		// return new AErrorType(node.getLocation(), true);

		if (!(TypeComparator.isSubType(timeExpType, new ANatNumericBasicType())))
		{
			TypeErrorMessages.TIME_UNIT_EXPRESSION_MUST_BE_NAT.customizeMessage(timeExp
					+ "", timeExpType + "");
			node.setType(new AErrorType(timeExp.getLocation(), true));
			return node.getType();

		}

		node.setType(new AActionType());

		return node.getType();
	}

	@Override
	public PType caseAStopAction(AStopAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{
		return new AActionType(node.getLocation(), true);
	}

	@Override
	public PType caseASynchronousParallelismParallelAction(
			ASynchronousParallelismParallelAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		PAction leftAction = node.getLeftAction();
		PVarsetExpression leftNameSet = node.getLeftNamesetExpression();

		PAction rightAction = node.getRightAction();
		PVarsetExpression rightNameSet = node.getLeftNamesetExpression();

		PType leftActionType = leftAction.apply(THIS, question);

		if (leftNameSet != null)
		{
			PType leftNameSetType = leftNameSet.apply(THIS, question);

		}

		PType rightActionType = rightAction.apply(THIS, question);

		if (rightNameSet != null)
		{
			PType rightNameSetType = rightNameSet.apply(THIS, question);

		}
		return new AActionType(node.getLocation(), true);
	}

	@Override
	public PType caseAParametrisedAction(AParametrisedAction node,
			TypeCheckInfo question) throws AnalysisException
	{

		PAction action = node.getAction();

		// Params are already added to the environment above as we have the
		// defining expressions there !
		// at least in the case of caseAParametrisedInstantiatedAction. See how
		// it is done there if your are in trouble
		// with this guy.
		LinkedList<PParametrisation> params = node.getParametrisations();

		PType actionType = action.apply(THIS, question);

		node.setType(new AActionType());
		return node.getType();
	}

	public PType createNewReturnValue(INode node, TypeCheckInfo question)
			throws AnalysisException
	{
		return null;
	}

	@Override
	public PType createNewReturnValue(Object node, TypeCheckInfo question)
			throws AnalysisException
	{
		return null;
	}

}
