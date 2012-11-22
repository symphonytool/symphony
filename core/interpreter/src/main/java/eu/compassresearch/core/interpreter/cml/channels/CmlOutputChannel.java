package eu.compassresearch.core.interpreter.cml.channels;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.events.ChannelObserver;

public interface CmlOutputChannel<T extends Value> extends CmlChannel {

	public void write(T value);
	public void registerOnChannelWrite(ChannelObserver observer);
	public void unregisterOnChannelWrite(ChannelObserver observer);
	
}
