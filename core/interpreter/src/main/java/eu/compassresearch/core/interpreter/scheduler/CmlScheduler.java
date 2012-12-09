package eu.compassresearch.core.interpreter.scheduler;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourSignal;
import eu.compassresearch.core.interpreter.cml.CmlProcess;
import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.events.CmlProcessObserver;
import eu.compassresearch.core.interpreter.events.CmlProcessStateEvent;
import eu.compassresearch.core.interpreter.events.TraceEvent;
import eu.compassresearch.core.interpreter.runtime.CmlRuntime;

//
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.concurrent.ConcurrentLinkedQueue;
//
//import org.overture.ast.lex.Dialect;
//import org.overture.config.Settings;
//
//import eu.compassresearch.ast.actions.ACommunicationAction;
//import eu.compassresearch.core.interpreter.cml.CMLProcessOld;
//import eu.compassresearch.core.interpreter.cml.CMLChannelEvent;
//import eu.compassresearch.core.interpreter.runtime.CmlRuntime;
//import eu.compassresearch.core.interpreter.util.Pair;
//
public class CmlScheduler implements CmlProcessObserver {

	List<CmlProcess> running = new LinkedList<CmlProcess>();
	List<CmlProcess> waiting = new LinkedList<CmlProcess>();
	List<CmlProcess> finished = new LinkedList<CmlProcess>();
	
	private CmlSupervisorEnvironment sve;
	
	public CmlScheduler(CmlSupervisorEnvironment sve)
	{
		this.sve = sve;
	}
	
	public void addProcess(CmlProcess process)
	{
		if(process.waiting())
			waiting.add(process);
		else
			running.add(process);
	}
	
	public void start() throws AnalysisException {
		
		//Active state
		while( waiting.size() > 0 || running.size() > 0)
		{
			
			CmlRuntime.logger().fine("----------------step----------------");
			
			//execute each of the running pupils until they are either finished or in wait state
			for(Iterator<CmlProcess> iterator = new Vector<CmlProcess>(running).iterator(); iterator.hasNext();)
			{
				CmlProcess p = iterator.next();
				while(!p.finished() && 
						!p.waiting())
				{
					CmlBehaviourSignal signal = p.execute(sve);
					
					if(signal != CmlBehaviourSignal.EXEC_SUCCESS)
						throw new RuntimeException("Change this!!!!, but now that you haven't changed this yet, " +
								"then let me tell you that the return CMLBehaviourSignal was unsuccesful");

					CmlRuntime.logger().fine("current trace of '"+p+"': " + p.getTraceModel());
					CmlRuntime.logger().fine("next: " + p.nextStepToString());
				}
			}

			//Since we can have newly created children, must might have to go back another round before inspecting
			if(running.size() == 0)
			{

				/**
				 * Now, all the processes are sleeping tight, so the selected decision strategy needs to 
				 * decide which event should occur and wake them up.
				 */
				for(Iterator<CmlProcess> iterator = new Vector<CmlProcess>(waiting).iterator(); iterator.hasNext();)
				{
					CmlProcess p = iterator.next();
					if(p.level() == 0)
					{
						CmlAlphabet alpha = p.inspect();

						if(alpha.isEmpty())
							throw new RuntimeException("Change this!!!!, but now that you " +
									"haven't changed this yet a deadlock has occured");
						else
						{
							//Select and set the communication event
							sve.setSelectedCommunication(sve.decisionFunction().select(p.inspect()));
							//signal all the processes that are listening for this channel
							sve.selectedCommunication().getChannel().signal();
						}
					}
				}
			}
		}
	}
	
	/**
	 * CmlProcessObserver interface methods
	 */
	
