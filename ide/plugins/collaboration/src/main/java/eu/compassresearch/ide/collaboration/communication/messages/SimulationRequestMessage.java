package eu.compassresearch.ide.collaboration.communication.messages;

import org.eclipse.ecf.core.identity.ID;

public class SimulationRequestMessage extends BaseMessage
{
	private static final long serialVersionUID = 1999661751331824137L;
	private String process;
	
	public SimulationRequestMessage(ID sender, String collabProjectID, String process)
	{
		super(sender, collabProjectID);
		this.process = process;
	}

	public String getProcess()
	{
		return process;
	}
	
}
