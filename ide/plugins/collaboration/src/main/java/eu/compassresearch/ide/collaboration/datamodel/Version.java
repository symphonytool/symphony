package eu.compassresearch.ide.collaboration.datamodel;

public class Version extends Model {
	
	protected Shares shares;
	
	public Version(String title) {
		super(title);
		
		shares = new Shares();
	}
	
	public void addShare(Share share) {
		shares.addShare(share);
		share.setParent(this); 
		fireAdd(share);
	}		
	
	protected void removeShare(Share share) {
		shares.removeShare(share);
		share.addListener(NullDeltaListener.getSoleInstance());
		fireRemove(share);
	}
	
	public Shares getShares() {
		return shares;
	}
	
	/*
	 * @see Model#accept(ModelVisitorI, Object)
	 */
	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		//visitor.visitBook(this, passAlongArgument);
	}

}
