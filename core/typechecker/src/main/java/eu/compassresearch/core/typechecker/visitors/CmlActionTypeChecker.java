package eu.compassresearch.core.typechecker.visitors;

import static eu.compassresearch.core.typechecker.assistant.TypeCheckerUtil.findDefinition;
import static eu.compassresearch.core.typechecker.assistant.TypeCheckerUtil.setType;
import static eu.compassresearch.core.typechecker.assistant.TypeCheckerUtil.setTypeVoid;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.analysis.intf.IQuestionAnswer;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.statements.PStm;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.types.ABooleanBasicType;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.ANatNumericBasicType;
import org.overture.ast.types.AProductType;
import org.overture.ast.types.ASetType;
import org.overture.ast.types.PType;
import org.overture.ast.util.Utils;
import org.overture.typechecker.Environment;
import org.overture.typechecker.FlatCheckedEnvironment;
import org.overture.typechecker.TypeCheckException;
import org.overture.typechecker.TypeCheckInfo;
import org.overture.typechecker.TypeChecker;
import org.overture.typechecker.TypeCheckerErrors;
import org.overture.typechecker.TypeComparator;
import org.overture.typechecker.assistant.pattern.PPatternAssistantTC;

import eu.compassresearch.ast.CmlAstFactory;
import eu.compassresearch.ast.actions.AAlphabetisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AAlphabetisedParallelismReplicatedAction;
import eu.compassresearch.ast.actions.ACallAction;
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
import eu.compassresearch.ast.actions.ASignalCommunicationParameter;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AStartDeadlineAction;
import eu.compassresearch.ast.actions.AStmAction;
import eu.compassresearch.ast.actions.AStopAction;
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
import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.lex.CmlLexNameToken;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.api.TypeWarningMessages;
import eu.compassresearch.core.typechecker.assistant.ACallActionAssistant;
import eu.compassresearch.core.typechecker.assistant.PParametrisationAssistant;
import eu.compassresearch.core.typechecker.assistant.TypeCheckerUtil;

