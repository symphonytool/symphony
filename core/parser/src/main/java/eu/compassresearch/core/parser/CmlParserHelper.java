package eu.compassresearch.core.parser;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.antlr.runtime.CommonToken;

import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.APrivateAccess;
import org.overture.ast.definitions.AProtectedAccess;
import org.overture.ast.definitions.APublicAccess;
import org.overture.ast.definitions.PAccessBase;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.AApplyExp;
import org.overture.ast.expressions.ABooleanConstExp;
import org.overture.ast.expressions.AIsOfClassExp;
import org.overture.ast.expressions.APreExp;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.lex.LexBooleanToken;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexIntegerToken;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.lex.LexToken;
import org.overture.ast.lex.VDMToken;
import org.overture.ast.node.tokens.TAsync;
import org.overture.ast.node.tokens.TStatic;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.APatternTypePair;
import org.overture.ast.patterns.ARecordPattern;
import org.overture.ast.patterns.ASetBind;
import org.overture.ast.patterns.ATuplePattern;
import org.overture.ast.patterns.PMultipleBind;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.statements.AExternalClause;
import org.overture.ast.statements.PObjectDesignator;
import org.overture.ast.statements.PStateDesignator;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.typechecker.Pass;
import org.overture.ast.types.AAccessSpecifierAccessSpecifier;
import org.overture.ast.types.ABooleanBasicType;
import org.overture.ast.types.AFieldField;
import org.overture.ast.types.AFunctionType;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.ARecordInvariantType;
import org.overture.ast.types.AUnresolvedType;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.actions.ACallStatementAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.AMuAction;
import eu.compassresearch.ast.actions.ANewStatementAction;
import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASignalCommunicationParameter;
import eu.compassresearch.ast.actions.ASingleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.ASpecificationStatementAction;
// import eu.compassresearch.ast.actions.AUnresolvedObjectDesignator;
// import eu.compassresearch.ast.actions.AUnresolvedStateDesignator;
import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.actions.SStatementAction;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
// import eu.compassresearch.ast.definitions.AExplicitOperationDefinition;
import eu.compassresearch.ast.definitions.ALogicalAccess;
// import eu.compassresearch.ast.expressions.ACompChansetSetExp;
// import eu.compassresearch.ast.expressions.AComprehensionRenameChannelExp;
// import eu.compassresearch.ast.expressions.AEnumChansetSetExp;
import eu.compassresearch.ast.expressions.ANameChannelExp;
import eu.compassresearch.ast.expressions.AUnresolvedPathExp;
import eu.compassresearch.ast.patterns.ARenamePair;
import eu.compassresearch.ast.program.PSource;
// import eu.compassresearch.core.lexer.CmlLexeme;
// import eu.compassresearch.core.parser.util.Pair;

/**
 * Implements Parser actions and helper methods. TODO: A consistent method
 * naming strategy is needed. I have unfortunately a mixed naming convention at
 * the moment. TODO: We might split this file into several for files one for
 * expressions, processes and etc.
 * 
 * @author Anders Kaels Malmos
 * 
 */
public class CmlParserHelper {

    PSource currentSource = null;

    public CmlParserHelper(PSource currentSource) {
	this.currentSource = currentSource;
    }

    public static void setDefaultTrueInvarientDefinition(ATypeDefinition type){
    	LexNameToken fname = new LexNameToken("", new LexIdentifierToken("inv_"+type.getName(), false, type.getLocation()));
    	List<LexNameToken> typeParams = new LinkedList<LexNameToken>();
		List<List<PPattern>> parameters = new LinkedList<List<PPattern>>();
		PExp body = AstFactory.newABooleanConstExp(new LexBooleanToken(true, type.getLocation()));
		PExp precondition = null;
		PExp postcondition = null;
		boolean typeInvariant = true;
		LexNameToken measure = null;
		AFunctionType ftype = AstFactory.newAFunctionType(type.getLocation(), true, new LinkedList<PType>(), new ABooleanBasicType());
		AExplicitFunctionDefinition invDef = AstFactory.newAExplicitFunctionDefinition(fname, NameScope.LOCAL, typeParams, ftype, parameters, body, precondition, postcondition, typeInvariant, measure);
		type.setInvdef(invDef);
    }
    
    public static char convertEscapeToChar(String escape) {
	if (escape.startsWith("\\")) {
	    switch (escape.charAt(1)) {
	    case 'n':
		return '\n';
	    case '\\':
		return '\\';
	    case 'r':
		return '\r';
	    case 't':
		return '\t';
	    case 'f':
		return '\f';
	    case 'e':
		return (char) 0x001B;
	    case 'a':
		return (char) 0x0007;
	    case 'x':
		return (char) new BigInteger(escape.substring(2), 16)
			.intValue();
	    case 'u':
		return (char) new BigInteger(escape.substring(2), 16)
			.intValue();
	    case 'c':
		return (char) (escape.charAt(2) - 'A' + 1);
	    case '0':
		return (char) new BigInteger(escape.substring(2), 8).intValue();
	    case '\"':
		return '\"';
	    case '\'':
		return '\'';
	    default:
		throw new RuntimeException("Illegal escape sequence: " + escape);
	    }
	}
	return escape.charAt(0);
    }

    /**
     * Helpers
     */

    private LexNameToken dottedIdentifierToLexNameToken(
    	    List<LexIdentifierToken> ids) {
    	StringBuilder module = new StringBuilder();
    	// if we get in here there must at least be one identifier, id will be
    	// set to something other than null
    	LexIdentifierToken id = null;

    	for (ListIterator<LexIdentifierToken> it = ids.listIterator(); it
    		.hasNext();) {
    	    LexIdentifierToken prefixid = it.next();

    	    if (it.hasNext()) {
    		module.append(prefixid.getName() + ".");
    	    } else {
    		if (module.length() > 0)
    		    module.deleteCharAt(module.length() - 1);
    		else
    		    module.append("");
    		id = prefixid;
    	    }
    	}
    	return new LexNameToken(module.toString(), id);
    }

