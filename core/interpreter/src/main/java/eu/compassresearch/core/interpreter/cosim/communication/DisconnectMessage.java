package eu.compassresearch.core.interpreter.cosim.communication;

import eu.compassresearch.core.interpreter.debug.messaging.Message;
import eu.compassresearch.core.interpreter.debug.messaging.MessageType;

/**
 * Message to signal that a client should disconnect
 * 
 * @author kel
 */
public class DisconnectMessage extends Message
{

	/**
	 * default for message parsing
	 */
	public DisconnectMessage()
	{
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
		return "Disconnect";
	}

}
