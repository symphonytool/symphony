package eu.compassresearch.core.interpreter.cosim.communication;

import eu.compassresearch.core.interpreter.debug.messaging.Message;
import eu.compassresearch.core.interpreter.debug.messaging.MessageType;

/**
 * Message used to inspect a process located on the client use of a co-simulation
 * 
 * @author kel
 */
public class InspectMessage extends Message
{

	private String process;

	/**
	 * default for message parsing
	 */
	public InspectMessage()
	{
	}

	public InspectMessage(String process)
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
		return "Inspect: " + process;
	}

	public String getProcess()
	{
		return this.process;
	}

}
