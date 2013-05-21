package eu.compassresearch.core.interpreter.debug;

import java.rmi.Remote;

import eu.compassresearch.core.interpreter.api.CmlInterpreter;

public interface CmlDebugger extends Remote {

	public void initialize() throws Exception;
	
	public void start(DebugMode mode, CmlInterpreter cmlInterpreter);
	
	//public void stop();
	
	//public CmlDbgpStatus getStatus();
	
	//public void suspend();
}
