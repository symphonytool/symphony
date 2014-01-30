package eu.compassresearch.core.interpreter.cosim.communication;

import eu.compassresearch.core.interpreter.debug.messaging.JsonMessage;

/**
 * Message used to inspect a process located on the client use of a co-simulation
 * 
 * @author kel
 */
public class InspectMessage implements JsonMessage
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
	public String toString()
	{
		return "Inspect: " + process;
	}

	public String getProcess()
	{
		return this.process;
	}

}
