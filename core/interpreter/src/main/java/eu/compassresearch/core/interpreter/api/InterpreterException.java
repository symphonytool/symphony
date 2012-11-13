package eu.compassresearch.core.interpreter.api;

public class InterpreterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8020387717433469239L;

	public InterpreterException()
	{
		
	}

	public InterpreterException(String message)
	{
		super(message);
	}
	
	public InterpreterException(String message,Throwable cause)
	{
		super(message,cause);
	}
}
