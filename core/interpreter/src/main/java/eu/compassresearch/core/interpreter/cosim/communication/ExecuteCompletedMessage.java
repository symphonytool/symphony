package eu.compassresearch.core.interpreter.cosim.communication;

import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.debug.messaging.JsonMessage;

/**
 * Message reply to signal that the client have executed a {@link CmlTransition}
 * 
 * @author kel
 */
public class ExecuteCompletedMessage implements JsonMessage
{

	/**
	 * default for message parsing
	 */
	public ExecuteCompletedMessage()
	{
	}


	@Override
	public String toString()
	{
		return "Executed";
	}


}
