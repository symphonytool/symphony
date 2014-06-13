package eu.compassresearch.ide.collaboration.communication.messages;

import org.eclipse.ecf.core.identity.ID;

public class CollaborationStatusMessage extends BaseMessage
{
	private static final long serialVersionUID = 8728653370727412843L;
	
	boolean join;
	
	public CollaborationStatusMessage(ID sender, String projectID, boolean joining)
	{
		super(sender, projectID);
		join = joining;
	}
	
	public boolean isJoining(){
		return join;
	}
}
