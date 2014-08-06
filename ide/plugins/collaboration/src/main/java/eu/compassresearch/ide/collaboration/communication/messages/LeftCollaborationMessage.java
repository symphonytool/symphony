package eu.compassresearch.ide.collaboration.communication.messages;

import org.eclipse.ecf.core.identity.ID;

public class LeftCollaborationMessage extends BaseMessage
{
	private static final long serialVersionUID = 6136617078011072335L;

	public LeftCollaborationMessage(ID sender, String collabProjectID)
	{
		super(sender, collabProjectID);
	}

}
