package eu.compassresearch.core.parser;

import org.overture.ast.lex.LexLocation;

public class ParserException extends RuntimeException {

	private LexLocation location;
	private String message;
	
	public ParserException(LexLocation location, String message)
	{
		this.setLocation(location);
		this.message = message;
	}
	
	public ParserException(String message)
	{
		this.setLocation(null);
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		
		return "Parser error at " + getLocation() + " : " + message;
	}

	public LexLocation getLocation() {
		return location;
	}

	public void setLocation(LexLocation location) {
		this.location = location;
	}
	
	
//	public ParserException()
//	{
//		super();
//	}
//	
//	public ParserException(String message)
//	{
//		super(message);
//	}
//	
//	public ParserException(String message, Throwable cause)
//	{
//		super(message,cause);
//	}
}
