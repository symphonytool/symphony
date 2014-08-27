package eu.compassresearch.ide.collaboration.datamodel;

import java.util.UUID;

import org.eclipse.core.runtime.CoreException;

import eu.compassresearch.ide.collaboration.files.FileStatus;
import eu.compassresearch.ide.collaboration.files.FileUpdate;


public class CollaborationProject extends Model
{
	private static final long serialVersionUID = 8119280860561533717L;
	
	Configurations configurations;
	CollaborationGroup collaboratorGroup;
	
	private String workspaceName; 
	private String title; 
	private String description; 
	private final String uniqueID;
	
	public CollaborationProject(String workspaceName, String title, String description, String uniqueID, Model parent)
	{
		super(title, parent);
		this.description = description;
		this.name = "Collaboration Project: " + title;
		this.title = title;
		this.workspaceName = workspaceName;
		this.uniqueID = uniqueID;
		
		configurations = new Configurations(this);
		collaboratorGroup = new CollaborationGroup(this);
	}
	
	public CollaborationProject(String workspaceName, String title, String description, Model parent)
	{
		this(workspaceName,title, description, UUID.randomUUID().toString(), parent);
	}
	
	protected void removeConfiguration(Configuration configuration) {
		configurations.removeConfiguration(configuration);
		configuration.removeListener(listener);
		fireObjectRemovedEvent(configuration);
	}
	
	public void addNewConfiguration()
	{
		configurations.addNewConfiguration();	
	}
	
//	public void addCollaborator(User usr) {
//		collaboratorGroup.addCollaborator(usr);
//		fireObjectAddedEvent(collaboratorGroup);
//	}
	
//	protected void removeCollaboratorGroup(CollaborationGroup collabGroup) {
//		//collaboratorGroups.remove(collabGroup);
//		collabGroup.removeListener(listener);
//		fireObjectRemovedEvent(collabGroup);
//	}
	
	public Configurations getConfigurations() {
		return configurations;
	}
	
	public CollaborationGroup getCollaboratorGroup() {
		return collaboratorGroup;
	}

	public int size() {
		return configurations.size() + collaboratorGroup.size();
	}

	@Override
	public void addListener(IModelEventListener listener) {
		
		configurations.addListener(listener);
		collaboratorGroup.addListener(listener);
		
		super.addListener(listener);
	}
	
	@Override
	public void removeListener(IModelEventListener listener)
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

	public void addNewFile(FileStatus fileStatus) throws CoreException
	{
		configurations.addFile(fileStatus);
	}
	
	public void updateFile(FileUpdate fileUpdate) 
	{
		configurations.updateFile(fileUpdate);
	}
	
	public FileStatus getFileStatus(FileStatus fileStatus)
	{
		Configuration newestConfiguration = configurations.getNewestConfiguration();
		
		if(newestConfiguration == null){
			return fileStatus;
		} else {
			FileStatus status = newestConfiguration.getFileStatus(fileStatus);

			return status;
		}
	}
	
	@Override
	public CollaborationProject getCollaborationProject()
	{
		return this;
	}

	public Configuration getConfiguration(String configurationUniqueID)
	{
		return configurations.getConfigurationFromId(configurationUniqueID);
	}

	public Configuration getNewestConfiguration()
	{
		return configurations.getNewestConfiguration();
	}
}
