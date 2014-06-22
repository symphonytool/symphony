package eu.compassresearch.ide.collaboration.communication.messages;

import java.util.List;

import org.eclipse.ecf.core.identity.ID;

public class CollaborationGroupUpdateMessage extends BaseMessage
{
	private static final long serialVersionUID = 4646759443672052156L;
	private List<ID> collaborationGroupMembers;
	
	public CollaborationGroupUpdateMessage(ID sender, String collabProjectID, List<ID> collaborationGroupMembers)
	{
		super(sender, collabProjectID);
		this.collaborationGroupMembers = collaborationGroupMembers;
		
		
	}

	public List<ID> getCollaborationGroupMembers()
	{
		return collaborationGroupMembers;
	}
}
