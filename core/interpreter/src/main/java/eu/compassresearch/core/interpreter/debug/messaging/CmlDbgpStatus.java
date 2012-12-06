package eu.compassresearch.core.interpreter.debug.messaging;

public enum CmlDbgpStatus
{
	STARTING, 			//Fires when the interpreter is about to start
	RUNNING, 			//Fires when the interpreter is running
	BREAK, 				//Fires when the interpreter reached a breakpoint
	CHOICE,             //Fires when the interpreter is waiting for a choice
	STOPPING, 			//Fires when the interpreter is about to stop
	STOPPED, 			//Fires when the interpreter has stopped
	CONNECTION_CLOSED; 	//Fires when the interpreter connection has closed

	@Override
	public String toString()
	{
		return super.toString().toLowerCase();
	}
}