public class CmlActionTypeChecker extends
		QuestionAnswerCMLAdaptor<TypeCheckInfo, PType>
{

	private final ITypeIssueHandler issueHandler;

	/**
	 * Type checker for var set expressions used for channel sets
	 */
	private final QuestionAnswerAdaptor<TypeCheckInfo, PType> channelSetChecker;
	/**
	 * Type checker for var set expressions used for name sets
	 */
	private final QuestionAnswerAdaptor<TypeCheckInfo, PType> nameSetChecker;

	public CmlActionTypeChecker(IQuestionAnswer<TypeCheckInfo, PType> root,
			ITypeIssueHandler issueHandler,
			QuestionAnswerAdaptor<TypeCheckInfo, PType> channelSetChecker,
			QuestionAnswerAdaptor<TypeCheckInfo, PType> nameSetChecker)
	{
		super(root);
		this.issueHandler = issueHandler;
		this.channelSetChecker = channelSetChecker;
		this.nameSetChecker = nameSetChecker;
	}

	/**
	 * Case to handle statements embedded in actions
	 */
	public PType caseAStmAction(AStmAction node, TypeCheckInfo question)
			throws AnalysisException
	{
		PStm stm = node.getStatement();
		node.setType(stm.apply(THIS, question));
		return node.getType();
	}

	@Override
	public PType caseACallAction(ACallAction node, TypeCheckInfo question)
			throws AnalysisException
	{
		List<PType> atypes = question.assistantFactory.createACallObjectStatementAssistant().getArgTypes(node.getArgs(), THIS, question);

		if (question.env.isVDMPP())
		{
			node.getName().setTypeQualifier(atypes);
		}

		PDefinition opdef = question.env.findName(node.getName(), question.scope);// findDefinition(node.getName(),
																					// question.env);

		if (opdef == null)
		{
			TypeCheckerErrors.report(3437, "Action " + node.getName()
					+ " is not in scope", node.getLocation(), node);
			question.env.listAlternatives(node.getName());
			node.setType(AstFactory.newAUnknownType(node.getLocation()));
			return node.getType();
		}

		if (opdef instanceof AActionDefinition)
		{
			AActionDefinition actionDef = (AActionDefinition) opdef;

			List<PType> paramTypes = new Vector<PType>();
			for (PParametrisation localDef : actionDef.getDeclarations())
			{
				PType t = localDef.getDeclaration().getType();
				question.assistantFactory.createPTypeAssistant().typeResolve(t, null, THIS, question);
				paramTypes.add(t);
			}

			// Reset the name's qualifier with the actual operation type so
			// that runtime search has a simple TypeComparator call.

			if (question.env.isVDMPP())
			{
				node.getName().setTypeQualifier(paramTypes);
			}
			node.setType(AstFactory.newAVoidReturnType(node.getLocation()));
			ACallActionAssistant.checkArgTypes(node, node.getType(), paramTypes, atypes);

			return node.getType();
		} else
		{
			TypeCheckerErrors.report(3438, "Name is not an action", node.getLocation(), node);
			node.setType(AstFactory.newAUnknownType(node.getLocation()));
			return node.getType();
		}

	}

	@Override
	public PType caseAUntimedTimeoutAction(AUntimedTimeoutAction node,
			TypeCheckInfo question) throws AnalysisException
	{
		PType leftType = node.getLeft().apply(THIS, question);

		PType rightType = node.getRight().apply(THIS, question);

		return setType(question.assistantFactory, node, leftType, rightType);
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

		if (!TypeComparator.isSubType(timedExpType, new AIntNumericBasicType(), question.assistantFactory))
		{
			issueHandler.addTypeError(timedExp, TypeErrorMessages.TIME_UNIT_EXPRESSION_MUST_BE_NAT, timedExp
					+ "", timedExpType + "");
		}

		return TypeCheckerUtil.setType(question.assistantFactory, node, leftType, rightType);
	}

	@Override
	public PType caseAInternalChoiceReplicatedAction(
			AInternalChoiceReplicatedAction node, TypeCheckInfo question)
			throws AnalysisException
	{

		PAction repAction = node.getReplicatedAction();
		LinkedList<PSingleDeclaration> repDecl = node.getReplicationDeclaration();

		List<PDefinition> defs = new Vector<PDefinition>();

		for (PSingleDeclaration d : repDecl)
		{
			PType type = d.apply(THIS, question);

			for (PDefinition newDef : type.getDefinitions())
			{
				defs.add(newDef);
			}

		}

		PType actionType = repAction.apply(THIS, question.newScope(defs));

		return TypeCheckerUtil.setType(question.assistantFactory, node, actionType);

	}

	@Override
	public PType caseAGeneralisedParallelismReplicatedAction(
			AGeneralisedParallelismReplicatedAction node, TypeCheckInfo question)
			throws AnalysisException
	{
		PType csexpType = node.getChansetExpression().apply(channelSetChecker, question);

		if (csexpType == null)
		{
			issueHandler.addTypeError(node, TypeErrorMessages.EXPECTED_A_CHANNELSET, ""
					+ csexpType);
			return null;
		}

		PType sexpType = node.getNamesetExpression().apply(nameSetChecker, question);

		if (sexpType == null)
		{
			issueHandler.addTypeError(node, TypeErrorMessages.EXPECTED_A_NAMESET, ""
					+ sexpType);
			return null;
		}

		List<PDefinition> defs = new Vector<PDefinition>();

		for (PSingleDeclaration decl : node.getReplicationDeclaration())
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

				CmlLexNameToken name = new CmlLexNameToken("", singleDecl.getIdentifier());

				ALocalDefinition def = AstFactory.newALocalDefinition(name.getLocation(), name, NameScope.LOCAL, expType);
				defs.add(def);
			}

			if (decl instanceof ATypeSingleDeclaration)
			{
				ATypeSingleDeclaration singleDecl = (ATypeSingleDeclaration) decl;
				CmlLexNameToken name = new CmlLexNameToken("", singleDecl.getIdentifier());
				ALocalDefinition def = AstFactory.newALocalDefinition(name.getLocation(), name, singleDecl.getNameScope(), singleDecl.getType());
				defs.add(def);
			}
		}

		// FIXME how should we handle write only checks
		PType repActionType = node.getReplicatedAction().apply(THIS, question.newScope(defs, NameScope.NAMESANDANYSTATE));

		return TypeCheckerUtil.setType(question.assistantFactory, node, repActionType);
	}

	@Override
	public PType caseAExternalChoiceReplicatedAction(
			AExternalChoiceReplicatedAction node, TypeCheckInfo question)
			throws AnalysisException
	{

		PAction action = node.getReplicatedAction();
		LinkedList<PSingleDeclaration> decl = node.getReplicationDeclaration();

		List<PDefinition> defs = new Vector<PDefinition>();

		for (PSingleDeclaration d : decl)
		{
			PType declType = d.apply(THIS, question);
			for (PDefinition def : declType.getDefinitions())
			{
				defs.add(def);
			}
		}

		PType actionType = action.apply(THIS, question.newScope(defs));

		return TypeCheckerUtil.setType(question.assistantFactory, node, actionType);
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
		return TypeCheckerUtil.setType(question.assistantFactory, node, leftActionType, rightActionType);
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
			leftNamesetExp.apply(nameSetChecker, question);

		}

		if (rightnamesetExp != null)
		{
			rightnamesetExp.apply(nameSetChecker, question);
		}

		// All done!
		return TypeCheckerUtil.setType(question.assistantFactory, node, leftActionType, rightActionType);
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
		PVarsetExpression rightnamesetExp = node.getRightNamesetExpression();

		// type-check sub-actions
		PType leftActionType = leftAction.apply(THIS, question);

		PType rightActionType = rightAction.apply(THIS, question);

		// type-check the chanset
		chansetExp.apply(channelSetChecker, question);

		PType leftName = null;
		PType rightName = null;
		// type-check the namesets
		if (leftNamesetExp != null)
		{
			leftName = leftNamesetExp.apply(nameSetChecker, question);
		}

		if (rightnamesetExp != null)
		{
			rightName = rightnamesetExp.apply(nameSetChecker, question);
		}

		// No overlap possible if they are null
		if (leftName != null && rightName != null)
		{
			// no overlap possible if empty
			if (!(leftName.getDefinitions().isEmpty() || rightName.getDefinitions().isEmpty()))
			{
				// check disjointness
				boolean disjoint = true;
				List<String> repeatedIds = new Vector<String>();
				for (PDefinition lDef : leftName.getDefinitions())
				{
					if (rightName.getDefinitions().contains(lDef))
					{
						disjoint = false;
						// report disjoint required error
						// System.out.println();
						repeatedIds.add(lDef.getName() + "");
					}
				}

				// report global disjoint error
				if (!disjoint)
				{
					issueHandler.addTypeError(leftNamesetExp, TypeErrorMessages.PARALLEL_NAMESETS_MUST_BE_DISJOINT_REPEATED_STATE, ""
							+ Utils.listToString(repeatedIds, ","));
				}
			}

		}

		// All done!
		return TypeCheckerUtil.setType(question.assistantFactory, node, leftActionType, rightActionType);
	}

	@Override
	public PType caseAChannelRenamingAction(AChannelRenamingAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		// PAction action = node.getAction();
		//
		// SRenameChannelExp renameExp = node.getRenameExpression();

		// FIXME throw new InternalException(0, "caseAChannelRenamingAction not implemented");
		return setTypeVoid(node);
		// return new AActionType(node.getLocation(), true);
	}

	@Override
	public PType caseAWaitAction(AWaitAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		PExp timedExp = node.getExpression();
		PType timedExpType = timedExp.apply(THIS, question);

		if (!TypeComparator.isSubType(timedExpType, new AIntNumericBasicType(), question.assistantFactory))
		{
			issueHandler.addTypeError(timedExpType, TypeErrorMessages.TIME_UNIT_EXPRESSION_MUST_BE_NAT, timedExp
					+ "", timedExp + " (" + timedExpType + ")");
			return null;
		}

		return TypeCheckerUtil.setType(question.assistantFactory, node, AstFactory.newAVoidType(node.getLocation()));
	}

	@Override
	public PType caseAMuAction(AMuAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		// extract elements from the node
		LinkedList<ILexIdentifierToken> ids = node.getIdentifiers();
		LinkedList<PAction> acts = node.getActions();

		// too many identifiers
		if (ids.size() > acts.size())
		{
			for (int i = acts.size(); i < ids.size(); i++)
			{
				issueHandler.addTypeError(ids.get(i), TypeErrorMessages.IDENTIFIER_IS_MISSING_ACTION_DEFINITION, ids.get(i).getName());
			}
		} else if (ids.size() < acts.size())
		{
			for (int i = ids.size(); i < acts.size(); i++)
			{
				issueHandler.addTypeWarning(acts.get(i), TypeWarningMessages.UNREACHABLE_DEFINITION);
			}
		}

		List<PDefinition> local = new Vector<PDefinition>();

		// add IDs to the environment
		for (ILexIdentifierToken id : ids)
		{
			local.add(CmlAstFactory.newAActionDefinition(id, null));
		}

		TypeCheckInfo info = new TypeCheckInfo(question.assistantFactory, new FlatCheckedEnvironment(question.assistantFactory, local, question.env, NameScope.LOCAL), question.scope);
		// check the actions
		List<PType> types = new Vector<PType>();
		for (PAction act : acts)
		{
			PType actType = act.apply(THIS, info);
			types.add(actType);
		}

		return TypeCheckerUtil.setType(question.assistantFactory, node, types);
	}

	@Override
	public PType caseAChaosAction(AChaosAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{
		return TypeCheckerUtil.setTypeVoid(node);
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

		if (!TypeComparator.isSubType(timeExpType, new ANatNumericBasicType(), question.assistantFactory))
		{
			issueHandler.addTypeError(timeExp, TypeErrorMessages.TIME_UNIT_EXPRESSION_MUST_BE_NAT, timeExp
					+ "", timeExpType + "");

		}

		return setType(question.assistantFactory, node, leftType, rightType);
	}

	@Override
	public PType caseASequentialCompositionReplicatedAction(
			ASequentialCompositionReplicatedAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		PAction replicatedAction = node.getReplicatedAction();
		LinkedList<PSingleDeclaration> decls = node.getReplicationDeclaration();

		List<PDefinition> localDefinitions = new Vector<PDefinition>();
		Environment repActionEnv = new FlatCheckedEnvironment(question.assistantFactory, localDefinitions, question.env, NameScope.NAMES);

		for (PSingleDeclaration decl : decls)
		{
			PType declType = decl.apply(THIS, question);

			if (declType instanceof ASetType)
			{
				issueHandler.addTypeError(declType, TypeErrorMessages.SEQ_TYPE_EXPECTED, decl
						+ "", declType + "");
				return null;
			}

			for (PDefinition def : declType.getDefinitions())
			{
				localDefinitions.add(def);
			}
		}

		PType replicatedActionType = replicatedAction.apply(THIS, new TypeCheckInfo(question.assistantFactory, repActionEnv, NameScope.NAMES));

		return setType(question.assistantFactory, node, replicatedActionType);
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

		leftChanSet.apply(channelSetChecker, question);

		if (leftNameSet != null)
		{
			leftNameSet.apply(nameSetChecker, question);
		}

		PType rightActionType = rightAction.apply(THIS, question);

		rightChanSet.apply(channelSetChecker, question);

		if (rightNameSet != null)
		{
			rightNameSet.apply(nameSetChecker, question);
		}

		return setType(question.assistantFactory, node, leftActionType, rightActionType);
	}

	@Override
	public PType caseAGuardedAction(AGuardedAction node, TypeCheckInfo question)
			throws AnalysisException
	{

		PExp exp = node.getExpression();
		PAction action = node.getAction();

		PType expType = exp.apply(THIS, question);

		if (!TypeComparator.isSubType(expType, AstFactory.newABooleanBasicType(node.getLocation()), question.assistantFactory))
		{
			issueHandler.addTypeError(exp, TypeErrorMessages.INCOMPATIBLE_TYPE, "bool", ""
					+ expType);
			return null;
		}

		PType actionType = action.apply(THIS, question);

		return setType(question.assistantFactory, node, actionType);

	}

	@Override
	public PType caseADivAction(ADivAction node, TypeCheckInfo question)
			throws AnalysisException
	{
		return setTypeVoid(node);
	}

	@Override
	public PType caseACommonInterleavingReplicatedAction(
			ACommonInterleavingReplicatedAction node, TypeCheckInfo question)
			throws AnalysisException
	{

		PVarsetExpression namesetExp = node.getNamesetExpression();
		PAction repAction = node.getReplicatedAction();
		LinkedList<PSingleDeclaration> decls = node.getReplicationDeclaration();

		namesetExp.apply(nameSetChecker, question);

		List<PDefinition> defs = new Vector<PDefinition>();

		for (PSingleDeclaration decl : decls)
		{
			PType declType = decl.apply(THIS, question);

			for (PDefinition def : declType.getDefinitions())
			{
				defs.add(def);
			}
		}

		PType repActionType = repAction.apply(THIS, question.newScope(defs));

		return setType(question.assistantFactory, node, repActionType);
	}

	@Override
	public PType caseAInterleavingReplicatedAction(
			AInterleavingReplicatedAction node, TypeCheckInfo question)
			throws AnalysisException
	{

		PVarsetExpression namesetExp = node.getNamesetExpression();
		PAction repAction = node.getReplicatedAction();
		LinkedList<PSingleDeclaration> decls = node.getReplicationDeclaration();

		namesetExp.apply(nameSetChecker, question);

		List<PDefinition> defs = new Vector<PDefinition>();

		for (PSingleDeclaration decl : decls)
		{
			PType declType = decl.apply(THIS, question);

			for (PDefinition def : declType.getDefinitions())
			{
				defs.add(def);
			}
		}

		PType repActionType = repAction.apply(THIS, question.newScope(defs));

		return setType(question.assistantFactory, node, repActionType);
	}

	@Override
	public PType caseAInternalChoiceAction(AInternalChoiceAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		PAction left = node.getLeft();
		PAction right = node.getRight();

		PType leftType = left.apply(THIS, question);

		PType rightType = right.apply(THIS, question);

		return setType(question.assistantFactory, node, leftType, rightType);
	}

	@Override
	public PType caseAReferenceAction(AReferenceAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		PDefinition def = question.env.findName(node.getName(), question.scope); // findDefinition(node.getName().getIdentifier(),
																					// question.env);

		if (def == null)
		{
			issueHandler.addTypeError(node, TypeErrorMessages.UNDEFINED_SYMBOL, node.getName()
					+ "");
			node.setType(AstFactory.newAUnknownType(node.getLocation()));
			return node.getType();
		}

		PType type = null;

		if (!(def instanceof AActionDefinition))
		{
			issueHandler.addTypeError(node, TypeErrorMessages.EXPECTED_AN_ACTION, " a "
					+ question.assistantFactory.createPDefinitionAssistant().kind(def)
					+ " deinition:" + node.getName());

		} else
		{

			AActionDefinition actionDef = (AActionDefinition) def;
			node.setActionDefinition(actionDef);
		}

		if (type == null)

		{
			type = AstFactory.newAVoidType(node.getLocation());
		}

		node.setType(type);
		return node.getType();
	}

	@SuppressWarnings("deprecation")
	@Override
	public PType caseACommunicationAction(ACommunicationAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{
		AChannelType chanType = null;

		PDefinition channel = findDefinition(node.getIdentifier(), question.env);

		// There should be a channel defined with this name
		if (null == channel)
		{
			issueHandler.addTypeError(node, TypeErrorMessages.CHANNEL_NOT_DECLARED, node.getIdentifier().getName());
		} else if (!(channel instanceof AChannelDefinition))
		{
			issueHandler.addTypeError(channel, TypeErrorMessages.DEFINITION_X_BUT_FOUND_Y, "channel", question.assistantFactory.createPDefinitionAssistant().kind(channel), channel.getName().getName()
					+ "");
		} else
		{
			chanType = ((AChannelDefinition) channel).getType();
		}

		if (chanType == null)
		{
			chanType = new AChannelType(node.getLocation(), true, new Vector<PType>());
		}

		List<PDefinition> localDefinitions = new Vector<PDefinition>();
		Environment local = new FlatCheckedEnvironment(question.assistantFactory, localDefinitions, question.env, NameScope.NAMES);
		TypeCheckInfo info = new TypeCheckInfo(question.assistantFactory, local, NameScope.NAMESANDSTATE);

		LinkedList<PCommunicationParameter> commParams = node.getCommunicationParameters();

		if (chanType.getParameters().size() > commParams.size())
		{
			issueHandler.addTypeError(node.getIdentifier(), TypeErrorMessages.COMMUNICATION_TOO_FEW_ARGUMENTS, node.getIdentifier().getName(), ""
					+ commParams.size(), "" + chanType.getParameters().size());
		} else if (chanType.getParameters().size() < commParams.size())
		{
			issueHandler.addTypeError(node.getIdentifier(), TypeErrorMessages.COMMUNICATION_TOO_MANY_ARGUMENTS, node.getIdentifier().getName(), ""
					+ commParams.size(), "" + chanType.getParameters().size());
		}

		if (chanType.getParameters().isEmpty() && !commParams.isEmpty())
		{
			issueHandler.addTypeError(node.getIdentifier(), TypeErrorMessages.COMMUNICATION_NOT_ALLOWED_OVER_UNTYPED_CHANNEL, node.getIdentifier().getName());
		}

		PType expectedType = null;
		PType actualType = null;
		for (int i = 0; i < commParams.size(); i++)
		{
			if (chanType.getParameters().size() > i)
			{
				expectedType = chanType.getParameters().get(i);
			} else
			{
				expectedType = AstFactory.newAUnknownType(chanType.getLocation());
			}
			actualType = null;
			List<ILexNameToken> names = new Vector<ILexNameToken>();

			PCommunicationParameter commParam = commParams.get(i);

			if (commParam instanceof AReadCommunicationParameter)
			{
				AReadCommunicationParameter readParam = (AReadCommunicationParameter) commParam;
				PPattern p = readParam.getPattern();

				question.assistantFactory.createPPatternAssistant().typeResolve(p, THIS, question);

				actualType = question.assistantFactory.createPPatternAssistant().getPossibleType(p);

				names.addAll(PPatternAssistantTC.getVariableNames(p));

			} else if (commParam instanceof AWriteCommunicationParameter
					|| commParam instanceof ASignalCommunicationParameter)
			{
				if (commParam.getExpression() != null)
				{
					actualType = commParam.getExpression().apply(THIS, info);
				} else
				{
					issueHandler.addTypeError(commParam, TypeErrorMessages.COMMUNICATION_PARAMETER_MISSING, ""
							+ i, "" + expectedType);
					actualType = AstFactory.newAUnknownType(node.getLocation());
				}
			}

			// Type check parameter
			if (!TypeComparator.compatible(expectedType, actualType))
			{
				issueHandler.addTypeError(commParam, TypeErrorMessages.COMMUNICATION_PARAMETER_TYPE_NOT_COMPATIBLE, ""
						+ actualType, "" + i, "" + expectedType);

			}

			// Set the type to the expected one. If it was a write/signal parm this doesn't matter otherwise this gives
			// a better check downstream
			actualType = expectedType;

			// finally add the parameter to the available definitions for the -> action and constraint if the commparm
			// was a read
			if (!names.isEmpty())
			{
				List<PType> localTypes = new Vector<PType>();
				if (actualType instanceof AProductType)
				{
					localTypes.addAll(((AProductType) actualType).getTypes());
				} else
				{
					localTypes.add(actualType);
				}

				for (int j = 0; j < names.size(); j++)
				{
					PType t = null;
					if (localTypes.size() > j)
					{
						t = localTypes.get(j);
					} else
					{
						t = AstFactory.newAUnknownType(actualType.getLocation());
					}
					localDefinitions.add(AstFactory.newALocalDefinition(names.get(j).getLocation(), names.get(j), NameScope.LOCAL, t));
				}

				PExp constraintExp = commParam.getExpression();
				if (constraintExp != null)
				{
					PType constraintType = constraintExp.apply(THIS, new TypeCheckInfo(question.assistantFactory, local, NameScope.NAMESANDSTATE));

					if (!(constraintType instanceof ABooleanBasicType))
					{
						issueHandler.addTypeError(constraintExp, TypeErrorMessages.CONSTRAINT_MUST_BE_A_BOOLEAN_EXPRESSION, constraintExp.toString());
					}
				}
			}
		}

		PType commType = node.getAction().apply(this, info);

		return setType(question.assistantFactory, node, commType);
	}

	@Override
	public PType caseASequentialCompositionAction(
			ASequentialCompositionAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		PType leftType = node.getLeft().apply(THIS, question);
		PType rightType = node.getRight().apply(THIS, question);

		return setType(question.assistantFactory, node, leftType, rightType);
	}

	@Override
	public PType caseASkipAction(ASkipAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{
		return setTypeVoid(node);
	}

	@Override
	public PType caseAExternalChoiceAction(AExternalChoiceAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		PAction left = node.getLeft();
		PAction right = node.getRight();

		PType leftType = left.apply(THIS, question);

		PType rightType = right.apply(THIS, question);

		return setType(question.assistantFactory, node, leftType, rightType);
	}

	@Override
	public PType caseAHidingAction(AHidingAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		PAction action = node.getLeft();
		PVarsetExpression chanSet = node.getChansetExpression();

		PType actionType = action.apply(THIS, question);

		chanSet.apply(channelSetChecker, question);

		return setType(question.assistantFactory, node, actionType);
	}

	@Override
	public PType defaultPParametrisation(PParametrisation node,
			TypeCheckInfo question) throws AnalysisException
	{
		ALocalDefinition decl = node.getDeclaration();

		try
		{
			return question.assistantFactory.createPTypeAssistant().typeResolve(decl.getType(), null, THIS, question);
		} catch (TypeCheckException te)
		{
			TypeChecker.report(3427, te.getMessage(), te.location);
		}

		return null;
	}

	@Override
	public PType caseAVresParametrisation(AVresParametrisation node,
			TypeCheckInfo question) throws AnalysisException
	{
		return defaultPParametrisation(node, question);
	}

	@Override
	public PType caseAParametrisedInstantiatedAction(
			AParametrisedInstantiatedAction node, TypeCheckInfo question)
			throws AnalysisException
	{

		AParametrisedAction action = node.getAction();
		LinkedList<PExp> args = node.getArgs();

		List<PDefinition> defs = new Vector<PDefinition>();

		LinkedList<PParametrisation> parameterNames = node.getAction().getParametrisations();
		int i = 0;
		for (PExp exp : args)
		{
			// FIXME what is this exp for, it is not used in the check
			/* PType expType = */exp.apply(THIS, question);

			if (i > parameterNames.size())
			{
				continue;
			}
			PParametrisation pa = parameterNames.get(i++);
			pa.apply(THIS, question);
			ALocalDefinition localDef = pa.getDeclaration();
			defs.add(localDef);
		}

		PType actionType = action.apply(THIS, question.newScope(defs));

		return setType(question.assistantFactory, node, actionType);
	}

	@Override
	public PType caseAStartDeadlineAction(AStartDeadlineAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		PAction event = node.getLeft();
		PExp timeExp = node.getExpression();

		PType type = event.apply(THIS, question);

		PType expType = timeExp.apply(THIS, question);

		if (!TypeComparator.isSubType(expType, new ANatNumericBasicType(), question.assistantFactory))
		{
			issueHandler.addTypeError(timeExp, TypeErrorMessages.TIME_UNIT_EXPRESSION_MUST_BE_NAT, timeExp
					+ "", expType + "");
		}
		return setType(question.assistantFactory, node, type);
	}

	@Override
	public PType caseAEndDeadlineAction(AEndDeadlineAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		PAction event = node.getLeft();
		PExp timeExp = node.getExpression();

		PType eventType = event.apply(THIS, question);

		PType timeExpType = timeExp.apply(THIS, question);

		if (!TypeComparator.isSubType(timeExpType, new ANatNumericBasicType(), question.assistantFactory))
		{
			issueHandler.addTypeError(timeExp, TypeErrorMessages.TIME_UNIT_EXPRESSION_MUST_BE_NAT, timeExp
					+ "", timeExpType + "");
		}

		return setType(question.assistantFactory, node, eventType);
	}

	@Override
	public PType caseAStopAction(AStopAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{
		return setTypeVoid(node);
	}

	@Override
	public PType caseAParametrisedAction(AParametrisedAction node,
			TypeCheckInfo question) throws AnalysisException
	{
		TypeCheckInfo info = question;

		if (!node.getParametrisations().isEmpty())
		{
			info = question.newInfo(PParametrisationAssistant.updateEnvironment(question.env, node.getParametrisations()));
		}

		PType actionType = node.getAction().apply(THIS, info);

		return setType(question.assistantFactory, node, actionType);
	}

	@Override
	public PType caseAAlphabetisedParallelismReplicatedAction(
			AAlphabetisedParallelismReplicatedAction node,
			TypeCheckInfo question) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return setTypeVoid(node);
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
