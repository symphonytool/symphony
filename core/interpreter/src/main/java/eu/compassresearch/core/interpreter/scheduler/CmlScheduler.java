package eu.compassresearch.core.interpreter.scheduler;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.runtime.Context;

public class CmlScheduler {

	List<ProcessThread> processes;
	private final int numberOfThreads = 100;
	private ExecutorService threadPool;
	//private List<Future> futures;
	
	
	public CmlScheduler()
	{
		processes = new LinkedList<ProcessThread>();
		threadPool = Executors.newFixedThreadPool(numberOfThreads);
	}
	
	public void addExecutionThread(ProcessThread executionThread)
	{
		processes.add(executionThread);
	}
	
	public void addProcessThread(PProcess process, Context context)
	{
		ProcessThread processThread = new ProcessThread(context, process); 
		context.setProcessThread(processThread);
		processes.add(processThread);
		threadPool.execute(processThread);
		threadPool.shutdown();
		//threadPool.submit(task)
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
