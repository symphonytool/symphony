package eu.compassresearch.core.interpreter.cml.events;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.CmlChannel;

/**
 * This represents an observable channel event from a set of CmlBehaviourThread objects
 * @author akm
 *
 */
public abstract class ObservableEvent implements CmlEvent {

	final protected CmlChannel channel;
	final protected Set<CmlBehaviourThread> eventSources;
	
	public ObservableEvent(CmlBehaviourThread eventSource, CmlChannel channel)
	{
		this.eventSources = new HashSet<CmlBehaviourThread>();
		this.eventSources.add(eventSource);
		this.channel = channel;
	}
	
	public ObservableEvent(CmlChannel channel)
	{
		this.eventSources = new HashSet<CmlBehaviourThread>();
		this.channel = channel;
	}
	
	protected ObservableEvent(Set<CmlBehaviourThread> sources, CmlChannel channel)
	{
		this.eventSources = sources;
		this.channel = channel;
	}
	
	/**
	 * Returns the set of sources that are prepared to engage in this event
	 * @return
	 */
	public Set<CmlBehaviourThread> getEventSources()
	{
		return eventSources;
	}
	
	/**
	 * The channel of this involved in this events
	 * @return
	 */
	public CmlChannel getChannel()
	{
		return channel;
	}

	/**
	 * This creates a synchronized event between this and other.
	 * @param other
	 * @return The synchronized event 
	 */
	public abstract ObservableEvent synchronizeWith(ObservableEvent other);
		
	/**
	 * Two Observable events are comparable if the are occurring on the same channel and
	 * the sources of one must either be a subset of the other or equal to.
	 * 
	 * Values do not have to be identical
	 * @param other
	 * @return
	 */
	public boolean isComparable(ObservableEvent other) {

		return this.equals(other);
	}
	
	public abstract Value getValue();
	
	public abstract void setValue(Value value);
	
	public abstract boolean isValuePrecise();
	
	public abstract List<ObservableEvent> expand();
	
	/**
	 * return the most precise of this and other
	 * @param other
	 * @return
	 */
	public abstract ObservableEvent meet(ObservableEvent other); 
	
	@Override
	public boolean equals(Object obj) {

		ObservableEvent other = null;
		
		if(!(obj instanceof ObservableEvent))
			return false;
		
		other = (ObservableEvent)obj;
		
		return other.getChannel().equals(getChannel()) && 
				// other is subset of this or this is a subset of other
				(other.getEventSources().containsAll(getEventSources()) || 
						getEventSources().containsAll(other.getEventSources()));
	}
	
}
