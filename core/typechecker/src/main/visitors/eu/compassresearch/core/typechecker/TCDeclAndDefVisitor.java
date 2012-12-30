package eu.compassresearch.core.typechecker;


import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.ws.handler.MessageContext.Scope;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AClassClassDefinition;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AUntypedDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.expressions.ANotYetSpecifiedExp;
import org.overture.ast.expressions.ASubclassResponsibilityExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameList;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.typechecker.Pass;
import org.overture.ast.types.AAccessSpecifierAccessSpecifier;
import org.overture.ast.types.AClassType;
import org.overture.ast.types.AFunctionType;
import org.overture.ast.types.AOperationType;
import org.overture.ast.types.AProductType;
import org.overture.ast.types.PType;
import org.overture.parser.messages.VDMError;
import org.overture.typechecker.Environment;
import org.overture.typechecker.FlatEnvironment;
import org.overture.typechecker.PrivateClassEnvironment;
import org.overture.typechecker.PublicClassEnvironment;
import org.overture.typechecker.TypeCheckException;
import org.overture.typechecker.TypeCheckInfo;
import org.overture.typechecker.TypeChecker;
import org.overture.typechecker.assistant.definition.ATypeDefinitionAssistantTC;
import org.overture.typechecker.assistant.definition.PDefinitionAssistantTC;
import org.overture.typechecker.assistant.definition.SClassDefinitionAssistantTC;
import org.overture.typechecker.assistant.pattern.PPatternListAssistantTC;
import org.overture.typechecker.util.TypeCheckerUtil;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.AExpressionSingleDeclaration;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AActionsDefinition;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChannelsDefinition;
import eu.compassresearch.ast.definitions.AClassDefinition;
import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AFunctionsDefinition;
import eu.compassresearch.ast.definitions.AInitialDefinition;
import eu.compassresearch.ast.definitions.AOperationsDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.ATypesDefinition;
import eu.compassresearch.ast.definitions.AValuesDefinition;
import eu.compassresearch.ast.definitions.SCmlOperationDefinition;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ast.types.AActionParagraphType;
import eu.compassresearch.ast.types.AActionType;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.ast.types.AErrorType;
import eu.compassresearch.ast.types.AInitialParagraphType;
import eu.compassresearch.ast.types.AOperationParagraphType;
import eu.compassresearch.ast.types.AProcessParagraphType;
import eu.compassresearch.ast.types.AStateParagraphType;
import eu.compassresearch.ast.types.AStatementType;
import eu.compassresearch.ast.types.ATypeParagraphType;
import eu.compassresearch.ast.types.AValueParagraphType;
import eu.compassresearch.core.typechecker.api.TypeCheckQuestion;
import eu.compassresearch.core.typechecker.api.TypeComparator;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

