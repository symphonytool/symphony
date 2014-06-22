package eu.compassresearch.ide.collaboration.communication.messages;

import org.eclipse.ecf.core.identity.ID;

public class SimulationStartMessage extends BaseMessage
{
	private static final long serialVersionUID = 3640987554078286465L;
	private String process;

	public SimulationStartMessage(ID sender, String collabProjectID, String process)
	{
		super(sender, collabProjectID);
		this.process = process;
	}

	public String getProcess()
	{
		return process;
	}
}
