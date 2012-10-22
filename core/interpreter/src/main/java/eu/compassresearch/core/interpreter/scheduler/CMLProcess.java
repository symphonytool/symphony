package eu.compassresearch.core.interpreter.scheduler;

import java.util.List;
import java.util.Set;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.core.interpreter.runtime.ChannelEvent;

public interface CMLProcess {

	public List<ACommunicationAction> WaitForEventOffer();
	
	public void eventOccured(ChannelEvent event);
	
	public Set<String> getChannelSet();
	
	public void start();
	
	public boolean isSkip();
	
	public String getRemainingInterpretationState(boolean expand);
}
