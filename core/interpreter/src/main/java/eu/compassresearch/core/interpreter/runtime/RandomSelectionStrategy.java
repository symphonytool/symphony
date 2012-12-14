package eu.compassresearch.core.interpreter.runtime;

import java.util.Set;

import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlCommunicationSelectionStrategy;
import eu.compassresearch.core.interpreter.cml.events.CmlCommunicationEvent;
import eu.compassresearch.core.interpreter.cml.events.ObservableCmlEvent;
/**
 * This class implements a random selection CMLCommunicaiton of the alphabet 
 * @author akm
 *
 */
public class RandomSelectionStrategy implements
		CmlCommunicationSelectionStrategy {

	@Override
	public ObservableCmlEvent select(CmlAlphabet availableChannelEvents) {
		
		Set<ObservableCmlEvent> comms = availableChannelEvents.getObservableEvents();
		ObservableCmlEvent selectedComm = null;
		
		if(!comms.isEmpty())
			selectedComm = availableChannelEvents.getObservableEvents().iterator().next();
		CmlRuntime.logger().fine("Available events " + availableChannelEvents.getObservableEvents());
		CmlRuntime.logger().fine("The supervisor environment picks : " + selectedComm);
		
		return selectedComm;
	}

}
