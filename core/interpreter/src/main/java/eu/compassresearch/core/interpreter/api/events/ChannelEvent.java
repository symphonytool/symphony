package eu.compassresearch.core.interpreter.api.events;

import eu.compassresearch.core.interpreter.api.values.CMLChannelValue;

/**
 * This represents activity change of CmlInterpreterState in a specific CmlInterpreter instance. This event is fired
 * whenever a CmlInterpreter changes its state.
 * 
 * @author akm
 */
public class ChannelEvent extends Event<CMLChannelValue>
{

	final private ChannelActivity eventType;

	public ChannelEvent(CMLChannelValue channel, ChannelActivity eventType)
	{
		super(channel);
		// this.channel = channel;
		this.eventType = eventType;
	}

	public ChannelActivity getEventType()
	{
		return eventType;
	}
}