	@Override
	public void onStateChange(CmlProcessStateEvent stateEvent) {

		switch(stateEvent.getFrom())
		{
		case WAIT_CHILD:
		case WAIT_EVENT:
			waiting.remove(stateEvent.getSource());
			break;
		case INITIALIZED:
		case RUNNABLE:
		case RUNNING:
			running.remove(stateEvent.getSource());
			break;
		}
		
		switch(stateEvent.getTo())
		{
		case WAIT_CHILD:
		case WAIT_EVENT:
			waiting.add(stateEvent.getSource());
			break;
		case INITIALIZED:
		case RUNNABLE:
		case RUNNING:
			running.add(stateEvent.getSource());
			break;
		case FINISHED:
			finished.add(stateEvent.getSource());
			break;
		}
	}
	
	
	@Override
	public void onTraceChange(TraceEvent traceEvent) {
		//TODO: here the presenting logic should be for running process
		//CmlProcess p = traceEvent.getSource();
		//CmlRuntime.logger().fine("current trace: " + p.getTraceModel());
		//CmlRuntime.logger().fine("next: " + p.nextStepToString());
	}
	
//		
//	public Map<CMLProcessOld,List<ACommunicationAction>> step()
//	{
//		HashMap< CMLProcessOld,List<ACommunicationAction> > allEvents = 
//					new HashMap<CMLProcessOld, List<ACommunicationAction>>();
//		
//		while(!processes.isEmpty())
//		{
//			CMLProcessOld pt = processes.poll();
//			
//			if(!pt.isSkip()){
//				
//				List<ACommunicationAction> cActions = pt.WaitForEventOffer();
//				
//				if(!cActions.isEmpty())
//				{
//					allEvents.put(pt, cActions);
//					executedInCurrentStep.add(pt);
//				}
//			}
//		}
//						
//		while(!executedInCurrentStep.isEmpty())
//		{
//			CMLProcessOld pt = executedInCurrentStep.poll();
//													
//			processes.add(pt);
//		}
//		
//		return allEvents;
//	}
//	
//	public void handleAddedProcesses()
//	{
//		while(!addedProcesses.isEmpty())
//		{
//			CMLProcessOld pt = addedProcesses.poll();
//			processes.add(pt);
//			pt.start(CmlRuntime.getSupervisorEnvironment());
//		}
//	}
//	
//	public List<Pair<CMLProcessOld, CMLChannelEvent>> selectEvent(Map<CMLProcessOld,List<ACommunicationAction>> availableEvents)
//	{
//		List<Pair<CMLProcessOld, CMLChannelEvent>> res = 
//				new LinkedList<Pair<CMLProcessOld,CMLChannelEvent>>();
//
//		for(Entry<CMLProcessOld,List<ACommunicationAction>> entry : availableEvents.entrySet())
//		{
//			//For now, select a the first event from the set of possible events
//			ACommunicationAction event = entry.getValue().get(0);
//
////			res.add(new Pair<CMLProcessOld, CMLChannelEvent>(entry.getKey(),
////					new CMLChannelEvent(event.getIdentifier().getName())));
//			trace.add(event);
//		}
//
//		return res;
//	}
//	
//	public void start()
//	{
//		trace = new LinkedList<ACommunicationAction>();
//		handleAddedProcesses();
//		
//		while(!processes.isEmpty())
//		{
//			handleAddedProcesses();
//			
//			synchronized (sync) {
//
//				System.out.println("--------begin step---------");
//				
//				Map<CMLProcessOld,List<ACommunicationAction>> allEvents = step();
//				
//				System.out.println("Offered Events:");
//								
//				for(List<ACommunicationAction> ev : allEvents.values())
//					printEvents(ev);
//								
//				System.out.println();
//				
//				System.out.println("Current interpretation state:");
//				for(Pair<CMLProcessOld, CMLChannelEvent> actionToExec :
//					selectEvent(allEvents))
//				{
//					System.out.println(actionToExec.first.getRemainingInterpretationState(true));
//					actionToExec.first.eventOccured(actionToExec.second);
//					
//					System.out.println();
//					
//					System.out.println("The ENV(Simulator) picks: " + actionToExec.second.getChannel());
//				
//					System.out.println();
//				}
//							
//				System.out.println("Trace after step:");
//				printTrace();
//						
//				//System.out.println("--------end step---------");
//			}			
//		}
//	}
//
}
