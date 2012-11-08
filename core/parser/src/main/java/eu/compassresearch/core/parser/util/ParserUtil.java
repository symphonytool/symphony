package eu.compassresearch.core.parser.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.overture.ast.definitions.APrivateAccess;
import org.overture.ast.definitions.APublicAccess;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.lex.LexToken;
import org.overture.ast.lex.VDMToken;
import org.overture.ast.node.tokens.TAsync;
import org.overture.ast.node.tokens.TStatic;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.ARecordPattern;
import org.overture.ast.patterns.ASetBind;
import org.overture.ast.patterns.ATuplePattern;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.AAccessSpecifierAccessSpecifier;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.ast.actions.ASignalCommunicationParameter;
import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.expressions.AUnresolvedPathExp;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.lexer.CmlLexeme;
import eu.compassresearch.core.lexer.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.parser.ParserException;

public class ParserUtil {

	PSource currentSource = null;

	public ParserUtil(PSource currentSource)
	{
		this.currentSource = currentSource;
	}
	public static char convertEscapeToChar(String escape)
	{
		if (escape.startsWith("\\")) {
			switch(escape.charAt(1)) {
			case 'n':  return '\n';
			case '\\': return '\\';
			case 'r': return '\r';
			case 't': return '\t';
			case 'f': return '\f';
			case 'e': return (char)0x001B;
			case 'a': return (char)0x0007;
			case 'x': return (char)new BigInteger(escape.substring(2), 16).intValue();
			case 'u': return (char)new BigInteger(escape.substring(2), 16).intValue();
			case 'c': return (char)(escape.charAt(2) - 'A' + 1);
			case '0': return (char)new BigInteger(escape.substring(2), 8).intValue();
			case '\"' : return '\"';
			case '\'': return '\'';
			default:
				throw new RuntimeException("Illegal escape sequence: "+escape);
			}
		}
		return escape.charAt(0);
	}


	/* FIXME
	 * needs to throw an error if the name is multipart
	 */
	public List<LexIdentifierToken> convertNameListToIdentifierList(List<LexNameToken> nameList) {
		List<LexIdentifierToken> out = new Vector<LexIdentifierToken>();
		for (LexNameToken name : nameList) {
			out.add(extractLexIdentifierToken(name));
		}
		return out;
	}

	public LexNameToken extractNameFromUNDERNAMEToken(CmlLexeme mkUnder)
	{
		String nameString = mkUnder.getValue().substring(3);

		LexNameToken name = null;
		if(nameString.matches(".+['`''.'].+")){
			String[] tokens = nameString.split("['`''.']");
			name = new LexNameToken(tokens[0],
					tokens[1],
					extractLexLocation(mkUnder),
					false,
					true);
		}
		else
			name = new LexNameToken("Default",
					nameString,
					extractLexLocation(mkUnder),
					false,
					true);
		return name;
	}

	public AAccessSpecifierAccessSpecifier getDefaultAccessSpecifier(boolean isStatic, boolean isAsync, LexLocation loc)
	{
		/* return new AAccessSpecifierAccessSpecifier(new APublicAccess(), */
		/*                             (isStatic ? new TStatic() : null), */
		/*                             (isAsync ? new TAsync() : null),loc); */

		return new AAccessSpecifierAccessSpecifier(new APublicAccess(),
				(isStatic ? new TStatic() : null),
				(isAsync ? new TAsync() : null));

	}

	public AAccessSpecifierAccessSpecifier getPrivateAccessSpecifier(boolean isStatic, boolean isAsync, LexLocation loc)
	{
		/* return new AAccessSpecifierAccessSpecifier(new APrivateAccess(), */
		/*                             (isStatic ? new TStatic() : null), */
		/*                             (isAsync ? new TAsync() : null),loc); */
		return new AAccessSpecifierAccessSpecifier(new APrivateAccess(),
				(isStatic ? new TStatic() : null),
				(isAsync ? new TAsync() : null));
	}

