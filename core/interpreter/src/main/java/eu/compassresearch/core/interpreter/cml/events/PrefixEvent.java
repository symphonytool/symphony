package eu.compassresearch.core.interpreter.cml.events;

import eu.compassresearch.core.interpreter.cml.CmlProcess;
import eu.compassresearch.core.interpreter.events.ChannelObserver;
import eu.compassresearch.core.interpreter.values.CMLChannelValue;

public class PrefixEvent extends ObservableCmlEvent {

	public PrefixEvent(CmlProcess eventSource, CMLChannelValue channel) {
		super(eventSource, channel);
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode() + this.eventSource.hashCode();
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
		
		return other.getChannel().equals(getChannel()) && other.getEventSource().equals(getEventSource());
	}

	@Override
	public void handleChannelEventRegistration(ChannelObserver observer) {
		
		getChannel().onChannelSignal().registerObserver(observer);
	}

}
