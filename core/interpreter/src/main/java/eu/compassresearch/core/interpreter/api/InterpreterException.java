package eu.compassresearch.core.interpreter.api;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexLocation;

public class InterpreterException extends AnalysisException {

	final LexLocation location;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8020387717433469239L;

	public InterpreterException()
	{
		location = null;
	}

	public InterpreterException(String message)
	{
		super(message);
		location = null;
	}
	
	public InterpreterException(LexLocation location, String message)
	{
		super(message);
		this.location = location;
	}
	
	public InterpreterException(String message,Throwable cause)
	{
		super(message,cause);
		this.location = null;
	}
}