    /**
     * Since names now can be longer than 2 ids, then we cannot determine at
     * this point if this is a "name" or a "field ref", so an
     * UnresolvedObjectDesignator is returned and the typechecker should fix
     * this
     * 
     * @param ids
     * @return
     */
    // private PObjectDesignator dottedIdentifierToObjDesignator(
    // 	    List<LexIdentifierToken> ids) {
    // 	LexLocation loc = extractLexLocation(extractFirstLexLocation(ids),
    // 		extractLastLexLocation(ids));

    // 	// if only one element then this must be name
    // 	if (ids.size() == 1)
    // 	    return AstFactory
    // 		    .newAIdentifierObjectDesignator(dottedIdentifierToLexNameToken(ids));
    // 	// we cannot decide if this is a name with multiple components or a
    // 	// field ref
    // 	else
    // 	    return new AUnresolvedObjectDesignator(loc, ids);
    // }

    // private PStateDesignator dottedIdentifierToStateDesignator(
    // 	    List<LexIdentifierToken> ids) {
    // 	LexLocation loc = extractLexLocation(extractFirstLexLocation(ids),
    // 		extractLastLexLocation(ids));

    // 	// if only one element then this must be name
    // 	if (ids.size() == 1)
    // 	    return AstFactory
    // 		    .newAIdentifierStateDesignator(dottedIdentifierToLexNameToken(ids));
    // 	// we cannot decide if this is a name with multiple components or a
    // 	// field ref
    // 	else
    // 	    return new AUnresolvedStateDesignator(loc, ids);
    // }

    /*
     * FIXME needs to throw an error if the name is multipart
     */
    // public List<LexIdentifierToken> convertNameListToIdentifierList(
    // 	    List<LexNameToken> nameList) {
    // 	List<LexIdentifierToken> out = new LinkedList<LexIdentifierToken>();
    // 	for (LexNameToken name : nameList) {
    // 	    out.add(extractLexIdentifierToken(name));
    // 	}
    // 	return out;
    // }

    // public LexNameToken extractNameFromUNDERNAMEToken(CmlLexeme mkUnder) {
    // 	String nameString = mkUnder.getValue().substring(3);

    // 	LexNameToken name = null;
    // 	if (nameString.matches(".+['`''.'].+")) {
    // 	    String[] tokens = nameString.split("['`''.']");
    // 	    name = new LexNameToken(tokens[0], tokens[1],
    // 		    extractLexLocation(mkUnder), false, true);
    // 	} else
    // 	    name = new LexNameToken("", nameString,
    // 		    extractLexLocation(mkUnder), false, true);
    // 	return name;
    // }

    public static AAccessSpecifierAccessSpecifier getAccessSpecifierFromToken(CommonToken qualifier) {
	PAccessBase access = null;
	if (qualifier == null || "private".equals(qualifier.getText()) ) {
	    access = new APrivateAccess();
	} else if ("protected".equals(qualifier.getText()) ) {
	    access = new AProtectedAccess();
	} else if ("logical".equals(qualifier.getText()) ) {
	    access = new ALogicalAccess();
	} else { // "public"
	    access = new APublicAccess();
	}
	return new AAccessSpecifierAccessSpecifier(access, null, null);
    }

    public static AAccessSpecifierAccessSpecifier getDefaultAccessSpecifier(
	    boolean isStatic, boolean isAsync, LexLocation loc) {
	/* return new AAccessSpecifierAccessSpecifier(new APublicAccess(), */
	/* (isStatic ? new TStatic() : null), */
	/* (isAsync ? new TAsync() : null),loc); */

	return new AAccessSpecifierAccessSpecifier(new APublicAccess(),
		(isStatic ? new TStatic() : null), (isAsync ? new TAsync()
			: null));

    }

    public static AAccessSpecifierAccessSpecifier getPrivateAccessSpecifier(
	    boolean isStatic, boolean isAsync, LexLocation loc) {
	/* return new AAccessSpecifierAccessSpecifier(new APrivateAccess(), */
	/* (isStatic ? new TStatic() : null), */
	/* (isAsync ? new TAsync() : null),loc); */
	return new AAccessSpecifierAccessSpecifier(new APrivateAccess(),
		(isStatic ? new TStatic() : null), (isAsync ? new TAsync()
			: null));
    }

    // public LexToken extractLexToken(CmlLexeme lexeme) {
    // 	LexLocation loc = extractLexLocation(lexeme);
    // 	VDMToken tok = null;
    // 	for (VDMToken t : VDMToken.values()) {
    // 	    String tokenDisplay = t.toString();
    // 	    if (tokenDisplay != null && tokenDisplay.equals(lexeme.getValue())) {
    // 		tok = t;
    // 		break;
    // 	    }
    // 	}
    // 	if (tok == null)
    // 	    throw new RuntimeException("Cannot find VDM token for "
    // 		    + lexeme.getValue());
    // 	return new LexToken(loc, tok);
    // }

    // public LexLocation extractLexLocation(CmlLexeme lexeme) {
    // 	return new LexLocation(currentSource.toString(), "",
    // 		lexeme.getStartPos().line, lexeme.getStartPos().column,
    // 		lexeme.getEndPos().line, lexeme.getEndPos().column,
    // 		lexeme.getStartPos().offset, lexeme.getEndPos().offset);
    // }

    // public LexLocation extractLexLocation(CmlLexeme start, CmlLexeme end) {
    // 	return new LexLocation(currentSource.toString(), "",
    // 		start.getStartPos().line, start.getStartPos().column,
    // 		end.getEndPos().line, end.getEndPos().column,
    // 		start.getStartPos().offset, end.getEndPos().offset);
    // }

    // public LexLocation extractLexLocation(CmlLexeme start, LexLocation end) {

    // 	return new LexLocation(currentSource.toString(), "",
    // 		start.getStartPos().line, start.getStartPos().column,
    // 		end.endLine, end.endPos, start.getStartPos().offset,
    // 		end.endOffset);
    // }

    // public LexLocation extractLexLocation(LexLocation start, CmlLexeme end) {

    // 	return new LexLocation(currentSource.toString(), "", start.startLine,
    // 		start.startPos, end.getEndPos().line, end.getEndPos().column,
    // 		start.startOffset, end.getEndPos().offset);
    // }

