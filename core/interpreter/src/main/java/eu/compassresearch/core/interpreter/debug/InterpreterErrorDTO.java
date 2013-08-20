package eu.compassresearch.core.interpreter.debug;

public class InterpreterErrorDTO {

	private final String message;
	private final Throwable throwable;
	
	/*
	 * Dummy constructor for serialization
	 */
	private InterpreterErrorDTO(){
		message = null;
		throwable = null;
	}
	
	public InterpreterErrorDTO(String message)
	{
		this.message = message;
		this.throwable = null;
	}
	
	public InterpreterErrorDTO(String message, Throwable throwable)
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
