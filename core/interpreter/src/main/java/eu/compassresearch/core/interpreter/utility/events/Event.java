package eu.compassresearch.core.interpreter.utility.events;

import java.util.LinkedList;
import java.util.List;

public abstract class Event<T> {

	protected final List<T> sources;
	
	public Event(T source)
	{
		sources = new LinkedList<T>();
		this.sources.add(0,source);
	}

	public Event(List<T> sourceList)
	{
		this.sources = sourceList;
	}
	
	public T getSource() {
		return sources.get(0);
	}
	
	public List<T> getSources() {
		return new LinkedList<T>(sources);
	}
	
	public boolean isRedirectedEvent()
	{
		return sources.size() > 1;
	}
}
