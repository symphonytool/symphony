package eu.compassresearch.core.interpreter.events;

public interface EventObserver<E extends Event> {

	public void onEvent(Object source, E event);
}
