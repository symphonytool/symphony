package eu.compassresearch.core.interpreter.debug;

import org.overture.ast.intf.lex.ILexLocation;

public class InterpreterErrorDTO {

	private final String message;
	private final ILexLocation location;
	
	/*
	 * Dummy constructor for serialization
	 */
	@SuppressWarnings("unused")
	private InterpreterErrorDTO(){
		message = null;
		location = null;
	}
	
	public InterpreterErrorDTO(String message)
	{
		this.message = message;
		location = null;
	}
	
	public InterpreterErrorDTO(String message, ILexLocation location)
	{
		this.message = message;
		this.location = location;
	}
	
	public String getErrorMessage()
	{
		return message;
	}
	
	@Override
	public String toString() {
		return message + " at " + location;
	}

	public ILexLocation getLocation() {
		return location;
	}
	
}
