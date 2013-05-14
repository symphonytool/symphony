package eu.compassresearch.core.interpreter.api;

import eu.compassresearch.core.interpreter.cml.core.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.transitions.CmlTransition;

/**
 * Implements a specific strategy for selecting a CMLtransition from a CMLAlphabet
 * @author akm
 *
 */
public interface SelectionStrategy {
	/**
	 * Implements a specific strategy for selecting a CMLtransition from a CMLAlphabet
	 * @return The selected transition to be performed
	 */
	public CmlTransition select(CmlAlphabet availableTransitions);
}
