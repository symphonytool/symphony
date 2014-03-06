package eu.compassresearch.core.interpreter.cosim.communication;

import eu.compassresearch.core.interpreter.debug.messaging.JsonMessage;

/**
 * Message to signal that a client should disconnect
 * 
 * @author kel
 */
public class AbortMessage implements JsonMessage
{

	public int getErrorCode()
	{
		return errorCode;
	}

	public String getMessage()
	{
		return message;
	}

	private int errorCode;
	private String message;
	
	/**
	 * default for message parsing
	 */
	protected AbortMessage()
	{
	}

	public AbortMessage(int errorCode, String message)
	{
		this.errorCode = errorCode;
		this.message = message;
	}

	@Override
	public String toString()
	{
		return "Abort " + errorCode + " " + message;
	}

}
