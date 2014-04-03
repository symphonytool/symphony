package eu.compassresearch.core.interpreter.api.events;

import eu.compassresearch.core.interpreter.api.values.CmlChannel;

/**
 * This represents activity change of CmlInterpreterState in a specific CmlInterpreter instance. This event is fired
 * whenever a CmlInterpreter changes its state.
 * 
 * @author akm
 */
public class ChannelEvent extends Event<CmlChannel>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final private ChannelEventTypeActivity eventType;

	public ChannelEvent(CmlChannel channel, ChannelEventTypeActivity eventType)
	{
		super(channel);
		// this.channel = channel;
		this.eventType = eventType;
	}

	public ChannelEventTypeActivity getEventType()
	{
		return eventType;
	}
}
