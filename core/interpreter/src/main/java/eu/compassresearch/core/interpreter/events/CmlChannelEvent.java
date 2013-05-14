package eu.compassresearch.core.interpreter.events;

import eu.compassresearch.core.interpreter.cml.core.CmlCommunicationType;
import eu.compassresearch.core.interpreter.values.CMLChannelValue;

public class CmlChannelEvent extends Event<CMLChannelValue>{
		
	final private CmlCommunicationType eventType;
	
	public CmlChannelEvent(CMLChannelValue channel, CmlCommunicationType eventType)
	{
		super(channel);
		//this.channel = channel;
		this.eventType = eventType;
	}

	public CmlCommunicationType getEventType()
	{
		return eventType;
	}
}
