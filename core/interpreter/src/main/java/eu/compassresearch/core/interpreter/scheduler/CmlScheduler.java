package eu.compassresearch.core.interpreter.scheduler;

import java.util.List;

public class CmlScheduler {

	List<ExecutionThread> allProcesses;
	
	List<ExecutionThread> activeProcesses;
	
	public CmlScheduler()
	{
		
	}
	
	
	public boolean step()
	{
		return false;
	}
	
	public boolean start()
	{
		return false;	
		
	}
}
