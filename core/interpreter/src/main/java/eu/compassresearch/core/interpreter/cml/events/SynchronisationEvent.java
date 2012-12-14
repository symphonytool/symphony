package eu.compassresearch.core.interpreter.cml.events;

import eu.compassresearch.core.interpreter.cml.CmlProcess;
import eu.compassresearch.core.interpreter.events.ChannelObserver;
import eu.compassresearch.core.interpreter.values.CMLChannelValue;

public class SynchronisationEvent extends ObservableCmlEvent {

	public SynchronisationEvent(CmlProcess eventSource, CMLChannelValue channel) {
		super(eventSource, channel);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void handleChannelEventRegistration(ChannelObserver observer) {
		// TODO Auto-generated method stub
		
	}

}
