package eu.compassresearch.ide.collaboration.messages;

import org.eclipse.ecf.core.identity.ID;

public class CollaborationStatusMessage extends BaseMessage
{
	private static final long serialVersionUID = 8728653370727412843L;
	
	boolean join;
	
	public CollaborationStatusMessage(ID sender, ID receiver, boolean joining)
	{
		super(sender, receiver);
		join = joining;
	}
	
	public boolean isJoining(){
		return join;
	}
}
