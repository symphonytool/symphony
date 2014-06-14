package eu.compassresearch.ide.collaboration.communication.messages;

import org.eclipse.ecf.core.identity.ID;

public class CollaborationRequest extends BaseMessage
{
	private static final long serialVersionUID = -6377515365389121903L;

	String title;
	String msg;
	
	public CollaborationRequest(ID sender, String projectID, String title, String message)
	{
		super(sender, projectID);
		
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