    // public LexLocation extractLexLocation(LexLocation start, LexLocation end) {
    // 	return new LexLocation(currentSource.toString(), "", start.startLine,
    // 		start.startPos, end.endLine, end.endPos, start.startOffset,
    // 		end.endOffset);
    // }

    // public LexLocation combineLexLocation(LexLocation start, LexLocation end) {
    // 	return extractLexLocation(start, end);
    // }

    // public LexLocation extractLastLexLocation(List<?> fields) {
    // 	try {
    // 	    Object o = fields.get(0);
    // 	    Class<?> clz = o.getClass();

    // 	    Method locMethod = clz.getMethod("getLocation", new Class<?>[] {});

    // 	    LexLocation candidate = (LexLocation) locMethod.invoke(o, null);
    // 	    for (Object p : fields) {
    // 		LexLocation pLoc = (LexLocation) locMethod.invoke(o, null);
    // 		if (pLoc.endOffset > candidate.endOffset)
    // 		    candidate = pLoc;
    // 	    }
    // 	    return candidate;
    // 	} catch (Exception e) {
    // 	    throw new RuntimeException(e);
    // 	}
    // }

    // public LexLocation extractFirstLexLocation(List<?> fields) {
    // 	try {
    // 	    Object o = fields.get(0);
    // 	    Class<?> clz = o.getClass();

    // 	    Method locMethod = clz.getMethod("getLocation", new Class<?>[] {});
    // 	    LexLocation candidate = (LexLocation) locMethod.invoke(o, null);
    // 	    for (Object p : fields) {
    // 		LexLocation pLoc = (LexLocation) locMethod.invoke(o, null);
    // 		if (pLoc.startOffset < candidate.startOffset)
    // 		    candidate = pLoc;
    // 	    }
    // 	    return candidate;
    // 	} catch (Exception e) {
    // 	    throw new RuntimeException(e);
    // 	}
    // }

    // public <T extends PPattern> LexLocation extractLexLeftMostFromPatterns(
    // 	    List<T> ptrns) {
    // 	LexLocation candidate = ptrns.get(0).getLocation();
    // 	for (PPattern p : ptrns) {
    // 	    if (p.getLocation().endOffset > candidate.endOffset)
    // 		candidate = p.getLocation();
    // 	}
    // 	return candidate;
    // }

    // public LexNameToken extractLexNameToken(CmlLexeme lexeme) {
    // 	return new LexNameToken("", lexeme.getValue(),
    // 		extractLexLocation(lexeme), false, true);
    // }

    // public LexNameToken extractLexNameToken(CmlLexeme lexeme, boolean old) {
    // 	return new LexNameToken("", lexeme.getValue(),
    // 		extractLexLocation(lexeme), old, true);
    // }

    // public LexNameToken extractLexNameToken(Object obj) {
    // 	CmlLexeme lexeme = (CmlLexeme) obj;
    // 	return new LexNameToken("", lexeme.getValue(),
    // 		extractLexLocation(lexeme), false, true);
    // }

    // public LexIdentifierToken extractLexIdentifierToken(Object obj) {
    // 	return extractLexIdentifierToken((CmlLexeme) obj);
    // }

    // public LexIdentifierToken extractLexIdentifierToken(CmlLexeme lexeme) {
    // 	return new LexIdentifierToken(lexeme.getValue(), false,
    // 		extractLexLocation(lexeme));
    // }

    // public LexIdentifierToken extractLexIdentifierToken(CmlLexeme lexeme,
    // 	    boolean old) {
    // 	return new LexIdentifierToken(lexeme.getValue(), old,
    // 		extractLexLocation(lexeme));
    // }

    // public LexIdentifierToken extractLexIdentifierToken(LexNameToken name) {
    // 	return new LexIdentifierToken(name.getName(), false, name.getLocation());
    // }

    public <T> List<T> caseFirstListElement(T element) {
	List<T> list = new LinkedList<T>();
	list.add(element);
	return list;
    }

    public <T> List<T> caseNextListElement(Object listObj, T element) {
	List<T> list = (List<T>) listObj;
	list.add(element);
	return list;
    }

    /**
     * Actions
     */

    public AActionDefinition caseActionDefinition(Object IDENTIFIER,
	    Object EQUALS, Object paragraphAction) {
    	/*
    	 * Commented out by RWL: cml.g does not use non-static methods 
    	 * on the CmlParserHelper. Thus this methods is not in use anymore
    	 * 
	Object[] pa = (Object[]) paragraphAction;
	List<ATypeSingleDeclaration> declarations = (List<ATypeSingleDeclaration>) pa[0];
	PAction action = (PAction) pa[1];
	LexLocation defLocation = combineLexLocation(
		extractLexLocation((CmlLexeme) IDENTIFIER),
		action.getLocation());
	
	AActionDefinition actionDefinition = new AActionDefinition();
	actionDefinition.setAccess(getDefaultAccessSpecifier(false, false, defLocation));
	actionDefinition.setAction(action);
	actionDefinition.setDeclarations(declarations);
	
//	return new AActionDefinition(defLocation,
//		extractLexNameToken(IDENTIFIER), NameScope.LOCAL, false, null,
//		getPrivateAccessSpecifier(false, false, defLocation),// Access
//		null, null,// Pass
//		declarations, action);
 
 */
	return null;
    }

    public List<ARenamePair> caseARenamePair(Object from, Object to) {
	List<ARenamePair> renamePairs = new LinkedList<ARenamePair>();
	ARenamePair pair = new ARenamePair(false,
		ConvertPExpToANameChannelExp(from),
		ConvertPExpToANameChannelExp(to));
	renamePairs.add(pair);
	return renamePairs;
    }

    public List<ARenamePair> caseRenameList(Object renameList, Object from,
	    Object to) {
	List<ARenamePair> renamePairs = (List<ARenamePair>) renameList;
	ARenamePair pair = new ARenamePair(false,
		ConvertPExpToANameChannelExp(from),
		ConvertPExpToANameChannelExp(to));
	renamePairs.add(pair);
	return renamePairs;
    }

    // public PAction caseDottedIdentifierLRPARENToCallAction(
    // 	    Object dottedIdentifier, Object PAREN, Object argsObj) {
    // 	List<LexIdentifierToken> ids = (List<LexIdentifierToken>) dottedIdentifier;
    // 	List<? extends PExp> args = (List<? extends PExp>) argsObj;

