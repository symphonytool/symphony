package eu.compassresearch.ide.collaboration.datamodel;

import org.eclipse.ecf.core.identity.ID;

public class User extends Model {
	
	private static final long serialVersionUID = 5634470790158709661L;
	
	private boolean hasJoinedGroup;
	private boolean hasDeclinedGroup;
	private ID userID;
	
	public User(ID userID, Model parent) {
		super(userID.getName(), parent);
		
		this.userID = userID;
	}
	
	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public String toString()
	{
		return name + "(" + userID.getName() + ")";
	}

	@Override
	public CollaborationProject getCollaborationProject()
	{
		return getParent().getCollaborationProject();
	}

	public boolean hasJoinedGroup()
	{
		return hasJoinedGroup;
	}

	public void acceptedToJoinGroup(boolean accepted)
	{
		if(accepted){
			this.hasJoinedGroup = accepted;
		} else {
			this.hasDeclinedGroup = true;
		}
		
		fireObjectUpdatedEvent(this);
	}

	public boolean hasDeclinedGroup()
	{
		return hasDeclinedGroup;
	}

	public ID getUserID()
	{
		return userID;
	}
}
