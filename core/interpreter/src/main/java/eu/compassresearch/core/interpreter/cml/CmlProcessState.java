package eu.compassresearch.core.interpreter.cml;

public enum CmlProcessState {
	WAIT_EVENT, 	//The process is Waiting for an event
	WAIT_CHILD, 	//The process is waiting for its children to become a desired state 
	RUNNING, 		//The process is currently begin executed
	RUNNABLE, 		//The process is ready to be executed
	INITIALIZED, 	//The process has been created but not yet started
	FINISHED 		//The process has finished
}
