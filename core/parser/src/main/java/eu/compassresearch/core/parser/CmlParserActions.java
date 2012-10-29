package eu.compassresearch.core.parser;

import java.io.File;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.overture.ast.definitions.APrivateAccess;
import org.overture.ast.definitions.APublicAccess;
import org.overture.ast.expressions.AForAllExp;
import org.overture.ast.expressions.AMkBasicExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.lex.LexToken;
import org.overture.ast.lex.VDMToken;
import org.overture.ast.node.INode;
import org.overture.ast.node.tokens.TAsync;
import org.overture.ast.node.tokens.TStatic;
import org.overture.ast.patterns.PMultipleBind;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.AAccessSpecifierAccessSpecifier;
import org.overture.ast.types.ATokenBasicType;
import org.overture.ast.util.ClonableFile;

import eu.compassresearch.ast.preview.DotGraphVisitor;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.lexer.CmlLexeme;
import eu.compassresearch.core.lexer.CmlLexer;

/**
 * 2012-10-28 18:00:
 * 
 * The generated parser with its actions is exceeding the 64K code side of an
 * allowed class in Java
 * 
 * Therefore parser actions are extracted to this class.
 * 
 * 
 * 
 * @author rwl
 * 
 */
public class CmlParserActions {

	private PSource currentSource;

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

	public CmlParserActions(PSource source) {
		this.currentSource = source;
	}

	// *************************
	// *** PRIVATE OPERATIONS ***
	// *************************

	/*
	 * FIXME needs to throw an error if the name is multipart
	 */
	List<LexIdentifierToken> convertNameListToIdentifierList(
			List<LexNameToken> nameList) {
		List<LexIdentifierToken> out = new Vector<LexIdentifierToken>();
		for (LexNameToken name : nameList) {
			out.add(extractLexIdentifierToken(name));
		}
		return out;
	}

	LexNameToken extractNameFromUNDERNAMEToken(CmlLexeme mkUnder) {
		String nameString = mkUnder.getValue().substring(3);

		LexNameToken name = null;
		if (nameString.matches(".+['`''.'].+")) {
			String[] tokens = nameString.split("['`''.']");
			name = new LexNameToken(tokens[0], tokens[1],
					extractLexLocation(mkUnder), false, true);
		} else
			name = new LexNameToken("Default", nameString,
					extractLexLocation(mkUnder), false, true);
		return name;
	}

	AAccessSpecifierAccessSpecifier getDefaultAccessSpecifier(boolean isStatic,
			boolean isAsync, LexLocation loc) {
		/* return new AAccessSpecifierAccessSpecifier(new APublicAccess(), */
		/* (isStatic ? new TStatic() : null), */
		/* (isAsync ? new TAsync() : null),loc); */

		return new AAccessSpecifierAccessSpecifier(new APublicAccess(),
				(isStatic ? new TStatic() : null), (isAsync ? new TAsync()
						: null));

	}

	AAccessSpecifierAccessSpecifier getPrivateAccessSpecifier(boolean isStatic,
			boolean isAsync, LexLocation loc) {
		/* return new AAccessSpecifierAccessSpecifier(new APrivateAccess(), */
		/* (isStatic ? new TStatic() : null), */
		/* (isAsync ? new TAsync() : null),loc); */
		return new AAccessSpecifierAccessSpecifier(new APrivateAccess(),
				(isStatic ? new TStatic() : null), (isAsync ? new TAsync()
						: null));
	}

	LexToken extractLexToken(CmlLexeme lexeme) {
		LexLocation loc = extractLexLocation(lexeme);
		VDMToken tok = null;
		for (VDMToken t : VDMToken.values()) {
			String tokenDisplay = t.toString();
			if (tokenDisplay != null && tokenDisplay.equals(lexeme.getValue())) {
				tok = t;
				break;
			}
		}
		if (tok == null)
			throw new RuntimeException("Cannot find VDM token for "
					+ lexeme.getValue());
		return new LexToken(loc, tok);
	}

	LexLocation extractLexLocation(CmlLexeme lexeme) {
		return new LexLocation(currentSource.toString(), "Default",
				lexeme.getStartPos().line, lexeme.getStartPos().column,
				lexeme.getEndPos().line, lexeme.getEndPos().column,
				lexeme.getStartPos().offset, lexeme.getEndPos().offset);
	}

	LexLocation extractLexLocation(CmlLexeme start, CmlLexeme end) {
		return new LexLocation(currentSource.toString(), "Default",
				start.getStartPos().line, start.getStartPos().column,
				end.getEndPos().line, end.getEndPos().column,
				start.getStartPos().offset, end.getEndPos().offset);
	}

	LexLocation extractLexLocation(CmlLexeme start, LexLocation end) {

		return new LexLocation(currentSource.toString(), "Default",
				start.getStartPos().line, start.getStartPos().column,
				end.endLine, end.endPos, start.getStartPos().offset,
				end.endOffset);
	}

	LexLocation extractLexLocation(LexLocation start, CmlLexeme end) {

		return new LexLocation(currentSource.toString(), "Default",
				start.startLine, start.startPos, end.getEndPos().line,
				end.getEndPos().column, start.startOffset,
				end.getEndPos().offset);
	}

	LexLocation extractLexLocation(LexLocation start, LexLocation end) {
		return new LexLocation(currentSource.toString(), "Default",
				start.startLine, start.startPos, end.endLine, end.endPos,
				start.startOffset, end.endOffset);
	}

