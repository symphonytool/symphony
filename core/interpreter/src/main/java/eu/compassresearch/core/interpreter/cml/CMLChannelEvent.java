package eu.compassresearch.core.interpreter.cml;

import eu.compassresearch.core.interpreter.values.CMLChannelValue;

public class CMLChannelEvent {
		
	final private CMLChannelValue channel;
	final private CMLCommunicationType eventType;
	
	public CMLChannelEvent(CMLChannelValue channel, CMLCommunicationType eventType)
	{
		this.channel = channel;
		this.eventType = eventType;
	}

	public CMLChannelValue getChannel() {
		return channel;
	}
	
	public CMLCommunicationType getEventType()
	{
		return eventType;
	}
}
