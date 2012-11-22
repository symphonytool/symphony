package eu.compassresearch.core.interpreter.runtime;

import java.util.Set;

import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlCommunicationSelectionStrategy;
import eu.compassresearch.core.interpreter.cml.events.CmlCommunicationEvent;
/**
 * This class implements a random selection CMLCommunicaiton of the alphabet 
 * @author akm
 *
 */
public class RandomSelectionStrategy implements
		CmlCommunicationSelectionStrategy {

	@Override
	public CmlCommunicationEvent select(CmlAlphabet availableChannelEvents) {
		
		Set<CmlCommunicationEvent> comms = availableChannelEvents.getCommunicationEvents();
		CmlCommunicationEvent selectedComm = null;
		
		if(!comms.isEmpty())
			selectedComm = availableChannelEvents.getCommunicationEvents().iterator().next();
		CmlRuntime.logger().fine("Available events " + availableChannelEvents.getCommunicationEvents());
		CmlRuntime.logger().fine("The supervisor environment picks : " + selectedComm);
		
		return selectedComm;
	}

}
