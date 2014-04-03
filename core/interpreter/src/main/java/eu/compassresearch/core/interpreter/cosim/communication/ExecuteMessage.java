package eu.compassresearch.core.interpreter.cosim.communication;

import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.debug.messaging.JsonMessage;

/**
 * Message to signal that a client should execute a {@link CmlTransition}
 * 
 * @author kel
 */
public class ExecuteMessage implements JsonMessage
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
	public String toString()
	{
		return "Execute: " + transition;
	}

	public CmlTransition getTransition()
	{
		return this.transition;
	}

}
