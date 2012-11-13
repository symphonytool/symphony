package eu.compassresearch.core.interpreter.cml;

import java.util.Set;

import eu.compassresearch.core.interpreter.runtime.CmlRuntime;
/**
 * This class implements a random selection CMLCommunicaiton of the alphabet 
 * @author akm
 *
 */
public class RandomSelectionStrategy implements
		CmlCommunicationSelectionStrategy {

	@Override
	public CmlCommunication select(CmlAlphabet availableChannelEvents) {
		
		Set<CmlCommunication> comms = availableChannelEvents.getCommunicationEvents();
		CmlCommunication selectedComm = null;
		
		if(!comms.isEmpty())
			selectedComm = availableChannelEvents.getCommunicationEvents().iterator().next();
		CmlRuntime.logger().fine("Available events " + availableChannelEvents.getCommunicationEvents());
		CmlRuntime.logger().fine("The supervisor environment picks : " + selectedComm);
		
		return selectedComm;
	}

}
