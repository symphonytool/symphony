package eu.compassresearch.core.interpreter.scheduler;

import java.util.List;

import eu.compassresearch.ast.actions.ACommunicationAction;

public interface Process {

	public List<ACommunicationAction> WaitForEventOffer();
	
	public void eventOccured(ACommunicationAction event);
	
}
