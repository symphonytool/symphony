package eu.compassresearch.core.interpreter.util;

import java.util.LinkedList;
import java.util.List;

public class EventSource<O extends EventObserver<E>,E> {

	protected List<O> observers;
	protected Object source;
	
	public EventSource(Object source)
	{
		observers = new LinkedList<O>();
		this.source= source; 
	}
	
	public void registerObserver(O observer)
	{
		observers.add(observer);
	}
	
	public void unregisterObserver(O observer)
	{
		observers.remove(observer);
	}
	
	public void fireEvent(E event)
	{
		for(O observer : observers)
		{
			observer.onEvent(source, event);
		}
	}
}
