package eu.compassresearch.core.interpreter.scheduler;

public class ProcessException extends Exception {

	public ProcessException(){}
	
	public ProcessException(String message)
	{
		super(message);
	}
	
	public ProcessException(String message, Throwable cause)
	{
		super(message,cause);
	}
}
