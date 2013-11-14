package eu.compassresearch.ide.collaboration.messages;

import org.eclipse.ecf.core.user.IUser;

public class CollaborationRequest extends BaseMessage
{
	private static final long serialVersionUID = -6377515365389121903L;

	String title;
	String msg;
	
	public CollaborationRequest(IUser sender, IUser receiver, String title, String message)
	{
		super(sender,receiver);
		
		this.title = title;
		this.msg = message;
	}
	
	public String getTitle()
	{
		return title;
	}

	public String getMessage()
	{
		return msg;
	}
}
