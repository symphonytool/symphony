package eu.compassresearch.ide.collaboration.communication.messages;

import org.eclipse.ecf.core.identity.ID;

public class SimulationRequestMessage extends BaseMessage
{
	private static final long serialVersionUID = 1999661751331824137L;
	private String process;
	private String host;
	
	public SimulationRequestMessage(ID sender, String collabProjectID, String process, String host)
	{
		super(sender, collabProjectID);
		this.process = process;
		this.host = host;
	}

	public String getProcess()
	{
		return process;
	}

	public String getHost()
	{
		return host;
	}
}
