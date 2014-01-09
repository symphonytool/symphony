package eu.compassresearch.ide.collaboration.datamodel;
public interface IDeltaListener {
	public void onObjectUpdated(DeltaEvent event);
	public void onObjectRemove(DeltaEvent event);
}
