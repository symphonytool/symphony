package eu.compassresearch.core.interpreter.api;

public class NoProcessFoundException extends InterpreterException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1208333338422627416L;

	public NoProcessFoundException()
	{
		
	}

	public NoProcessFoundException(String message)
	{
		super(message);
	}
	
	public NoProcessFoundException(String message,Throwable cause)
	{
		super(message,cause);
	}
}
