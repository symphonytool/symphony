package eu.compassresearch.ide.collaboration.communication.messages;

import org.eclipse.ecf.core.identity.ID;

public class LeftCollaborationMessage extends BaseMessage
{
	public LeftCollaborationMessage(ID sender, String collabProjectID)
	{
		super(sender, collabProjectID);
	}

}
