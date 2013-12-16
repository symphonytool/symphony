package eu.compassresearch.core.parser;

import java.io.File;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.CommonToken;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.lex.LexLocation;

public class CmlParserError
{
	public final String message;
	public final String sourceName;
	public final RecognitionException error;
	
	final int line;
	final int charPositionInLine;
	final int startIndex;
	final int stopIndex;

	public CmlParserError(String message, RecognitionException e,String sourceName, int line, int charPositionInLine, int startIndex, int stopIndex)
	{
		this.message = message;
		this.sourceName = sourceName;
		this.error = e;
		this.line = line;
		this.charPositionInLine = charPositionInLine;
		this.startIndex = startIndex;
		this.stopIndex=stopIndex;
	}
	
	public CmlParserError(String message, RecognitionException e,String sourceName, CommonToken token)
	{
		this(message, e, sourceName, token.getLine(), token.getCharPositionInLine(), token.getStartIndex(), token.getStopIndex());
	}
	
	public ILexLocation getLocation(File sourceFile)
	{
		 return new LexLocation(sourceFile, "", line, charPositionInLine, line, charPositionInLine, startIndex, stopIndex);
	}
	
	public ILexLocation getLocation()
	{
		 return new LexLocation(new File(sourceName), "", line, charPositionInLine, line, charPositionInLine, startIndex, stopIndex);
	}
	
	@Override
	public String toString()
	{
		return getErrorHeader(error)+" "+message;
	}
	
	public String getErrorHeader(RecognitionException e) {
		StringBuffer out = new StringBuffer();

		if (sourceName!=null) {
			out.append(sourceName);
			out.append(" ");
		}
				
		out.append("line ");
		out.append(line);
		out.append(":");
		out.append(charPositionInLine);
		// out.append(" offset ");
		// out.append(startIndex);
		// out.append("-");
		// out.append(stopIndex);

		return out.toString();
	}
}
