package eu.compassresearch.core.interpreter.api.transitions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.api.CmlChannel;
import eu.compassresearch.core.interpreter.api.behaviour.CmlAlphabet;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.values.ChannelNameValue;

class SynchronizationEvent extends AbstractChannelEvent implements ObservableEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7043128094830839293L;

	public SynchronizationEvent(CmlBehaviour eventSource, ChannelNameValue channelName) {
		super(eventSource, channelName);
	}
	
	public SynchronizationEvent(Set<CmlBehaviour> eventSources, ChannelNameValue channelName) {
		super(eventSources, channelName);
	}
	
	public SynchronizationEvent(ChannelNameValue channelName) {
		super(channelName);
	}

	@Override
	public int hashCode() {
		
		return channelName.getChannel().getName().hashCode(); //(this.eventSource != null ? this.eventSource.hashCode() : "null".hashCode());
	}

	@Override
	public String toString() {
		return channelName.getChannel().getName();// + " : " + this.getEventSources();
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
		
		return new SynchronizationEvent(sources, channelName);
	}

	@Override
	public ObservableEvent meet(ObservableEvent other) {
		return this;
	}

//	@Override
//	public Value getValue() {
//		return null;
//	}
//
//	@Override
//	public void setValue(Value value) {
//		// do nothing
//	}

	@Override
	public boolean isPrecise() {
		return true;
	}

	@Override
	public List<ChannelEvent> expand() {
		return Arrays.asList((ChannelEvent)this);
	}
}
