package eu.compassresearch.core.interpreter.utility.events;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import eu.compassresearch.core.interpreter.CmlRuntime;

public class EventSourceHandler<O,E> implements EventSource<O>{

	protected List<O> observers;
	protected Object source;
	protected EventFireMediator<O,E> eventMediator;
	
	public EventSourceHandler(Object source, EventFireMediator<O,E> eventMediator)
	{
		observers = new LinkedList<O>();
		this.source= source; 
		this.eventMediator = eventMediator;
	}
	
	public EventSourceHandler(EventSourceHandler<O,E> other)
	{
		observers = new LinkedList<O>(other.observers);
		this.source= other.source; 
		this.eventMediator = other.eventMediator;
	}
	
	@Override
	public void registerObserver(O observer)
	{
		observers.add(observer);
		CmlRuntime.logger().finest(observer.toString() + " registered on "+ source.toString() + " for "+observer.getClass().getSimpleName()+" events");
	}
	
	@Override
	public void unregisterObserver(O observer)
	{
		observers.remove(observer);
		CmlRuntime.logger().finest(observer.toString() + " unregistered on "+ source.toString() + " for "+observer.getClass().getSimpleName()+" events");
	}
	
	@Override
	public boolean isRegistered(O observer) {
		return observers.contains(observer);
	}
	
	public void fireEvent(E event)
	{
		for(O observer : new Vector<O>(observers))
		{
			eventMediator.fireEvent(observer, observer, event);
		}
	}
}

//public class EventSourceHandler<E extends Event> implements EventSource<E>{
//
//	protected List<EventObserver<E>> observers;
//	protected Object source;
//	
//	public EventSourceHandler(Object source)
//	{
//		observers = new LinkedList<EventObserver<E>>();
//		this.source= source; 
//	}
//	
//	public void registerObserver(EventObserver<E> observer)
//	{
//		observers.add(observer);
//		CmlRuntime.logger().finest(observer.toString() + " registered on "+ source.toString() + " for "+observer.getClass().getSimpleName()+" events");
//	}
//	
//	public void unregisterObserver(EventObserver<E> observer)
//	{
//		observers.remove(observer);
//		CmlRuntime.logger().finest(observer.toString() + " unregistered on "+ source.toString() + " for "+observer.getClass().getSimpleName()+" events");
//	}
//	
//	public void fireEvent(E event)
//	{
//		for(EventObserver<E> observer : new Vector<EventObserver<E>>(observers))
//		{
//			observer.onEvent(source, event);
//		}
//	}
//}