    // 	LexNameToken name = null;
    // 	PObjectDesignator objectDesignator = null;
    // 	LexLocation location = null;

    // 	int listSize = ids.size();
    // 	// if the are only one id this must be the name of the method
    // 	if (listSize == 1) {
    // 	    name = dottedIdentifierToLexNameToken(ids);
    // 	    location = extractLexLocation(name.getLocation(), (CmlLexeme) PAREN);

    // 	} else {
    // 	    name = dottedIdentifierToLexNameToken(ids.subList(listSize - 2,
    // 		    listSize - 1));
    // 	    objectDesignator = dottedIdentifierToObjDesignator(ids.subList(0,
    // 		    listSize - 1));
    // 	    location = extractLexLocation(objectDesignator.getLocation(),
    // 		    (CmlLexeme) PAREN);
    // 	}

    // 	return new ACallStatementAction(location, objectDesignator, name, args);
    // }

    public PAction caseDottedIdentifierToRefAction(Object dottedIdentifier) {
	List<LexIdentifierToken> ids = (List<LexIdentifierToken>) dottedIdentifier;

	LexNameToken name = dottedIdentifierToLexNameToken(ids);

	return new AReferenceAction(name.getLocation(), name,
		new LinkedList<PExp>());
    }

    /**
     * Actions - statements
     */

    // public PAction caseNewStatementAction(Object stateDesignator, Object NEW,
    // 	    Object newExp) {

    // 	/*
    // 	 * Need to rip out the path-based stuff here. rule was: | path
    // 	 * COLONEQUALS NEW path LRPAREN
    // 	 */
    // 	ANewStatementAction stm = null;
    // 	// these were Paths
    // 	PStateDesignator target = dottedIdentifierToStateDesignator((List<LexIdentifierToken>) stateDesignator); // should
    // 														 // probably
    // 														 // be
    // 														 // more
    // 														 // specific,
    // 														 // typewise
    // 	AApplyExp applyExp = (AApplyExp) newExp;
    // 	List<? extends PExp> args = applyExp.getArgs();
    // 	LexLocation location = combineLexLocation(target.getLocation(),
    // 		applyExp.getLocation());
    // 	// TODO:Typechecker need to check the target to see what the name and
    // 	LexNameToken name = null;
    // 	if (applyExp.getRoot() instanceof AVariableExp)
    // 	    name = ((AVariableExp) applyExp.getRoot()).getName();
    // 	else if (applyExp.getRoot() instanceof AUnresolvedPathExp)
    // 	    name = dottedIdentifierToLexNameToken(((AUnresolvedPathExp) applyExp
    // 		    .getRoot()).getIdentifiers());
    // 	else
    // 	    throw new ParserException(location, "");

    // 	return new ANewStatementAction(location, target, name, args);
    // }

    // public PAction caseMuAction(Object start, Object expressionList,
    // 	    Object actionList, Object end) {
    // 	List<LexIdentifierToken> ids = convertExpressionListToLexIdentifierTokenList((List<PExp>) expressionList);
    // 	return new AMuAction(extractLexLocation((CmlLexeme) start,
    // 		(CmlLexeme) end), ids, (List<PAction>) actionList);
    // }

    // public ASpecificationStatementAction caseImplicitOperationBody(
    // 	    Object extsObj, Object preObj, Object postObj) {
    // 	List<? extends AExternalClause> exts = (List<? extends AExternalClause>) extsObj;
    // 	PExp pre = (PExp) preObj;
    // 	PExp post = (PExp) postObj;
    // 	LexLocation loc = null;
    // 	if (exts != null) {
    // 	    // FIXME for some weird reason the exts do not have locations?
    // 	    // loc = extractLexLocation(extractFirstLexLocation(exts),
    // 	    // post.getLocation());
    // 	    loc = extractLexLocation(pre.getLocation(), post.getLocation());
    // 	} else if (pre != null) {
    // 	    loc = extractLexLocation(pre.getLocation(), post.getLocation());
    // 	} else {
    // 	    loc = post.getLocation();
    // 	}
    // 	return new ASpecificationStatementAction(loc, exts, pre, post);
    // }

    // public PAction caseDottedIdentifierToAssignmentStm(Object idsObj,
    // 	    Object COLONEQUALS, Object expression) {

    // 	List<LexIdentifierToken> ids = (List<LexIdentifierToken>) idsObj;
    // 	PStateDesignator stateDesignator = dottedIdentifierToStateDesignator(ids);
    // 	PExp exp = (PExp) expression;
    // 	LexLocation location = extractLexLocation(
    // 		stateDesignator.getLocation(), exp.getLocation());
    // 	return new ASingleGeneralAssignmentStatementAction(location,
    // 		stateDesignator, exp);
    // }

    /**
     * Definitions
     */

    // public AExternalClause caseVarInformation(Object mode, Object dottedId,
    // 	    Object type) {
    // 	LexNameToken name = dottedIdentifierToLexNameToken((List<LexIdentifierToken>) dottedId);
    // 	List<LexNameToken> ids = new LinkedList<LexNameToken>();
    // 	ids.add(name);
    // 	// ids.add(convertDottedIdentifierToLexNameToken((List<? extends
    // 	// LexNameToken>)$id));
    // 	return new AExternalClause((LexToken) mode, ids, (PType) type);
    // }

    // public AExternalClause caseMultiVarInformation(Object infoObj,
    // 	    Object dottedId, Object type) {
    // 	AExternalClause info = (AExternalClause) infoObj;
    // 	info.getIdentifiers()
    // 		.add(dottedIdentifierToLexNameToken((List<LexIdentifierToken>) dottedId));
    // 	if (info.getType() == null && type != null)
    // 	    info.setType((PType) type);
    // 	else if (info.getType() != null && type != null)
    // 	    throw new ParserException(info.getType().getLocation(),
    // 		    ParserErrorMessage.VARINFORMATION_MULTIPLETYPES_DEFINED
    // 			    .customizeMessage());

    // 	return info;
    // }

