package eu.compassresearch.core.interpreter.cosim.communication;

import eu.compassresearch.core.interpreter.debug.messaging.JsonMessage;

/**
 * Message to signal that a client should disconnect
 * 
 * @author kel
 */
public class DisconnectMessage implements JsonMessage
{

	/**
	 * default for message parsing
	 */
	public DisconnectMessage()
	{
	}

	@Override
	public String toString()
	{
		return "Disconnect";
	}

}
