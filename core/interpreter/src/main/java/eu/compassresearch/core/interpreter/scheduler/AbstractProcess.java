package eu.compassresearch.core.interpreter.scheduler;

import java.util.List;
import java.util.concurrent.SynchronousQueue;

import eu.compassresearch.ast.actions.ACommunicationAction;

public abstract class AbstractProcess implements Process {

	protected SynchronousQueue<List<ACommunicationAction>> offeredEvents = new SynchronousQueue<List<ACommunicationAction>>();
	protected SynchronousQueue<ACommunicationAction> recievedEvent = new SynchronousQueue<ACommunicationAction>();
	
	@Override
	public List<ACommunicationAction> WaitForEventOffer()
	{
		List<ACommunicationAction> events = null;
		try {
			events = offeredEvents.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return events;
	}
	
	@Override
	public void eventOccured(ACommunicationAction event)
	{
		try {
			recievedEvent.put(event);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