    // public PDefinition caseExplicitFunctionDefinition(Object id,
    // 	    Object functionType, Object checkId, Object parameterList,
    // 	    Object functionBodyObj, Object preExpr_opt, Object postExpr_opt,
    // 	    Object measureExpr) {
    // 	LexNameToken name = extractLexNameToken((CmlLexeme) id);
    // 	LexNameToken checkName = extractLexNameToken((CmlLexeme) checkId);
    // 	if (!name.equals(checkName))
    // 	    throw new ParserException(name.getLocation(),
    // 		    ParserErrorMessage.FUNCTION_NAMES_ARE_NOT_CONSISTENT
    // 			    .customizeMessage(name.getIdentifier().getName(),
    // 				    checkName.getIdentifier().getName()));

    // 	LexLocation loc = extractLexLocation((CmlLexeme) id);
    // 	AFunctionType ftype = (AFunctionType) functionType;
    // 	PExp functionBody = (PExp) functionBodyObj;
    // 	List<List<PPattern>> args = (List<List<PPattern>>) parameterList;
    // 	AExplicitFunctionDefinition res = new AExplicitFunctionDefinition();
    // 	res.setAccess(getPrivateAccessSpecifier(false, false, loc));
    // 	res.setPass(Pass.DEFS);
    // 	res.setName(name);
    // 	res.setLocation(loc);
    // 	res.setType(ftype);
    // 	res.setBody(functionBody);
    // 	res.setMeasure((LexNameToken) measureExpr);
    // 	res.setParamPatternList(args);
    // 	res.setIsUndefined(false);
    // 	res.setRecursive(false);
    // 	return res;
    // }

	/*
	 * Checking that the two IDENTIFIERS are equivalent
	 */
// <<<<<<< HEAD
 
 // =======

 //    @SuppressWarnings("deprecation")
 //    public PDefinition caseImplicitFunctionDefinition(Object qual, Object id,
 // 	    Object ptypes, Object retvalsObj, Object pre, Object post) {
 // 	AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier) qual;
 // 	LexNameToken name = extractLexNameToken((CmlLexeme) id);
 // 	List<APatternListTypePair> paramPatterns = (List<APatternListTypePair>) ptypes;

 // 	List<PType> paramTypes = new LinkedList<PType>();
 // 	for (APatternListTypePair pp : paramPatterns)
 // 	    paramTypes.add(pp.getType());

 // 	if (pre == null)
 // 	{
 // 		ABooleanConstExp exp =AstFactory.newABooleanConstExp(new LexBooleanToken(true, name.getLocation()));
 // 		pre = exp;
 // 	}
	
 // 	// FIXME This conversion is caused by a flaw in the VDM tree and needs
 // 	// to be fixed at some point.
 // 	// The result is actually a list af patterns but somehow this is not the
 // 	// case in the vdm ast definition.
 // 	List<APatternTypePair> retvals = (List<APatternTypePair>) retvalsObj;

 // 	APatternTypePair retval = null;
 // 	// retval.setType(value)
 // 	if (retvals.size() == 1) {
 // 	    retval = retvals.get(0);
 // // >>>>>>> origin/development
 // 	}
 // 	// if there is more create a tuple pattern to contain them all and only
 // 	// take the type from the first one
 // 	else {
 // 	    ATuplePattern tuple = new ATuplePattern();
 // 	    List<PPattern> plist = new LinkedList<PPattern>();
 // 	    for (APatternTypePair pair : (List<APatternTypePair>) retvals) {
 // 		plist.add(pair.getPattern());
 // 	    }
 // 	    tuple.setPlist(plist);
 // 	    retval = new APatternTypePair(false, tuple);
 // 	}

 // 	APatternTypePair result = retval;// (APatternTypePair)retvals;

 // 	PExp preExp = (PExp) pre;
 // 	PExp postExp = (PExp) post;
 // 	LexLocation location = combineLexLocation(name.getLocation(),
 // 		postExp.getLocation());
 // 	AImplicitFunctionDefinition impFunc = new AImplicitFunctionDefinition(
 // 		location, NameScope.LOCAL, false, access, null,// Pass
 // 		null, paramPatterns, result, preExp, postExp, null/*
 // 								   * LexNameToken
 // 								   * measure
 // 								   */);
 // 	AFunctionType type = AstFactory.newAFunctionType(name.getLocation(),
 // 		true, paramTypes, result.getType());

 // 	type.setResult(result.getType());
 // 	impFunc.setName(name);
 // 	impFunc.setType(type);
 // 	// TODO RWL Set PreDef
	
 // 	return impFunc;
 //    }

    /**
     * 
     * @param qual
     * @param id
     * @param COLON
     * @param opType
     * @param checkId
     * @param paramList
     * @param DEQUALS
     * @param bodyObj
     * @param pre
     * @param post
     * @return
     */
    // public PDefinition caseExplicitOperationDefinition(Object qual, Object id,
    // 	    Object COLON, Object opType, Object checkId, Object paramList,
    // 	    Object DEQUALS, Object bodyObj, Object pre, Object post) {
    // 	LexNameToken name = extractLexNameToken(id);
    // 	LexNameToken checkIdname = extractLexNameToken(checkId);

    // 	if (!name.equals(checkIdname))
    // 	    throw new ParserException(name.getLocation(),
    // 		    ParserErrorMessage.OPERATION_NAMES_ARE_NOT_CONSISTENT
    // 			    .customizeMessage(name.getIdentifier().getName(),
    // 				    checkIdname.getIdentifier().getName()));
    // 	SStatementAction body = null;
    // 	try {
    // 	    body = (SStatementAction) bodyObj;
    // 	} catch (ClassCastException ex) {
    // 	    if (bodyObj instanceof ASequentialCompositionAction)
    // 		throw new ParserException(checkIdname.getLocation(),
    // 			ParserErrorMessage.SEMI_BETWEEN_CLASS_OPERATIONS
    // 				.customizeMessage());
    // 	    else
    // 		throw new ParserException(checkIdname.getLocation(),
    // 			ParserErrorMessage.ACTIONS_INSIDE_CLASS_OPERATION
    // 				.customizeMessage());
    // 	}
    // 	LexLocation loc = extractLexLocation(name.location, body.getLocation());
    // 	AExplicitOperationDefinition res = new AExplicitOperationDefinition();
    // 	res.setLocation(loc);
    // 	res.setAccess((AAccessSpecifierAccessSpecifier) qual);
    // 	res.setName(name);
    // 	res.setType((PType) opType);

