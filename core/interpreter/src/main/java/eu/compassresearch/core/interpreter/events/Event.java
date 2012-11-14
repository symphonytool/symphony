package eu.compassresearch.core.interpreter.events;

public abstract class Event<T> {

	protected final T source;
	
	public Event(T source)
	{
		this.source = source;
	}
	
	public T getSource() {
		return source;
	}
}
