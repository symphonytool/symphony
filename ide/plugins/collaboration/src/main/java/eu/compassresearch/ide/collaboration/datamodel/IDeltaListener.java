package eu.compassresearch.ide.collaboration.datamodel;
public interface IDeltaListener {
	public void onObjectAdded(DeltaEvent event);
	public void onObjectRemove(DeltaEvent event);
}
