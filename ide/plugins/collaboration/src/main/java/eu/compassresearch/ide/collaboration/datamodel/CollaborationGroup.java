package eu.compassresearch.ide.collaboration.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollaborationGroup extends Model {

	private static final long serialVersionUID = 1083165101733581295L;
	
	protected Map<String,User> collaborators;
	
	public CollaborationGroup(Model parent) {
		super("Collaborators", parent);
		collaborators = new HashMap<String, User>();
	}

	public void addCollaborator(String username) {
		User user = new User(username, this);
		collaborators.put(user.getName(), user);
		user.listener = this.listener;
		fireObjectAddedEvent(user);
	}
	
	public void removeCollaborator(User user) {
		collaborators.remove(user);
		user.removeListener(listener);
		fireObjectRemovedEvent(user);
	}
	
	public User getUser(String name) {	
		return collaborators.get(name);
	}	
	
	public List<User> getCollaborators() {
		return new ArrayList<User>(collaborators.values());
	}

	public int size() {
		return collaborators.size();
	}

	@Override
	public void addListener(IDeltaListener listener)
	{
		for (User u : collaborators.values())
		{
			u.addListener(listener);
		}
		
		super.addListener(listener);
	}
	
	@Override
	public void removeListener(IDeltaListener listener)
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
}
