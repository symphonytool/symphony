package eu.compassresearch.core.interpreter.events;

public interface ChannelObserver {

	public void onChannelEvent(Object source, CmlChannelEvent event);
}
