package eu.compassresearch.core.interpreter.util;

public interface EventObserver<E> {

	public void onEvent(Object source, E event);
}
