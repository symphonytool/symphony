package eu.compassresearch.ide.collaboration.treeview.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TreeRoot extends Model {
	protected List contracts;
	protected List collaboratorGroups;
	
	public TreeRoot() {
		contracts = new ArrayList();
		collaboratorGroups = new ArrayList();
	}
	
	public TreeRoot(String name) {
		this();
		this.name = name;
	}
	
	public void addContracts(Contracts contract) {
		contracts.add(contract);
		contract.parent = this;
		fireAdd(contract);
	}
	
	protected void removeContracts(Contracts contract) {
		contracts.remove(contract);
		contract.addListener(NullDeltaListener.getSoleInstance());
		fireRemove(contract);
	}
	
	public void addCollaboratorGroup(CollaborationGroup collabGroup) {
		collaboratorGroups.add(collabGroup);
		collabGroup.parent = this;
		fireAdd(collabGroup);
	}
	
	protected void removeCollaboratorGroup(CollaborationGroup collabGroup) {
		collaboratorGroups.remove(collabGroup);
		collabGroup.addListener(NullDeltaListener.getSoleInstance());
		fireRemove(collabGroup);
	}
	
	public List getContracts() {
		return contracts;
	}
	
	public List getCollaboratorGroups() {
		return collaboratorGroups;
	}

	public int size() {
		return contracts.size() + collaboratorGroups.size();
	}

	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		//visitor.visitContracts(this, passAlongArgument);
	}
	
	@Override
	public void addListener(IDeltaListener listener) {
		
		for (Iterator iterator = contracts.iterator(); iterator.hasNext();) {
			Contracts contracts = (Contracts) iterator.next();
			contracts.addListener(listener);
		}
		
		for (Iterator iterator = collaboratorGroups.iterator(); iterator.hasNext();) {
			CollaborationGroup collabGrp = (CollaborationGroup) iterator.next();
			collabGrp.addListener(listener);
		}
		
		super.addListener(listener);
	}

}
