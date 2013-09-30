package eu.compassresearch.core.interpreter.api;


/**
 * The possible states a CML interpreter can be in: 
 * <p>DEADLOCKED: 				The interpreter has deadlocked
 * <p>FAILED:					The interpreter threw an exception 
 * <p>FINISHED:		 			The interpreter has successfully terminated	 
 * <p>INITIALIZED:  			The interpreter is initialized but not started
 * <p>SUSPENDED: 				The interpreter is suspended, this state enables inspection of processes and actions
 * <p>TERMINATED: 				The interpreter has been stopped by an external user
 * <p>RUNNING: 					The interpreter is running
 * <p>WAITING_FOR_ENVIRONMENT:	THE interpreter is waiting for an external response from the environment
 * @author akm
 *
 */
public enum CmlInterpretationStatus {

	/**
	 * The interpreter is deadlocked
	 */
	DEADLOCKED,         
	/**
	 * The interpreter threw an exception
	 */
	FAILED,
	/**
	 * The interpreter is initialized but not yet started
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
	 * The interpreter has successfully terminated the simulation/animation
	 * of a model
	 */
	FINISHED,
	/**
	 * The interpreter is waiting for the environment to make a choice
	 */
	WAITING_FOR_ENVIRONMENT,
	/**
	 *  The interpreter has been terminated by an external user and has finished the simulation/animation
	 * 	the model. 
	 */
	TERMINATED_BY_USER;
	 		
}
