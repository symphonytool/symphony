package eu.compassresearch.ide.collaboration.treeview.model;
public interface IDeltaListener {
	public void add(DeltaEvent event);
	public void remove(DeltaEvent event);
}
