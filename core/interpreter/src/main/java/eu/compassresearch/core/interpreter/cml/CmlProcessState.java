package eu.compassresearch.core.interpreter.cml;

/**
 * This enum contains the state that a process can be in at any given time.
 * @author akm
 *
 */
public enum CmlProcessState {
	WAIT, 	//Waiting for an event occur
	INITIALIZED, 	//Has been created but not yet started
	FINISHED, 		//Successfully finished
	//ABORTED,
	STOPPED         //Deadlocked
}
