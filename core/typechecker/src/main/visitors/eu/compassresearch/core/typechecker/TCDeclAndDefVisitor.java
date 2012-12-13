package eu.compassresearch.core.typechecker;


import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AClassClassDefinition;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.ALocalDefinition;
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
import org.overture.typechecker.TypeChecker;
import org.overture.typechecker.assistant.definition.ATypeDefinitionAssistantTC;
import org.overture.typechecker.assistant.definition.PDefinitionAssistantTC;
import org.overture.typechecker.assistant.definition.SClassDefinitionAssistantTC;
import org.overture.typechecker.assistant.pattern.PPatternListAssistantTC;

import eu.compassresearch.ast.actions.SStatementAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.AExpressionSingleDeclaration;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AActionParagraphDefinition;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChannelParagraphDefinition;
import eu.compassresearch.ast.definitions.AClassParagraphDefinition;
import eu.compassresearch.ast.definitions.AExplicitOperationDefinition;
import eu.compassresearch.ast.definitions.AFunctionParagraphDefinition;
import eu.compassresearch.ast.definitions.AInitialParagraphDefinition;
import eu.compassresearch.ast.definitions.AOperationParagraphDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.AProcessParagraphDefinition;
import eu.compassresearch.ast.definitions.AStateParagraphDefinition;
import eu.compassresearch.ast.definitions.ATypesParagraphDefinition;
import eu.compassresearch.ast.definitions.AValueParagraphDefinition;
import eu.compassresearch.ast.definitions.SOperationDefinition;
import eu.compassresearch.ast.types.AActionParagraphType;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.ast.types.AErrorType;
import eu.compassresearch.ast.types.AFunctionParagraphType;
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
	public PType caseAInitialParagraphDefinition(
			AInitialParagraphDefinition node, org.overture.typechecker.TypeCheckInfo question)
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
	public PType caseAActionParagraphDefinition(
			AActionParagraphDefinition node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {

		for (AActionDefinition actionDefinition : node.getActions()) {
			actionDefinition.apply(this, question);
		}

		node.setType(new AActionParagraphType());
		return node.getType();
	}

	@Override
	public PType caseAActionDefinition(AActionDefinition node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {

		// Add this to the current scope
		((TypeCheckQuestion) question).addVariable(node.getName()
				.getIdentifier(), node);

		node.setType(new AStatementType());
		return node.getType();
	}

	@Override
	public PType caseATypesParagraphDefinition(ATypesParagraphDefinition node,
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
	public PType caseAValueParagraphDefinition(AValueParagraphDefinition node,
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
		PType declaredType = node.getType().apply(parentChecker, question);
		if (declaredType instanceof AErrorType)
			return declaredType;

		PType expressionType = exp.apply(parentChecker, question);
		if (expressionType instanceof AErrorType)
			return expressionType;

		node.setExpType(expressionType);

		// Add this value definition to the environment
		CmlTypeCheckInfo tci = (CmlTypeCheckInfo) question;
		ALocalDefinition localDef = new ALocalDefinition(node.getLocation(),
				NameScope.GLOBAL, false, node.getAccess(), Pass.VALUES, true);
		localDef.setName(node.getName());
		localDef.setType(declaredType);
		tci.addVariable(node.getName(), localDef);

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
			if (!(def instanceof AValueParagraphDefinition)) {
				res.add(def);
				return res;
			}

			// flatten the AvalueDefinition into a list
			AValueParagraphDefinition vpDef = (AValueParagraphDefinition) def;
			for (PDefinition innerDef : vpDef.getValueDefinitions()) {
				innerDef.setPass(Pass.VALUES);
				res.add(innerDef);
			}
			return res;
		}
	}

	private static class OvertureToCmlTypeParagraphHandler implements OvertureToCmlHandler
	{

		@Override
		public List<PDefinition> handle(PDefinition def) {
			List<PDefinition> res = new LinkedList<PDefinition>();
			// is it not a Value Paragraph Definition behave like Identity
			if (!(def instanceof ATypesParagraphDefinition)) {
				res.add(def);
				return res;
			}

			// else flatten ATypeParagraphDefinition into a list
			ATypesParagraphDefinition tpdef = (ATypesParagraphDefinition)def;
			for(PDefinition d : tpdef.getTypes())
			{
				d.setPass(Pass.TYPES);
				res.add(d);
			}
			return res;
		}

	}

	private static class OvertureToCmlFunctionParagraphHandler implements OvertureToCmlHandler
	{

		@Override
		public List<PDefinition> handle(PDefinition def) {

			List<PDefinition> result = new LinkedList<PDefinition>();

			if (! (def instanceof AFunctionParagraphDefinition))
			{
				result.add(def);
				return result;
			}

			AFunctionParagraphDefinition funlist = (AFunctionParagraphDefinition)def;
			for(PDefinition pdef : funlist.getFunctionDefinitions())
			{
				result.add(pdef);
			}

			return result;

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
		overtureClassBits.put(AExplicitFunctionDefinition.class, id);
		overtureClassBits.put(AImplicitFunctionDefinition.class, id);
		overtureClassBits.put(AValueDefinition.class, id);
		overtureClassBits.put(AUntypedDefinition.class, id);
		overtureClassBits.put(AValueParagraphDefinition.class,
				new OvertureToCmlValuesParahraphHandler());
		overtureClassBits.put(ATypesParagraphDefinition.class, new OvertureToCmlTypeParagraphHandler());
		overtureClassBits.put(AFunctionParagraphDefinition.class, new OvertureToCmlFunctionParagraphHandler());

	}

	/*
	 * Create an Overture class that
	 */
	private static AClassClassDefinition createSurrogateClass(
			AClassParagraphDefinition node,
			CmlTypeCheckInfo question) {



		if (question.getGlobalClassDefinitions() == null)
			throw new NullPointerException();

		List<SClassDefinition> superDefs = new LinkedList<SClassDefinition>();
		superDefs.add(question.getGlobalClassDefinitions());

		// So a bit of work needs to be done for definitions
		List<PDefinition> overtureReadyCMLDefinitions = new LinkedList<PDefinition>();

		if (question.env.getEnclosingDefinition() instanceof AClassClassDefinition)
			overtureReadyCMLDefinitions
			.addAll(((AClassClassDefinition) question.env
					.getEnclosingDefinition()).getDefinitions());

		// Lets mangle the CML definitions for Overture to cope with them
		for (PDefinition def : node.getDefinitions()) {
			if (overtureClassBits.containsKey(def.getClass()))
				overtureReadyCMLDefinitions.addAll(overtureClassBits.get(
						def.getClass()).handle(def));
		}

		// Nice :) we can use AstFactory now !
		AClassClassDefinition surrogateOvertureClass = AstFactory
				.newAClassClassDefinition(node.getName(),
						new LexNameList(node.getSupernames()),
						overtureReadyCMLDefinitions);

		surrogateOvertureClass.setSuperDefs(superDefs);

		return surrogateOvertureClass;
	}

	PType typeCheckWithOverture(AClassParagraphDefinition node,
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
				if (def instanceof AClassParagraphDefinition)
				{
					AClassParagraphDefinition cdef = (AClassParagraphDefinition)def;
					surrogateDefinitions.add(createSurrogateClass(cdef, info));
				}
			}
		}
		else
		{
			node.setType(issueHandler.addTypeError(node, TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(""+node)));
			return node.getType();
		}


		Environment surrogateEnvironment = new FlatEnvironment(surrogateDefinitions);

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
			Class<T> type, AClassParagraphDefinition clz) {

		List<T> result = new LinkedList<T>();

		for (PDefinition d : clz.getDefinitions())
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
	public PType caseAClassParagraphDefinition(AClassParagraphDefinition node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {

		// Check environment, it must be a CML environment as we are coming from top-level
		if (!(question instanceof CmlTypeCheckInfo))
		{
			node.setType(issueHandler.addTypeError(node,TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(node+"")));
			return node.getType();
		}
		CmlTypeCheckInfo cmlEnvironment = (CmlTypeCheckInfo)question;
		question.contextSet(CmlTypeCheckInfo.class, cmlEnvironment);

		// Create Surrogate Overture Class
		AClassClassDefinition surrogate = createSurrogateClass(node, cmlEnvironment);

		// Type check surrogate with overture
		PType classType = typeCheckWithOverture(node, surrogate, question);
		if (classType == null || classType instanceof AErrorType)
			return new AErrorType();

		// Find out what Overture is not doing for us
		List<PDefinition> thoseHandledByCOMPASS = new LinkedList<PDefinition>();
		for (PDefinition def : node.getDefinitions())
			if (!overtureClassBits.containsKey(def.getClass()))
				thoseHandledByCOMPASS.add(def);

		// Handle the COMPASS definitions
		{
			CmlTypeCheckInfo classQuestion = cmlEnvironment.newScope(surrogate);

			// add state
			List<AStateParagraphDefinition> states = findParticularDefinitionType(
					AStateParagraphDefinition.class, node);
			for (AStateParagraphDefinition paragraph : states) {

				PType paragraphType = paragraph.apply(parentChecker, question);
				if (!successfulType(paragraphType)) {
					return issueHandler
							.addTypeError(
									paragraph,
									TypeErrorMessages.PARAGRAPH_HAS_TYPES_ERRORS
									.customizeMessage(paragraph
											.getName() + ""));

				}

			}
			List<VDMError> errs = TypeChecker.getErrors();
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

		cmlEnvironment.addType(node.getName(), node);
		question.contextRem(CmlTypeCheckInfo.class);
		AClassType result = new AClassType(node.getLocation(), true, node.getDefinitions(),
				node.getName(), node.getClassDefinition());
		node.setType(result);
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
	 * A Function Paragraph is well typed if all the type definitions it
	 * contains are well typed.
	 * 
	 * 
	 * 
	 */
	@Override
	public PType caseAFunctionParagraphDefinition(
			AFunctionParagraphDefinition node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {
		for (PDefinition def : node.getFunctionDefinitions()) {
			PType type = def.apply(parentChecker, question);
			if (type == null)
				throw new AnalysisException(
						"Unable to determined type for function definition: "
								+ def);
		}
		node.setType(new AFunctionParagraphType());

		return node.getType();
	}

	@Override
	public PType caseAOperationParagraphDefinition(
			AOperationParagraphDefinition node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {
		CmlTypeCheckInfo newQ = (CmlTypeCheckInfo) question;
		for (SOperationDefinition def : node.getOperations()) {
			PType defType = def.apply(parentChecker, question);
			if (defType == null)
				issueHandler.addTypeError(def,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
						.customizeMessage(def.toString()));
			newQ.addVariable(def.getName(), def);
		}

		node.setType(new AOperationParagraphType());
		return node.getType();
	}

	@Override
	public PType caseAStateParagraphDefinition(AStateParagraphDefinition node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {
		CmlTypeCheckInfo newQ = (CmlTypeCheckInfo) question;
		// Go through all the state defs and typecheck them
		for (PDefinition def : node.getStateDefs()) {
			def.apply(this, question);
			newQ.addVariable(def.getName(), def);
		}

		node.setType(new AStateParagraphType());

		return node.getType();
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
	public PType caseAProcessParagraphDefinition(
			AProcessParagraphDefinition node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {
		// make a new scope for the process
		CmlTypeCheckInfo newScope = (CmlTypeCheckInfo) ((TypeCheckQuestion) question)
				.newScope(null);

		AProcessDefinition pdef = node.getProcessDefinition();
		PType pType = pdef.apply(parentChecker, newScope);
		if (!successfulType(pType)) {
			issueHandler.addTypeError(pdef,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
					.customizeMessage(pdef.getName() + ""));
			pdef.setType(new AErrorType());
			return pdef.getType();
		}

		CmlTypeCheckInfo newQ = (CmlTypeCheckInfo) question;
		newQ.addVariable(node.getName(), node);
		// Marker type indicating paragraph type check ok
		node.setType(new AProcessParagraphType());
		return node.getType();
	}

	@Override
	public PType caseAProcessDefinition(AProcessDefinition node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {

		return node.getProcess().apply(this.parentChecker, question);
	}

	@Override
	public PType caseATypeSingleDeclaration(ATypeSingleDeclaration node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {
		AChannelType ctype = new AChannelType();
		ctype.setType(node.getType());
		node.setType(new AChannelType());

		return node.getType();
	}

	@Override
	public PType caseAChannelParagraphDefinition(
			AChannelParagraphDefinition node,
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
	public PType caseAExplicitOperationDefinition(
			AExplicitOperationDefinition node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {

		// add the parameter to the Environment

		// check the body
		CmlTypeCheckInfo newQuestion = (CmlTypeCheckInfo) createEnvironmentWithFormals(
				question, node);
		SStatementAction operationBody = node.getBody();
		PType bodyType = operationBody.apply(parentChecker, newQuestion);
		if (bodyType == null)
			throw new AnalysisException("Unable to type check operation body "
					+ node.getName());

		// check constructor
		boolean isCtor = node.getIsConstructor();
		if (isCtor) // check type is of class type
		{
			AOperationType operType = node.getType();
			if (!operType.getResult()
					.equals(node.getAncestor(AClassParagraphDefinition.class)
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
		} else if (funDef instanceof AExplicitOperationDefinition) {
			patterns = AExplicitOperationDefinition.class.cast(funDef)
					.getParameterPatterns();

			paramTypes = AExplicitOperationDefinition.class.cast(funDef)
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