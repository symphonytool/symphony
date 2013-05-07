package eu.compassresearch.core.interpreter.cml;

import eu.compassresearch.core.interpreter.cml.events.CmlTransition;


public interface CmlEventSelectionStrategy {
	/**
	 * Implements a specific strategy for selecting a CMLCommunication from a CMLAlphabet
	 * @return The selected channel
	 */
	public CmlTransition select(CmlAlphabet availableChannelEvents);
}
