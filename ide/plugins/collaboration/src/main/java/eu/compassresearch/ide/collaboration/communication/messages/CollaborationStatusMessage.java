package eu.compassresearch.ide.collaboration.communication.messages;

import org.eclipse.ecf.core.user.IUser;

public class CollaborationStatusMessage extends BaseMessage
{
	private static final long serialVersionUID = 8728653370727412843L;
	
	boolean join;
	
	public CollaborationStatusMessage(IUser sender, IUser receiver, boolean joining)
	{
		super(sender, receiver);
		join = joining;
	}
	
	public boolean isJoining(){
		return join;
	}
}
