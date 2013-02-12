package eu.compassresearch.core.interpreter.api;

public class InterpreterError {

	private final String message;
	
	public InterpreterError(String message)
	{
		this.message = message;
	}
	
	public String getErrorMessage()
	{
		return message;
	}
	
}
