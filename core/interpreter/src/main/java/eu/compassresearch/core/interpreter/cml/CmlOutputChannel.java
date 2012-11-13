package eu.compassresearch.core.interpreter.cml;

import org.overture.interpreter.values.Value;

public interface CmlOutputChannel<T extends Value> extends CmlChannel {

	public void write(T value);
	public void registerOnChannelWrite(ChannelObserver observer);
	public void unregisterOnChannelWrite(ChannelObserver observer);
	
}
