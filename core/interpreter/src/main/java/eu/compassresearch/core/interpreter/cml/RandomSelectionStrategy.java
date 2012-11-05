package eu.compassresearch.core.interpreter.cml;

import java.util.Set;
/**
 * This class implements a random selection CMLCommunicaiton of the alphabet 
 * @author akm
 *
 */
public class RandomSelectionStrategy implements
		CMLCommunicationSelectionStrategy {

	@Override
	public CMLCommunication select(CMLAlphabet availableChannelEvents) {

		
		Set<CMLCommunication> comms = availableChannelEvents.getAllCommunications();
		CMLCommunication selectedComm = null;
		
		if(!comms.isEmpty())
			selectedComm = availableChannelEvents.getAllCommunications().iterator().next();
		
		return selectedComm;
	}

}
