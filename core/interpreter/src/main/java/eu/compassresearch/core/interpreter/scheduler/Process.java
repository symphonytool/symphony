package eu.compassresearch.core.interpreter.scheduler;

import java.util.List;

import eu.compassresearch.ast.actions.ACommunicationAction;

public abstract class Process {

	protected List<ACommunicationAction> externalAcceptedEvents;
	
	public List<ACommunicationAction> getAcceptedEvents()
	{
		return externalAcceptedEvents;
	}
	
	public abstract boolean isStuck();
	
	public abstract boolean step();
	
	
}
