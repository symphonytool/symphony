package eu.compassresearch.core.interpreter.api;

/**
 * Enum of the four possible states an CML interpreter can be in.
 * @author akm
 *
 */
public enum CmlInterpreterState {

	/**
	 * The interpreter is running
	 */
	RUNNING, 			
	/**
	 * The interpreter has successfully terminated
	 */
	TERMINATED,         
	/**
	 * The interpreter has deadlocked
	 */
	DEADLOCKED,         
	/**
	 * The interpreter is initialized
	 */
	INITIALIZED,			
	/**
	 * The interpreter threw an exception
	 */
	FAILED
}
