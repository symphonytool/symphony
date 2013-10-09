package eu.compassresearch.ide.collaboration.treeview.model;

import java.util.ArrayList;
import java.util.List;

public class CollaborationGroup extends Model {
	protected List collaborators;
	
	public CollaborationGroup() {
		collaborators = new ArrayList();
		this.name = "Collaborators";
		collaborators.add(new User("(You)"));
	}

	public void addCollaborator(User user) {
		collaborators.add(user);
		user.parent = this;
		fireAdd(user);
	}
	
	protected void removeCollaborator(User user) {
		collaborators.remove(user);
		user.addListener(NullDeltaListener.getSoleInstance());
		fireRemove(user);
	}
	
	public List getCollaborators() {
		return collaborators;
	}

	public int size() {
		return collaborators.size();
	}

	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		//visitor.visitContracts(this, passAlongArgument);
	}
}