    // 	res.setParameterPatterns((List<? extends PPattern>) paramList);
    // 	res.setBody(body);
    // 	res.setPrecondition((PExp) pre);
    // 	res.setPostcondition((PExp) post);
    // 	res.setIsConstructor(false);
    // 	return res;
    // }

    /**
     * Expressions
     */

    // public AApplyExp caseExpressionApply(Object rootExpObj,
    // 	    Object expressionList, Object RPAREN) {
    // 	PExp rootExp = (PExp) rootExpObj;
    // 	LexLocation location = extractLexLocation(rootExp.getLocation(),
    // 		(CmlLexeme) RPAREN);
    // 	List<PExp> exps = (List<PExp>) expressionList;
    // 	AApplyExp res = new AApplyExp(location, rootExp, exps);
    // 	return res;
    // }

    // public APreExp caseExpressionPrecondition(Object PREUNDER, Object expList,
    // 	    Object RPAREN) {
    // 	List<PExp> exprs = (List<PExp>) expList;
    // 	PExp function = exprs.get(0);
    // 	LexLocation loc = extractLexLocation((CmlLexeme) PREUNDER,
    // 		(CmlLexeme) RPAREN);
    // 	return new APreExp(loc, function, exprs.subList(1, exprs.size()));
    // }

    // public AIsOfClassExp caseExpressionIsOfBaseClass(Object ISOFCLASS,
    // 	    Object dottedId, Object exp, Object RPAREN) {
    // 	LexLocation loc = extractLexLocation((CmlLexeme) ISOFCLASS,
    // 		(CmlLexeme) RPAREN);
    // 	LexNameToken name = dottedIdentifierToLexNameToken((List<LexIdentifierToken>) dottedId);
    // 	return new AIsOfClassExp(loc, name, (PExp) exp);
    // }

    public LexNameToken caseMeasure(Object dottedIdentifier) {
	return dottedIdentifierToLexNameToken((List<LexIdentifierToken>) dottedIdentifier);
    }

    private ANameChannelExp ConvertPExpToANameChannelExp(Object exp) {
	ANameChannelExp cn = null;
	/*
	 * 
	 * Commented out by RWL: This methods is not in use anymore.
	 * 
	// This is a single id channel name
	if (exp instanceof AVariableExp) {
	    AVariableExp varExp = (AVariableExp) exp;
	    cn = new ANameChannelExp(varExp.getLocation(), varExp.getName(),
		    new LinkedList<PPattern>());
	} else if (exp instanceof AUnresolvedPathExp) {
	    AUnresolvedPathExp unresolvedExp = (AUnresolvedPathExp) exp;

	    LexNameToken name = new LexNameToken("", unresolvedExp
		    .getIdentifiers().get(0));

	    List<PPattern> patterns = new LinkedList<PPattern>();
	    for (LexIdentifierToken id : unresolvedExp.getIdentifiers()
		    .subList(1, unresolvedExp.getIdentifiers().size())) {
		LexNameToken idName = new LexNameToken("", id);
		patterns.add(new AIdentifierPattern(id.getLocation(),
			new LinkedList<PDefinition>(), false, idName, false));
	    }

	    LexLocation location = extractLexLocation(name.getLocation(),
		    extractLastLexLocation(patterns));
	    cn = new ANameChannelExp(location, name, patterns);
	}
	// This is a channelname with dotted expressions
	else if (exp instanceof Pair<?, ?>) {
	    // This is already converted in a pair of AVariableExp and a list of
	    // ASignalCommunicationParameter
	    // in the expression rule to make the communicationAction easier. So
	    // all we have to do here is to
	    // take out the patterns
	    Pair<AVariableExp, List<ASignalCommunicationParameter>> pair = (Pair<AVariableExp, List<ASignalCommunicationParameter>>) exp;

	    List<PPattern> patterns = new LinkedList<PPattern>();
	    for (ASignalCommunicationParameter s : pair.second) {
		patterns.add(s.getPattern());
	    }

	    LexLocation location = extractLexLocation(pair.first.getLocation(),
		    extractLastLexLocation(pair.second));
	    cn = new ANameChannelExp(location, pair.first.getName(), patterns);
	} else {
	    throw new ParserException(((PExp) exp).getLocation(),
		    ParserErrorMessage.MALFORMED_CHANNEL_EXPRESSION
			    .customizeMessage(exp.toString()));
	}
*/
	return cn;
    }

    // public PExp caseRenameExpressionAComprehensionRenameChannelExp(
    // 	    Object DLSQUARE, Object from, Object to, Object bindList,
    // 	    Object pred, Object DRSQUARE) {
    // 	ARenamePair pair = new ARenamePair(false,
    // 		ConvertPExpToANameChannelExp(from),
    // 		ConvertPExpToANameChannelExp(to));
    // 	return new AComprehensionRenameChannelExp(extractLexLocation(
    // 		(CmlLexeme) DLSQUARE, (CmlLexeme) DRSQUARE), pair,
    // 		(List<? extends PMultipleBind>) bindList, null);
    // }

    private List<LexIdentifierToken> convertExpressionListToLexIdentifierTokenList(
    	    List<PExp> exprs) {
    	List<LexIdentifierToken> ids = new LinkedList<LexIdentifierToken>();
    	for (PExp exp : exprs) {
    	    // If the expression is a single identifer it will be converted into
    	    // a AVariableExp
    	    if (exp instanceof AVariableExp) {
    		AVariableExp var = (AVariableExp) exp;
    		ids.add(var.getName().getIdentifier());
    	    } 
	    // else
    	    // 	throw new ParserException(exp.getLocation(),
    	    // 		ParserErrorMessage.MALFORMED_CHANNEL_SET_EXPRESSION
    	    // 			.customizeMessage(exp.toString()));
    	}
    	return ids;
    }

