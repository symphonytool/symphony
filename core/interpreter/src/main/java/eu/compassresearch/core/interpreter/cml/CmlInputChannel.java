package eu.compassresearch.core.interpreter.cml;

import org.overture.interpreter.values.Value;

public interface CmlInputChannel<T extends Value> extends CmlChannel{
	
	public T read();
	public void registerOnChannelRead(ChannelObserver observer);
	public void unregisterOnChannelRead(ChannelObserver observer);

}
