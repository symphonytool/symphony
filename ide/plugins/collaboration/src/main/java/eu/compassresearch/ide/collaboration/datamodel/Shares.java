package eu.compassresearch.ide.collaboration.datamodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Shares extends Model {
	protected List shares;

	public Shares() {
		shares = new ArrayList();
		this.name = "Shares";
	}
	
	public void addShare(Share share) {
		shares.add(share);
		share.setParent(this);
		fireAdd(share);
	}
	
	protected void removeShare(Share share) {
		shares.remove(share);
		share.addListener(NullDeltaListener.getSoleInstance());
		fireRemove(share);
	}
	
	public List getShares() {
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
		
		for (Iterator iterator = shares.iterator(); iterator.hasNext();) {
			Share share = (Share) iterator.next();
			share.addListener(listener);
		}
		
		super.addListener(listener);
	}
}
