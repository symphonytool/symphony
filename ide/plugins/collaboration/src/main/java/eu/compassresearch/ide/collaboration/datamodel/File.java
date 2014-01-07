package eu.compassresearch.ide.collaboration.datamodel;

import java.util.Date;

public class File extends Model
{

	private static final long serialVersionUID = -6007439345479622225L;

	protected Shares shares;
	protected final long timestamp_ux_epoch;
	private final String hash;
	private final String filePath;
	private boolean newFile;

	public File(String name, String hash, String filePath, Model parent)
	{
		super(name, parent);
		this.shares = new Shares(this);
		this.hash = hash;
		this.filePath = filePath;
		Date d = new Date();
		this.timestamp_ux_epoch = d.getTime();
		newFile = false;
	}

	private File(String name, String hash, String filePath, Shares shares, Date timestamp, Model parent)
	{
		super(name, parent);

		this.shares = shares;
		this.hash = hash;
		this.filePath = filePath;
		this.timestamp_ux_epoch = timestamp.getTime();
		newFile = false;
	}

	public void addShare(Share share)
	{
		shares.addShare(share);
		fireObjectAddedEvent(share);
	}

	protected void removeShare(Share share)
	{
		shares.removeShare(share);
		share.removeListener(listener);
		fireObjectRemovedEvent(share);
	}

	public Shares getShares()
	{
		return shares;
	}

	public String getHash()
	{
		return hash;
	}

	public File clone()
	{
		File f = new File(name, this.hash, this.filePath, shares.clone(),getTimeStamp(), getParent());
		return f;
	}

	public String getFilePath()
	{
		return filePath;
	}

	public Date getTimeStamp(){
		return new Date(timestamp_ux_epoch);
	}
	
	public CollaborationProject getCollaborationProject(){
		
		return getParent().getCollaborationProject();
	}
	
	@Override
	public String toString()
	{
		return super.toString() + " - " +  getTimeStamp();
	}

	@Override
	public void addListener(IDeltaListener listener)
	{
		shares.addListener(listener);
		super.addListener(listener);
	}

	@Override
	public void removeListener(IDeltaListener listener)
	{
		shares.removeListener(listener);
		super.removeListener(listener);
	}

	public void accept(IModelVisitor visitor, Object passAlongArgument)
	{
		// visitor.visitFile(this, passAlongArgument);
	}

	public boolean isNewFile()
	{
		return newFile;
	}

	public void setNewFile(boolean newFile)
	{
		this.newFile = newFile;
	}

}
