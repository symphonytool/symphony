package eu.compassresearch.core.interpreter.api;

import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.debug.CmlDebugger;

/**
 * Implements a specific strategy for selecting a CMLtransition from a CMLAlphabet
 * 
 * @author akm
 */
public interface SelectionStrategy
{

	/**
	 * This method should receive all the available transitions
	 */
	public void choices(CmlTransitionSet availableTransitions);

	/**
	 * This takes must pick one of the transitions given in the choices method
	 * 
	 * @return the next transition
	 */
	public CmlTransition resolveChoice();

	/**
	 * Initializes the strategy
	 */
	public void initialize(CmlInterpreter interpreter, CmlDebugger debugger);

}
