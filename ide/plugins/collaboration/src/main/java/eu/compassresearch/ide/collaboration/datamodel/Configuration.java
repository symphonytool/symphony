package eu.compassresearch.ide.collaboration.datamodel;

import java.util.UUID;

import eu.compassresearch.ide.collaboration.communication.messages.FileStatusMessage.NegotiationStatus;

public class Configuration extends Model {

	private static final long serialVersionUID = 3865745639653377130L;

	protected Files files;
	private NegotiationStatus status;
	private final String uniqueID;
	private Configuration parentConfiguration;

	private Configuration(){
		uniqueID = UUID.randomUUID().toString();
		files = new Files();
		files.setParent(this);
	}
	
	public Configuration(File file) {
		this();
		addFile(file);
		parentConfiguration = null;
	}
	
	public Configuration(Configuration oldConfiguration, File newFile) {
		this();
		
		parentConfiguration = oldConfiguration;
		Files filesInOldConfig = oldConfiguration.getFiles();
		Files filesInNewConfig = filesInOldConfig.clone();
		files.setParent(this);
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

	public boolean isKnownFile(String hash)
	{
		return files.isFileKnown(hash);
	}

	public Configuration getParentConfiguration()
	{
		return parentConfiguration;
	}

	public String getUniqueID()
	{
		return uniqueID;
	}
}
