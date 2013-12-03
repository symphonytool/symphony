package eu.compassresearch.ide.collaboration.datamodel;

import org.eclipse.ecf.core.user.IUser;

import eu.compassresearch.ide.collaboration.communication.messages.FileStatusMessage.NegotiationStatus;

public class Contract extends Model {
	protected Versions versions;
	
	private String filename;
	private IUser sender;
	private IUser receiver;
	private NegotiationStatus status;

	public Contract() {
		versions = new Versions();

	}

	public Contract(String fileName, IUser sender, IUser receiver) {
		this();
		this.filename = fileName;
		this.name = filename.substring(0, filename.indexOf("."));
		
		this.sender = sender;
		this.receiver = receiver;
	}
	
	public void addVersion(Version version) {
		versions.addVersion(version);
		version.setParent(this);
		fireAdd(version);
	}
	
	protected void removeVersion(Version version) {
		versions.removeVersion(version);
		version.addListener(NullDeltaListener.getSoleInstance());
		fireRemove(version);
	}

	public Versions getVersions() {
		return versions;
	}

	public int size() {
		return getVersions().size();
	}

	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		visitor.visitContract(this, passAlongArgument);
	}
	
	@Override
	public void addListener(IDeltaListener listener) {
		versions.addListener(listener);			
		super.addListener(listener);
	}

	public String getFilename()
	{
		return filename;
	}

	public IUser getSender()
	{
		return sender;
	}

	public IUser getReceiver()
	{
		return receiver;
	}

	public NegotiationStatus getStatus()
	{
		return status;
	}

	public void setStatus(NegotiationStatus status)
	{
		this.status = status;
		fireAdd(this);
	}	
}
