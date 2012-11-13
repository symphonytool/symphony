package eu.compassresearch.core.interpreter.cml;

import eu.compassresearch.core.interpreter.values.CMLChannelValue;

public class CmlChannelEvent {
		
	final private CMLChannelValue channel;
	final private CmlCommunicationType eventType;
	
	public CmlChannelEvent(CMLChannelValue channel, CmlCommunicationType eventType)
	{
		this.channel = channel;
		this.eventType = eventType;
	}

	public CMLChannelValue getChannel() {
		return channel;
	}
	
	public CmlCommunicationType getEventType()
	{
		return eventType;
	}
}
