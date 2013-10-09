package eu.compassresearch.core.analysis.modelchecker.visitors;

import org.overture.ast.lex.LexLocation;

public class ModelcheckerRuntimeException extends RuntimeException {

	final LexLocation location;
	
	public ModelcheckerRuntimeException()
	{
		location = null;
	}

	public ModelcheckerRuntimeException(String message)
	{
		super(message);
		location = null;
	}
	
	public ModelcheckerRuntimeException(LexLocation location, String message)
	{
		super(message);
		this.location = location;
	}
	
	public ModelcheckerRuntimeException(String message,Throwable cause)
	{
		super(message,cause);
		this.location = null;
	}
	
	public ModelcheckerRuntimeException(Throwable cause)
	{
		super(cause);
		this.location = null;
	}
	
	public ModelcheckerRuntimeException(LexLocation location, Throwable cause)
	{
		super(cause);
		this.location = location;
	}
}
