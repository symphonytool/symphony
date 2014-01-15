package eu.compassresearch.core.interpreter.cosim.communication;

import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.debug.messaging.Message;
import eu.compassresearch.core.interpreter.debug.messaging.MessageType;

/**
 * Message to signal that a client should execute a {@link CmlTransition}
 * 
 * @author kel
 */
public class ExecuteMessage extends Message
{

	private CmlTransition transition;

	/**
	 * default for message parsing
	 */
	public ExecuteMessage()
	{
	}

	public ExecuteMessage(CmlTransition transition)
	{
		this.transition = transition;
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
		return "Execute: " + transition;
	}

	public CmlTransition getTransition()
	{
		return this.transition;
	}

}
