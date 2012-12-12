package eu.compassresearch.core.interpreter.events;

public interface EventSource<E extends Event> {

	public void registerObserver(EventObserver<E> observer);
	
	public void unregisterObserver(EventObserver<E> observer);
}
