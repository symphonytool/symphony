package eu.compassresearch.core.interpreter.events;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import eu.compassresearch.core.interpreter.runtime.CmlRuntime;

public class EventSourceHandler<E extends Event> implements EventSource<E>{

	protected List<EventObserver<E>> observers;
	protected Object source;
	
	public EventSourceHandler(Object source)
	{
		observers = new LinkedList<EventObserver<E>>();
		this.source= source; 
	}
	
	public void registerObserver(EventObserver<E> observer)
	{
		observers.add(observer);
		CmlRuntime.logger().finest(observer.toString() + " registered on "+ source.toString() + " for "+observer.getClass().getSimpleName()+" events");
	}
	
	public void unregisterObserver(EventObserver<E> observer)
	{
		observers.remove(observer);
		CmlRuntime.logger().finest(observer.toString() + " unregistered on "+ source.toString() + " for "+observer.getClass().getSimpleName()+" events");
	}
	
	public void fireEvent(E event)
	{
		for(EventObserver<E> observer : new Vector<EventObserver<E>>(observers))
		{
			observer.onEvent(source, event);
		}
	}
}
