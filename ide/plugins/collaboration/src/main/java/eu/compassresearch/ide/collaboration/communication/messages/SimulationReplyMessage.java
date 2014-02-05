package eu.compassresearch.ide.collaboration.communication.messages;

import org.eclipse.ecf.core.identity.ID;

public class SimulationReplyMessage extends BaseMessage
{
	private static final long serialVersionUID = 7323277832042155789L;
	private String process;
	private boolean accepted;
	
	public SimulationReplyMessage(ID sender, String collabProjectID, String process, boolean accepted)
	{
		super(sender, collabProjectID);
		// TODO Auto-generated constructor stub
		this.process = process;
		this.accepted = accepted;
	}

	public String getProcess()
	{
		return process;
	}

	public boolean isAccepted()
	{
		return accepted;
	}

}
