package eu.compassresearch.core.interpreter.cosim.communication;

import java.util.List;
import java.util.Vector;

import eu.compassresearch.core.interpreter.debug.messaging.Message;
import eu.compassresearch.core.interpreter.debug.messaging.MessageType;

/**
 * Message used by a client to register which processes it implements
 * 
 * @author kel
 */
public class ProvidesImplementationMessage extends Message
{

	List<String> processes = new Vector<String>();

	/**
	 * Default constructor for message passing
	 */
	public ProvidesImplementationMessage()
	{
	}

	public ProvidesImplementationMessage(List<String> processes)
	{
		this.processes.addAll(processes);
	}

	public List<String> getProcesses()
	{
		return this.processes;
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
		return "Provides implementation for: " + processes;
	}

}
