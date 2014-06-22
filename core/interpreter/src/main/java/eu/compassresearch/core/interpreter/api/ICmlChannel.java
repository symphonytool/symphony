package eu.compassresearch.core.interpreter.api;

import java.util.List;

import org.overture.ast.types.PType;

import eu.compassresearch.core.interpreter.api.events.ChannelEventTypeActivity;
import eu.compassresearch.core.interpreter.api.events.ChannelObserver;
import eu.compassresearch.core.interpreter.api.events.EventSource;

public interface ICmlChannel
{

	public String getName();

	/**
	 * Returns the types of the possible values that can be sent on this channel
	 * 
	 * @return
	 */
	public List<PType> getValueTypes();

	/**
	 * Signals that this channel is selected by the environment to all the channel listeners
	 * @param channelActivity 
	 */
	public void signalChannelEvent(ChannelEventTypeActivity channelActivity);

	/**
	 * Register or unregister for the onChannelSignal event
	 * 
	 * @return The appropriate EventSource for event registration
	 */
	public EventSource<ChannelObserver> onChannelEvent();

}
