package eu.compassresearch.core.interpreter.events;

public interface EventSource<O> {

	public void registerObserver(O observer);
	
	public void unregisterObserver(O observer);
}
