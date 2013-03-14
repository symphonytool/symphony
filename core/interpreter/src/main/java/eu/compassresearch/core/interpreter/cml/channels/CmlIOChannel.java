//package eu.compassresearch.core.interpreter.cml.channels;
//
//import org.overture.interpreter.values.Value;
//
//import eu.compassresearch.core.interpreter.events.ChannelObserver;
//import eu.compassresearch.core.interpreter.events.EventSource;
//
//public interface CmlIOChannel<T extends Value>  extends CmlChannel {
//
//	/**
//	 * Reads a value from the channel
//	 * @return
//	 */
//	public T read();
//	
//	/**
//	 * Register or unregister for the onChannelRead event
//	 * @return The appropriate EventSource for event registration
//	 */
//	public EventSource<ChannelObserver> onChannelRead();
//	
//	
//	/**
//	 * Writes a value to the channel
//	 * @param value
//	 */
//	public void write(T value);
//	
//	/**
//	 * Register or unregister for the onChannelWrite event
//	 * @return The appropriate EventSource for event registration
//	 */
//	public EventSource<ChannelObserver> onChannelWrite();
//	
//}
