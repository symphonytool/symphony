package eu.compassresearch.ide.collaboration.datamodel;

public class Version extends Model {
	
	private static final long serialVersionUID = -6007439345479622225L;
	
	protected Shares shares;
	
	public Version(String title) {
		super(title);
		
		shares = new Shares();
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
	
	/*
	 * @see Model#accept(ModelVisitorI, Object)
	 */
	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		//visitor.visitBook(this, passAlongArgument);
	}

}
