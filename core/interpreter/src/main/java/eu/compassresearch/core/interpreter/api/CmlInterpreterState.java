package eu.compassresearch.core.interpreter.api;


/**
 * Enum of the possible states an CML interpreter can be in. 
 * <p>
 * The following state are possible
 * <p>RUNNING: 					The interpreter is running
 * <p>TERMINATED:	 			The interpreter has successfully terminated	 
 * <p>DEADLOCKED: 				The interpreter has deadlocked
 * <p>INITIALIZED:  			The interpreter is initialized but not started
 * <p>FAILED:					The interpreter threw an exception 
 * <p>SUSPENDED: 				The interpreter is suspended, this state enables inspection of processes and actions
 * <p>WAITING_FOR_ENVIRONMENT:	THE interpreter is waiting for an external response from the environment
 * @author akm
 *
 */
public enum CmlInterpreterState{

	/**
	 * The interpreter has deadlocked
	 */
	DEADLOCKED,         
	/**
	 * The interpreter threw an exception
	 */
	FAILED,
	/**
	 * The interpreter is initialized but not started
	 */
	INITIALIZED,
	/**
	 * The interpreter is running
	 */
	RUNNING,
	/**
	 * The interpreter is suspended	 
	 */
	SUSPENDED,
	/**
	 * The interpreter has successfully terminated
	 */
	TERMINATED,
	
	/**
	 * The interpreter is waiting for the environment to make a choice
	 */
	WAITING_FOR_ENVIRONMENT
	 		
}
