package eu.compassresearch.core.interpreter.cml.events;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviour;
import eu.compassresearch.core.interpreter.cml.CmlChannel;

class PrefixEvent extends AbstractObservableEvent {

	public PrefixEvent(CmlBehaviour eventSource, CmlChannel channel) {
		super(eventSource, channel);
	}
	
	public PrefixEvent(Set<CmlBehaviour> eventSources, CmlChannel channel) {
		super(eventSources, channel);
	}
	
	public PrefixEvent(CmlChannel channel) {
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

		if(!(obj instanceof PrefixEvent))
			return false;
		
		return super.equals(obj);
	}

	@Override
	public CmlAlphabet getAsAlphabet() {
		return new CmlAlphabet(this);
	}

	@Override
	public AbstractObservableEvent synchronizeWith(AbstractObservableEvent syncEvent) {
		
		Set<CmlBehaviour> sources = new HashSet<CmlBehaviour>();
		sources.addAll(this.getEventSources());
		sources.addAll(syncEvent.getEventSources());
		
		return new PrefixEvent(sources, channel);
	}

	@Override
	public AbstractObservableEvent meet(AbstractObservableEvent other) {
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

	@Override
	public List<AbstractObservableEvent> expand() {
		return Arrays.asList((AbstractObservableEvent)this);
	}
}
