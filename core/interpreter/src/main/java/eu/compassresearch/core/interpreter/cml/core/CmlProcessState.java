package eu.compassresearch.core.interpreter.cml.core;

/**
 * This possible states that a process can be in at any given time.
 * @author akm
 *
 */
public enum CmlProcessState {
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
	//ABORTED,
	/**
	 * The process is deadlocked
	 */
	STOPPED         
}
