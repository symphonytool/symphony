package eu.compassresearch.ide.collaboration.datamodel;

import java.util.ArrayList;
import java.util.List;

public class Visibility extends Model {

	private static final long serialVersionUID = -5246629853836276264L;
	
	protected List<Visible> visibility;

	public Visibility(Model parent) {
		super("Visibility", parent);
		visibility = new ArrayList<Visible>();
	}
	
	private Visibility(List<Visible> shares, Model parent) {
		super("Visibility", parent);
		this.visibility = shares;
	}
	
	public void addVisible(Visible share) {
		visibility.add(share);
		fireObjectUpdatedEvent(share);
	}
	
	protected void removeVisible(Visible share) {
		visibility.remove(share);
		share.removeListener(listener);
		fireObjectRemovedEvent(share);
	}
	
	public List<Visible> getVisibleList() {
		return visibility;
	}
	
	public int size() {
		return visibility.size();
	}
	
	public Visibility clone(){
		
		ArrayList<Visible> clone = new ArrayList<Visible>();
		
		for (Visible share : visibility)
		{
			clone.add(share.clone());
		}
	
		return new Visibility(clone, getParent());
	}
	
	@Override
	public String toString()
	{
		int size = visibility.size();
		
		return super.toString() + (size > 0 ? " (" + visibility.size() + ")" : "");
	}
	
	@Override
	public void addListener(IDeltaListener listener) {
		
		for (Visible s : visibility)
		{
			s.addListener(listener);
		}
		
		super.addListener(listener);
	}
	
	@Override
	public void removeListener(IDeltaListener listener)
	{
		for (Visible s : visibility)
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
