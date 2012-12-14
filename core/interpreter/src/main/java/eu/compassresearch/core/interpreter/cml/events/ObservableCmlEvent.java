package eu.compassresearch.core.interpreter.cml.events;

import eu.compassresearch.core.interpreter.cml.CmlProcess;
import eu.compassresearch.core.interpreter.events.ChannelObserver;
import eu.compassresearch.core.interpreter.values.CMLChannelValue;

public abstract class ObservableCmlEvent extends CmlEvent {

	final protected CMLChannelValue channel;
	final protected CmlProcess eventSource;
	
	public ObservableCmlEvent(CmlProcess eventSource, CMLChannelValue channel)
	{
		this.eventSource = eventSource;
		this.channel = channel;
	}
	
	public CmlProcess getEventSource()
	{
		return eventSource;
	}
	
	public CMLChannelValue getChannel()
	{
		return channel;
	}
	
	public abstract void handleChannelEventRegistration(ChannelObserver observer);
}
