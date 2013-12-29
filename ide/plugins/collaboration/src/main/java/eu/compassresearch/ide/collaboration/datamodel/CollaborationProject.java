package eu.compassresearch.ide.collaboration.datamodel;

import java.io.IOException;
import java.util.UUID;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;


public class CollaborationProject extends Model
{
	private static final long serialVersionUID = 8119280860561533717L;
	
	Configurations configurations;
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
		
		configurations = new Configurations();
		configurations.setParent(this);
		collaboratorGroup = new CollaborationGroup();
		collaboratorGroup.setParent(this);
	}
	
	public CollaborationProject(String workspaceName, String title, String description)
	{
		this(workspaceName,title, description, UUID.randomUUID().toString());
	}
	
	private void addConfiguration(Configuration configuration) {
		configurations.addConfiguration(configuration);
		configuration.setParent(this);
		fireObjectAddedEvent(configuration);
	}
	
	protected void removeConfiguration(Configuration configuration) {
		configurations.removeConfiguration(configuration);
		configuration.removeListener(listener);
		fireObjectRemovedEvent(configuration);
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
	
	public Configurations getConfiguration() {
		return configurations;
	}
	
	public CollaborationGroup getCollaboratorGroup() {
		return collaboratorGroup;
	}

	public int size() {
		return configurations.size() + collaboratorGroup.size();
	}

	@Override
	public void addListener(IDeltaListener listener) {
		
		configurations.addListener(listener);
		collaboratorGroup.addListener(listener);
		
		super.addListener(listener);
	}
	
	@Override
	public void removeListener(IDeltaListener listener)
	{
		configurations.removeListener(listener);
		collaboratorGroup.removeListener(listener);
		
		super.removeListener(listener);
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

	public boolean addNewFile(IFile file) throws CoreException, IOException
	{
		return configurations.addFile(file);
	}
}
