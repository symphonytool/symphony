package eu.compassresearch.ide.collaboration.treeview.model;

public class Contract extends Model {
	protected Versions versions;
	protected Shares shares;
	
	private String filename;

	public Contract() {
		versions = new Versions();
		shares = new Shares();
	}

	public Contract(String fileName) {
		this();
		this.filename = fileName;
		this.name = filename.substring(0, filename.indexOf("."));
	}
	
	public void addVersion(Version version) {
		versions.addVersion(version);
		version.parent = this;
		fireAdd(version);
	}
	
	public void addShare(Share share) {
		shares.addShare(share);
		share.parent = this;
		fireAdd(share);
	}		
	
	protected void removeShare(Share share) {
		shares.removeShare(share);
		share.addListener(NullDeltaListener.getSoleInstance());
		fireRemove(share);
	}
	
	protected void removeVersion(Version version) {
		versions.removeVersion(version);
		version.addListener(NullDeltaListener.getSoleInstance());
		fireRemove(version);
	}

	public Shares getShares() {
		return shares;
	}
	
	public Versions getVersions() {
		return versions;
	}

	public int size() {
		return getVersions().size() + getShares().size();
	}

	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		visitor.visitContract(this, passAlongArgument);
	}
	
	@Override
	public void addListener(IDeltaListener listener) {
		versions.addListener(listener);
		shares.addListener(listener);
			
		super.addListener(listener);
	}

	public String getFilename()
	{
		return filename;
	}	
}
