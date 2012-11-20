package eu.compassresearch.core.typechecker;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexLocation;
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
import org.overture.typechecker.FlatEnvironment;

import eu.compassresearch.ast.actions.SStatementAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AActionParagraphDefinition;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChannelParagraphDefinition;
import eu.compassresearch.ast.definitions.AClassParagraphDefinition;
import eu.compassresearch.ast.definitions.AExplicitOperationDefinition;
import eu.compassresearch.ast.definitions.AFunctionParagraphDefinition;
import eu.compassresearch.ast.definitions.AOperationParagraphDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.AProcessParagraphDefinition;
import eu.compassresearch.ast.definitions.AStateParagraphDefinition;
import eu.compassresearch.ast.definitions.ATypesParagraphDefinition;
import eu.compassresearch.ast.definitions.AValueParagraphDefinition;
import eu.compassresearch.ast.types.AActionParagraphType;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.ast.types.AErrorType;
import eu.compassresearch.ast.types.AFunctionParagraphType;
import eu.compassresearch.ast.types.AOperationParagraphType;
import eu.compassresearch.ast.types.AProcessParagraphType;
import eu.compassresearch.ast.types.AStateParagraphType;
import eu.compassresearch.ast.types.AStatementType;
import eu.compassresearch.ast.types.ATypeParagraphType;
import eu.compassresearch.ast.types.AValueParagraphType;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeCheckQuestion;
import eu.compassresearch.core.typechecker.api.TypeComparator;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

@SuppressWarnings("serial")
class TCDeclAndDefVisitor extends
		QuestionAnswerCMLAdaptor<org.overture.typechecker.TypeCheckInfo, PType> {

	// Errors and other things are recorded on this guy
	private CmlTypeChecker parentChecker;
	private TypeComparator typeComparator;
	private final TypeIssueHandler issueHandler;

	public TCDeclAndDefVisitor(CmlTypeChecker parent,
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
		
		for(AActionDefinition actionDefinition : node.getActions())
		{
			actionDefinition.apply(this,question);
		}
		
		node.setType(new AActionParagraphType());
		return node.getType();
	}
	
	@Override
	public PType caseAActionDefinition(AActionDefinition node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {
		
		//Add this to the current scope
		((TypeCheckQuestion)question).addVariable(node.getName().getIdentifier(), node);
		
		node.setType(new AStatementType());
		return  node.getType();
	}
	
	@Override
	public PType caseATypesParagraphDefinition(ATypesParagraphDefinition node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {
		TypeCheckInfo newQ = (TypeCheckInfo) question;
		LinkedList<ATypeDefinition> defs = node.getTypes();
		for (ATypeDefinition d : defs) {
			PType type = d.apply(parentChecker, question);
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
		TypeCheckInfo newQ = (TypeCheckInfo) question;
		LinkedList<PDefinition> _list = node.getValueDefinitions();
		ShieldedList<PDefinition> list = new ShieldedList<PDefinition>();
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

		TypeCheckInfo tci = (TypeCheckInfo) question;
		tci.addVariable(node.getName(), node);

		// Check type consistency
		if (!typeComparator.isSubType(expressionType, declaredType))
			issueHandler.addTypeError(node,
					TypeErrorMessages.EXPECTED_SUBTYPE_RELATION
							.customizeMessage(expressionType.toString(),
									declaredType.toString()));

		// if there is a parent definition lets find things
		List<PDefinition> newDefs = new LinkedList<PDefinition>();
		if (question.env.getEnclosingDefinition() != null)
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
		TypeCheckInfo newQ = (TypeCheckInfo) question;
		// Add this class to the current environment
		PType clzType = new AClassType();
		clzType.setDefinitions(node.getDefinitions());
		node.setType(clzType);

		// Create scope for the class body
		TypeCheckInfo classQuestion = (TypeCheckInfo) newQ.newScope(node);
		((FlatEnvironment) classQuestion.env).add(node);
		for (PDefinition def : node.getDefinitions()) {
			newQ.updateContextNameToCurrentScope(def);
			PType type = def.apply(parentChecker, classQuestion);
			if (type == null)
				throw new AnalysisException("Unable to determine type for: "
						+ def);
		}

		return clzType;
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
		TypeCheckInfo newQ = (TypeCheckInfo) question;
		for (PDefinition def : node.getOperations()) {
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
		TypeCheckInfo newQ = (TypeCheckInfo) question;
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
		//make a new scope for the process
		TypeCheckInfo newScope = (TypeCheckInfo)((TypeCheckQuestion)question).newScope(node);
		// TODO: Rethink the environment
		AProcessDefinition pdef = node.getProcessDefinition();
		pdef.apply(parentChecker, newScope);

		TypeCheckInfo newQ = (TypeCheckInfo) question;
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
		TypeCheckInfo newQ = (TypeCheckInfo) question;
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

		TypeCheckInfo newQ = (TypeCheckInfo) question;

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
		// add the state of the enclosing class to the Environment

		// add the parameter to the Environment

		// check the body
		TypeCheckInfo newQuestion = (TypeCheckInfo) createEnvironmentWithFormals(
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

		TypeCheckInfo newQuestion = (TypeCheckInfo) current;
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
		}

		// setup local environment

		TypeCheckQuestion functionBodyEnv = newQuestion.newScope(funDef);

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
				AAccessSpecifierAccessSpecifier access_;
				PType type_ = paramType;
				if (paramType instanceof AProductType) {
					AProductType pt = (AProductType) paramType;
					type_ = pt.getTypes().get(i);
				}

				Pass pass_ = Pass.DEFS;
				Boolean valueDefinition_ = false;
				LexNameToken name_ = ((AIdentifierPattern) p).getName();
				ALocalDefinition local = new ALocalDefinition(location_,
						nameScope_, used_, classDefinition_, null, type_,
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
		TypeCheckInfo newQuestion = (TypeCheckInfo) createEnvironmentWithFormals(
				question, node);

		PExp body = node.getBody();
		body.apply(parentChecker, newQuestion);
		if (body.getType() == null)
			issueHandler.addTypeError(body,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(node.getName().name));

		// Check funcType <: bodyType in question
		AFunctionType funcType = node.getType();
		if (!typeComparator.isSubType(funcType, body.getType()))
			issueHandler.addTypeError(body,
					TypeErrorMessages.EXPECTED_SUBTYPE_RELATION
							.customizeMessage(funcType.toString(), body
									.getType().toString()));

		// Nonetheless the function type will be the type its definition to
		// facilitate further type checking even in the presents of errors.
		return funcType;
	}

}