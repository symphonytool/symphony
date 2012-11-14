package eu.compassresearch.core.interpreter.cml.channels;

import eu.compassresearch.core.interpreter.events.ChannelObserver;

/**
 * A Channel that are able to signal, thus only synchronization
 * @author akm
 *
 */
public interface CmlChannelSignal extends CmlChannel {

	/**
	 * Signals to the channel listeners that they can now proceed 
	 */
	public void signal();
	/**
	 * Register a observer on this channel
	 * @param observer
	 */
	public void registerOnChannelSignal(ChannelObserver observer);
	/**
	 * Unregisters an observer on the channel
	 * @param observer
	 */
	public void unregisterOnChannelSignal(ChannelObserver observer);
}
