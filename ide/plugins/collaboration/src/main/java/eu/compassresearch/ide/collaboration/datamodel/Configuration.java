package eu.compassresearch.ide.collaboration.datamodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.ecf.core.identity.ID;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.datamodel.ConfigurationStatus.ConfigurationNegotiationStatus;
import eu.compassresearch.ide.collaboration.files.FileStatus;
import eu.compassresearch.ide.collaboration.notifications.Notification;

public class Configuration extends Model {

	private static final long serialVersionUID = 3865745639653377130L;

	enum CollaborationState {LOCAL,SHARED,RECEIVED};
	
	protected Files files;
	protected ConfigurationStatuses configurationStatuses;
	
	private String uniqueID;
	protected long timestamp_ux_epoch; 
	private Configuration parentConfiguration;
	private CollaborationState collabState;
	//used to sign configuration
	private String configurationSignature;
	//used to indicate when the configuration contains files that should not be shared to the entire collaboration group
	private boolean hasLimitedVisibility;

	public Configuration(Model parent){
		super("Unnamed Configuration", parent);
		uniqueID = UUID.randomUUID().toString();
		files = new Files(this);
		configurationStatuses = new ConfigurationStatuses(this);
		collabState = CollaborationState.LOCAL;
		configurationSignature = null;
		Date d = new Date();
		timestamp_ux_epoch = d.getTime();
		this.name =  uniqueID.substring(0, 8) + " - " + new Date(timestamp_ux_epoch);
		hasLimitedVisibility = false;
	}
	
	
	//CTOR used when new a new configuration is received
	public Configuration(String configurationUniqueID, long timeStamp,
			String signedBy, List<String> sentTo, Configuration oldConfiguration, Model parent){
		
		super("Unnamed Configuration", parent);
		
		uniqueID = configurationUniqueID;
		timestamp_ux_epoch = timeStamp;
		configurationSignature = signedBy;
		collabState = CollaborationState.RECEIVED;
		hasLimitedVisibility = false;
		this.name =  uniqueID.substring(0, 8) + " - " + new Date(timestamp_ux_epoch);
		files = new Files(this);
		configurationStatuses = new ConfigurationStatuses(this);
		parentConfiguration = oldConfiguration;

		configurationStatuses.addConfigurationStatus(sentTo);
		
	}
	
	//CTOR used when creating a configuration on the basis of an older configuration
	public Configuration(Configuration oldConfiguration, Model parent) {
		this(parent);
		
		parentConfiguration = oldConfiguration;
		Files filesInOldConfig = oldConfiguration.getFiles();
		Files filesInNewConfig = filesInOldConfig.copy(this);
		files = filesInNewConfig;
		configurationStatuses = new ConfigurationStatuses(this);
		hasLimitedVisibility = oldConfiguration.hasLimitedVisibility();
	}

	public void addFile(File file) throws CoreException {
		
		if(configurationSignature != null) {
			throw new CoreException(new Status(Status.ERROR, Activator.PLUGIN_ID, Notification.Collab_File_ERROR_FILE_ADD_TO_SIGNED));
		}
		
		files.addFile(file);
		fireObjectUpdatedEvent(file);
	}
	
	public void addNewFile(String fileName, String fileHash, long timestamp, String filePath, List<String> visibilityList)
	{
		File file = new File(fileName, fileHash, timestamp, filePath, this);
		
		if(visibilityList != null && !visibilityList.isEmpty()){
			for (String visibilityName : visibilityList)
			{
				file.addVisibility(visibilityName);
			}
		}
		
		files.addFile(file);
	}
	
	protected void removeFile(File file) {
		files.removeFile(file);
		file.removeListener(listener);
		fireObjectRemovedEvent(file);
	}

	public Files getFiles() {
		return files;
	}

	
	public ConfigurationStatuses getConfigurationStatuses()
	{
		return configurationStatuses;
	}

	public int size() {
		return files.size();
	}

	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		visitor.visitContract(this, passAlongArgument);
	}
	
	@Override
	public void addListener(IModelEventListener listener) {
		files.addListener(listener);	
		configurationStatuses.addListener(listener);
		super.addListener(listener);
	}
	
	@Override
	public void removeListener(IModelEventListener listener)
	{
		files.removeListener(listener);
		configurationStatuses.removeListener(listener);
		super.removeListener(listener);
	}

	public Configuration getParentConfiguration()
	{
		return parentConfiguration;
	}

	public String getUniqueID()
	{
		return uniqueID;
	}
	
	@Override
	public String toString()
	{
		return super.toString();
	}

	@Override
	public CollaborationProject getCollaborationProject()
	{
		return getParent().getCollaborationProject();
	}
	
	public Date getTimeStamp(){
		return new Date(timestamp_ux_epoch);
	}

	public boolean isShared()
	{
		return collabState == CollaborationState.SHARED;
	}
	
	public boolean isReceived()
	{
		return collabState == CollaborationState.RECEIVED;
	}
	
	public boolean isLocal()
	{
		return collabState == CollaborationState.LOCAL;
	}
	
	public boolean isSigned()
	{
		return configurationSignature != null;
	}
	
	public String getSignedBy()
	{
		return configurationSignature;
	}
	
	public boolean hasLimitedVisibility()
	{		
		//check if still true
		if(!hasLimitedVisibility){
			
			for (File file : files.getFilesList())
			{
				if(file.getVisibility().size() != 0) {
					hasLimitedVisibility = true;
					break;
				}
			}
		}
		
		return hasLimitedVisibility;
	}
	
	public void setLimitedVisibility()
	{		
		hasLimitedVisibility = true;
	}

	public void setConfigurationShared()
	{
		if(collabState != CollaborationState.SHARED) {
			collabState = CollaborationState.SHARED;
			
			Date d = new Date();
			timestamp_ux_epoch = d.getTime();
			
			fireObjectUpdatedEvent(this);
		}
	}

	public void setSignedBy(String user)
	{
		if(this.configurationSignature == null) {
			this.configurationSignature = user;
			
			Date d = new Date();
			timestamp_ux_epoch = d.getTime();
			
			fireObjectUpdatedEvent(this);
		}
	}

	public FileStatus getFileStatus(FileStatus fileStatus)
	{
		return files.retrieveFileStatus(fileStatus);
	}

	public File getFile(String filename)
	{
		return files.getFile(filename);
	}


	public void setStatus(ID connectedUser, ConfigurationNegotiationStatus status)
	{
		ConfigurationStatus configurationStatus = configurationStatuses.getConfigurationStatus(connectedUser);
		
		if(configurationStatus != null) {
			configurationStatus.setStatus(status);
		}
	}

	public void addSentTo(List<User> sentTo)
	{
		ArrayList<String> users = new ArrayList<>();
		for (User user : sentTo)
		{
			users.add(user.getName());
		}
		
		configurationStatuses.addConfigurationStatus(users);
	}

}
