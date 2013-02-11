package eu.compassresearch.core.interpreter.cml;

import eu.compassresearch.core.interpreter.cml.events.ObservableEvent;


public interface CmlCommunicationSelectionStrategy {
	/**
	 * Implements a specific strategy for selecting a CMLCommunication from a CMLAlphabet
	 * @return The selected channel
	 */
	public ObservableEvent select(CmlAlphabet availableChannelEvents);
}
