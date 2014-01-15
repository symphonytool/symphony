package eu.compassresearch.core.interpreter.cosim.communication;

import eu.compassresearch.core.interpreter.debug.messaging.Message;
import eu.compassresearch.core.interpreter.debug.messaging.MessageType;

/**
 * Message used to ask a client about whether it is finished or not
 * 
 * @author kel
 */
public class FinishedRequestMessage extends Message
{

	private String process;

	/**
	 * default for message parsing
	 */
	public FinishedRequestMessage()
	{
	}

	public FinishedRequestMessage(String process)
	{
		this.process = process;
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
		return "Is finished: " + process;
	}

	public String getProcess()
	{
		return this.process;
	}

}
