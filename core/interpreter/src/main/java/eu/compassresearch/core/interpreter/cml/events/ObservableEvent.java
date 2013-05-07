package eu.compassresearch.core.interpreter.cml.events;


public interface ObservableEvent extends CmlTransition{

	/**
	 * This creates a synchronized event between this and other.
	 * @param other
	 * @return The synchronized event 
	 */
	public ObservableEvent synchronizeWith(ObservableEvent other);
	
	/**
	 * Two Observable events are comparable if the are occurring on the same channel and
	 * the sources of one must either be a subset of the other or equal to.
	 * 
	 * Values do not have to be identical
	 * @param other
	 * @return
	 */
	public boolean isComparable(ObservableEvent other);
	
}
