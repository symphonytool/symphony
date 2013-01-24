package eu.compassresearch.core.interpreter.cml.events;

import java.util.List;

import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.channels.CmlChannel;

public abstract class ObservableEvent extends CmlEvent {

	final protected CmlChannel channel;
	final protected CmlBehaviourThread eventSource;
	
	public ObservableEvent(CmlBehaviourThread eventSource, CmlChannel channel)
	{
		this.eventSource = eventSource;
		this.channel = channel;
	}
	
	public CmlBehaviourThread getEventSource()
	{
		return eventSource;
	}
	
	public boolean isReferenceEvent()
	{
		return getEventSource() == null;
	}
	
	public CmlChannel getChannel()
	{
		return channel;
	}
	
	/**
	 * This creates a ObservableEvent instance that has source set to null. This is used to compare
	 * equality of events from different sources.
	 * @return
	 */
	public abstract ObservableEvent getReferenceEvent();
	
	public abstract CmlAlphabet getAsAlphabet();
	
	public abstract ObservableEvent synchronizeWith(CmlBehaviourThread source,ObservableEvent syncEvent);
	
	public boolean isComparable(ObservableEvent other) {

		return other.getChannel().equals(getChannel()) && 
				other.getEventSource() == getEventSource();
	}
	
	/**
	 * return the most precise of this and other
	 * @param other
	 * @return
	 */
	public abstract ObservableEvent meet(ObservableEvent other); 
//	/**
//	 * Determines whether the are any input parameters that does not have a specific value
//	 * @return
//	 */
//	public abstract boolean isResolved();
//	
//	
//	public interface EventResolver
//	{
//		public void resolve(List<CommunicationParameter> params);
//	}
//	
//	public abstract void resolve(EventResolver resolver);
	
}
