package eu.compassresearch.core.interpreter.scheduler;

import java.util.List;
import java.util.Set;

import eu.compassresearch.ast.actions.ACommunicationAction;

public interface CMLProcess {

	public List<ACommunicationAction> WaitForEventOffer();
	
	public void eventOccured(ACommunicationAction event);
	
	public Set<ACommunicationAction> getChannelSet();
	
	public void start();
	
	public boolean isSkip();
	
	public String getRemainingInterpretationState(boolean expand);
}
