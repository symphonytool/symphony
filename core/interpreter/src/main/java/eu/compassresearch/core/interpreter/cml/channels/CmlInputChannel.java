package eu.compassresearch.core.interpreter.cml.channels;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.events.ChannelObserver;
import eu.compassresearch.core.interpreter.events.EventSource;

public interface CmlInputChannel<T extends Value> extends CmlChannel{
	
	public T read();
	
	/**
	 * Register or unregister for the onChannelRead event
	 * @return The appropriate EventSource for event registration
	 */
	public EventSource<ChannelObserver> onChannelRead();
}
