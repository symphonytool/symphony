package eu.compassresearch.core.interpreter.cosim.communication;

import eu.compassresearch.core.interpreter.debug.messaging.Message;
import eu.compassresearch.core.interpreter.debug.messaging.MessageType;

/**
 * Message used by a client to reply to a {@link FinishedRequestMessage}
 * 
 * @author kel
 */
public class FinishedReplyMessage extends Message
{

	private String process;
	private Boolean finished;

	/**
	 * default for message parsing
	 */
	public FinishedReplyMessage()
	{
	}

	public FinishedReplyMessage(String process, Boolean finished)
	{
		this.process = process;
		this.finished = finished;
	}

	@Override
	public MessageType getType()
	{
		return MessageType.RESPONSE;
	}

	@Override
	public String getKey()
	{
		return null;
	}

	@Override
	public String toString()
	{
		return "IsFinished reply: " + process + " " + (finished ? "yes" : "no");
	}

	public String getProcess()
	{
		return this.process;
	}

	public boolean isFinished()
	{
		return this.finished;
	}

}
