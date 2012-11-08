package eu.compassresearch.core.parser.util;

public class ParserException extends RuntimeException {

	public ParserException()
	{
		super();
	}
	
	public ParserException(String message)
	{
		super(message);
	}
	
	public ParserException(String message, Throwable cause)
	{
		super(message,cause);
	}
}
