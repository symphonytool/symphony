package eu.compassresearch.ide.collaboration.datamodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.compassresearch.ide.collaboration.files.FileUpdate;

public class File extends Model
{	
	private enum FileState {NEWFILE,UPDATED,NORMAL,INITIAL};
	
	private static final long serialVersionUID = -6007439345479622225L;

	protected Shares shares;
	protected long timestamp_ux_epoch;
	private String hash;
	private final String filePath;
	private FileState fileState;
	private boolean hasBeenStored;

	public File(String name, String hash, String filePath, Model parent)
	{
		super(name, parent);
		this.shares = new Shares(this);
		this.hash = hash;
		this.filePath = filePath;
		Date d = new Date();
		this.timestamp_ux_epoch = d.getTime();
		this.fileState = FileState.INITIAL;
		this.hasBeenStored = false;
	}

	private File(String name, String hash, String filePath, Shares shares, Date timestamp, Model parent)
	{
		super(name, parent);

		this.shares = shares;
		this.hash = hash;
		this.filePath = filePath;
		this.timestamp_ux_epoch = timestamp.getTime();
		this.fileState = FileState.NORMAL;
		this.hasBeenStored = false; 
	}

	public File(String fileName, String fileHash, long timestamp,
			String filePath, Configuration parent)
	{
		super(fileName,parent);
		
		this.shares = new Shares(this);
		this.hash = fileHash;
		this.timestamp_ux_epoch = timestamp;
		this.fileState = FileState.NORMAL;
		this.hasBeenStored = true;
		this.filePath = filePath; 
	}

	public void addShare(Share share)
	{
		Configuration config = (Configuration) getParent();
		config.setLimitedVisibility();
		
		shares.addShare(share);
		fireObjectUpdatedEvent(share);
	}
	
	public void addShare(String name)
	{
		addShare(new Share(name, this));
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

	public String getFilePath()
	{
		return filePath;
	}
	
	public String getHashFileName(){
		//limit the likelihood of windows 255 char max file path
		return hash.substring(0, 30);
	}

	public Date getTimeStamp(){
		return new Date(timestamp_ux_epoch);
	}
	
	public CollaborationProject getCollaborationProject(){
		
		return getParent().getCollaborationProject();
	}
	
	public List<String> getVisibilityList()
	{
		List<String> collaboratorsList = new ArrayList<String>();
		for (Share share : getShares().getSharesList())
		{
			collaboratorsList .add(share.getName());
		}
		return collaboratorsList;
	}

	public boolean isVisibilityLimited(){
		return shares.size() > 0;
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
		return fileState == FileState.NEWFILE;
	}
	
	public boolean isUpdatedFile()
	{
		return fileState == FileState.UPDATED;
	}

	public void setAsNewFile()
	{
		if(fileState == FileState.INITIAL){
			fileState = FileState.NEWFILE;
			fireObjectUpdatedEvent(this);
		}
	}
	
	public void setUpdate(FileUpdate fileUpdate)
	{
		fileState = FileState.UPDATED;
		hash = fileUpdate.getHash();
		timestamp_ux_epoch = new Date().getTime();
		
		fireObjectUpdatedEvent(this);
	}

	public boolean isStored()
	{
		return hasBeenStored;
	}

	public void setAsStored()
	{
		this.hasBeenStored = true;
	}

	public File copy(Model newParent)
	{
		File f = new File(name, this.hash, this.filePath, shares.clone(),getTimeStamp(), newParent);
		return f;
	}
	
	/*
	 * (non-Javadoc)
	 *  Equal if they have the same name. Use isIdentical() for a compare of both name and hash.
	 * 
	 * Returns true if the values returned by getName() are equal, else false. 
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof File) {
			File compareTo = (File) obj;
			
			if(compareTo.getName().equals(this.name))
				return true;
		}
		
		return false;
	}
	
	public boolean isIdentical(File compareTo)
	{			
			if(compareTo.getHash().equals(this.hash) && compareTo.getName().equals(this.name))
				return true;
			else
				return false;
	}
}
