package eu.compassresearch.core.interpreter.api;

/**
 * Enum of the four possible states an CML interpreter can be in. 
 * <p>
 * The following state are possible
 * <p>RUNNING: 			The interpreter is running
 * <p>TERMINATED:	 	The interpreter has successfully terminated	 
 * <p>DEADLOCKED: 		The interpreter has deadlocked
 * <p>INITIALIZED:  	The interpreter is initialized but not started
 * <p>FAILED:			The interpreter threw an exception 
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
	 * The interpreter is initialized but not started
	 */
	INITIALIZED,			
	/**
	 * The interpreter threw an exception
	 */
	FAILED
}
