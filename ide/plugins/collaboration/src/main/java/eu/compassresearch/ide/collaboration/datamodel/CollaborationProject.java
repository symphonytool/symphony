package eu.compassresearch.ide.collaboration.datamodel;


public class CollaborationProject extends Model
{
	Contracts contracts;
	CollaborationGroup collaboratorGroup;
	
	protected String workspaceName; 
	
	public CollaborationProject(String workspaceName)
	{
		this.name = "Collaboration Project";
		this.workspaceName = workspaceName;
		
		contracts = new Contracts();
		contracts.setParent(this);
		collaboratorGroup = new CollaborationGroup();
		collaboratorGroup.setParent(this);
	}
	
	public void addContract(Contract contract) {
		contracts.addContract(contract);
		contract.setParent(this);
		fireAdd(contract);
	}
	
	protected void removeContracts(Contract contract) {
		contracts.removeContract(contract);
		contract.addListener(NullDeltaListener.getSoleInstance());
		fireRemove(contract);
	}
	
	public void addCollaborator(User usr) {
		collaboratorGroup.addCollaborator(usr);
		fireAdd(collaboratorGroup);
	}
	
	protected void removeCollaboratorGroup(CollaborationGroup collabGroup) {
		//collaboratorGroups.remove(collabGroup);
		collabGroup.addListener(NullDeltaListener.getSoleInstance());
		fireRemove(collabGroup);
	}
	
	public Contracts getContracts() {
		return contracts;
	}
	
	public CollaborationGroup getCollaboratorGroup() {
		return collaboratorGroup;
	}

	public int size() {
		return contracts.size() + collaboratorGroup.size();
	}

	@Override
	public void addListener(IDeltaListener listener) {
		
		contracts.addListener(listener);
		collaboratorGroup.addListener(listener);
		
		super.addListener(listener);
	}
	
	@Override
	public String toString()
	{
		return super.toString() + " on " + workspaceName;
	}
	
	@Override
	public void accept(IModelVisitor visitor, Object passAlongArgument)
	{
	}
}
