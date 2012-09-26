package eu.compassresearch.core.interpreter.scheduler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.overture.ast.lex.Dialect;
import org.overture.config.Settings;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.core.interpreter.runtime.ChannelEvent;
import eu.compassresearch.core.interpreter.util.Pair;

public class CmlScheduler {

	ConcurrentLinkedQueue<CMLProcess> processes;
	ConcurrentLinkedQueue<CMLProcess> executedInCurrentStep;
	ConcurrentLinkedQueue<CMLProcess> addedProcesses;
	private List<ACommunicationAction> trace = new LinkedList<ACommunicationAction>();
	//private final int numberOfThreads = 100;
	//private ExecutorService threadPool;
	private Object sync = new Object();
	private boolean animate = false;
	
	public CmlScheduler()
	{
		processes = new ConcurrentLinkedQueue<CMLProcess>();
		addedProcesses = new ConcurrentLinkedQueue<CMLProcess>();
		executedInCurrentStep = new ConcurrentLinkedQueue<CMLProcess>();
		//threadPool = Executors.newFixedThreadPool(numberOfThreads);
		Settings.dialect = Dialect.VDM_PP;
	}
	
	public CMLProcess addProcess(CMLProcess process)
	{
		synchronized (sync) {
		
			addedProcesses.add(process);
			return process;
		}
	}
		
	public Map<CMLProcess,List<ACommunicationAction>> step()
	{
		HashMap< CMLProcess,List<ACommunicationAction> > allEvents = 
					new HashMap<CMLProcess, List<ACommunicationAction>>();
		
		while(!processes.isEmpty())
		{
			CMLProcess pt = processes.poll();
			
			if(!pt.isSkip()){
				
				List<ACommunicationAction> cActions = pt.WaitForEventOffer();
				
				if(!cActions.isEmpty())
				{
					allEvents.put(pt, cActions);
					executedInCurrentStep.add(pt);
				}
			}
		}
						
		while(!executedInCurrentStep.isEmpty())
		{
			CMLProcess pt = executedInCurrentStep.poll();
													
			processes.add(pt);
		}
		
		return allEvents;
	}
	
	public void handleAddedProcesses()
	{
		while(!addedProcesses.isEmpty())
		{
			CMLProcess pt = addedProcesses.poll();
			processes.add(pt);
			pt.start();
		}
	}
	
	public void printTrace()
	{
		printEvents(trace);
	}
	
	public List<String> getTrace()
	{
		LinkedList<String> outTrace = new LinkedList<String>();
		for(ACommunicationAction a : trace)
		{
			outTrace.add(a.getIdentifier().getName());
		}
		
		return outTrace;
	}
	
	private void printEvents(List<ACommunicationAction> offeredActions)
	{
		for(ACommunicationAction a : offeredActions)
		{
			System.out.print("<" + a.getIdentifier() + ">");
		}
		System.out.println();
	}
	
	public List<Pair<CMLProcess, ChannelEvent>> selectEvent(Map<CMLProcess,List<ACommunicationAction>> availableEvents)
	{
		List<Pair<CMLProcess, ChannelEvent>> res = 
				new LinkedList<Pair<CMLProcess,ChannelEvent>>();
			
		if(animate)
		{
//			for(Entry<CMLProcess,List<ACommunicationAction>> entry : availableEvents.entrySet())
//			{
//				System.out.println("Available events:");
//				System.out.println(entry.getValue().get(0));
//				System.in.read();
//			}
		}
		else
		{
			for(Entry<CMLProcess,List<ACommunicationAction>> entry : availableEvents.entrySet())
			{
				//For now, select a the first event from the set of possible events
				ACommunicationAction event = entry.getValue().get(0);
				
				res.add(new Pair<CMLProcess, ChannelEvent>(entry.getKey(),
															new ChannelEvent(event.getIdentifier().getName())));
				trace.add(event);
			}
		}
			
		return res;
	}
	
	public void start()
	{
		trace = new LinkedList<ACommunicationAction>();
		handleAddedProcesses();
		
		while(!processes.isEmpty())
		{
			handleAddedProcesses();
			
			synchronized (sync) {

				System.out.println("--------begin step---------");
				
				Map<CMLProcess,List<ACommunicationAction>> allEvents = step();
				
				System.out.println("Offered Events:");
								
				for(List<ACommunicationAction> ev : allEvents.values())
					printEvents(ev);
								
				System.out.println();
				
				System.out.println("Current interpretation state:");
				for(Pair<CMLProcess, ChannelEvent> actionToExec :
					selectEvent(allEvents))
				{
					System.out.println(actionToExec.first.getRemainingInterpretationState(true));
					actionToExec.first.eventOccured(actionToExec.second);
					
					System.out.println();
					
					System.out.println("The ENV(Simulator) picks: " + actionToExec.second.getChannelName());
				
					System.out.println();
				}
							
				System.out.println("Trace after step:");
				printTrace();
						
				//System.out.println("--------end step---------");
			}			
		}
	}

}