    // public PExp caseACompChansetSetExp(Object LCURLYBAR, Object chanexp,
    // 	    Object bindList, Object exp, Object BARRCURLY) {
    // 	LexLocation loc = extractLexLocation((CmlLexeme) LCURLYBAR,
    // 		(CmlLexeme) BARRCURLY);
    // 	ANameChannelExp chanNameExp = ConvertPExpToANameChannelExp(chanexp);
    // 	PExp pred = (PExp) exp;
    // 	return new ACompChansetSetExp(loc, chanNameExp,
    // 		(List<PMultipleBind>) bindList, pred);
    // }

    // public PExp caseAEnumChansetSetExp(Object LCURLYBAR, Object list,
    // 	    Object BARRCURLY) {
    // 	LexLocation loc = extractLexLocation((CmlLexeme) LCURLYBAR,
    // 		(CmlLexeme) BARRCURLY);
    // 	List<LexIdentifierToken> ids = convertExpressionListToLexIdentifierTokenList((List<PExp>) list);
    // 	return new AEnumChansetSetExp(loc, ids);
    // }

    // public LexIntegerToken CmlLexemeToLexIntegerToken(Object NUMERAL) {
    // 	CmlLexeme lexeme = (CmlLexeme) NUMERAL;
    // 	LexLocation loc = extractLexLocation(lexeme);
    // 	return new LexIntegerToken(Long.decode(lexeme.getValue()), loc);
    // }

    // public PExp expressionDotHashNumeralToFieldNumberExp(Object tupleObj,
    // 	    Object NUMERAL) {
    // 	PExp tuple = (PExp) tupleObj;
    // 	LexIntegerToken field = CmlLexemeToLexIntegerToken(NUMERAL);
    // 	return AstFactory.newAFieldNumberExp(tuple, field);
    // }

    // public PExp caseExpDotIdentifier(PExp prefix, LexIdentifierToken id) {
    // 	/*
    // 	 * If prefix is a variableExp then this is converted into a
    // 	 * UnresolvedPathExp since we cannot know if its a field select
    // 	 * expression or a name exp and it could also be communication
    // 	 * parameters
    // 	 */
    // 	PExp retExp = null;
    // 	if (prefix instanceof AVariableExp) {

    // 	    AVariableExp varExp = (AVariableExp) prefix;

    // 	    List<LexIdentifierToken> ids = new LinkedList<LexIdentifierToken>();
    // 	    ids.add(varExp.getName().getIdentifier());
    // 	    ids.add(id);
    // 	    AUnresolvedPathExp upe = new AUnresolvedPathExp(extractLexLocation(
    // 		    varExp.getLocation(), id.getLocation()), ids);
    // 	    retExp = upe;
    // 	} else if (prefix instanceof AUnresolvedPathExp) {
    // 	    // if the prefix is a list of ids
    // 	    AUnresolvedPathExp upe = (AUnresolvedPathExp) prefix;
    // 	    upe.getIdentifiers().add(id);
    // 	    upe.setLocation(extractLexLocation(upe.getLocation(),
    // 		    id.getLocation()));
    // 	    retExp = upe;
    // 	} else {
    // 	    // if the prefix is any other expression it must be a field exp
    // 	    retExp = AstFactory.newAFieldExp(prefix, id);
    // 	}

    // 	return retExp;
    // }

    // Communication expressions start

    public ACommunicationAction caseCommunicationAction(Object exp,
	    PAction action) {
    	/*
    	 * Commented out by RWL: Not used by cml.g
    	 * 
    	 
	List<PCommunicationParameter> communicationParameters = new LinkedList<PCommunicationParameter>();
	LexLocation location = null;
	AVariableExp varExp = null;

	if (exp instanceof AVariableExp) {
	    varExp = (AVariableExp) exp;
	    location = varExp.getLocation();
	} else if (exp instanceof AUnresolvedPathExp) {
	    AUnresolvedPathExp aupe = (AUnresolvedPathExp) exp;
	    LexIdentifierToken lit = aupe.getIdentifiers().getFirst();
	    varExp = new AVariableExp(lit.location, new LexNameToken(
		    "", lit), lit.name);
	    for (LexIdentifierToken lit2 : aupe.getIdentifiers().subList(1,
		    aupe.getIdentifiers().size())) {
		AIdentifierPattern aip = new AIdentifierPattern();
		aip.setName(new LexNameToken("", lit2));
		aip.setLocation(lit2.location);
		communicationParameters.add(new ASignalCommunicationParameter(lit2.location, aip));
	    }
	    location = extractLexLocation(varExp.getLocation(),
		    extractLastLexLocation(communicationParameters));

	}

	else {
	    Pair<AVariableExp, List<PCommunicationParameter>> com = (Pair<AVariableExp, List<PCommunicationParameter>>) exp;
	    varExp = com.first;
	    communicationParameters = com.second;
	    location = extractLexLocation(varExp.getLocation(),
		    extractLastLexLocation(communicationParameters));
	}

	return new ACommunicationAction(location, varExp.getName()
		.getIdentifier(), communicationParameters, action);
		*/
    	return null;
    }

    // private <T extends PCommunicationParameter> Pair<AVariableExp, List<PCommunicationParameter>> communicationParamHelper(
    // 	    Object exp, PPattern pattern, T param) {
    // 	Pair<AVariableExp, List<PCommunicationParameter>> ret = null;

