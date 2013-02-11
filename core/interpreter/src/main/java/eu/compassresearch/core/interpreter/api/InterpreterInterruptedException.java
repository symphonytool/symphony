package eu.compassresearch.core.interpreter.api;

import org.overture.ast.lex.LexLocation;

public class InterpreterInterruptedException extends InterpreterException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1097652754521612681L;

	public InterpreterInterruptedException()
	{
	}

	public InterpreterInterruptedException(String message)
	{
		super(message);
	}
	
	public InterpreterInterruptedException(LexLocation location, String message)
	{
		super(location,message);
	}
	
	public InterpreterInterruptedException(String message,Throwable cause)
	{
		super(message,cause);
	}
}
