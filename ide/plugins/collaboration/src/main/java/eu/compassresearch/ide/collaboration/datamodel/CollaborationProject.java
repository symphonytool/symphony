package eu.compassresearch.ide.collaboration.datamodel;

import java.util.UUID;


public class CollaborationProject extends Model
{
	private static final long serialVersionUID = 8119280860561533717L;
	
	Contracts contracts;
	CollaborationGroup collaboratorGroup;
	
	private String workspaceName; 
	private String title; 
	private String description; 
	private final String uniqueID;
	
	public CollaborationProject(String workspaceName, String title, String description, String uniqueID)
	{
		this.title = title;
		this.description = description;
		this.name = "Collaboration Project: " + title;
		this.workspaceName = workspaceName;
		this.uniqueID = uniqueID;
		
		contracts = new Contracts();
		contracts.setParent(this);
		collaboratorGroup = new CollaborationGroup();
		collaboratorGroup.setParent(this);
	}
	
	public CollaborationProject(String workspaceName, String title, String description)
	{
		this(workspaceName,title, description, UUID.randomUUID().toString());
	}
	
	public void addContract(Contract contract) {
		contracts.addContract(contract);
		contract.setParent(this);
		fireObjectAddedEvent(contract);
	}
	
	protected void removeContracts(Contract contract) {
		contracts.removeContract(contract);
		contract.removeListener(listener);
		fireObjectRemovedEvent(contract);
	}
	
	public void addCollaborator(User usr) {
		collaboratorGroup.addCollaborator(usr);
		fireObjectAddedEvent(collaboratorGroup);
	}
	
	protected void removeCollaboratorGroup(CollaborationGroup collabGroup) {
		//collaboratorGroups.remove(collabGroup);
		collabGroup.removeListener(listener);
		fireObjectRemovedEvent(collabGroup);
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
		return super.toString();
	}
	
	@Override
	public void accept(IModelVisitor visitor, Object passAlongArgument)
	{
	}

	public String getProjectWorkspaceName()
	{
		return workspaceName;
	}

	public String getTitle()
	{
		return title;
	}

	public String getDescription()
	{
		return description;
	}

	public String getUniqueID()
	{
		return uniqueID;
	}
}
