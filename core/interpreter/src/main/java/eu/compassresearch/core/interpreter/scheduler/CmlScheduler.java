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
import eu.compassresearch.core.interpreter.cml.CMLProcessOld;
import eu.compassresearch.core.interpreter.cml.CMLChannelEvent;
import eu.compassresearch.core.interpreter.runtime.CmlRuntime;
import eu.compassresearch.core.interpreter.util.Pair;

public class CmlScheduler {

	ConcurrentLinkedQueue<CMLProcessOld> processes;
	ConcurrentLinkedQueue<CMLProcessOld> executedInCurrentStep;
	ConcurrentLinkedQueue<CMLProcessOld> addedProcesses;
	private List<ACommunicationAction> trace = new LinkedList<ACommunicationAction>();
	//private final int numberOfThreads = 100;
	//private ExecutorService threadPool;
	private Object sync = new Object();
	
	public CmlScheduler()
	{
		processes = new ConcurrentLinkedQueue<CMLProcessOld>();
		addedProcesses = new ConcurrentLinkedQueue<CMLProcessOld>();
		executedInCurrentStep = new ConcurrentLinkedQueue<CMLProcessOld>();
		//threadPool = Executors.newFixedThreadPool(numberOfThreads);
		Settings.dialect = Dialect.VDM_PP;
	}
	
	public CMLProcessOld addProcess(CMLProcessOld process)
	{
		synchronized (sync) {
		
			addedProcesses.add(process);
			return process;
		}
	}
		
	public Map<CMLProcessOld,List<ACommunicationAction>> step()
	{
		HashMap< CMLProcessOld,List<ACommunicationAction> > allEvents = 
					new HashMap<CMLProcessOld, List<ACommunicationAction>>();
		
		while(!processes.isEmpty())
		{
			CMLProcessOld pt = processes.poll();
			
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
			CMLProcessOld pt = executedInCurrentStep.poll();
													
			processes.add(pt);
		}
		
		return allEvents;
	}
	
	public void handleAddedProcesses()
	{
		while(!addedProcesses.isEmpty())
		{
			CMLProcessOld pt = addedProcesses.poll();
			processes.add(pt);
			pt.start(CmlRuntime.getSupervisorEnvironment());
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
	
	public List<Pair<CMLProcessOld, CMLChannelEvent>> selectEvent(Map<CMLProcessOld,List<ACommunicationAction>> availableEvents)
	{
		List<Pair<CMLProcessOld, CMLChannelEvent>> res = 
				new LinkedList<Pair<CMLProcessOld,CMLChannelEvent>>();

		for(Entry<CMLProcessOld,List<ACommunicationAction>> entry : availableEvents.entrySet())
		{
			//For now, select a the first event from the set of possible events
			ACommunicationAction event = entry.getValue().get(0);

//			res.add(new Pair<CMLProcessOld, CMLChannelEvent>(entry.getKey(),
//					new CMLChannelEvent(event.getIdentifier().getName())));
			trace.add(event);
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
				
				Map<CMLProcessOld,List<ACommunicationAction>> allEvents = step();
				
				System.out.println("Offered Events:");
								
				for(List<ACommunicationAction> ev : allEvents.values())
					printEvents(ev);
								
				System.out.println();
				
				System.out.println("Current interpretation state:");
				for(Pair<CMLProcessOld, CMLChannelEvent> actionToExec :
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
