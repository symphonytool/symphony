package eu.compassresearch.ide.collaboration.datamodel;

import java.util.Date;
import java.util.UUID;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.communication.messages.ConfigurationStatusMessage.NegotiationStatus;
import eu.compassresearch.ide.collaboration.files.FileStatus;
import eu.compassresearch.ide.collaboration.notifications.Notification;

public class Configuration extends Model {

	private static final long serialVersionUID = 3865745639653377130L;

	enum CollaborationState {LOCAL,SHARED,RECEIVED};
	
	protected Files files;
	private NegotiationStatus status;
	private String uniqueID;
	protected long timestamp_ux_epoch; 
	private Configuration parentConfiguration;
	private CollaborationState collabState;
	private String configurationSignature;
	private boolean hasLimitedVisibility;

	public Configuration(Model parent){
		super("Unnamed Configuration", parent);
		uniqueID = UUID.randomUUID().toString();
		files = new Files(this);
		collabState = CollaborationState.LOCAL;
		configurationSignature = null;
		Date d = new Date();
		timestamp_ux_epoch = d.getTime();
		this.name =  uniqueID.substring(0, 8) + " - " + new Date(timestamp_ux_epoch);
		hasLimitedVisibility = false;
	}
	
	public Configuration(String configurationUniqueID, long timeStamp,
			String signedBy, Configuration oldConfiguration, Model parent){
		
		super("Unnamed Configuration", parent);
		
		uniqueID = configurationUniqueID;
		timestamp_ux_epoch = timeStamp;
		configurationSignature = signedBy;
		collabState = CollaborationState.RECEIVED;
		hasLimitedVisibility = false;
		this.name =  uniqueID.substring(0, 8) + " - " + new Date(timestamp_ux_epoch);
		files = new Files(this);
		parentConfiguration = oldConfiguration;
	}
	
	public Configuration(Configuration oldConfiguration, Model parent) {
		this(parent);
		
		parentConfiguration = oldConfiguration;
		Files filesInOldConfig = oldConfiguration.getFiles();
		Files filesInNewConfig = filesInOldConfig.copy(this);
		files = filesInNewConfig;
		hasLimitedVisibility = oldConfiguration.hasLimitedVisibility();
	}
	
	public void addFile(File file) throws CoreException {
		
		if(configurationSignature != null) {
			throw new CoreException(new Status(Status.ERROR, Activator.PLUGIN_ID, Notification.Collab_File_ERROR_FILE_ADD_TO_SIGNED));
		}
		
		files.addFile(file);
		fireObjectUpdatedEvent(file);
	}
	
	public void addNewFile(String fileName, String fileHash, long timestamp, String filePath)
	{
		files.addFile(new File(fileName, fileHash, timestamp, filePath, this));
	}
	
	protected void removeFile(File file) {
		files.removeFile(file);
		file.removeListener(listener);
		fireObjectRemovedEvent(file);
	}

	public Files getFiles() {
		return files;
	}

	public int size() {
		return files.size();
	}

	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		visitor.visitContract(this, passAlongArgument);
	}
	
	@Override
	public void addListener(IDeltaListener listener) {
		files.addListener(listener);			
		super.addListener(listener);
	}
	
	@Override
	public void removeListener(IDeltaListener listener)
	{
		files.removeListener(listener);
		super.removeListener(listener);
	}

	public NegotiationStatus getStatus()
	{
		return status;
	}

	public void setStatus(NegotiationStatus status)
	{
		this.status = status;
		fireObjectUpdatedEvent(this);
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
				if(file.getShares().size() != 0) {
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
}
