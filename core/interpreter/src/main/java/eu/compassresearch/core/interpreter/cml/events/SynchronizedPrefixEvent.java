package eu.compassresearch.core.interpreter.cml.events;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.channels.CmlChannel;

public class SynchronizedPrefixEvent extends ObservableEvent {

	private Set<ObservableEvent> synchronisingEvents; 
	
	public SynchronizedPrefixEvent(CmlBehaviourThread eventSource, ObservableEvent first, ObservableEvent second) {
		super(eventSource, first.getChannel());
		synchronisingEvents = new HashSet<ObservableEvent>();
		synchronisingEvents.add(first);
		synchronisingEvents.add(second);
	}
	
	public SynchronizedPrefixEvent(CmlBehaviourThread eventSource, Set<ObservableEvent> synchronisingEvents) {
		super(eventSource, extractChannelValue(synchronisingEvents));
		this.synchronisingEvents = synchronisingEvents;
	}
	
	private static CmlChannel extractChannelValue(Set<ObservableEvent> synchronisingEvents)
	{
		CmlChannel channel = synchronisingEvents.iterator().next().getChannel();
		return channel;
	}
	
	public void addSyncEvent(ObservableEvent event)
	{
		synchronisingEvents.add(event);
	}
	
	public CmlAlphabet getSynchronousEvents()
	{
		return new CmlAlphabet(synchronisingEvents, new HashSet<CmlSpecialEvent>());
	}
	

	@Override
	public int hashCode() {
		return synchronisingEvents.hashCode();
	}

	@Override
	public String toString() {
		return synchronisingEvents.iterator().next().toString();
	}

	@Override
	public boolean equals(Object obj) {
		
		if((obj instanceof SynchronizedPrefixEvent))
		{
			SynchronizedPrefixEvent other = (SynchronizedPrefixEvent)obj;
			return other.getChannel().equals(getChannel()) && 
					synchronisingEvents.equals(other.synchronisingEvents);
		}
		else
			return false;
	}

	/**
	 * There is no reference event for a synchronisation event
	 */
	@Override
	public ObservableEvent getReferenceEvent() {
		
		return getAsAlphabet().getReferenceEvents().iterator().next();
	}

	@Override
	public CmlAlphabet getAsAlphabet() {

		CmlAlphabet alpha = new CmlAlphabet();
		for(ObservableEvent ev : synchronisingEvents)
			alpha = alpha.union(ev.getAsAlphabet());
		
		return alpha;
	}

	@Override
	public ObservableEvent synchronizeWith(CmlBehaviourThread source,
			ObservableEvent syncEvent) {
		return new SynchronizedPrefixEvent(source, this, syncEvent);
	}

	@Override
	public ObservableEvent meet(ObservableEvent other) {
		return this;
	}

	@Override
	public Value getValue() {
		return null;
	}

	@Override
	public void setValue(Value value) {
		// do nothing
	}

	@Override
	public boolean isValuePrecise() {
		return true;
	}
}
