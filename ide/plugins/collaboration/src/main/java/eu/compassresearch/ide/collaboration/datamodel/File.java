package eu.compassresearch.ide.collaboration.datamodel;

public class File extends Model {
	
	private static final long serialVersionUID = -6007439345479622225L;
	
	protected Shares shares;
	private final String hash;
	private final String filePath; 
	
	public File(String name, String hash, String filePath) {
		super(name);
		
		shares = new Shares();
		this.hash = hash;
		this.filePath = filePath;
	}
	
	private File(String name, String hash, String filePath, Shares shares) {
		super(name);
		
		this.shares = shares;
		this.hash = hash;
		this.filePath = filePath;
	}
	
	public void addShare(Share share) {
		shares.addShare(share);
		share.setParent(this); 
		fireObjectAddedEvent(share);
	}		
	
	protected void removeShare(Share share) {
		shares.removeShare(share);
		share.removeListener(listener);
		fireObjectRemovedEvent(share);
	}
	
	public Shares getShares() {
		return shares;
	}
	
	public String getHash()
	{
		return hash;
	}
	
	public File clone(){
		File f = new File(name, this.hash, this.filePath, shares.clone());
        return f;
    }
	
	public String getFilePath()
	{
		return filePath;
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
	
	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		//visitor.visitFile(this, passAlongArgument);
	}

}
