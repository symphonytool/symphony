package eu.compassresearch.ide.collaboration.datamodel;

import java.io.Serializable;


public abstract class Model implements Serializable {
	
	private static final long serialVersionUID = -8593960173574664214L;
	
	private Model parent;
	protected String name;	
	protected IDeltaListener listener;
	
	public Model(String name, Model parent) {
		this.name = name;
		this.parent = parent;
	}
	
	protected void fireObjectUpdatedEvent(Object added) {
		if(listener != null)
			listener.onObjectUpdated(new DeltaEvent(added));
	}

	protected void fireObjectRemovedEvent(Object removed) {
		if(listener != null)
			listener.onObjectRemove(new DeltaEvent(removed));
	}
	
	public void setName(String name) {
		
		if(name == null)
			return;
		
		this.name = name;
	}
	
	public Model getParent() {
		return parent;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract CollaborationProject getCollaborationProject();
	
	public void addListener(IDeltaListener listener) {
		this.listener = listener;
	}
	
	public void removeListener(IDeltaListener listener) {

		if(this.listener != null && this.listener.equals(listener)) {
			this.listener = null;
		}
	}
	
	@Override
	public String toString()
	{
		return name;
	}
	
	public abstract void accept(IModelVisitor visitor, Object passAlongArgument);
}
