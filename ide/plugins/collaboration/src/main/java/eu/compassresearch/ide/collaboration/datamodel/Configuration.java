package eu.compassresearch.ide.collaboration.datamodel;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import eu.compassresearch.ide.collaboration.communication.messages.FileStatusMessage.NegotiationStatus;
import eu.compassresearch.ide.collaboration.files.FileHandler;

public class Configuration extends Model {

	private static final long serialVersionUID = 3865745639653377130L;

	protected Files files;
	private NegotiationStatus status;
	private final String uniqueID;
	protected final long timestamp_ux_epoch; 
	private Configuration parentConfiguration;

	private Configuration(Model parent){
		super("Unnamed Configuration", parent);
		uniqueID = UUID.randomUUID().toString();
		files = new Files(this);
		Date d = new Date();
		timestamp_ux_epoch = d.getTime();
		this.name =  uniqueID.substring(0, 8) + " - " + new Date(timestamp_ux_epoch);
	}
	
	public Configuration(File file, Model parent) {
		this(parent);
		addFile(file);
		parentConfiguration = null;
	}
	
	public Configuration(Configuration oldConfiguration, File newFile, Model parent) {
		this(parent);
		
		parentConfiguration = oldConfiguration;
		Files filesInOldConfig = oldConfiguration.getFiles();
		Files filesInNewConfig = filesInOldConfig.clone();
		filesInNewConfig.addFile(newFile);
		files = filesInNewConfig;
	}
	
	private void addFile(File file) {
		files.addFile(file);
		fireObjectAddedEvent(file);
	}
	
	protected void removeFile(File version) {
		files.removeFile(version);
		version.removeListener(listener);
		fireObjectRemovedEvent(version);
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
		fireObjectAddedEvent(this);
	}

	public boolean isKnownFile(IFile file) throws CoreException, IOException
	{		
		return files.isFileKnown(file);
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
}
