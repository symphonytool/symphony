package eu.compassresearch.core.interpreter.cml.events;

import eu.compassresearch.core.interpreter.cml.CmlProcess;
import eu.compassresearch.core.interpreter.events.ChannelObserver;
import eu.compassresearch.core.interpreter.values.CMLChannelValue;

public abstract class ObservableEvent extends CmlEvent {

	final protected CMLChannelValue channel;
	final protected CmlProcess eventSource;
	
	public ObservableEvent(CmlProcess eventSource, CMLChannelValue channel)
	{
		this.eventSource = eventSource;
		this.channel = channel;
	}
	
	public CmlProcess getEventSource()
	{
		return eventSource;
	}
	
	public boolean isReferenceEvent()
	{
		return getEventSource() == null;
	}
	
	public CMLChannelValue getChannel()
	{
		return channel;
	}
	
	public abstract ObservableEvent getReferenceEvent();
	
	public abstract boolean isReferencedFrom(ObservableEvent ref);
	
	public abstract void handleChannelEventRegistration(ChannelObserver observer);
	
	public abstract void handleChannelEventUnregistration(ChannelObserver observer);
}