	LexLocation combineLexLocation(LexLocation start, LexLocation end) {
		return extractLexLocation(start, end);
	}

	LexLocation extractLastLexLocation(List<?> fields) {
		try {
			Object o = fields.get(0);
			Class<?> clz = o.getClass();

			Method locMethod = clz.getMethod("getLocation", new Class<?>[] {});

			LexLocation candidate = (LexLocation) locMethod.invoke(o, null);
			for (Object p : fields) {
				LexLocation pLoc = (LexLocation) locMethod.invoke(o, null);
				if (pLoc.endOffset > candidate.endOffset)
					candidate = pLoc;
			}
			return candidate;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	LexLocation extractFirstLexLocation(List<?> fields) {
		try {
			Object o = fields.get(0);
			Class<?> clz = o.getClass();

			Method locMethod = clz.getMethod("getLocation", new Class<?>[] {});
			LexLocation candidate = (LexLocation) locMethod.invoke(o, null);
			for (Object p : fields) {
				LexLocation pLoc = (LexLocation) locMethod.invoke(o, null);
				if (pLoc.startOffset < candidate.startOffset)
					candidate = pLoc;
			}
			return candidate;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	<T extends PPattern> LexLocation extractLexLeftMostFromPatterns(
			List<T> ptrns) {
		LexLocation candidate = ptrns.get(0).getLocation();
		for (PPattern p : ptrns) {
			if (p.getLocation().endOffset > candidate.endOffset)
				candidate = p.getLocation();
		}
		return candidate;
	}

	List<LexIdentifierToken> convertPathListToIdentifiers(
			List<LexNameToken> pathList) {
		List<LexIdentifierToken> identifiers = new LinkedList<LexIdentifierToken>();
		for (LexNameToken name : pathList) {
			if (name.explicit == true || !name.module.equals("Default"))
				throw new RuntimeException(
						"A single expression declaration can only contain identifiers");
			identifiers.add(0, name.getIdentifier());
		}

		return identifiers;
	}

	LexNameToken extractLexNameToken(CmlLexeme lexeme) {
		return new LexNameToken("Default", lexeme.getValue(),
				extractLexLocation(lexeme), false, true);
	}

	LexNameToken extractLexNameToken(Object obj) {
		CmlLexeme lexeme = (CmlLexeme) obj;
		return new LexNameToken("Default", lexeme.getValue(),
				extractLexLocation(lexeme), false, true);
	}

	LexIdentifierToken extractLexIdentifierToken(Object obj) {
		return extractLexIdentifierToken((CmlLexeme) obj);
	}

	LexIdentifierToken extractLexIdentifierToken(CmlLexeme lexeme) {
		return new LexIdentifierToken(lexeme.getValue(), false,
				extractLexLocation(lexeme));
	}

	LexIdentifierToken extractLexIdentifierToken(LexNameToken name) {
		return new LexIdentifierToken(name.getName(), false, name.getLocation());
	}

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			System.out.println("Usage : java CmlParser <inputfile>");
		} else {
			CmlLexer scanner = null;
			try {
				String filePath = args[0];
				ClonableFile file = new ClonableFile(filePath);
				AFileSource fileSource = new AFileSource();
				fileSource.setName(file.getName());
				scanner = new CmlLexer(new java.io.FileReader(file));
				CmlParser cmlParser = new CmlParser(scanner);
				cmlParser.setDocument(fileSource);
				// cmlParser.setDebugLevel(1);

				// do {
				// System.out.println(scanner.yylex());
				boolean result = cmlParser.parse();
				if (result) {
					System.out.println("parsed!");

					DotGraphVisitor dgv = new DotGraphVisitor();
					INode node = cmlParser.getDocument();

					node.apply(dgv, null);

					File dotFile = new File("generatedAST.gv");
					java.io.FileWriter fw = new java.io.FileWriter(dotFile);
					fw.write(dgv.getResultString());
					fw.close();

					// System.out.println(dgv.getResultString());

				} else {
					System.out.println("Not parsed!");
					System.exit(-1);
				}

				// } while (!scanner.zzAtEOF);

			} catch (java.io.FileNotFoundException e) {
				System.out.println("File not found : \"" + args[0] + "\"");
				System.exit(-2);
			} catch (java.io.IOException e) {
				System.out
						.println("IO error scanning file \"" + args[0] + "\"");
				System.out.println(e);
				System.exit(-3);
			} catch (Exception e) {
				System.out.println("Unexpected exception:");
				e.printStackTrace();
				System.exit(-4);
			}
		}
	}

	public Object mkundertokenLParenExpressionRParen(Object mkUnderToken,
			Object expression) {
		LexLocation loc = extractLexLocation((CmlLexeme) mkUnderToken);
		return new AMkBasicExp(new ATokenBasicType(loc, true), loc,
				(PExp) expression);
	}

	public Object forAllBindListAtExpressionFORALL(Object _forAll,
			Object _binds, Object _at, Object _expression) {
		CmlLexeme forAll = (CmlLexeme) _forAll;
		List<PMultipleBind> binds = (List<PMultipleBind>) _binds;
		CmlLexeme at = (CmlLexeme) _at;
		PExp expression = (PExp) _expression;
		LexLocation loc = combineLexLocation(extractLexLocation(forAll),
				expression.getLocation());

		AForAllExp forAllExp = new AForAllExp(loc, binds, expression);
		return forAllExp;
	}
}
