package eu.compassresearch.ide.collaboration.datamodel;

import java.util.Date;
import java.util.UUID;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.communication.messages.FileStatusMessage.NegotiationStatus;
import eu.compassresearch.ide.collaboration.files.FileChangeManager.FileStatus;
import eu.compassresearch.ide.collaboration.notifications.Notification;

public class Configuration extends Model {

	private static final long serialVersionUID = 3865745639653377130L;

	protected Files files;
	private NegotiationStatus status;
	private final String uniqueID;
	protected long timestamp_ux_epoch; 
	private Configuration parentConfiguration;
	private boolean sharedConfiguration;
	private String configurationSignature;

	public Configuration(Model parent){
		super("Unnamed Configuration", parent);
		uniqueID = UUID.randomUUID().toString();
		files = new Files(this);
		sharedConfiguration = false;
		configurationSignature = null;
		Date d = new Date();
		timestamp_ux_epoch = d.getTime();
		this.name =  uniqueID.substring(0, 8) + " - " + new Date(timestamp_ux_epoch);
	}
	
	public Configuration(Configuration oldConfiguration, Model parent) {
		this(parent);
		
		parentConfiguration = oldConfiguration;
		Files filesInOldConfig = oldConfiguration.getFiles();
		Files filesInNewConfig = filesInOldConfig.clone();
		files = filesInNewConfig;
	}
	
	public void addFile(File file) throws CoreException {
		
		if(configurationSignature != null) {
			throw new CoreException(new Status(Status.ERROR, Activator.PLUGIN_ID, Notification.Collab_File_ERROR_FILE_ADD_TO_SIGNED));
		}
		
		files.addFile(file);
		fireObjectUpdatedEvent(file);
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

	public boolean isSharedConfiguration()
	{
		return sharedConfiguration;
	}
	
	public boolean isSigned()
	{
		return configurationSignature != null;
	}
	
	public String getSignedBy()
	{
		return configurationSignature;
	}

	public void setConfigurationShared()
	{
		if(!this.sharedConfiguration) {
			this.sharedConfiguration = true;
			
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
	
	public FileStatus getFileStatus(IFile file)
	{
		return files.getFileStatus(file);
	}

	public File getFile(String filename)
	{
		return files.getFile(filename);
	}
}
