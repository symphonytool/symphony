package eu.compassresearch.core.interpreter.cml;

/**
 * This enum contains the state that a process can be in at any given time.
 * @author akm
 *
 */
public enum CmlProcessState {
	WAIT_EVENT, 	//The process is Waiting for an event
	WAIT_CHILD, 	//The process is waiting for its children to become a desired state 
	RUNNING, 		//The process is currently begin executed
	RUNNABLE, 		//The process is ready to be executed
	INITIALIZED, 	//The process has been created but not yet started
	FINISHED 		//The process has finished
}
