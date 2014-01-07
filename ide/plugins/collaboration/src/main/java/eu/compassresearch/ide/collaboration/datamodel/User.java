package eu.compassresearch.ide.collaboration.datamodel;

public class User extends Model {
	
	private static final long serialVersionUID = 5634470790158709661L;
	
	private boolean hasJoinedGroup;
	private boolean hasDeclinedGroup;
	
	public User(String name, Model parent) {
		super(name, parent);
	}
	
	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		
	}
	
	@Override
	public String getName()
	{
		return name;
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
		
		fireObjectAddedEvent(this);
	}

	public boolean hasDeclinedGroup()
	{
		return hasDeclinedGroup;
	}
}
