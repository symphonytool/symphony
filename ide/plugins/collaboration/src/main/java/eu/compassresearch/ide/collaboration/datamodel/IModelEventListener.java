package eu.compassresearch.ide.collaboration.datamodel;
public interface IModelEventListener {
	public void onObjectUpdated(DeltaEvent event);
	public void onObjectRemove(DeltaEvent event);
}
