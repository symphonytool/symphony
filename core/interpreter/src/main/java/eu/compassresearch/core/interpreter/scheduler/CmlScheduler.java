package eu.compassresearch.core.interpreter.scheduler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.runtime.Context;
import eu.compassresearch.core.interpreter.util.Pair;

public class CmlScheduler {

	ConcurrentLinkedQueue<ProcessThread> processes;
	ConcurrentLinkedQueue<ProcessThread> executedInCurrentStep;
	ConcurrentLinkedQueue<ProcessThread> addedProcesses;
	private List<ACommunicationAction> trace = new LinkedList<ACommunicationAction>();
	//private final int numberOfThreads = 100;
	//private ExecutorService threadPool;
	//private List<Future> futures;
	private Object sync = new Object();
	
	public CmlScheduler()
	{
		processes = new ConcurrentLinkedQueue<ProcessThread>();
		addedProcesses = new ConcurrentLinkedQueue<ProcessThread>();
		executedInCurrentStep = new ConcurrentLinkedQueue<ProcessThread>();
		//threadPool = Executors.newFixedThreadPool(numberOfThreads);
	}
	
	public ProcessThread addProcessThread(PProcess process, Context context)
	{
		synchronized (sync) {
		
			ProcessThread processThread = new ProcessThread(context, process); 
			context.setProcessThread(processThread);
			addedProcesses.add(processThread);
			
			return processThread;
		}
	}
		
	public Map<ProcessThread,List<ACommunicationAction>> step()
	{
		HashMap< ProcessThread,List<ACommunicationAction> > allEvents = 
					new HashMap<ProcessThread, List<ACommunicationAction>>();
		
		while(!processes.isEmpty())
		{
			ProcessThread pt = processes.poll();
			
			if(pt.t.isAlive()){
				
				List<ACommunicationAction> cActions = pt.WaitForEventOffer();
				
				if(!cActions.isEmpty())
				{
					allEvents.put(pt, cActions);
					//ACommunicationAction a = selectEvent(cActions);
					//pt.eventOccured(a);
					executedInCurrentStep.add(pt);
				}
			}
		}
						
		while(!executedInCurrentStep.isEmpty())
		{
			ProcessThread pt = executedInCurrentStep.poll();
													
			processes.add(pt);
		}
		
		return allEvents;
	}
	
	public void handleAddedProcesses()
	{
		while(!addedProcesses.isEmpty())
		{
			ProcessThread pt = addedProcesses.poll();
			processes.add(pt);
			pt.t.start();
		}
	}
	
	public void printTrace()
	{
		for(ACommunicationAction a : trace)
		{
			System.out.print("<" + a.getIdentifier() + ">");
		}
		System.out.println();
	}
	
	public void printEvents(List<ACommunicationAction> offeredActions)
	{
		for(ACommunicationAction a : offeredActions)
		{
			System.out.print("<" + a.getIdentifier() + ">");
		}
		System.out.println();
	}
			
	
	public List<Pair<ProcessThread, ACommunicationAction>> selectEvent(Map<ProcessThread,List<ACommunicationAction>> availableEvents)
	{
		List<Pair<ProcessThread, ACommunicationAction>> res = 
				new LinkedList<Pair<ProcessThread,ACommunicationAction>>();
		
						
		for(Entry<ProcessThread,List<ACommunicationAction>> entry : availableEvents.entrySet())
		{
			//For now, select a random event from the set of possible events
			ACommunicationAction event = entry.getValue().get(0);
			res.add(new Pair<ProcessThread, ACommunicationAction>(entry.getKey(),event));
			trace.add(event);
		}
			
		return res;
	}
	
	public void start()
	{
		handleAddedProcesses();
		
		while(!processes.isEmpty())
		{
			handleAddedProcesses();
			
			synchronized (sync) {

				System.out.println("--------begin step---------");
				
				Map<ProcessThread,List<ACommunicationAction>> allEvents = step();
				
				System.out.println("Offered Events:");
				
				
				for(List<ACommunicationAction> ev : allEvents.values())
					printEvents(ev);
				
				
				for(Pair<ProcessThread, ACommunicationAction> actionToExec :
					selectEvent(allEvents))
				{
					actionToExec.first.eventOccured(actionToExec.second);
				}
											
				
				System.out.println("Current Trace:");
				printTrace();
						
				System.out.println("--------end step---------");
			}			
		}
	}

}
