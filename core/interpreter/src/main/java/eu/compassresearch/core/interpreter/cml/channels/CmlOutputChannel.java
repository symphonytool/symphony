package eu.compassresearch.core.interpreter.cml.channels;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.events.ChannelObserver;
import eu.compassresearch.core.interpreter.events.EventSource;

public interface CmlOutputChannel<T extends Value> extends CmlChannel {

	public void write(T value);
	
	/**
	 * Register or unregister for the onChannelWrite event
	 * @return The appropriate EventSource for event registration
	 */
	public EventSource<ChannelObserver> onChannelWrite();
	
}
