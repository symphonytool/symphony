package eu.compassresearch.ide.collaboration.datamodel;

import java.util.ArrayList;
import java.util.List;

public class Shares extends Model {

	private static final long serialVersionUID = -5246629853836276264L;
	
	protected List<Share> shares;

	public Shares(Model parent) {
		super("Shares", parent);
		shares = new ArrayList<Share>();
	}
	
	private Shares(List<Share> shares, Model parent) {
		super("Shares", parent);
		this.shares = shares;
	}
	
	public void addShare(Share share) {
		shares.add(share);
		fireObjectUpdatedEvent(share);
	}
	
	protected void removeShare(Share share) {
		shares.remove(share);
		share.removeListener(listener);
		fireObjectRemovedEvent(share);
	}
	
	public List<Share> getSharesList() {
		return shares;
	}
	
	public int size() {
		return shares.size();
	}
	
	public Shares clone(){
		
		ArrayList<Share> clone = new ArrayList<Share>();
		
		for (Share share : shares)
		{
			clone.add(share.clone());
		}
	
		return new Shares(clone, getParent());
	}
	
	@Override
	public String toString()
	{
		int size = shares.size();
		
		return super.toString() + (size > 0 ? " (" + shares.size() + ")" : "");
	}
	
	@Override
	public void addListener(IDeltaListener listener) {
		
		for (Share s : shares)
		{
			s.addListener(listener);
		}
		
		super.addListener(listener);
	}
	
	@Override
	public void removeListener(IDeltaListener listener)
	{
		for (Share s : shares)
		{
			s.removeListener(listener);
		}
		
		super.removeListener(listener);
	}
	
	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		//visitor.visitContracts(this, passAlongArgument);
	}

	@Override
	public CollaborationProject getCollaborationProject()
	{
		return getParent().getCollaborationProject();
	}
}
