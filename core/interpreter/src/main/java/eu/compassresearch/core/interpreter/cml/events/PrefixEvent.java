package eu.compassresearch.core.interpreter.cml.events;

import java.util.Arrays;
import java.util.List;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.CmlChannel;

public class PrefixEvent extends ObservableEvent {

	public PrefixEvent(CmlBehaviourThread eventSource, CmlChannel channel) {
		super(eventSource, channel);
	}

	@Override
	public int hashCode() {
		
		return this.toString().hashCode() + (this.eventSource != null ? this.eventSource.hashCode() : "null".hashCode());
	}

	@Override
	public String toString() {
		return channel.getName();
	}

	@Override
	public boolean equals(Object obj) {
		PrefixEvent other = null;
		
		if(!(obj instanceof PrefixEvent))
			return false;
		
		other = (PrefixEvent)obj;
		
		return other.getChannel().equals(getChannel()) && 
				other.getEventSource() == getEventSource();
	}

	@Override
	public ObservableEvent getReferenceEvent() {
		return new PrefixEvent(null, channel);
	}

	@Override
	public CmlAlphabet getAsAlphabet() {
		return new CmlAlphabet(this);
	}

	@Override
	public ObservableEvent synchronizeWith(CmlBehaviourThread source,ObservableEvent syncEvent) {
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

	@Override
	public List<ObservableEvent> expand() {
		return Arrays.asList((ObservableEvent)this);
	}
}
