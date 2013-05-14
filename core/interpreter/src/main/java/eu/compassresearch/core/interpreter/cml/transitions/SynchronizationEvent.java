package eu.compassresearch.core.interpreter.cml.transitions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.cml.core.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.core.CmlBehaviour;
import eu.compassresearch.core.interpreter.cml.core.CmlChannel;

class SynchronizationEvent extends AbstractChannelEvent implements ObservableEvent{

	public SynchronizationEvent(CmlBehaviour eventSource, CmlChannel channel) {
		super(eventSource, channel);
	}
	
	public SynchronizationEvent(Set<CmlBehaviour> eventSources, CmlChannel channel) {
		super(eventSources, channel);
	}
	
	public SynchronizationEvent(CmlChannel channel) {
		super(channel);
	}

	@Override
	public int hashCode() {
		
		return channel.getName().hashCode(); //(this.eventSource != null ? this.eventSource.hashCode() : "null".hashCode());
	}

	@Override
	public String toString() {
		return channel.getName();// + " : " + this.getEventSources();
	}

	@Override
	public boolean equals(Object obj) {

		if(!(obj instanceof SynchronizationEvent))
			return false;
		
		return super.equals(obj);
	}

	@Override
	public CmlAlphabet getAsAlphabet() {
		return new CmlAlphabet(this);
	}

	@Override
	public ObservableEvent synchronizeWith(ObservableEvent syncEvent) {
		
		Set<CmlBehaviour> sources = new HashSet<CmlBehaviour>();
		sources.addAll(this.getEventSources());
		sources.addAll(syncEvent.getEventSources());
		
		return new SynchronizationEvent(sources, channel);
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
	public boolean isPrecise() {
		return true;
	}

	@Override
	public List<ChannelEvent> expand() {
		return Arrays.asList((ChannelEvent)this);
	}
}