	public LexToken extractLexToken(CmlLexeme lexeme)
	{
		LexLocation loc = extractLexLocation(lexeme);
		VDMToken tok = null;
		for(VDMToken t : VDMToken.values())
		{
			String tokenDisplay = t.toString();
			if (tokenDisplay != null && tokenDisplay.equals(lexeme.getValue())) { tok = t; break; }
		}
		if (tok == null) throw new RuntimeException("Cannot find VDM token for "+lexeme.getValue());
		return new LexToken(loc, tok);
	}

	public LexLocation extractLexLocation(CmlLexeme lexeme)
	{
		return new LexLocation(currentSource.toString(), "Default",
				lexeme.getStartPos().line,
				lexeme.getStartPos().column,
				lexeme.getEndPos().line,
				lexeme.getEndPos().column,
				lexeme.getStartPos().offset,
				lexeme.getEndPos().offset);
	}

	public LexLocation extractLexLocation(CmlLexeme start, CmlLexeme end)
	{
		return new LexLocation(currentSource.toString(), "Default",
				start.getStartPos().line,
				start.getStartPos().column,
				end.getEndPos().line,
				end.getEndPos().column,
				start.getStartPos().offset,
				end.getEndPos().offset);
	}

	public LexLocation extractLexLocation(CmlLexeme start, LexLocation end)
	{

		return new LexLocation(currentSource.toString(), "Default",
				start.getStartPos().line, start.getStartPos().column,
				end.endLine,
				end.endPos,
				start.getStartPos().offset,
				end.endOffset);
	}

	public LexLocation extractLexLocation(LexLocation start, CmlLexeme end)
	{

		return new LexLocation(currentSource.toString(), "Default",
				start.startLine, start.startPos,
				end.getEndPos().line,
				end.getEndPos().column,
				start.startOffset,
				end.getEndPos().offset);
	}

	public LexLocation extractLexLocation(LexLocation start, LexLocation end)
	{
		return new LexLocation(currentSource.toString(), "Default",
				start.startLine, start.startPos,
				end.endLine,
				end.endPos,
				start.startOffset,
				end.endOffset);
	}

	public LexLocation combineLexLocation(LexLocation start, LexLocation end)
	{
		return extractLexLocation(start,end);
	}

