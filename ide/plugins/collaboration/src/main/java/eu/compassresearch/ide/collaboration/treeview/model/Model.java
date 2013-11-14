package eu.compassresearch.ide.collaboration.treeview.model;
public abstract class Model {
	protected Model parent;
	protected String name;	
	protected IDeltaListener listener = NullDeltaListener.getSoleInstance();
	
	public Model(String name) {
		this.name = name;

	}
	
	public Model() {
	}	
	
	protected void fireAdd(Object added) {
		listener.add(new DeltaEvent(added));
	}

	protected void fireRemove(Object removed) {
		listener.remove(new DeltaEvent(removed));
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
	
	public void addListener(IDeltaListener listener) {
		this.listener = listener;
	}
	
	public void removeListener(IDeltaListener listener) {
		if(this.listener.equals(listener)) {
			this.listener = NullDeltaListener.getSoleInstance();
		}
	}
	
	public abstract void accept(IModelVisitor visitor, Object passAlongArgument);
}
