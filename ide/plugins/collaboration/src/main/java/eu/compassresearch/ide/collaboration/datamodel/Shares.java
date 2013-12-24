package eu.compassresearch.ide.collaboration.datamodel;

import java.util.ArrayList;
import java.util.List;

public class Shares extends Model {

	private static final long serialVersionUID = -5246629853836276264L;
	
	protected List<Share> shares;

	public Shares() {
		shares = new ArrayList<Share>();
		this.name = "Shares";
	}
	
	public void addShare(Share share) {
		shares.add(share);
		share.setParent(this);
		fireObjectAddedEvent(share);
	}
	
	protected void removeShare(Share share) {
		shares.remove(share);
		share.removeListener(listener);
		fireObjectRemovedEvent(share);
	}
	
	public List<Share> getShares() {
		return shares;
	}
	
	public int size() {
		return shares.size();
	}

	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		//visitor.visitContracts(this, passAlongArgument);
	}
	
	@Override
	public void addListener(IDeltaListener listener) {
		
		for (Share s : shares)
		{
			s.addListener(listener);
		}
		
		super.addListener(listener);
	}
}