	public LexLocation extractLastLexLocation(List<?> fields)
	{
		try {
			Object o = fields.get(0);
			Class<?> clz = o.getClass();

			Method locMethod = clz.getMethod("getLocation", new Class<?>[] {});

			LexLocation candidate = (LexLocation)locMethod.invoke(o, null);
			for(Object p : fields) {
				LexLocation pLoc = (LexLocation)locMethod.invoke(o, null);
				if (pLoc.endOffset > candidate.endOffset)
					candidate = pLoc;
			}
			return candidate;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public LexLocation extractFirstLexLocation(List<?> fields)
	{
		try {
			Object o = fields.get(0);
			Class<?> clz = o.getClass();

			Method locMethod = clz.getMethod("getLocation", new Class<?>[] {});
			LexLocation candidate = (LexLocation)locMethod.invoke(o, null);
			for(Object p : fields) {
				LexLocation pLoc = (LexLocation)locMethod.invoke(o, null);
				if (pLoc.startOffset < candidate.startOffset)
					candidate = pLoc;
			}
			return candidate;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static CmlParser newParserFromSource(PSource doc) throws FileNotFoundException
	{
		if (doc instanceof AFileSource) {
			AFileSource fs = (AFileSource)doc;
			File f= fs.getFile();
			FileReader reader = new FileReader(f);
			CmlLexer lexer = new CmlLexer(reader);
			CmlParser parser = new CmlParser(lexer);
			parser.setDocument(fs);
			return parser;
		}

		if (doc instanceof AInputStreamSource) {
			AInputStreamSource is = (AInputStreamSource)doc;
			InputStreamReader in = new InputStreamReader(is.getStream());
			CmlLexer lexer = new CmlLexer(in);
			CmlParser parser = new CmlParser(lexer);
			parser.setDocument(is);
			return parser;
		}
		return null;
	}

	public< T extends PPattern> LexLocation extractLexLeftMostFromPatterns(List<T> ptrns)
	{
		LexLocation candidate = ptrns.get(0).getLocation();
		for(PPattern p : ptrns) {
			if (p.getLocation().endOffset > candidate.endOffset)
				candidate = p.getLocation();
		}
		return candidate;
	}

	public List<LexIdentifierToken> convertPathListToIdentifiers(List<LexNameToken> pathList)
	{
		List<LexIdentifierToken> identifiers = new LinkedList<LexIdentifierToken>();
		for (LexNameToken name : pathList){
			if(name.explicit == true || !name.module.equals("Default"))
				throw new RuntimeException("A single expression declaration can only contain identifiers");
			identifiers.add(0,name.getIdentifier());
		}

		return identifiers;
	}

	public LexNameToken extractLexNameToken(CmlLexeme lexeme)
	{
		return new LexNameToken("Default", lexeme.getValue(), extractLexLocation(lexeme), false, true);
	}

	public LexNameToken extractLexNameToken(CmlLexeme lexeme, boolean old)
	{
		return new LexNameToken("Default", lexeme.getValue(), extractLexLocation(lexeme), old, true);
	}

	public LexNameToken extractLexNameToken(Object obj)
	{
		CmlLexeme lexeme = (CmlLexeme)obj;
		return new LexNameToken("Default",lexeme.getValue(), extractLexLocation(lexeme),false, true);
	}

	public LexIdentifierToken extractLexIdentifierToken(Object obj)
	{
		return extractLexIdentifierToken((CmlLexeme)obj);
	}

	public LexIdentifierToken extractLexIdentifierToken(CmlLexeme lexeme)
	{
		return new LexIdentifierToken(lexeme.getValue(), false, extractLexLocation(lexeme));
	}

	public LexIdentifierToken extractLexIdentifierToken(CmlLexeme lexeme, boolean old)
	{
		return new LexIdentifierToken(lexeme.getValue(), old, extractLexLocation(lexeme));
	}

	public LexIdentifierToken extractLexIdentifierToken(LexNameToken name)
	{
		return new LexIdentifierToken(name.getName(), false, name.getLocation());
	}
	
	/**
	 * Expression helpers
	 */
	
	public PExp caseExpDotIdentifier(PExp prefix, LexIdentifierToken id)
	{
		/*If prefix is a variableExp then this is converted into a UnresolvedPathExp
	    since we cannot know if its a field select expression or a name exp
	    and it could also be communication parameters
		 */
		PExp retExp = null;
		if(prefix instanceof AVariableExp ){
			
			AVariableExp varExp = (AVariableExp)prefix; 
			
			List<LexIdentifierToken> ids = new LinkedList<LexIdentifierToken>();
			ids.add(varExp.getName().getIdentifier());
			ids.add(id);
			AUnresolvedPathExp upe = new AUnresolvedPathExp(extractLexLocation(varExp.getLocation(),id.getLocation()),
															ids);
			retExp = upe;
		}
		else if(prefix instanceof AUnresolvedPathExp){
			//if the prefix is a list of ids
			AUnresolvedPathExp upe = (AUnresolvedPathExp)prefix;
			upe.getIdentifiers().add(id);
			upe.setLocation(extractLexLocation(upe.getLocation(),id.getLocation()));
			retExp = upe;
		}
		else{
			//if the prefix is any other expression it must be a field exp
			retExp = AstFactory.newAFieldExp(prefix, id); 
		}
		
		return retExp;
	}
	
	//Communication expressions start
	
	public ACommunicationAction caseCommunicationAction(Object exp,PAction action)
	{
		List<PCommunicationParameter> communicationParameters = new LinkedList<PCommunicationParameter>();
		LexLocation location = null;
		AVariableExp varExp = null;
		
		if(exp instanceof AVariableExp)
		{
			varExp = (AVariableExp)exp;
			location = varExp.getLocation();
		}
		//else if
		else
		{
			Pair<AVariableExp,List<PCommunicationParameter>> com = (Pair<AVariableExp,List<PCommunicationParameter>>)exp;
			varExp = com.first;
			communicationParameters = com.second;
			location = extractLexLocation(varExp.getLocation(),extractLastLexLocation(communicationParameters));
		}
		
		return new ACommunicationAction(location,varExp.getName(), communicationParameters, action);
	}
			
	private <T extends PCommunicationParameter > Pair<AVariableExp,List<PCommunicationParameter>> communicationParamHelper(Object exp, PPattern pattern, T param)
	{
		Pair<AVariableExp,List<PCommunicationParameter>> ret = null;
				
		//if this is true, then this is the first com. param.
		if(exp instanceof AVariableExp)
		{
			AVariableExp varExp = (AVariableExp)exp;
			List<PCommunicationParameter> coms = new LinkedList<PCommunicationParameter>();
			param.setLocation(extractLexLocation(varExp.getLocation(),
	          		pattern.getLocation()));
			param.setPattern(pattern);
			coms.add(param);
			ret = new Pair<AVariableExp, List<PCommunicationParameter>>(varExp, coms);
		}
		else if(exp instanceof Pair<?,?>)
		{
			ret = (Pair<AVariableExp,List<PCommunicationParameter>>)exp;
			param.setLocation(extractLexLocation(ret.first.getLocation(),
					pattern.getLocation()));
			param.setPattern(pattern);
			ret.second.add(param);
		}
		else
			throw new ParserException("A Communication construct must begin with an identifier");
		
		return ret;
	}
	
	
	/**
	 * This returns the channel identifier and the list of communicationsparams.
	 * @param exp
	 * @param patternObj
	 * @return
	 */
	public Pair<AVariableExp,List<PCommunicationParameter>> caseExpBangMatchValue(Object exp, Object patternObj)
	{
		PPattern pattern = (PPattern)patternObj;
		return communicationParamHelper(exp,pattern,new AWriteCommunicationParameter());
	}
	
	/**
	 * This returns the channel identifier and the list of communicationsparams.
	 * @param exp
	 * @param lexeme
	 * @return
	 */
	public Pair<AVariableExp,List<PCommunicationParameter>> caseExpBangIdentifier(Object exp, Object lexeme)
	{
		LexIdentifierToken id = extractLexIdentifierToken(lexeme);
		Pair<AVariableExp,List<PCommunicationParameter>> ret = null;
		
		//create a identifier pattern
		LexNameToken name = new LexNameToken("",id);
		PPattern pattern = new AIdentifierPattern(id.getLocation(),null,false,name,false);
		
		return communicationParamHelper(exp, pattern,new AWriteCommunicationParameter());
	}
	
	public Pair<AVariableExp,List<PCommunicationParameter>> caseExpDotMatchValue(Object exp, Object patternObj)
	{
		if(exp instanceof AVariableExp || exp instanceof Pair<?,?>)
		{
			PPattern pattern = (PPattern)patternObj;
			return communicationParamHelper(exp,pattern,new ASignalCommunicationParameter());
		}
		else
			//TODO what can this be other than communication?
			throw new ParserException("I don't really now what to do here right now");
		

	}
	
	public Pair<AVariableExp,List<PCommunicationParameter>> caseExpQuestionPattern(Object exp, Object patternObj)
	{
		PPattern pattern = (PPattern)patternObj;
		
		//pattern must be indentifier pattern, tuple pattern or record pattern 
		//according to the parameter rule at page 35
		
		if(pattern instanceof AIdentifierPattern  ||
				pattern instanceof ATuplePattern ||
				pattern instanceof ARecordPattern )
			return communicationParamHelper(exp,pattern,new AReadCommunicationParameter());
		else
			throw new ParserException("communication paramters must be either identifier, tuple or record pattern"); 
	}
	
	public Pair<AVariableExp,List<PCommunicationParameter>> caseExpQuestionSetBind(Object exp, Object setBindObj)
	{
		ASetBind setBind = (ASetBind)setBindObj;
		AReadCommunicationParameter rcp = new AReadCommunicationParameter();
		rcp.setExpression(setBind.getSet());
				
		return communicationParamHelper(exp,setBind.getPattern(),rcp);
	}
	
	//Communication expressions end
	
}