@SuppressWarnings({ "unchecked", "deprecation", "serial" })
class TCDeclAndDefVisitor extends
QuestionAnswerCMLAdaptor<org.overture.typechecker.TypeCheckInfo, PType> {




	@Override
	public PType caseAActionsDefinition(AActionsDefinition node,
			TypeCheckInfo question) throws AnalysisException {

		LinkedList<AActionDefinition> actions = node.getActions();
		for(AActionDefinition action : actions)
		{
			PType actionType = action.apply(parentChecker,question);
			if (!successfulType(actionType))
			{
				node.setType(issueHandler.addTypeError(action, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(action+"")));
				return node.getType();
			}
			
			if (!(actionType instanceof AActionType))
			{
				node.setType(issueHandler.addTypeError(action, TypeErrorMessages.EXPECTED_AN_ACTION.customizeMessage(action+"")));
				return node.getType();
			}
		}
		
		
		node.setType(new AActionParagraphType());
		return node.getType();
	}

	@Override
	public PType caseAStateDefinition(AStateDefinition node,
			TypeCheckInfo question) throws AnalysisException {

		CmlTypeCheckInfo cmlenv = CmlTCUtil.getCmlEnv(question);

		LinkedList<PDefinition> defs = node.getStateDefs();
		for(PDefinition def : defs)
		{
			question.scope = NameScope.LOCAL;
			PType defType = def.apply(parentChecker, question);
			if (!TCDeclAndDefVisitor.successfulType(defType))
			{
				node.setType(issueHandler.addTypeError(def, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(""+def)));
				return node.getType();
			}
			cmlenv.addVariable(def.getName(), def);
		}

		if (node.getInvdef() != null) {
			node.getInvdef().apply(parentChecker, question);
		}

		if (node.getInitdef() != null) {
			node.getInitdef().apply(parentChecker, question);
		}

		node.setType(new AStateParagraphType(node.getLocation(), true));
		return node.getType();
	}

	@Override
	public PType caseAOperationsDefinition(AOperationsDefinition node,
			TypeCheckInfo question) throws AnalysisException {

		LinkedList<SCmlOperationDefinition> operations = node.getOperations();
		for(SCmlOperationDefinition odef : operations)
		{
			PType operationType  = odef.apply(parentChecker, question);
			if (!TCDeclAndDefVisitor.successfulType(operationType))
			{
				node.setType(issueHandler.addTypeError(odef, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(odef+"")));
				return node.getType();
			}

			if (!(operationType instanceof AOperationType))
			{
				node.setType(issueHandler.addTypeError(odef, TypeErrorMessages.EXPECTED_OPERATION_DEFINITION.customizeMessage(odef.getName()+"")));
				return node.getType();
			}
		}
		node.setType(new AOperationParagraphType());
		return node.getType();
	}

	@Override
	public PType caseAInitialDefinition(
			AInitialDefinition node, org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {
		// TODO Auto-generated method stub

		PDefinition oper = node.getOperationDefinition();
		if (oper != null)
		{
			oper.apply(parentChecker,question);
		}

		return new AInitialParagraphType();
	}

	@Override
	public PType caseAExpressionSingleDeclaration(
			AExpressionSingleDeclaration node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {

		PExp expression = node.getExpression();

		PType expressionType = expression.apply(parentChecker, question);
		if (!successfulType(expressionType))
			return issueHandler.addTypeError(expressionType,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
					.customizeMessage(expression + ""));

		LinkedList<LexIdentifierToken> identifiers = node.getIdentifiers();
		for (LexIdentifierToken id : identifiers) {
			LexNameToken name = null;
			if (id instanceof LexNameToken)
				name = (LexNameToken) id;
			else
				name = new LexNameToken(question.env.getEnclosingDefinition()
						.getName().module, id.getName(), id.getLocation());

			ALocalDefinition localDef = AstFactory
					.newALocalDefinition(id.getLocation(), name,
							node.getNameScope(), expressionType);
			((FlatEnvironment) question.env).add(localDef);
		}

		return expressionType;
	}

	public PType typeCheckOvertureClass(AClassClassDefinition node,
			CmlTypeCheckInfo question) throws AnalysisException {

		// Create class environment
		PrivateClassEnvironment self = new PrivateClassEnvironment(node, null);

		List<SClassDefinition> classes = new LinkedList<SClassDefinition>();
		classes.add(node);
		Environment allClasses = new PublicClassEnvironment(classes);

		for (SClassDefinition c : classes) {
			if (!c.getTypeChecked()) {
				SClassDefinitionAssistantTC.implicitDefinitions(c, allClasses);
			}
		}

		for (SClassDefinition c : classes) {
			if (!c.getTypeChecked()) {
				try {
					Environment selfInner = new PrivateClassEnvironment(c,
							allClasses);
					SClassDefinitionAssistantTC.typeResolve(c, null,
							new org.overture.typechecker.TypeCheckInfo(
									selfInner));
				} catch (TypeCheckException te) {
					issueHandler.addTypeError(te.location, te.getMessage());
				} catch (AnalysisException te) {
					issueHandler.addTypeError(c, te.getMessage());
				}
			}
		}



		// Errors will be reported statically by the sub-visitors and the
		// assistants
		// on the TypeChecker.errors list.
		TypeChecker.clearErrors();
		question.contextSet(CmlTypeCheckInfo.class, (CmlTypeCheckInfo)question);
		OvertureRootCMLAdapter tc = new OvertureRootCMLAdapter(parentChecker,
				issueHandler);
		typeCheckPass(node, Pass.TYPES, self, tc,question,issueHandler);
		if (TypeChecker.getErrorCount() == 0)
			typeCheckPass(node, Pass.VALUES, self, tc,question,issueHandler);
		if (TypeChecker.getErrorCount() == 0)
			typeCheckPass(node, Pass.DEFS, self, tc,question, issueHandler);


		// add overture errors to cml errors
		List<VDMError> errs = TypeChecker.getErrors();
		for (VDMError e : errs) {
			issueHandler.addTypeError(e.location, e.toProblemString());

		}

		if (TypeChecker.getErrorCount() != 0)
			return new AErrorType();

		question.contextRem(CmlTypeCheckInfo.class);
		return new AClassType(node.getLocation(), true, node.getName());

	}

	@Override
	public PType caseAAssignmentDefinition(AAssignmentDefinition node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {

		PExp expression = node.getExpression();
		PType declaredType = node.getType();

		// Check the declared type
		PType actualDeclaredType = declaredType.apply(parentChecker, question);

		if (!successfulType(actualDeclaredType))
			return new AErrorType();

		if (expression != null) {

			// Check the expression
			PType expType = expression.apply(parentChecker, question);
			if (!successfulType(expType))
				return new AErrorType();

			// Check assignment compatibility
			if (!typeComparator.isSubType(expType, actualDeclaredType)) {
				return issueHandler.addTypeError(node,
						TypeErrorMessages.EXPECTED_SUBTYPE_RELATION
						.customizeMessage("" + expType.toString(), ""
								+ actualDeclaredType));
			}
			node.setExpType(expType);
		}
		else
		{
			// This was a bad idea
			// node.setExpression(new AUndefinedExp(actualDeclaredType, node.getLocation()));
			// node.setExpType(node.getExpression().getType());
		}
		// Add the assignment to the environment
		node.setType(actualDeclaredType);

		return actualDeclaredType;
	}

	// Errors and other things are recorded on this guy
	private VanillaCmlTypeChecker parentChecker;
	private TypeComparator typeComparator;
	private final TypeIssueHandler issueHandler;

	public TCDeclAndDefVisitor(VanillaCmlTypeChecker parent,
			TypeComparator typeComparator, TypeIssueHandler issueHandler) {
		this.parentChecker = parent;
		this.issueHandler = issueHandler;
		this.typeComparator = typeComparator;
	}

	// -------------------------------------------------------
	// Cases
	// -------------------------------------------------------


	@Override
	public PType caseAActionDefinition(AActionDefinition node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {

		// Add this to the current scope
		((TypeCheckQuestion) question).addVariable(node.getName()
				.getIdentifier(), node);
		
		
		// For every parametrisation add the decl to the action's environment.
		LinkedList<PParametrisation> decls = node.getDeclarations();
		PAction action = node.getAction();
		
		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);
		CmlTypeCheckInfo actionScope = cmlEnv.newScope();
		for(PParametrisation decl : decls)
		{
			PType declType = decl.apply(parentChecker,question);
			if (!TCDeclAndDefVisitor.successfulType(declType))
			{
				node.setType(issueHandler.addTypeError(decl, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(decl+"")));
				return node.getType();
			}
			for(PDefinition def : declType.getDefinitions())
				actionScope.addVariable(def.getName(), def);
		}

		// type check the action
		PType actionType = action.apply(parentChecker,question);
		if (!TCDeclAndDefVisitor.successfulType(actionType))
		{
			node.setType(issueHandler.addTypeError(action, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(action+"")));
			return node.getType();
		}
		
		node.setType(new AActionType(node.getLocation(), true));
		return node.getType();
	}

	@Override
	public PType caseATypesDefinition(ATypesDefinition node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {
		CmlTypeCheckInfo newQ = (CmlTypeCheckInfo) question;
		LinkedList<ATypeDefinition> defs = node.getTypes();
		for (ATypeDefinition d : defs) {
			PType type = d.apply(parentChecker, question);
			if (!TCDeclAndDefVisitor.successfulType(type)) {
				issueHandler.addTypeError(d,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
						.customizeMessage(d.getName() + ""));
				d.setType(new AErrorType());
				return d.getType();
			}
			newQ.addType(d.getName(), d);
			d.setType(type);
		}
		node.setType(new ATypeParagraphType());
		return node.getType();
	}

	@Override
	public PType caseAValuesDefinition(AValuesDefinition node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {

		CmlTypeCheckInfo newQ = (CmlTypeCheckInfo) question;
		LinkedList<PDefinition> _list = node.getValueDefinitions();
		List<PDefinition> list = new LinkedList<PDefinition>();
		list.addAll(_list);
		for (PDefinition def : list) {
			PType defType = def.apply(parentChecker, newQ);
			def.setType(defType);
		}

		node.setType(new AValueParagraphType());
		return node.getType();
	}

	@Override
	public PType caseAValueDefinition(AValueDefinition node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {

		node.setName(new LexNameToken("", node.getName().getName(), node
				.getLocation()));

		// Acquire declared type and expression type
		PExp exp = node.getExpression();
		PType declaredType = node.getType();



		PType expressionType = exp.apply(parentChecker, question);
		if (expressionType instanceof AErrorType)
			return expressionType;

		node.setExpType(expressionType);
		if (declaredType == null)
			declaredType=node.getExpType();

		// Handle the pattern and add the resulting definition to the environment
		CmlTypeCheckInfo tci = (CmlTypeCheckInfo) question;
		PPattern pattern = node.getPattern();
		PType patternType = pattern.apply(parentChecker,question);
		if (patternType == null)
		{
			node.setType(issueHandler.addTypeError(pattern, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(""+pattern)));
			return node.getType();
		}
		for(PDefinition def : patternType.getDefinitions())
		{
			def.setType(declaredType);
			tci.addVariable(def.getName(), def);
		}

		// Check type consistency
		if (!typeComparator.isSubType(expressionType, declaredType))
			issueHandler.addTypeError(node,
					TypeErrorMessages.EXPECTED_SUBTYPE_RELATION
					.customizeMessage(expressionType.toString(),
							declaredType.toString()));

		// Get the enclosing definition that this value definition will be added
		// to

		PDefinition enclosingDef = question.env.getEnclosingDefinition();

		// if there is a parent definition lets find things
		List<PDefinition> newDefs = new LinkedList<PDefinition>();
		if (enclosingDef != null)
			newDefs = getHandler(node.getPattern()).getDefinitions(
					node.getPattern(), question.env.getEnclosingDefinition());

		node.setDefs(newDefs);

		// No matter the declared type is the type of the definition
		node.setType(declaredType);
		return node.getType();
	}

	private static interface PatternHandlerDelegate<K extends PPattern> {
		public List<PDefinition> getDefinitions(K pattern, PDefinition parentDef);
	}

	public static Map<Class<?>, PatternHandlerDelegate<?>> ptrnDelegates;

	static {
		ptrnDelegates = new HashMap<Class<?>, TCDeclAndDefVisitor.PatternHandlerDelegate<?>>();
		ptrnDelegates.put(AIdentifierPattern.class,
				new PatternHandlerDelegate<AIdentifierPattern>() {

			@Override
			public List<PDefinition> getDefinitions(
					AIdentifierPattern pattern, PDefinition parentDef) {
				List<PDefinition> result = new LinkedList<PDefinition>();
				result.add(parentDef);
				return result;
			}
		});
	}

	private static <K extends PPattern> PatternHandlerDelegate<K> getHandler(
			K instance) {
		if (instance == null)
			return null;
		PatternHandlerDelegate<K> k = (PatternHandlerDelegate<K>) ptrnDelegates
				.get(instance.getClass());
		return k;
	}

	private interface OvertureToCmlHandler {
		public List<PDefinition> handle(PDefinition def);
	}

	/*
	 * The Identity transformation
	 */
	private static class OvertureToCmlIDhandle implements OvertureToCmlHandler {
		public List<PDefinition> handle(PDefinition def) {
			return Arrays.asList(new PDefinition[] { def });
		}
	}

	/*
	 * AValueParagraphDefinition transformation to List<AValueDefinition>.
	 * 
	 * CML Values are wrapped inside a AValuesParagraphDefinition. This function
	 * flattens that CML structure into a list of plain AValueDefinitions.
	 */
	private static class OvertureToCmlValuesParahraphHandler implements
	OvertureToCmlHandler {
		public List<PDefinition> handle(PDefinition def) {
			// is it not a Value Paragraph Definition behave like Identity
			List<PDefinition> res = new LinkedList<PDefinition>();
			if (!(def instanceof AValuesDefinition)) {
				res.add(def);
				return res;
			}

			// flatten the AvalueDefinition into a list
			AValuesDefinition vpDef = (AValuesDefinition) def;
			for (PDefinition innerDef : vpDef.getValueDefinitions()) {
				innerDef.setPass(Pass.VALUES);
				res.add(innerDef);
			}
			vpDef.setName(new LexNameToken("", new LexIdentifierToken("", false, vpDef.getLocation())));
			return res;
		}
	}

	private static class OvertureToCmlTypeParagraphHandler implements OvertureToCmlHandler
	{

		@Override
		public List<PDefinition> handle(PDefinition def) {
			List<PDefinition> res = new LinkedList<PDefinition>();
			// is it not a Value Paragraph Definition behave like Identity
			if (!(def instanceof ATypesDefinition)) {
				res.add(def);
				return res;
			}

			// else flatten ATypeParagraphDefinition into a list
			ATypesDefinition tpdef = (ATypesDefinition)def;
			for(PDefinition d : tpdef.getTypes())
			{
				d.setPass(Pass.TYPES);
				res.add(d);
			}
			return res;
		}

	}

	/**
	 * Apply definition handlers to definition.
	 * 
	 * Takes a paragraph definition and flattens it into a list of PDefinitions
	 * and does some processing of the individual definitions to adapt them for 
	 * Overture.
	 *  
	 * @param def
	 * @return
	 */
	static List<PDefinition> handleDefinitionsForOverture(PDefinition def)
	{
		OvertureToCmlHandler handler = overtureClassBits.get(def.getClass());
		if (handler!=null) return handler.handle(def);
		return null;
	}

	private static class OvertureToCmlFunctionHandler implements OvertureToCmlHandler
	{

		/**
		 * 
		 */
		@Override
		public List<PDefinition> handle(PDefinition def) {

			List<PDefinition> result = new LinkedList<PDefinition>();

			AFunctionType fnType = null;
			do {

				if (def instanceof AExplicitFunctionDefinition) {
					fnType = ((AExplicitFunctionDefinition)def).getType();
					break;
				}
				if (def instanceof AImplicitFunctionDefinition) {
					fnType = ((AImplicitFunctionDefinition)def).getType();
					break;
				}
				result.add(def);
				return result;

			} while(false);

			flattenProductParamterType(fnType);
			result.add(def);

			return result;

		}

		/*
		 * VDMPP parser adds types one by one for the outer product type.
		 * E.g. f: int * int -> int has arg-types [int,int] rather than 
		 * (int * int).
		 * 
		 * @param pdef
		 */
		private void flattenProductParamterType(AFunctionType fnType) {

			if (fnType.getParameters().size() == 1)
			{
				PType firstType = fnType.getParameters().get(0);
				LinkedList<PType> res = new LinkedList<PType>();
				if (firstType instanceof AProductType)
				{

					res.addAll( ((AProductType)firstType).getTypes());
					fnType.setParameters(res);
					//							Collections.reverse(res);
				}
			}

		}

	}

	/*
	 * This Map defines which parts of a CML Class Paragraph should go into an
	 * Overture surrogate class for type checking
	 */
	private static Map<Class<?>, OvertureToCmlHandler> overtureClassBits;
	static {
		OvertureToCmlIDhandle id = new OvertureToCmlIDhandle();
		overtureClassBits = new HashMap<Class<?>, OvertureToCmlHandler>();
		overtureClassBits.put(ATypeDefinition.class, id);
		overtureClassBits.put(AExplicitFunctionDefinition.class, new OvertureToCmlFunctionHandler());
		overtureClassBits.put(AImplicitFunctionDefinition.class, id);
		overtureClassBits.put(AValueDefinition.class, id);
		overtureClassBits.put(AUntypedDefinition.class, id);
		overtureClassBits.put(AFunctionsDefinition.class, new AFunctionsDefinitionHandler());
		overtureClassBits.put(AValuesDefinition.class, new AValuesDefinitionHandler());
		overtureClassBits.put(ATypesDefinition.class, new ATypesDefinitionHandler());
	}

	private static class ATypesDefinitionHandler implements OvertureToCmlHandler {

		@Override
		public List<PDefinition> handle(PDefinition def) {
			List<PDefinition> res =new LinkedList<PDefinition>();
			if (def instanceof ATypesDefinition)
			{
				for(PDefinition tdef : ((ATypesDefinition) def).getTypes())
					res.add(tdef);
			}
			
			return res;
		}
		
	}
	
	private static class AValuesDefinitionHandler implements OvertureToCmlHandler
	{

		@Override
		public List<PDefinition> handle(PDefinition def) {

			List<PDefinition> res= new LinkedList<PDefinition>();
			if (def instanceof AValuesDefinition)
			{
				for(PDefinition d : ((AValuesDefinition) def).getValueDefinitions())
					res.add(d);
			}
			else
			{
				res.add(def);
			}
			
			return res;
		}
		
	}
	
	private static class AFunctionsDefinitionHandler implements OvertureToCmlHandler
	{

		@Override
		public List<PDefinition> handle(PDefinition def) {

			List<PDefinition> res = new LinkedList<PDefinition>();
			if (def instanceof AFunctionsDefinition)
			{
				for(PDefinition fdef : ((AFunctionsDefinition) def).getFunctionDefinitions())
					res.add(fdef);
			}
			else
				res.add(def);


			return res;
		}

	}

	/*
	 * Create an Overture class that
	 */
	private static AClassClassDefinition createSurrogateClass(
			AClassDefinition node,
			CmlTypeCheckInfo question) {



		if (question.getGlobalClassDefinitions() == null)
			throw new NullPointerException();

		List<SClassDefinition> superDefs = new LinkedList<SClassDefinition>();
		superDefs.add(question.getGlobalClassDefinitions());
		// TODO RWL Go recursive and create surrogates for each ancestor to node
		// from node.getSuperDefs().


		// So a bit of work needs to be done for definitions
		List<PDefinition> overtureReadyCMLDefinitions = new LinkedList<PDefinition>();

		if (question.env.getEnclosingDefinition() instanceof AClassClassDefinition)
			overtureReadyCMLDefinitions
			.addAll(((AClassClassDefinition) question.env
					.getEnclosingDefinition()).getDefinitions());

		// Lets mangle the CML definitions for Overture to cope with them
		for (PDefinition def : node.getBody()) {
			if (overtureClassBits.containsKey(def.getClass()))
				overtureReadyCMLDefinitions.addAll(overtureClassBits.get(
						def.getClass()).handle(def));
		}

		// Nice :) we can use AstFactory now !
		AClassClassDefinition surrogateOvertureClass = AstFactory
				.newAClassClassDefinition(node.getName(),
						new LexNameList(), // TODO: empty list here, if doing inheritance this needs to be fixed
						overtureReadyCMLDefinitions);

		surrogateOvertureClass.setSuperDefs(superDefs);

		return surrogateOvertureClass;
	}

	PType typeCheckWithOverture(AClassDefinition node,
			AClassClassDefinition surrogate,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {

		// Add all available classes in the current environment as overture classes.
		List<PDefinition> surrogateDefinitions = new LinkedList<PDefinition>();
		if (question instanceof CmlTypeCheckInfo)
		{
			CmlTypeCheckInfo info = (CmlTypeCheckInfo)question;
			for(PDefinition def : info.getDefinitions())
			{
				if (def instanceof AClassDefinition)
				{
					AClassDefinition cdef = (AClassDefinition)def;
					surrogateDefinitions.add(createSurrogateClass(cdef, info));
				}
			}
		}
		else
		{
			node.setType(issueHandler.addTypeError(node, TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(""+node)));
			return node.getType();
		}



		Environment surrogateEnvironment = new FlatEnvironment(surrogateDefinitions, question.env);


		// Create class environment
		PrivateClassEnvironment self = new PrivateClassEnvironment(surrogate,
				surrogateEnvironment );

		// Errors will be reported statically by the sub-visitors and the
		// assistants
		// on the TypeChecker.errors list.
		TypeChecker.clearErrors();
		OvertureRootCMLAdapter tc = new OvertureRootCMLAdapter(parentChecker,
				issueHandler);
		typeCheckPass(surrogate, Pass.TYPES, self, tc,question,issueHandler);
		if (TypeChecker.getErrorCount() == 0)
			typeCheckPass(surrogate, Pass.VALUES, self, tc,question,issueHandler);
		if (TypeChecker.getErrorCount() == 0)
			typeCheckPass(surrogate, Pass.DEFS, self, tc,question,issueHandler);

		// add overture errors to cml errors
		List<VDMError> errs = TypeChecker.getErrors();
		for (VDMError e : errs) {
			issueHandler.addTypeError(e.location, e.toProblemString());

		}

		if (TypeChecker.getErrorCount() != 0)
			return new AErrorType();

		TypeChecker.clearErrors();

		return new AClassType(surrogate.getLocation(), true,
				surrogate.getDefinitions(), surrogate.getName(),
				surrogate.getClassDefinition());

	}

	/*
	 * Given a class-type like AStateDefinitionParagraph.class and a
	 * AClassParagraphDefinition find all definition of that class-type defined
	 * in that class paragraph.
	 */
	private static <T extends PDefinition> List<T> findParticularDefinitionType(
			Class<T> type, AClassDefinition clz) {

		List<T> result = new LinkedList<T>();

		for (PDefinition d : clz.getBody())
			if (type.isInstance(d))
				result.add(type.cast(d));

		return result;
	}

	public static boolean successfulType(PType type) {
		return !(type == null || type instanceof AErrorType);
	}

	// ------------------------------------------------
	// Paragraphs
	// -------------------------------------------------
	/**
	 * <pre>
	 * C:AClassParagraphDefinition Def_1 Type Ok 
	 * . 
	 * . 
	 * . 
	 * Def_n Type Ok
	 * -------------- C |- C Type OK
	 * </pre>
	 * 
	 */
	@Override
	public PType caseAClassDefinition(AClassDefinition node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {

		// Check environment, it must be a CML environment as we are coming from top-level
		if (!(question instanceof CmlTypeCheckInfo))
		{
			node.setType(issueHandler.addTypeError(node,TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(node+"")));
			return node.getType();
		}

		// Prepare an environment for the class body
		CmlTypeCheckInfo info = (CmlTypeCheckInfo)question;
		CmlTypeCheckInfo cmlClassEnv = CmlTCUtil.createCmlClassEnvironment(info, node);
		question.contextSet(CmlTypeCheckInfo.class, cmlClassEnv);
		AClassType result = new AClassType(node.getLocation(), true, node.getBody(),
				node.getName(), node.getClassDefinition());
		node.setType(result);

		// Add the self identifier 
		LexNameToken selfName = new LexNameToken("", new LexIdentifierToken("self", false, node.getLocation()));
		ALocalDefinition selfDef = AstFactory.newALocalDefinition(node.getLocation(), selfName, NameScope.LOCAL, result);
		cmlClassEnv.addVariable(selfDef.getName(), node);

		// Create Surrogate Overture Class
		AClassClassDefinition surrogate = createSurrogateClass(node, cmlClassEnv);
		result.setClassdef(surrogate);

		// Type check surrogate with overture
		PType classType = typeCheckWithOverture(node, surrogate, cmlClassEnv);
		if (classType == null || classType instanceof AErrorType)
			return new AErrorType();

		// Find out what Overture is not doing for us
		List<PDefinition> thoseHandledByCOMPASS = new LinkedList<PDefinition>();
		for (PDefinition def : node.getBody())
			if (!overtureClassBits.containsKey(def.getClass()))
				thoseHandledByCOMPASS.add(def);
// RWL This is handled by the CmlTCUtil.createCmlClassEnvironment
		//			else	
//			{
//				if (def instanceof ATypesDefinition)
//				{
//					List<PDefinition> typeDefs = handleDefinitionsForOverture(def);
//					for(PDefinition typeDef : typeDefs)
//						cmlClassEnv.addType(typeDef.getName(), typeDef);
//				}
//				
//				if (def instanceof AValuesDefinition || def instanceof AFunctionsDefinition)
//				{
//					List<PDefinition> valueDefs = handleDefinitionsForOverture(def);
//					for(PDefinition valDef : valueDefs)
//						cmlClassEnv.addVariable(valDef.getName(), valDef);
//				}
//				
//
//			}

		// Handle the COMPASS definitions
		{
			CmlTypeCheckInfo classQuestion = cmlClassEnv;//.newScope(surrogate);

			// add state
			List<AStateDefinition> states = findParticularDefinitionType(
					AStateDefinition.class, node);
			for (AStateDefinition paragraph : states) {

				PType paragraphType = paragraph.apply(parentChecker, cmlClassEnv);
				if (!successfulType(paragraphType)) {
					return issueHandler
							.addTypeError(
									paragraph,
									TypeErrorMessages.PARAGRAPH_HAS_TYPES_ERRORS
									.customizeMessage(paragraph
											.getName() + ""));

				}

			}
			for (PDefinition def : thoseHandledByCOMPASS) {
				PType type = def.apply(parentChecker, classQuestion);
				if (type == null || type instanceof AErrorType) {
					return issueHandler
							.addTypeError(def,
									TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
									.customizeMessage(def.getName()
											.toString()));
				}
			}
		}

		info.addType(node.getName(), node);
		question.contextRem(CmlTypeCheckInfo.class);
		return result;
	}

	// Modified version of the on in the Overture SClassAssistant Copied
	private static void typeCheckPass(
			SClassDefinition c,
			Pass p,
			Environment base,
			QuestionAnswerCMLAdaptor<org.overture.typechecker.TypeCheckInfo, PType> tc,
			org.overture.typechecker.TypeCheckInfo question, TypeIssueHandler issueHandler)
					throws AnalysisException {

		CmlTypeCheckInfo cmlEnv = null;
		if(question instanceof CmlTypeCheckInfo)
			cmlEnv = (CmlTypeCheckInfo)question;
		else
			cmlEnv=question.contextGet(CmlTypeCheckInfo.class);

		if (cmlEnv == null)
		{
			issueHandler.addTypeWarning(c, "Definitions in \""+c+"\" were missing a CML context. Some checks are omitted.");
			return;
		}

		// TODO RWL Here is the problem. cmlEnv is lost base and c takes over !

		CmlTypeCheckInfo classEnv = (CmlTypeCheckInfo) cmlEnv.newScope(base,c);
		classEnv.scope = NameScope.LOCAL;
		if (!c.getTypeChecked()) {
			try {
				// TODO RWL populate with all the other classes available

				SClassDefinitionAssistantTC.typeResolve(c, null,
						classEnv);
			} catch (TypeCheckException te) {
				TypeChecker.report(3427, te.getMessage(), te.location);
			} catch (AnalysisException te) {
				TypeChecker.report(3431, te.getMessage(), null);// FIXME:
				// internal
				// error
			}
		}

		if (c.getTypeChecked())
			return;

		for (PDefinition d : c.getDefinitions()) {
			if (d.getPass() == p) {
				d.apply(tc, classEnv);
			}
		}

		if (c.getInvariant() != null && c.getInvariant().getPass() == p) {
			c.getInvariant().apply(
					tc,
					cmlEnv);
		}

	}




	/**
	 * A process paragraph is well typed if all its constituents are.
	 * 
	 * <pre>
	 * |- P:AProcessParagraphDefinition 
	 * |- Process_1 Type Ok 
	 * . 
	 * . 
	 * . 
	 * |- Process_N Type Ok 
	 *    --------- 
	 * |- P Type Ok
	 * </pre>
	 * 
	 * 
	 */
	@Override
	public PType caseAProcessDefinition(
			AProcessDefinition node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {
		// make a new scope for the process
		CmlTypeCheckInfo newScope = (CmlTypeCheckInfo) ((TypeCheckQuestion) question)
				.newScope(null);
		
		LinkedList<PSingleDeclaration> state = node.getLocalState();
		for(PSingleDeclaration decl : state)
		{
			PType declType = decl.apply(parentChecker,question);
			if (!TCDeclAndDefVisitor.successfulType(declType))
			{
				node.setType(issueHandler.addTypeError(decl, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(decl+"")));
				return node.getType();
			}

			for(PDefinition def : declType.getDefinitions())
				newScope.addVariable(def.getName(), def);
		}
		
		CmlTypeCheckInfo newQ = (CmlTypeCheckInfo) question;
		newQ.addVariable(node.getName(), node);

		PProcess process = node.getProcess();
		PType pType = process.apply(parentChecker, newScope);
		if (!successfulType(pType)) {
			node.setType(			issueHandler.addTypeError(node,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
					.customizeMessage(node.getName() + ""))
					);
			return node.getType();
		}

		
		// Marker type indicating paragraph type check ok
		node.setType(new AProcessParagraphType());
		return node.getType();
	}


	/*
	@Override
	public PType caseAProcessDefinition(AProcessDefinition node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {

		return node.getProcess().apply(this.parentChecker, question);
	}
	 */

	@Override
	public PType caseATypeSingleDeclaration(ATypeSingleDeclaration node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {
		PType type = node.getType();
		if (type != null)
		{
			LinkedList<LexIdentifierToken> ids = node.getIdentifiers();
			List<PDefinition> defs = new LinkedList<PDefinition>();
			for(LexIdentifierToken id : ids)
			{
				LexNameToken idName = new LexNameToken("",id);
				ALocalDefinition localDef = AstFactory.newALocalDefinition(node.getLocation(), idName, NameScope.LOCAL, node.getType());
				defs.add(localDef);
			}
			type.setDefinitions(defs);
		}
		return node.getType();
	}

	@Override
	public PType caseAChannelsDefinition(
			AChannelsDefinition node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {

		CmlTypeCheckInfo newQ = (CmlTypeCheckInfo) question;

		LinkedList<AChannelNameDefinition> cns = node
				.getChannelNameDeclarations();
		for (AChannelNameDefinition decl : cns) {
			PType typeBack = decl.getSingleType().apply(this, question); // decl.apply(parentChecker,
			// question);
			if (typeBack == null)
				issueHandler.addTypeError(decl,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
						.customizeMessage(decl.toString()));
			else
				for (LexIdentifierToken id : decl.getSingleType()
						.getIdentifiers())
					newQ.addChannel(id, decl);
		}

		node.setType(new AChannelType());
		newQ.addType(node.getName(), node);

		return node.getType();
	}

	@Override
	public PType caseAExplicitCmlOperationDefinition(
			AExplicitCmlOperationDefinition node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {

		// add the parameter to the Environment

		// check the body
		CmlTypeCheckInfo newQuestion = (CmlTypeCheckInfo) createEnvironmentWithFormals(
				question, node);
		PAction operationBody = node.getBody();
		// TODO Check that the operationBody is only allowed operations
		question.contextSet(CmlTypeCheckInfo.class, newQuestion);
		PType bodyType = operationBody.apply(parentChecker, newQuestion);
		question.contextRem(CmlTypeCheckInfo.class);
		if (bodyType == null)
			throw new AnalysisException("Unable to type check operation body "
					+ node.getName());



		// check constructor
		boolean isCtor = node.getIsConstructor();
		if (isCtor) // check type is of class type
		{
			AOperationType operType = node.getType();
			if (!operType.getResult()
					.equals(node.getAncestor(AClassDefinition.class)
							.getType())) {

			}

		}

		return node.getType();
	}

	private TypeCheckQuestion createEnvironmentWithFormals(
			org.overture.typechecker.TypeCheckInfo current, PDefinition funDef)
					throws AnalysisException {

		CmlTypeCheckInfo newQuestion = (CmlTypeCheckInfo) current;
		List<PType> paramTypes = null;
		List<PPattern> patterns = null;

		if (funDef instanceof AExplicitFunctionDefinition) {
			// Flatten list of lists into one list.
			patterns = new LinkedList<PPattern>();
			for (List<PPattern> lp : AExplicitFunctionDefinition.class.cast(
					funDef).getParamPatternList())
				for (PPattern p : lp)
					patterns.add(p);

			paramTypes = AExplicitFunctionDefinition.class.cast(funDef)
					.getType().getParameters();
		} else if (funDef instanceof AExplicitCmlOperationDefinition) {
			patterns = AExplicitCmlOperationDefinition.class.cast(funDef)
					.getParameterPatterns();

			paramTypes = AExplicitCmlOperationDefinition.class.cast(funDef)
					.getType().getParameters();
		}

		// setup local environment

		CmlTypeCheckInfo functionBodyEnv = (CmlTypeCheckInfo)newQuestion.newScope(current, funDef);


		// add formal arguments to the environment
		int i = 0;
		for (PPattern p : patterns) {
			if (p instanceof AIdentifierPattern) {
				PType paramType = (i < paramTypes.size() ? paramTypes.get(i)
						: new AErrorType(p.getLocation(), true));
				AIdentifierPattern idp = (AIdentifierPattern) p;
				LexLocation location_ = p.getLocation();
				org.overture.ast.typechecker.NameScope nameScope_ = NameScope.LOCAL;
				Boolean used_ = false;
				SClassDefinition classDefinition_ = null;
				AAccessSpecifierAccessSpecifier access_ = null;
				PType type_ = paramType;
				if (paramType instanceof AProductType) {
					AProductType pt = (AProductType) paramType;
					type_ = pt.getTypes().get(i);
				}

				Pass pass_ = Pass.DEFS;
				Boolean valueDefinition_ = false;
				LexNameToken name_ = ((AIdentifierPattern) p).getName();
				ALocalDefinition local = new ALocalDefinition(location_,
						nameScope_, used_, classDefinition_, access_, type_,
						pass_, valueDefinition_, name_);
				functionBodyEnv.addVariable(idp.getName(), local);
			} else
				throw new AnalysisException(
						"Can only handle identifier patterns at this time.");
			i++;
		}

		/*
		 * TODO: Question, are identifier patterns the only one we care about
		 * for function parameters? If they are the AnalysisException thrown
		 * above must be turned into a type error.
		 */
		return functionBodyEnv;
	}

	@Override
	public PType caseAExplicitFunctionDefinition(
			AExplicitFunctionDefinition node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {

		// Type check the function body in an augmented environment
		CmlTypeCheckInfo newQuestion = (CmlTypeCheckInfo) createEnvironmentWithFormals(
				question, node);

		PExp body = node.getBody();
		body.apply(parentChecker, newQuestion);
		if (body.getType() == null)
			issueHandler.addTypeError(body,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
					.customizeMessage(node.getName().name));

		// Check funcType <: bodyType in question
		AFunctionType funcType = node.getType();
		if (!typeComparator.isSubType(funcType.getResult(), body.getType()))
			issueHandler.addTypeError(body,
					TypeErrorMessages.EXPECTED_SUBTYPE_RELATION
					.customizeMessage(funcType.toString(), body
							.getType().toString()));

		AFunctionType fType = (AFunctionType) PDefinitionAssistantTC.getType(node);
		node.getName().setTypeQualifier(fType.getParameters());

		if (node.getBody() instanceof ASubclassResponsibilityExp)
		{
			node.getClassDefinition().setIsAbstract(true);
		}

		if (node.getBody() instanceof ASubclassResponsibilityExp ||
				node.getBody() instanceof ANotYetSpecifiedExp)
		{
			node.setIsUndefined(true);
		}

		if (node.getPrecondition() != null)
		{
			PDefinitionAssistantTC.typeResolve(node.getPredef(),parentChecker,question);
		}

		if (node.getPostcondition() != null)
		{
			PDefinitionAssistantTC.typeResolve(node.getPostdef(),parentChecker,question);
		}

		for (List<PPattern> pp: node.getParamPatternList())
		{
			PPatternListAssistantTC.typeResolve(pp, parentChecker, question);
		}

		node.setType(funcType);
		node.setExpectedResult(funcType.getResult());
		node.setActualResult(body.getType());

		// Nonetheless the function type will be the type its definition to
		// facilitate further type checking even in the presents of errors.
		node.setType(funcType);
		return funcType;
	}

	@Override
	public PType caseATypeDefinition(ATypeDefinition node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {

		try
		{
			ATypeDefinitionAssistantTC.typeResolve(node, parentChecker, question);
		}
		catch (TypeCheckException e)
		{
			node.setType(issueHandler.addTypeError(node, e.getMessage()));
		}


		return node.getType();
	}

}