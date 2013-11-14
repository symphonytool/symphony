package eu.compassresearch.ide.collaboration.treeview.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollaborationGroup extends Model {
	protected Map<String,User> collaborators;
	
	public CollaborationGroup() {
		collaborators = new HashMap<String, User>();
		this.name = "Collaborators";
	}

	public void addCollaborator(User user) {
		collaborators.put(user.getName(), user);
		user.setParent(this);
		user.listener = this.listener;
		fireAdd(user);
	}
	
	public void removeCollaborator(User user) {
		collaborators.remove(user);
		user.addListener(NullDeltaListener.getSoleInstance());
		fireRemove(user);
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

	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		//visitor.visitContracts(this, passAlongArgument);
	}
}
