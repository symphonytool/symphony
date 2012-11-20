package eu.compassresearch.core.interpreter.cml;


public interface CmlCommunicationSelectionStrategy {
	/**
	 * Implements a specific strategy for selecting a CMLCommunication from a CMLAlphabet
	 * @return The selected channel
	 */
	public CmlCommunication select(CmlAlphabet availableChannelEvents);
}
