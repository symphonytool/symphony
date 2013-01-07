package eu.compassresearch.core.interpreter.cml.events;

import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.channels.CmlChannel;

public abstract class ObservableEvent extends CmlEvent {

	final protected CmlChannel channel;
	final protected CmlBehaviourThread eventSource;
	
	public ObservableEvent(CmlBehaviourThread eventSource, CmlChannel channel)
	{
		this.eventSource = eventSource;
		this.channel = channel;
	}
	
	public CmlBehaviourThread getEventSource()
	{
		return eventSource;
	}
	
	public boolean isReferenceEvent()
	{
		return getEventSource() == null;
	}
	
	public CmlChannel getChannel()
	{
		return channel;
	}
	
	public abstract ObservableEvent getReferenceEvent();
	
}
