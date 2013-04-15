package eu.compassresearch.core.interpreter.cml.events;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.cml.CmlBehaviour;
import eu.compassresearch.core.interpreter.cml.CmlChannel;

/**
 * This represents an observable channel event from a set of CmlBehaviourThread objects
 * @author akm
 *
 */
public abstract class AbstractObservableEvent extends AbstractCmlEvent {

	final protected CmlChannel channel;
	
	
	public AbstractObservableEvent(CmlBehaviour eventSource, CmlChannel channel)
	{
		super(eventSource);
		this.channel = channel;
	}
	
	public AbstractObservableEvent(CmlChannel channel)
	{
		super(new HashSet<CmlBehaviour>());
		this.channel = channel;
	}
	
	protected AbstractObservableEvent(Set<CmlBehaviour> sources, CmlChannel channel)
	{
		super(sources);
		this.channel = channel;
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
	public abstract AbstractObservableEvent synchronizeWith(AbstractObservableEvent other);
		
	/**
	 * Two Observable events are comparable if the are occurring on the same channel and
	 * the sources of one must either be a subset of the other or equal to.
	 * 
	 * Values do not have to be identical
	 * @param other
	 * @return
	 */
	public boolean isComparable(AbstractObservableEvent other) {

		return this.equals(other);
	}
	
	public abstract Value getValue();
	
	public abstract void setValue(Value value);
	
	public abstract boolean isValuePrecise();
	
	public abstract List<AbstractObservableEvent> expand();
	
	/**
	 * return the most precise of this and other
	 * @param other
	 * @return
	 */
	public abstract AbstractObservableEvent meet(AbstractObservableEvent other); 
	
	@Override
	public boolean equals(Object obj) {

		AbstractObservableEvent other = null;
		
		if(!(obj instanceof AbstractObservableEvent))
			return false;
		
		other = (AbstractObservableEvent)obj;
		
		return other.getChannel().equals(getChannel()) && 
				super.equals(obj);
	}
	
}
