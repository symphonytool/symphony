package eu.compassresearch.core.interpreter.cosim.communication;

import java.util.List;
import java.util.Vector;

import eu.compassresearch.core.interpreter.debug.messaging.JsonMessage;

/**
 * Message used by a client to register which processes it implements
 * 
 * @author kel
 */
public class RegisterSubSystemMessage implements JsonMessage
{

	List<String> processes = new Vector<String>();
	String version = "1.0.0";

	/**
	 * Default constructor for message passing
	 */
	public RegisterSubSystemMessage()
	{
	}

	public RegisterSubSystemMessage(List<String> processes)
	{
		this.processes.addAll(processes);
	}

	public RegisterSubSystemMessage(List<String> processes, String version)
	{
		this.processes.addAll(processes);
		this.version = version;
	}

	public List<String> getProcesses()
	{
		return this.processes;
	}
	
	public String getVersion()
	{
		return this.version;
	}

	@Override
	public String toString()
	{
		return "Register subsystem for: " + processes+" using protocol version: "+version;
	}

}
