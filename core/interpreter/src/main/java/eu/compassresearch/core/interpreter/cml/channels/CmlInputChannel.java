package eu.compassresearch.core.interpreter.cml.channels;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.events.ChannelObserver;

public interface CmlInputChannel<T extends Value> extends CmlChannel{
	
	public T read();
	public void registerOnChannelRead(ChannelObserver observer);
	public void unregisterOnChannelRead(ChannelObserver observer);

}
