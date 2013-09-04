package eu.compassresearch.core.interpreter.api.behaviour;

/**
 * This possible states that a CmlBehavior can be in at any given time.
 * @author akm
 *
 */
public enum CmlBehaviorState {
	/**
	 * Waiting for an event to occur
	 */
	WAIT, 	
	/**
	 * Has been created but is not yet started
	 */
	INITIALIZED, 	
	/**
	 * Successfully terminated
	 */
	FINISHED, 		
	/**
	 * The process is deadlocked
	 */
	STOPPED,
	/**
	 * An error occurred
	 */
	ERROR,
}
