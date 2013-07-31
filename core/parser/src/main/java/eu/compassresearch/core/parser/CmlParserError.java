package eu.compassresearch.core.parser;

import java.io.File;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.lex.LexLocation;

public class CmlParserError
{
	public final String message;
	public final RecognitionException error;
	
	final int line;
	final int charPositionInLine;
	final int startIndex;
	final int stopIndex;

	public CmlParserError(String message, RecognitionException e, int line, int charPositionInLine, int startIndex, int stopIndex)
	{
		this.message = message;
		this.error = e;
		this.line = line;
		this.charPositionInLine = charPositionInLine;
		this.startIndex = startIndex;
		this.stopIndex=stopIndex;
	}
	
	public CmlParserError(String message, RecognitionException e, Token token)
	{
		this(message,e,token.getLine(),token.getCharPositionInLine(),token.getTokenIndex(),token.getTokenIndex()+token.getText().length());
	}
	
	public ILexLocation getLocation(File sourceFile)
	{
		 return new LexLocation(sourceFile, "", line, charPositionInLine, line, charPositionInLine, startIndex, stopIndex);
	}
}
