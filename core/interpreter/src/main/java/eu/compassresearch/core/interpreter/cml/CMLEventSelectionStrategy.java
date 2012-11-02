package eu.compassresearch.core.interpreter.cml;


public interface CMLEventSelectionStrategy {
	/**
	 * Implements a certain strategy for selecting a between a set of available CMLChannelEvents
	 * @return The selected channel
	 */
	public CMLChannelEvent select(CMLAlphabet availableChannelEvents);
}
