package eu.compassresearch.core.interpreter.api;

public class InterpreterError {

	private final String message;
	private final Throwable throwable;
	
	private InterpreterError(){
		message = null;
		throwable = null;
	}
	
	public InterpreterError(String message)
	{
		this.message = message;
		this.throwable = null;
	}
	
	public InterpreterError(String message, Throwable throwable)
	{
		this.message = message;
		this.throwable = throwable;
	}
	
	public String getErrorMessage()
	{
		return message;
	}
	
	public Throwable getThrowable()
	{
		return throwable;
	}
	
	@Override
	public String toString() {
		return message + " : " + throwable.toString();
	}
	
}
