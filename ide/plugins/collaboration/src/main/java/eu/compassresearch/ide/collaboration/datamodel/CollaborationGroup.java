package eu.compassresearch.ide.collaboration.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.ecf.core.identity.ID;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.communication.ConnectionManager;

public class CollaborationGroup extends Model {

	private static final long serialVersionUID = 1083165101733581295L;
	
	protected Map<String,User> collaborators;
	
	public CollaborationGroup(Model parent) {
		super("Collaborators", parent);
		collaborators = new HashMap<String, User>();
	}
	
	public void addCollaborator(ID userId, boolean joined) {
		User user = new User(userId, this);
		
		if(joined) {
			user.acceptedToJoinGroup(true);
		} 
		collaborators.put(user.getName(), user);
		user.listener = this.listener;
		fireObjectUpdatedEvent(user);
	}
	
	public void removeCollaborator(User user) {
		collaborators.remove(user);
		user.removeListener(listener);
		fireObjectRemovedEvent(user);
	}
	
	public User getUser(String name) {	
		return collaborators.get(name);
	}	
	
	//get all collaborators, joined and invited
	public List<User> getCollaborators() {
		return new ArrayList<User>(collaborators.values());
	}
	
	//get collaborators that have accepted to join the collaboration. 
	public List<User> getJoinedCollaborators() {
		
		ID connectedUser = Activator.getDefault().getConnectionManager().getConnectedUser();
		String connectedUserName = connectedUser.getName();
		
		List<User> joinedUsers = new ArrayList<>();
		for (User usr : collaborators.values())
		{
			String userName = usr.getUserID().getName();
			if(usr.hasJoinedGroup() && !userName.equalsIgnoreCase(connectedUserName)){
				joinedUsers.add(usr);
			}
		}
		
		return joinedUsers;
	}

	public int size() {
		return collaborators.size();
	}

	@Override
	public void addListener(IModelEventListener listener)
	{
		for (User u : collaborators.values())
		{
			u.addListener(listener);
		}
		
		super.addListener(listener);
	}
	
	@Override
	public void removeListener(IModelEventListener listener)
	{
		for (User u : collaborators.values())
		{
			u.removeListener(listener);
		}
		
		super.removeListener(listener);
	}
	
	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		//visitor.visitContracts(this, passAlongArgument);
	}

	public boolean hasCollaborator(String name)
	{
		return collaborators.containsKey(name);
	}

	@Override
	public CollaborationProject getCollaborationProject()
	{
		return getParent().getCollaborationProject();
	}
	
	@Override
	public String toString()
	{
		int size = collaborators.size();
		
		return super.toString() + (size > 0 ? " (" + collaborators.size() + ")" : "");
	}
}
