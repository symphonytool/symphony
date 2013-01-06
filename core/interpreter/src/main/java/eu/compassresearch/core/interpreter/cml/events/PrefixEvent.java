package eu.compassresearch.core.interpreter.cml.events;

import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.events.ChannelObserver;
import eu.compassresearch.core.interpreter.values.CMLChannelValue;

public class PrefixEvent extends ObservableEvent {

	public PrefixEvent(CmlBehaviourThread eventSource, CMLChannelValue channel) {
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
	public void handleChannelEventRegistration(ChannelObserver observer) {
		
		getChannel().onChannelSignal().registerObserver(observer);
	}
	
	@Override
	public void handleChannelEventUnregistration(ChannelObserver observer) {
		getChannel().onChannelSignal().unregisterObserver(observer);
	}

}
