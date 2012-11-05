package eu.compassresearch.core.interpreter.cml;


public interface CMLCommunicationSelectionStrategy {
	/**
	 * Implements a specific strategy for selecting a CMLCommunication from a CMLAlphabet
	 * @return The selected channel
	 */
	public CMLCommunication select(CMLAlphabet availableChannelEvents);
}
