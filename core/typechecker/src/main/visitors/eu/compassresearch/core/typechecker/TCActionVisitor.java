package eu.compassresearch.core.typechecker;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.APrivateAccess;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.ATupleExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.patterns.ADefPatternBind;
import org.overture.ast.patterns.AExpressionPattern;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.ATuplePattern;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.statements.AExternalClause;
import org.overture.ast.statements.AIdentifierStateDesignator;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.typechecker.Pass;
import org.overture.ast.types.AAccessSpecifierAccessSpecifier;
import org.overture.ast.types.ABooleanBasicType;
import org.overture.ast.types.AFieldField;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.ANatNumericBasicType;
import org.overture.ast.types.AOperationType;
import org.overture.ast.types.AProductType;
import org.overture.ast.types.ARecordInvariantType;
import org.overture.ast.types.ASetType;
import org.overture.ast.types.AUnresolvedType;
import org.overture.ast.types.PType;
import org.overture.ast.types.SSeqType;
import org.overture.typechecker.Environment;
import org.overture.typechecker.FlatCheckedEnvironment;
import org.overture.typechecker.TypeCheckInfo;
import org.overture.typechecker.assistant.definition.PDefinitionListAssistantTC;
import org.overture.typechecker.assistant.definition.SClassDefinitionAssistantTC;
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
import eu.compassresearch.ast.actions.ADeclarationInstantiatedAction;
import eu.compassresearch.ast.actions.ADeclareStatementAction;
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
import eu.compassresearch.ast.actions.AInternalChoiceAction;
import eu.compassresearch.ast.actions.AInternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AInterruptAction;
import eu.compassresearch.ast.actions.ALetStatementAction;
import eu.compassresearch.ast.actions.AMuAction;
import eu.compassresearch.ast.actions.ANonDeterministicAltStatementAction;
import eu.compassresearch.ast.actions.ANonDeterministicDoStatementAction;
import eu.compassresearch.ast.actions.ANonDeterministicIfStatementAction;
import eu.compassresearch.ast.actions.AParametrisedAction;
import eu.compassresearch.ast.actions.AParametrisedInstantiatedAction;
import eu.compassresearch.ast.actions.AMultipleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.ANewStatementAction;
import eu.compassresearch.ast.actions.AReadCommunicationParameter;
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
import eu.compassresearch.ast.actions.ASynchronousParallelismParallelAction;
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
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AClassDefinition;
import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.SCmlOperationDefinition;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.expressions.SRenameChannelExp;
import eu.compassresearch.ast.types.AActionType;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.ast.types.AErrorType;
import eu.compassresearch.ast.types.AProcessType;
import eu.compassresearch.ast.types.AStatementType;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
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

	private static APatternListTypePair t;
	static
	{
		LexLocation l;

	}

	
	
	@Override
	public PType caseAValParametrisation(AValParametrisation node,
			TypeCheckInfo question) throws AnalysisException {
		
		ATypeSingleDeclaration decl = node.getDeclaration();
		List<PDefinition> defs = new LinkedList<PDefinition>();
		
		for(LexIdentifierToken id : decl.getIdentifiers())
		{
			LexNameToken name = new LexNameToken("",id);
			ALocalDefinition localDef = AstFactory.newALocalDefinition(node.getLocation(), name, NameScope.LOCAL, decl.getType());
			defs.add(localDef);
		}
		
		PType res = new AActionType(node.getLocation(),true);
		res.setDefinitions(defs);
		return res;
	}
	
	@Override
	public PType caseAResParametrisation(AResParametrisation node,
			TypeCheckInfo question) throws AnalysisException {
		
		ATypeSingleDeclaration decl = node.getDeclaration();
		List<PDefinition> defs = new LinkedList<PDefinition>();
		
		for(LexIdentifierToken id : decl.getIdentifiers())
		{
			LexNameToken name = new LexNameToken("",id);
			ALocalDefinition localDef = AstFactory.newALocalDefinition(node.getLocation(), name, NameScope.LOCAL, decl.getType());
			defs.add(localDef);
		}
		
		PType res = new AActionType(node.getLocation(),true);
		res.setDefinitions(defs);
		return res;
	}

	/**
	 * Actions can reference and call each other and for that we have two choices. 
	 * 
	 * Lookup restricted to declared order or dynamic order.
	 * 
	 * The design choice to go with dynamic order for flexibility in CML
	 * has been made. This solution suffers from possible cyclic references 
	 * which are resolved using the map below.
	 * 
	 * TCDeclAndDefVisitor will setup this map in the caseAActionsDefinition
	 * and tear it down again upon leaving. ACallStatementAction and AReferencedAction
	 * uses this map to check for cyclic references before going recursively down and
	 * type checking the referenced action if it has no type already.
	 * 
	 * The actionDefinntionsSeen is a map from referencee to referencer. Every referencee will
	 * maintain a list of Actions from where it has been referenced.
	 */
	private Map<AActionDefinition, List<PAction>> actionDefinitionsSeen = null;

	// use by caseAActionsDefinitions in TCDeclAndDefVisitor.
	void setupActionCycleMap() { this.actionDefinitionsSeen = new HashMap<AActionDefinition, List<PAction>>(); }
	// set it to null will give GC a go at it
	void tearDownActionCycleMap() { this.actionDefinitionsSeen = null; }
	// Add action to the map with empty referencer list
	void registerActionForCycleDetection(AActionDefinition actionDef){
		actionDefinitionsSeen.put(actionDef, new LinkedList<PAction>());
	}
	/**
	 * 
	 * Type check a referenced action that has not been type checked yet.
	 * 
	 * Check that there is no cycle.
	 * 
	 * @param referencer -- The node that dereferences 
	 * @param referencee -- The lookup node that has been dereferenced
	 * @param question -- The current environment
	 * @return The type of the referenced action.
	 * @throws AnalysisException - if something unpredicted happens.
	 */
	private PType resolveUntypedActionReference(PAction referencer, PDefinition referencee, TypeCheckInfo question) throws AnalysisException 
	{
		PType res = null;

		// get the list of referencers for this current referencee
		List<PAction> referencingActions = null;
		if (actionDefinitionsSeen.containsKey(referencee))
			referencingActions = actionDefinitionsSeen.get(referencee);
		else
			return issueHandler.addTypeError(referencee, "Action \""+referencee+"\" is not part of this group");	


		// Invariant:
		referencingActions.add(referencer);
		if (referencingActions.size() > 1)
		{
			StringBuilder cycleStr = new StringBuilder();
			List<PAction> cur = actionDefinitionsSeen.get(referencee);
			cycleStr.append(""+referencer);
			do
			{
				cycleStr.append(" -> ");
				PAction ac = cur.remove(0);
				cycleStr.append(ac+"");
				cur = actionDefinitionsSeen.get(ac);
			} while(cur != null && cur.size() > 0);
			issueHandler.addTypeWarning(referencee, TypeWarningMessages.CYCLE_ACTION_REFERENCE.customizeMessage(cycleStr.toString()));
			referencee.setType(new AActionType());
			return referencee.getType();
		} 

		referencee.setType(new AActionType());
		return referencee.getType();
	}

	@Override
	public PType caseAIfStatementAction(AIfStatementAction node,
			TypeCheckInfo question) throws AnalysisException {

		PAction thenAction = node.getThenStm();
		PAction elseAction = node.getElseStm();
		PExp testExp = node.getIfExp();
		LinkedList<AElseIfStatementAction> elseIfs = node.getElseIf();

		PType testExpType = testExp.apply(parentChecker,question);
		if (!TCDeclAndDefVisitor.successfulType(testExpType))
		{
			node.setType(issueHandler.addTypeError(testExp, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(testExp+"")));
			return node.getType();
		}

		if (!typeComparator.compatible(new ABooleanBasicType(), testExpType))
		{
			node.setType(issueHandler.addTypeError(testExp,TypeErrorMessages.INCOMPATIBLE_TYPE.customizeMessage("Boolean", ""+testExpType)));
			return node.getType();
		}

		PType thenActionType = thenAction.apply(parentChecker,question);
		if (!TCDeclAndDefVisitor.successfulType(thenActionType))
		{
			node.setType(issueHandler.addTypeError(thenAction, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(thenAction+"")));
			return node.getType();

		}

		//AKM: The else case is optional
		if(elseAction != null)
		{
			PType elseActionType = elseAction.apply(parentChecker,question);
			if (!TCDeclAndDefVisitor.successfulType(elseActionType))
			{
				node.setType(issueHandler.addTypeError(elseAction, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(elseAction+"")));
				return node.getType();

			}
		}

		for(AElseIfStatementAction elseIf : elseIfs)
		{
			PType elseIfType = elseIf.apply(parentChecker,question);
			if (!TCDeclAndDefVisitor.successfulType(elseIfType))
			{
				node.setType(issueHandler.addTypeError(elseIf, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(elseIf+"")));
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
		if (!TCDeclAndDefVisitor.successfulType(leftType))
		{
			node.setType(issueHandler.addTypeError(left, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(""+left)));
			return node.getType();
		}

		PType rightType = right.apply(parentChecker,question);
		if (!TCDeclAndDefVisitor.successfulType(rightType))
		{
			node.setType(issueHandler.addTypeError(right, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(right+"")));
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

		PType leftType = left.apply(parentChecker,question);
		if (!TCDeclAndDefVisitor.successfulType(leftType))
		{
			node.setType(issueHandler.addTypeError(left, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(left+"")));
			return node.getType();
		}


		PType rightType = right.apply(parentChecker,question);
		if (!TCDeclAndDefVisitor.successfulType(rightType))
		{
			node.setType(issueHandler.addTypeError(right,TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(right+"")));
			return node.getType();
		}

		PType timedExpType = timedExp.apply(parentChecker,question);
		if (!TCDeclAndDefVisitor.successfulType(timedExpType))
		{
			node.setType(issueHandler.addTypeError(timedExp, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(timedExp+"")));
			return node.getType();
		}

		if (!typeComparator.isSubType(timedExpType, new AIntNumericBasicType()))
		{
			node.setType(issueHandler.addTypeError(timedExp,TypeErrorMessages.TIME_UNIT_EXPRESSION_MUST_BE_NAT.customizeMessage(timedExp+"", timedExpType+"")));
			return node.getType();
		}

		node.setType(new AActionType());
		return node.getType();
	}



	@Override
	public PType caseAExternalClause(AExternalClause node,
			TypeCheckInfo question) throws AnalysisException {

		LinkedList<LexNameToken> ids = node.getIdentifiers();
		for(LexNameToken id : ids)
		{
			PDefinition def = CmlTCUtil.findDefByAllMeans(question, id);
			if (def == null)
			{
				node.setType(issueHandler.addTypeError(node, TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage(""+id)));
				return node.getType();
			}

			issueHandler.addTypeWarning(def, TypeWarningMessages.INCOMPLETE_TYPE_CHECKING.customizeMessage(def+""));
		}


		return super.caseAExternalClause(node, question);
	}



	@Override
	public PType caseASpecificationStatementAction(
			ASpecificationStatementAction node, TypeCheckInfo question)
					throws AnalysisException {

		CmlTypeCheckInfo cmlEnv = getTypeCheckInfo(question);
		if (cmlEnv == null)
		{
			node.setType(issueHandler.addTypeError(node, TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(node+"")));
			return node.getType();
		}

		CmlTypeCheckInfo prePostEnv = cmlEnv.newScope();
		LinkedList<AExternalClause> externals = node.getExternals();
		for(AExternalClause extClause : externals)
		{
			PType extClauseType = extClause.apply(parentChecker,question);
			if (!TCDeclAndDefVisitor.successfulType(extClauseType))
			{
				node.setType(issueHandler.addTypeError(extClause, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(extClause+"")));
				return node.getType();
			}
			LinkedList<LexNameToken> ids = extClause.getIdentifiers();
			for(LexIdentifierToken id : ids)
			{
				LexNameToken localName = new LexNameToken("",id);
				ALocalDefinition localDef = AstFactory.newALocalDefinition(node.getLocation(), localName, NameScope.LOCAL, extClauseType);
				prePostEnv.addVariable(id, localDef);
			}
		}

		PExp preCond = node.getPrecondition();
		PType preCondType = preCond.apply(parentChecker,prePostEnv);
		if (!TCDeclAndDefVisitor.successfulType(preCondType))
		{
			node.setType(issueHandler.addTypeError(preCond, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(preCond + "")));
			return node.getType();
		}

		PExp postCond = node.getPostcondition();
		PType postCondType = postCond.apply(parentChecker, prePostEnv);
		if (!TCDeclAndDefVisitor.successfulType(postCondType))



			node.setType(new AActionType());
		return node.getType();
	}



	@Override
	public PType caseAInternalChoiceReplicatedAction(
			AInternalChoiceReplicatedAction node, TypeCheckInfo question)
					throws AnalysisException {

		PAction repAction = node.getReplicatedAction();
		LinkedList<PSingleDeclaration> repDecl = node.getReplicationDeclaration();
		for(PSingleDeclaration d : repDecl)
		{
			PType type = d.apply(parentChecker,question);
			if (!TCDeclAndDefVisitor.successfulType(type))
			{
				node.setType(issueHandler.addTypeError(d, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(d+"")));
				return node.getType();
			}
		}

		PType actionType = repAction.apply(parentChecker,question);
		if (!TCDeclAndDefVisitor.successfulType(actionType))
		{
			node.setType(issueHandler.addTypeError(repAction, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(""+repAction)));
			return node.getType();
		}

		node.setType(new AActionType());
		return node.getType();

	}



	@Override
	public PType caseAGeneralisedParallelismReplicatedAction(
			AGeneralisedParallelismReplicatedAction node, TypeCheckInfo question)
					throws AnalysisException {

		// TODO RWL: What is the semantics of this?
		PVarsetExpression csexp = node.getChansetExpression();

		PVarsetExpression sexp = node.getNamesetExpression();

		PAction repAction = node.getReplicatedAction();

		LinkedList<PSingleDeclaration> repDecl = node.getReplicationDeclaration();

		issueHandler.addTypeWarning(node, TypeWarningMessages.INCOMPLETE_TYPE_CHECKING.customizeMessage(""+node));

		node.setType(new AActionType());
		return node.getType();
	}



	@Override
	public PType caseAExternalChoiceReplicatedAction(
			AExternalChoiceReplicatedAction node, TypeCheckInfo question)
					throws AnalysisException {


		PAction action = node.getReplicatedAction();
		LinkedList<PSingleDeclaration> decl = node.getReplicationDeclaration();
		for(PSingleDeclaration d : decl)
		{
			PType declType = d.apply(parentChecker,question);
			if (!TCDeclAndDefVisitor.successfulType(declType))
			{
				node.setType(issueHandler.addTypeError(d, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(d+"")));
				return node.getType();
			}
		}

		PType actionType = action.apply(parentChecker,question);
		if (!TCDeclAndDefVisitor.successfulType(actionType))
		{
			node.setType(issueHandler.addTypeError(action, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(""+action)));
			return node.getType();
		}

		node.setType(new AActionType());
		return node.getType();
	}



	@Override
	public PType caseANonDeterministicIfStatementAction(
			ANonDeterministicIfStatementAction node, TypeCheckInfo question)
					throws AnalysisException {

		LinkedList<ANonDeterministicAltStatementAction> alternatives = node.getAlternatives();
		for(ANonDeterministicAltStatementAction alt : alternatives)
		{
			PType altType = alt.apply(parentChecker,question);
			if (!TCDeclAndDefVisitor.successfulType(altType))
			{
				node.setType(issueHandler.addTypeError(alt, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(alt+"")));
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
		AIdentifierStateDesignator destVar = (AIdentifierStateDesignator) node
				.getDestination();

		PDefinition dest = cmlEnv.lookupVariable(destVar.getName());
		if (dest == null) {
			node.setType(issueHandler.addTypeError(
					node,
					TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage(""
							+ destVar.toString())));
			return node.getType();
		}
		PType destType = dest.getType();

		// lookup class
		PType classType = cmlEnv.lookupType(node.getClassName());
		if (classType == null) {
			node.setType(issueHandler.addTypeError(
					node,
					TypeErrorMessages.UNDEFINED_TYPE.customizeMessage(""
							+ classType)));
			return node.getType();
		}

		if (!(classType instanceof AClassDefinition)) {
			node.setType(issueHandler.addTypeError(
					node,
					TypeErrorMessages.EXPECTED_CLASS.customizeMessage(""
							+ node.getClassName())));
			return node.getType();
		}

		// make sure they match
		if (!typeComparator.isSubType(classType, destType)) {
			node.setType(issueHandler.addTypeError(node,
					TypeErrorMessages.EXPECTED_SUBTYPE_RELATION
					.customizeMessage(classType.toString(),
							destType.toString())));
			return node.getType();
		}

		// // typecheck arguments
		for (PExp arg : node.getArgs()) {
			PType pt = arg.apply(parentChecker, cmlEnv);
			if (!TCDeclAndDefVisitor.successfulType(pt)) {
				node.setType(issueHandler.addTypeError(
						arg,
						TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage(""
								+ arg)));
				return node.getType();
			}
			argtypes.add(pt);
		}

		AClassDefinition cpd = (AClassDefinition) classType
				.getDefinitions().get(0);

		PDefinition constructor = SClassDefinitionAssistantTC.findConstructor(
				cpd.getClassDefinition(), argtypes);

		if (constructor == null) {
			node.setType(issueHandler.addTypeError(node,
					TypeErrorMessages.MISSING_CONSTRUCTOR
					.customizeMessage(classType.toString(),
							argtypes.toString())));
			return node.getType();
		}

		// maybe more constructor stuff necessary?


		// // set stuff
		node.setClassdef(cpd);
		node.setCtorDefinition(constructor);

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
			if (!TCDeclAndDefVisitor.successfulType(pt)) {
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
			if (!TCDeclAndDefVisitor.successfulType(pt)) {
				node.setType(issueHandler.addTypeError(pd,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
						.customizeMessage("" + pd)));
				return node.getType();
			}
			newCmlEnv.addVariable(pd.getName(), pd);
		}

		// type check sub-action
		PType actionType = action.apply(parentChecker, newCmlEnv);
		if (!TCDeclAndDefVisitor.successfulType(actionType)) {
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
		if (!TCDeclAndDefVisitor.successfulType(leftActionType)) {
			node.setType(issueHandler.addTypeError(leftAction,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
					.customizeMessage("" + leftAction)));
			return node.getType();
		}

		PType rightActionType = rightAction.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(rightActionType)) {
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
		if (!TCDeclAndDefVisitor.successfulType(leftActionType)) {
			node.setType(issueHandler.addTypeError(leftAction,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
					.customizeMessage("" + leftAction)));
			return node.getType();
		}

		PType rightActionType = rightAction.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(rightActionType)) {
			node.setType(issueHandler.addTypeError(rightAction,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
					.customizeMessage("" + rightAction)));
			return node.getType();
		}

		// type-check the namesets
		if (leftNamesetExp != null)
		{
			PType leftNameSetType = leftNamesetExp.apply(parentChecker, question);

			if (!TCDeclAndDefVisitor.successfulType(leftNameSetType))
				return issueHandler.addTypeError(leftNamesetExp,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
						.customizeMessage(leftNamesetExp + ""));
		}

		if (rightnamesetExp != null) {
			PType rightNameSetType = rightnamesetExp.apply(parentChecker, question);
			if (!TCDeclAndDefVisitor.successfulType(rightNameSetType))
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
		if (!TCDeclAndDefVisitor.successfulType(actionType)) {
			node.setType(issueHandler.addTypeError(action,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
					.customizeMessage("" + action)));
			return node.getType();
		}

		for (ATypeSingleDeclaration declr : declarations) {
			PType declType = declr.apply(parentChecker, question);
			if (!TCDeclAndDefVisitor.successfulType(declType))
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
		if (!TCDeclAndDefVisitor.successfulType(leftActionType)) {
			node.setType(issueHandler.addTypeError(leftAction,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
					.customizeMessage("" + leftAction)));
			return node.getType();
		}

		PType rightActionType = rightAction.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(rightActionType)) {
			node.setType(issueHandler.addTypeError(rightAction,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
					.customizeMessage("" + rightAction)));
			return node.getType();
		}

		// type-check the chanset
		PType chanSetType = chansetExp.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(chanSetType))
			return issueHandler.addTypeError(chansetExp,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
					.customizeMessage(chansetExp + ""));

		// type-check the namesets
		if (leftNamesetExp != null)
		{
			PType leftNameSetType = leftNamesetExp.apply(parentChecker, question);
			if (!TCDeclAndDefVisitor.successfulType(leftNameSetType))
				return issueHandler.addTypeError(leftNamesetExp,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
						.customizeMessage(leftNamesetExp + ""));
		}
		
		if (rightnamesetExp != null)
		{
			PType rightNameSetType = rightnamesetExp.apply(parentChecker, question);
			if (!TCDeclAndDefVisitor.successfulType(rightNameSetType))
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
		if (!TCDeclAndDefVisitor.successfulType(setType))
		{
			node.setType(issueHandler.addTypeError(set, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(""+set)));
			return node.getType();
		}

		if (!(setType instanceof ASetType))
		{
			node.setType(issueHandler.addTypeError(set, TypeErrorMessages.SET_TYPE_EXPECTED.customizeMessage(""+set,""+setType)));
			return node.getType();
		}
		else
			patternType = setType;

		CmlTypeCheckInfo cmlEnv = getTypeCheckInfo(question);

		PType patternUnknownType = pattern.apply(parentChecker,question);
		if (!TCDeclAndDefVisitor.successfulType(patternUnknownType))
		{
			node.setType(issueHandler.addTypeError(pattern, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(pattern+"")));
			return node.getType();
		}

		if (!(patternUnknownType instanceof AUnresolvedType))
		{
			node.setType(issueHandler.addTypeError(pattern,TypeErrorMessages.INCOMPATIBLE_TYPE.customizeMessage("Unresolved Type",patternUnknownType+"")));
			return node.getType();
		}
		else
		{

		}

		node.setType(new AActionType());
		return node.getType();
	}



	@Override
	public PType caseAForSequenceStatementAction(
			AForSequenceStatementAction node, org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {

		PAction action = node.getAction();
		PExp exp = node.getExp();
		ADefPatternBind pattern = node.getPatternBind();
		PType patternType = null;

		// Get an CML environment  
		CmlTypeCheckInfo cmlQuestion = getTypeCheckInfo(question);
		if (cmlQuestion == null)
		{
			node.setType(issueHandler.addTypeError(exp, TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(exp+"")));
			return node.getType();
		}

		// Type check the expression ... 
		PType expType = exp.apply(parentChecker,question);
		if (!TCDeclAndDefVisitor.successfulType(expType))
		{
			node.setType(issueHandler.addTypeError(exp, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(""+exp)));
			return node.getType();
		}		

		// ... it has to be a sequence
		if (!(expType instanceof SSeqType))
		{
			node.setType(issueHandler.addTypeError(exp, TypeErrorMessages.SEQ_TYPE_EXPECTED.customizeMessage(exp+"",expType+"")));
			return node.getType();
		}
		else
			patternType = ((SSeqType)expType).getSeqof();

		// Create an extended local environment
		CmlTypeCheckInfo newEnv = cmlQuestion.newScope();

		pattern.setType(patternType);

		PType patType = pattern.apply(parentChecker,question);
		List<PDefinition> defs = patType.getDefinitions();
		for(PDefinition d : defs)
		{
			ALocalDefinition localDef = AstFactory.newALocalDefinition(pattern.getLocation(), d.getName(), NameScope.LOCAL,patternType);
			newEnv.addVariable(d.getName(), localDef);
		}


		// In this new environment lets check the given action
		PType actionType = action.apply(parentChecker,newEnv);
		if (!TCDeclAndDefVisitor.successfulType(actionType))
		{
			node.setType(issueHandler.addTypeError(action, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(""+action)));
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
		LexNameToken var = node.getVar();

		// Get the type of the by expression
		PType byExpType = null;
		if (byExp != null) {
			byExpType = byExp.apply(parentChecker, question);
			if (!TCDeclAndDefVisitor.successfulType(byExpType)) {
				node.setType(issueHandler.addTypeError(byExp,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
						.customizeMessage(byExp + "")));
				return node.getType();
			}
		}

		// Get the type of the to expression
		PType toExpType = toExp.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(toExpType)) {
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
		if (!TCDeclAndDefVisitor.successfulType(actionType)) {
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

		if (!TCDeclAndDefVisitor.successfulType(timedExpType)) {
			node.setType(issueHandler.addTypeError(timedExp,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
					.customizeMessage("" + timedExp)));
			return node.getType();
		}

		if (!typeComparator.isSubType(timedExpType, new AIntNumericBasicType())) {
			node.setType(issueHandler.addTypeError(timedExpType,
					TypeErrorMessages.TIME_UNIT_EXPRESSION_MUST_BE_NAT
					.customizeMessage(timedExp + "")));
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
		LinkedList<PPattern> ptrn = node.getPattern();
		PAction res = node.getResult();

		if (node.getDefs().size() == 0) {
			PPattern pattern = ptrn.get(0);
			node.setDefs(new LinkedList<PDefinition>());
			PPatternAssistantTC
			.typeResolve(
					pattern,
					(QuestionAnswerAdaptor<org.overture.typechecker.TypeCheckInfo, PType>) parentChecker,
					question);

			if (ptrn.get(0) instanceof AExpressionPattern) {
				// Only expression patterns need type checking...
				AExpressionPattern ep = (AExpressionPattern) pattern;
				ep.getExp().apply(parentChecker, question);
			}

			PPatternAssistantTC
			.typeResolve(
					pattern,
					(QuestionAnswerAdaptor<org.overture.typechecker.TypeCheckInfo, PType>) parentChecker,
					question);

			ACasesStatementAction stm = (ACasesStatementAction) node.parent();
			node.getDefs().addAll(
					PPatternAssistantTC.getDefinitions(pattern, stm.getExp()
							.getType(), NameScope.LOCAL));
		}

		PDefinitionListAssistantTC
		.typeCheck(
				node.getDefs(),
				(QuestionAnswerAdaptor<org.overture.typechecker.TypeCheckInfo, PType>) parentChecker,
				question);

		question.contextSet(CmlTypeCheckInfo.class, getTypeCheckInfo(question));
		Environment local = new FlatCheckedEnvironment(node.getDefs(),
				question.env, question.scope);
		PType r = node.getResult().apply(
				parentChecker,
				new org.overture.typechecker.TypeCheckInfo(local,
						question.scope));
		question.contextRem(CmlTypeCheckInfo.class);
		local.unusedCheck();

		return r;
	}

	@Override
	public PType caseACasesStatementAction(ACasesStatementAction node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {

		LinkedList<ACaseAlternativeAction> cases = node.getCases();
		for (ACaseAlternativeAction altAction : cases) {
			PType caseType = altAction.apply(parentChecker, question);
			if (!TCDeclAndDefVisitor.successfulType(caseType)) {
				node.setType(issueHandler.addTypeError(node,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
						.customizeMessage(altAction + "")));
				return node.getType();
			}
		}
		PExp exp = node.getExp();

		PType expType = exp.apply(parentChecker, question);
		if (TCDeclAndDefVisitor.successfulType(expType)) {
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
		LinkedList<LexIdentifierToken> ids = node.getIdentifiers();
		LinkedList<PAction> acts = node.getActions();

		// get the enclosing definition if any
		PDefinition enclosingDef = question.env.getEnclosingDefinition();

		// get the CML context we are in
		CmlTypeCheckInfo info = getTypeCheckInfo(question);

		//
		CmlTypeCheckInfo newQuestion = (CmlTypeCheckInfo) info.newScope(info,
				enclosingDef);

		// add IDs to the environment
		for (LexIdentifierToken id : ids) {
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
			if (!TCDeclAndDefVisitor.successfulType(actType)) {
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

		for (PSingleDeclaration decl : decls) {
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
		PVarsetExpression leftChanSet = node.getLeftChansetExpression();
		PVarsetExpression leftNameSet = node.getLeftNamesetExpression();

		PAction rightAction = node.getRightAction();
		PVarsetExpression rightChanSet = node.getRightChansetExpression();
		PVarsetExpression rightNameSet = node.getLeftNamesetExpression();

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
		AExplicitCmlOperationDefinition operation = node
				.getAncestor(AExplicitCmlOperationDefinition.class);
		if (operation == null)
			throw new AnalysisException(
					"Return Statement Action does not have explicit operation as parent.");

		PExp exp = node.getExp();
		PType type = exp.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(type))
		{
			node.setType(issueHandler.addTypeError(node, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(""+exp)));
			return node.getType();
		}
		//check return type of parent function and the expression
		AOperationType operType = operation.getType();
		if (!typeComparator.isSubType(type, operType.getResult()))
		{
			node.setType(issueHandler.addTypeError(node, TypeErrorMessages.INCOMPATIBLE_TYPE.customizeMessage(""+operType.getResult(),""+type)));
			return node.getType();
		}

		node.setType(new AStatementType(node.getLocation(),true));
		return node.getType();
	}




	@Override
	public PType caseAGuardedAction(AGuardedAction node, TypeCheckInfo question)
			throws AnalysisException {

		PExp exp = node.getExpression();
		PAction action = node.getAction();

		PType expType = exp.apply(parentChecker,question);
		if (!TCDeclAndDefVisitor.successfulType(expType))
		{
			node.setType(issueHandler.addTypeError(exp, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(exp+"")));
			return node.getType();
		}

		if (!typeComparator.isSubType(expType, AstFactory.newABooleanBasicType(node.getLocation())))
		{
			node.setType(issueHandler.addTypeError(exp, TypeErrorMessages.INCOMPATIBLE_TYPE.customizeMessage("bool", ""+expType)));
			return node.getType();
		}

		PType actionType = action.apply(parentChecker,question);
		if (!TCDeclAndDefVisitor.successfulType(actionType))
		{
			node.setType(issueHandler.addTypeError(action, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(""+action)));
			return node.getType();
		}

		if (!(actionType instanceof AActionType))
		{
			node.setType(issueHandler.addTypeError(action, TypeErrorMessages.EXPECTED_AN_ACTION_OR_OPERATION.customizeMessage(""+action)));
			return node.getType();
		}

		node.setType(new AActionType(node.getLocation(), true));
		return node.getType();

	}
	@Override
	public PType caseABlockStatementAction(ABlockStatementAction node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {

		// get CML environment
		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);
		if (cmlEnv == null)
		{
			node.setType(issueHandler.addTypeError(node, TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(node+"")));
			return node.getType();
		}

		// Create a new environment for this block
		CmlTypeCheckInfo blockEnv = cmlEnv.newScope();

		// extend the environment with optional declarations
		ADeclareStatementAction declared = node.getDeclareStatement();
		if (declared != null) {
			LinkedList<PDefinition> freshDefinitions = declared.getAssignmentDefs();
			for(PDefinition def : freshDefinitions)
				blockEnv.addVariable(def.getName(), def);
		}

		// check the action.
		PAction action = node.getAction();
		PType actionType = action.apply(parentChecker, blockEnv);
		if (!TCDeclAndDefVisitor.successfulType(actionType))
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
		if (type != null)
			return type;

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
					TypeErrorMessages.EXPECTED_AN_ACTION_OR_OPERATION.customizeMessage(node
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

		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);
		if (cmlEnv == null)
		{
			node.setType(issueHandler.addTypeError(node, TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(node+"")));
			return node.getType();
		}

		PDefinition channel = cmlEnv.lookupChannel(node.getIdentifier());
		AChannelNameDefinition channelNameDefinition = null;

		// There should be a channel defined with this name
		if (null == channel) {
			node.setType( issueHandler.addTypeError(node,
					TypeErrorMessages.NAMED_TYPE_UNDEFINED
					.customizeMessage(node.getIdentifier().name)));
			return node.getType();
		}


		if (!(channel instanceof AChannelNameDefinition))
		{
			node.setType(issueHandler.addTypeError(channel, TypeErrorMessages.EXPECTED_A_CHANNEL.customizeMessage(channel+"")));
			return node.getType();
		}


		channelNameDefinition = (AChannelNameDefinition)channel;

		CmlTypeCheckInfo commEnv = cmlEnv.newScope();
		int paramIndex = 0;
		LinkedList<PCommunicationParameter> commParams = node.getCommunicationParameters();
		for(PCommunicationParameter commParam : commParams)
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
				
				AReadCommunicationParameter readParam = (AReadCommunicationParameter)commParam;
				commPattern = readParam.getPattern();

				PType commPatternType = commPattern.apply(parentChecker,question);
				if (!TCDeclAndDefVisitor.successfulType(commPatternType))
				{
					node.setType(issueHandler.addTypeError(commPattern,TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(""+commPattern)));
					return node.getType();
				}

				if (typeDecl.getType() == null)
					typeDecl.setType(new AChannelType(commParam.getLocation(), true));

				if (commPattern instanceof AIdentifierPattern)
				{
					AIdentifierPattern id = (AIdentifierPattern)commPattern;
					AChannelType type = (AChannelType)typeDecl.getType();
					PType theType = null;
					if (type.getType() instanceof AProductType)
					{
						AProductType pType = (AProductType)type.getType();
						theType = pType.getTypes().get(paramIndex);
						paramIndex++;
					}
					ALocalDefinition chanDef = AstFactory.newALocalDefinition(commPattern.getLocation(), id.getName(), NameScope.LOCAL, theType);
					cmlEnv.addVariable(chanDef.getName(),chanDef);
					
				}
				
				if (commPattern instanceof ATuplePattern)
				{
					PType type = typeDecl.getType();
					if (!(type instanceof AChannelType))
					{
						node.setType(issueHandler.addTypeError(commPattern, TypeErrorMessages.INCOMPATIBLE_TYPE.customizeMessage("Channel type", ""+type)));
						return node.getType();
					}

					AChannelType chanType = (AChannelType)type;
					if (!(chanType.getType() instanceof AProductType))
					{
						node.setType(issueHandler.addTypeError(commPattern, TypeErrorMessages.INCOMPATIBLE_TYPE.customizeMessage(typeDecl.getType()+"", chanType.getType()+"")));
						return node.getType();
					}


					AProductType r = (AProductType)chanType.getType();

					if (commPatternType.getDefinitions().size() != r.getTypes().size())
					{
						node.setType(issueHandler.addTypeError(commPattern, TypeErrorMessages.PATTERN_MISMATCH.customizeMessage(r+"", commPattern+"")));
						return node.getType();
					}

					List<PDefinition> defs = commPatternType.getDefinitions();
					for(int i = 0; i < r.getTypes().size(); i++)
					{
						PDefinition def = defs.get(i); 
						PType componentType = r.getTypes().get(i);
						def.setType(componentType);
						commEnv.addVariable(def.getName(), def);
					}
				}


			}

			if (commParam instanceof AWriteCommunicationParameter)
			{
				AWriteCommunicationParameter writeParam = (AWriteCommunicationParameter)commParam;
				PExp writeExp = writeParam.getExpression();
				PType writeExpType = writeExp.apply(parentChecker, question);
				if(!TCDeclAndDefVisitor.successfulType(writeExpType))
				{
					node.setType(issueHandler.addTypeError(writeExp, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(writeExp+"")));
					return node.getType();
				}
				
				PType thisType = null;
				PType type = typeDecl.getType();
				
				if (!(type instanceof AChannelType))
				{
					node.setType(issueHandler.addTypeError(node, TypeErrorMessages.EXPECTED_A_CHANNEL.customizeMessage(node+"")));
					return node.getType();
				}
				
				AChannelType cType = (AChannelType)type;
				
				if (cType.getType() instanceof AProductType)
				{
					AProductType pType = (AProductType)cType.getType();
					thisType = pType.getTypes().get(paramIndex);
					paramIndex++;
				}
				else
					thisType = cType.getType();
				
				if (!typeComparator.isSubType(writeExpType, thisType))
				{
					node.setType(issueHandler.addTypeError(commParam, TypeErrorMessages.INCOMPATIBLE_TYPE.customizeMessage(""+thisType,""+writeExpType)));
					return node.getType();
				}
				
			}
		}



		PType commType = node.getAction().apply(this, commEnv);
		if (!TCDeclAndDefVisitor.successfulType(commType))
		{
			node.setType(issueHandler.addTypeError(node.getAction(),TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(node.getAction()+"")));
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
		if (!TCDeclAndDefVisitor.successfulType(leftType))
		{
			node.setType(issueHandler.addTypeError(node.getLeft(),TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(node.getLeft() + "")));
			return node.getType();
		}
		PType rightType = node.getRight().apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(rightType))
		{
			node.setType(issueHandler.addTypeError(node.getRight(),TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(node.getRight() + "")));
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
		PVarsetExpression chanSet = node.getChansetExpression();

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

	@Override
	public PType caseAVresParametrisation(AVresParametrisation node,
			TypeCheckInfo question) throws AnalysisException {

		ATypeSingleDeclaration decl = node.getDeclaration();
		PType declType = decl.apply(parentChecker,question);
		if (!TCDeclAndDefVisitor.successfulType(declType))
		{
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
		if (cmlEnv == null)
		{
			node.setType(issueHandler.addTypeError(node, TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(node+"")));
			return node.getType();
		}

		CmlTypeCheckInfo newCmlEnv = cmlEnv.newScope(); 

		LinkedList<PParametrisation> parameterNames = node.getAction().getParametrisations();
		int i = 0;
		for(PExp exp : args)
		{
			PType expType = exp.apply(parentChecker,question);
			if (!TCDeclAndDefVisitor.successfulType(expType))
			{
				node.setType(issueHandler.addTypeError(exp, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(exp+"")));
				return node.getType();
			}

			if (i > parameterNames.size())
				continue;
			PParametrisation pa = parameterNames.get(i++);
			ATypeSingleDeclaration decl = pa.getDeclaration();
			PType declType = decl.apply(parentChecker,question);
			if (!TCDeclAndDefVisitor.successfulType(declType))
			{
				node.setType(issueHandler.addTypeError(decl, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(""+decl)));
				return node.getType();
			}

			LinkedList<LexIdentifierToken> ids = decl.getIdentifiers();
			for(LexIdentifierToken id : ids)
			{
				LexNameToken idName = new LexNameToken("", id);
				ALocalDefinition localDef = AstFactory.newALocalDefinition( id.getLocation(), idName, NameScope.LOCAL, declType);
				newCmlEnv.addVariable(id, localDef);
			}
		}
		int a;
		PType actionType = action.apply(parentChecker,newCmlEnv);
		if (!TCDeclAndDefVisitor.successfulType(actionType))
		{
			node.setType(issueHandler.addTypeError(action, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(action+"")));
			return node.getType();
		}

		node.setType(new AActionType(node.getLocation(),true));
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

		LexNameToken name = node.getName();
		PDefinition callee = question.env.findName(name, NameScope.GLOBAL);
		
		LinkedList<PExp> args = node.getArgs();
		List<PType> argTypes = new LinkedList<PType>();
		for(PExp e : args)
		{
			PType eType = e.apply(parentChecker,question);
			if (!TCDeclAndDefVisitor.successfulType(eType))
			{
				node.setType(issueHandler.addTypeError(node, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(""+node)));
				return node.getType();
			}
			argTypes.add(eType);
			
		}

		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);
		if (cmlEnv != null)
		{
			if (callee == null) callee = cmlEnv.lookup(name, PDefinition.class);
			if (callee == null) { name.setTypeQualifier(argTypes); callee=cmlEnv.lookup(name, PDefinition.class); }
		}
		
		
		
		if(callee == null) callee = cmlEnv.lookup(name, PDefinition.class);
		if (callee == null)
			return issueHandler.addTypeError(
					node,
					TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage(name
							+ ""));

		if (!(callee instanceof AActionDefinition || callee instanceof PAction || callee instanceof SCmlOperationDefinition || callee instanceof AExplicitFunctionDefinition || callee instanceof AImplicitFunctionDefinition))
		{
			return issueHandler.addTypeError(callee,TypeErrorMessages.EXPECTED_AN_ACTION_OR_OPERATION.customizeMessage(""+callee));
		}

		if (callee.getType() == null)
		{
			PType calleeType = resolveUntypedActionReference(node, callee, question);
			if (!TCDeclAndDefVisitor.successfulType(calleeType))
			{
				node.setType(calleeType);
				return node.getType();
			}

		}

		// Action can only call actions.
		if (callee.getType() == null)
		{
			node.setType(issueHandler.addTypeError(callee, TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage(callee+"")));
			return node.getType();
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
		if (!TCDeclAndDefVisitor.successfulType(leftActionType))
			return issueHandler.addTypeError(leftActionType,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
					.customizeMessage(leftAction + ""));

		if (leftNameSet != null) {
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

		if (rightNameSet != null) {
			PType rightNameSetType = rightNameSet
					.apply(parentChecker, question);
			if (!TCDeclAndDefVisitor.successfulType(rightNameSetType))
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

		LinkedList<ANonDeterministicAltStatementAction> alternatives = node.getAlternatives();
		for(ANonDeterministicAltStatementAction act : alternatives){
			PType actType = act.apply(parentChecker,question);
			if (!TCDeclAndDefVisitor.successfulType(actType))
			{
				node.setType(issueHandler.addTypeError(act,TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(act+"")));
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
		PType guardType = guard.apply(parentChecker,question);
		if (!TCDeclAndDefVisitor.successfulType(guardType))
		{
			node.setType(issueHandler.addTypeError(guard, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(guard+"")));
			return node.getType();
		}

		PAction action = node.getAction();
		PType actionType = action.apply(parentChecker,question);
		if (!TCDeclAndDefVisitor.successfulType(actionType))
		{
			node.setType(issueHandler.addTypeError(action, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(""+action)));
			return node.getType();
		}


		node.setType(new AActionType());
		return node.getType();
	}






	@Override
	public PType caseAParametrisedAction(AParametrisedAction node,
			TypeCheckInfo question) throws AnalysisException {


		PAction action = node.getAction();

		// Params are already added to the environment above as we have the defining expressions there !
		// at least in the case of caseAParametrisedInstantiatedAction. See how it is done there if your are in trouble
		// with this guy.
		LinkedList<PParametrisation> params = node.getParametrisations();

		PType actionType=  action.apply(parentChecker,question);
		if (!TCDeclAndDefVisitor.successfulType(actionType))
		{
			node.setType(issueHandler.addTypeError(node,TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(action+"")));
			return node.getType();
		}

		node.setType(new AActionType());
		return node.getType();
	}




}
