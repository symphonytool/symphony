package eu.compassresearch.core.interpreter.cml;

/**
 * This enum contains the state that a process can be in at any given time.
 * @author akm
 *
 */
public enum CmlProcessState {
	WAIT_EVENT, 	//Waiting for an event occur
	WAIT_CHILD, 	//Waiting for its children to become a desired state 
	RUNNING, 		//Is currently begin executed
	RUNNABLE, 		//Is ready to be executed
	INITIALIZED, 	//Has been created but not yet started
	FINISHED, 		//Successfully finished
	//ABORTED,
	STOPPED         //Deadlocked
}
