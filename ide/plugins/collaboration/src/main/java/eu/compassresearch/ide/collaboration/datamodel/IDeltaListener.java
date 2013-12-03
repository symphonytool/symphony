package eu.compassresearch.ide.collaboration.datamodel;
public interface IDeltaListener {
	public void add(DeltaEvent event);
	public void remove(DeltaEvent event);
}