    // 	/*
    // 	 * 
    // 	 * Commented out by RWL: Not used by cml.g
    // 	 * 
    // 	// if this is true, then this is the first com. param.
    // 	if (exp instanceof AVariableExp) {
    // 	    AVariableExp varExp = (AVariableExp) exp;
    // 	    List<PCommunicationParameter> coms = new LinkedList<PCommunicationParameter>();
    // 	    param.setLocation(extractLexLocation(varExp.getLocation(),
    // 		    pattern.getLocation()));
    // 	    param.setPattern(pattern);
    // 	    coms.add(param);
    // 	    ret = new Pair<AVariableExp, List<PCommunicationParameter>>(varExp,
    // 		    coms);
    // 	} else if (exp instanceof Pair<?, ?>) {
    // 	    ret = (Pair<AVariableExp, List<PCommunicationParameter>>) exp;
    // 	    param.setLocation(extractLexLocation(ret.first.getLocation(),
    // 		    pattern.getLocation()));
    // 	    param.setPattern(pattern);
    // 	    ret.second.add(param);
    // 	} else if (exp instanceof AUnresolvedPathExp) {
    // 	    AUnresolvedPathExp aupe = (AUnresolvedPathExp) exp;
    // 	    LexIdentifierToken lit = aupe.getIdentifiers().getFirst();
    // 	    List<PCommunicationParameter> list = new LinkedList<PCommunicationParameter>();
    // 	    AVariableExp ave = new AVariableExp(lit.location, new LexNameToken(
    // 		    "", lit), lit.name);
    // 	    for (LexIdentifierToken lit2 : aupe.getIdentifiers().subList(1,
    // 		    aupe.getIdentifiers().size())) {
    // 		AIdentifierPattern aip = new AIdentifierPattern();
    // 		aip.setName(new LexNameToken("", lit2));
    // 		aip.setLocation(lit2.location);
    // 		list.add(new ASignalCommunicationParameter(lit2.location, aip));
    // 	    }
    // 	    ret = new Pair<AVariableExp, List<PCommunicationParameter>>(ave,
    // 		    list);
    // 	}

    // 	else
    // 	    throw new ParserException(((PExp) exp).getLocation(),
    // 		    "A Communication construct must begin with an identifier");
    // 	*/
    // 	return null;
    // }

    // /**
    //  * This returns the channel identifier and the list of communicationsparams.
    //  * 
    //  * @param exp
    //  * @param patternObj
    //  * @return
    //  */
    // public Pair<AVariableExp, List<PCommunicationParameter>> caseExpBangMatchValue(
    // 	    Object exp, Object patternObj) {
    // 	PPattern pattern = (PPattern) patternObj;
    // 	return communicationParamHelper(exp, pattern,
    // 		new AWriteCommunicationParameter());
    // }

    // /**
    //  * This returns the channel identifier and the list of communicationsparams.
    //  * 
    //  * @param exp
    //  * @param lexeme
    //  * @return
    //  */
    // public Pair<AVariableExp, List<PCommunicationParameter>> caseExpBangIdentifier(
    // 	    Object exp, Object lexeme) {
    // 	LexIdentifierToken id = extractLexIdentifierToken(lexeme);
    // 	Pair<AVariableExp, List<PCommunicationParameter>> ret = null;

    // 	// create a identifier pattern
    // 	LexNameToken name = new LexNameToken("", id);
    // 	PPattern pattern = new AIdentifierPattern(id.getLocation(), null,
    // 		false, name, false);

    // 	return communicationParamHelper(exp, pattern,
    // 		new AWriteCommunicationParameter());
    // }

    // public Pair<AVariableExp, List<PCommunicationParameter>> caseExpDotMatchValue(
    // 	    Object exp, Object patternObj) {
    // 	if (exp instanceof AVariableExp || exp instanceof Pair<?, ?>) {
    // 	    PPattern pattern = (PPattern) patternObj;
    // 	    return communicationParamHelper(exp, pattern,
    // 		    new ASignalCommunicationParameter());
    // 	} else
    // 	    // TODO what can this be other than communication? aha it can a
    // 	    // channel expression
    // 	    throw new ParserException(
    // 		    "I don't really now what to do here right now");

    // }

    // public Pair<AVariableExp, List<PCommunicationParameter>> caseExpQuestionPattern(
    // 	    Object exp, Object patternObj) {
    // 	PPattern pattern = (PPattern) patternObj;

    // 	// pattern must be indentifier pattern, tuple pattern or record pattern
    // 	// according to the parameter rule at page 35

    // 	if (pattern instanceof AIdentifierPattern
    // 		|| pattern instanceof ATuplePattern
    // 		|| pattern instanceof ARecordPattern)
    // 	    return communicationParamHelper(exp, pattern,
    // 		    new AReadCommunicationParameter());
    // 	else
    // 	    throw new ParserException(
    // 		    "communication paramters must be either identifier, tuple or record pattern");
    // }

    // public Pair<AVariableExp, List<PCommunicationParameter>> caseExpQuestionSetBind(
    // 	    Object exp, Object setBindObj) {
    // 	ASetBind setBind = (ASetBind) setBindObj;
    // 	AReadCommunicationParameter rcp = new AReadCommunicationParameter();
    // 	rcp.setExpression(setBind.getSet());

    // 	return communicationParamHelper(exp, setBind.getPattern(), rcp);
    // }

    // Communication expressions end

    /**
     * Type definition helpers
     */

    // public ATypeDefinition caseRecordTypeDefinition(Object qualifier,
    // 	    Object id, Object fieldList) {
    // 	AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier) qualifier;
    // 	LexNameToken name = extractLexNameToken((CmlLexeme) id);
    // 	List<AFieldField> fields = (List<AFieldField>) fieldList;
    // 	// FIXME fields should have a location but they don't!!, so records will
    // 	// not have the correct location
    // 	// LexLocation loc = extractLexLocation(name.getLocation(),
    // 	// extractLastLexLocation(fields));
    // 	LexLocation loc = name.getLocation();
    // 	ARecordInvariantType recType = AstFactory.newARecordInvariantType(name, fields);
    // 	ATypeDefinition result = AstFactory.newATypeDefinition( name, recType, null, null);
    // 	result.setLocation(loc);
    // 	result.setAccess(access);
    // 	return result;	
    // }

    /**
     * Types
     */

    @SuppressWarnings({ "deprecation", "unchecked" })
    public ANamedInvariantType caseDottedIdentifierToNamedType(
	    Object dottedIdentifier) {

	/*
	 * Convert the dottedIdentifier into a LexNameToken? Old code from the
	 * IDENTIFIER DOT IDENTIFIER production below.
	 */
	ANamedInvariantType type = new ANamedInvariantType();
	List<LexIdentifierToken> ids = (List<LexIdentifierToken>) dottedIdentifier;
	LexNameToken name = dottedIdentifierToLexNameToken(ids);
	type.setResolved(false);
	type.setLocation(name.getLocation());
	type.setName(name);
	type.setOpaque(false);
	AUnresolvedType t = new AUnresolvedType(name.getLocation(), false,
		new LinkedList<PDefinition>(), name);
	type.setType(t);
	return type;
    }

}
