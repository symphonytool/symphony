package eu.compassresearch.core.interpreter.api;

import org.overture.ast.lex.LexLocation;

public class InterpreterRuntimeException extends RuntimeException
{

	final LexLocation location;

	/**
	 * 
	 */
	private static final long serialVersionUID = 8020387717433469239L;

	public InterpreterRuntimeException()
	{
		location = null;
	}

	public InterpreterRuntimeException(String message)
	{
		super(message);
		location = null;
	}

	public InterpreterRuntimeException(LexLocation location, String message)
	{
		super(message);
		this.location = location;
	}

	public InterpreterRuntimeException(String message, Throwable cause)
	{
		super(message, cause);
		this.location = null;
	}

	public InterpreterRuntimeException(Throwable cause)
	{
		super(cause);
		this.location = null;
	}

	public InterpreterRuntimeException(LexLocation location, Throwable cause)
	{
		super(cause);
		this.location = location;
	}
}
