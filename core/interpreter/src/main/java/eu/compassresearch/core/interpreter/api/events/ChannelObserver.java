package eu.compassresearch.core.interpreter.api.events;

/**
 * Implementors of this interface can listen for events on a specific channel
 * @author akm
 *
 */
public interface ChannelObserver {

	public void onChannelEvent(Object source, ChannelEvent event);
}
