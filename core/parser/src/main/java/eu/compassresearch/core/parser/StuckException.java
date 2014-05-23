package eu.compassresearch.core.parser;

import org.antlr.runtime.IntStream;
import org.antlr.runtime.RecognitionException;

public class StuckException extends RecognitionException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String msg;

	public StuckException(String msg)
	{
		this.msg = msg;
	}

	public StuckException(IntStream input, String msg)
	{
		super(input);
		this.msg = msg;
	}

	public String getMessage()
	{
		return msg;
	}
}
