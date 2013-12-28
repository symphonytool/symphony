package eu.compassresearch.ide.collaboration.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollaborationGroup extends Model {

	private static final long serialVersionUID = 1083165101733581295L;
	
	protected Map<String,User> collaborators;
	
	public CollaborationGroup() {
		collaborators = new HashMap<String, User>();
		this.name = "Collaborators";
	}

	public void addCollaborator(User user) {
		collaborators.put(user.getName(), user);
		user.setParent(this);
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
	
	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		//visitor.visitContracts(this, passAlongArgument);
	}

	public boolean hasCollaborator(String name)
	{
		return collaborators.containsKey(name);
	}
}
