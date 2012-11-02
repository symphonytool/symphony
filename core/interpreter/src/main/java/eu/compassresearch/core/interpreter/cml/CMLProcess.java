package eu.compassresearch.core.interpreter.cml;

import java.util.List;
import java.util.Set;

import eu.compassresearch.ast.actions.ACommunicationAction;

public interface CMLProcess {

	public List<ACommunicationAction> WaitForEventOffer();
	
	public void eventOccured(CMLChannelEvent event);
	
	public Set<String> getChannelSet();
	
	public void start();
	
	public boolean isSkip();
	
	public String getRemainingInterpretationState(boolean expand);
}
