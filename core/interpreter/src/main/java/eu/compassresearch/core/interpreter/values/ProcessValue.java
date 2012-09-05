package eu.compassresearch.core.interpreter.values;

import java.util.LinkedList;
import java.util.List;

import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.core.interpreter.runtime.Context;
import eu.compassresearch.core.interpreter.scheduler.CMLProcess;

public class ProcessValue extends Value {

	private Context currentContext = null;
	private List<ACommunicationAction> offeredEvents = new LinkedList<ACommunicationAction>();
	private CMLProcess process = null;
		
	public ProcessValue()
	{}
	
	public ProcessValue(List<ACommunicationAction> availableEvents, Context currentContext)
	{
		this.offeredEvents = availableEvents;
		this.currentContext = currentContext;
	}
	
	public ProcessValue(ACommunicationAction availableEvent, Context currentContext)
	{
		this.offeredEvents = new LinkedList<ACommunicationAction>();
		this.offeredEvents.add(availableEvent);
		this.currentContext = currentContext;
		
	}
		
	public ProcessValue(CMLProcess process, Context currentContext)
	{
		this.process = process;
		this.currentContext = currentContext;	
	}
	
	public Context getCurrentContext() {
		return currentContext;
	}
	
	public List<ACommunicationAction> getOfferedEvents() {
		
		if (this.process != null)
			return this.process.WaitForEventOffer();
		else
			return offeredEvents;
	}

	public void setOfferedEvents(List<ACommunicationAction> offeredEvents) {
		this.offeredEvents = offeredEvents;
	}
			
	public CMLProcess getProcess()
	{
		return this.process;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "processValue";
	}

	@Override
	public boolean equals(Object other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		
		return 0;
	}

	@Override
	public String kind() {
		
		return toString();
	}

	@Override
	public Object clone() {
		
		return new ProcessValue();
	}

}
