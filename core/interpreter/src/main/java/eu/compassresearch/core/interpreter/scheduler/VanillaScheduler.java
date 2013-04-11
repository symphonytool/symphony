package eu.compassresearch.core.interpreter.scheduler;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.overture.interpreter.runtime.Context;

import eu.compassresearch.core.interpreter.CmlRuntime;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourSignal;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.CmlProcessState;
import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.cml.CmlTrace;
import eu.compassresearch.core.interpreter.cml.events.CmlEvent;
import eu.compassresearch.core.interpreter.events.CmlProcessStateEvent;
import eu.compassresearch.core.interpreter.events.CmlProcessStateObserver;

public class VanillaScheduler implements CmlProcessStateObserver , CmlScheduler{

	List<CmlBehaviourThread> running = new LinkedList<CmlBehaviourThread>();
	List<CmlBehaviourThread> waiting = new LinkedList<CmlBehaviourThread>();
	List<CmlBehaviourThread> finished = new LinkedList<CmlBehaviourThread>();
	List<CmlBehaviourThread> deadlocked = new LinkedList<CmlBehaviourThread>();
	
	private SchedulingPolicy policy;
	private boolean stopped = false;
	
	public VanillaScheduler(SchedulingPolicy policy)
	{
		this.policy = policy; 
	}
	
	@Override
	public void addProcess(CmlBehaviourThread process)
	{
		process.onStateChanged().registerObserver(this);
		if(process.waiting())
			waiting.add(process);
		else
			running.add(process);
	}
	
//	@Override
//	public void removeProcess(CmlProcess process)
//	{
//		process.onStateChanged().unregisterObserver(this);
//		if(process.running())
//			running.remove(process);
//		else if(process.waiting())
//			waiting.remove(process);
//		else 
//			finished.remove(process);
//			
//	}
	
	@Override
	public void clearProcesses()
	{
		running.clear();
		waiting.clear();
		finished.clear();
		deadlocked.clear();
	}
	
	/**
	 * Finds the top-level process
	 * @return The top-level process
	 */
	private CmlBehaviourThread getTopLevelProcess()
	{
		List<CmlBehaviourThread> foundProcesses = new LinkedList<CmlBehaviourThread>();
		
		for(CmlBehaviourThread p : getAllProcesses())
		{
			if(p.level() == 0)
			{
				foundProcesses.add(p);
			}
		}
		
		if(foundProcesses.size() == 1)
			return foundProcesses.get(0);
		else
			return null;
	}
	
	@Override
	public List<CmlBehaviourThread> getRunningProcesses()
	{
		return new Vector<CmlBehaviourThread>(running);
	}
	
	@Override
	public List<CmlBehaviourThread> getAllProcesses() {

		List<CmlBehaviourThread> all = new LinkedList<CmlBehaviourThread>(running);
		all.addAll(waiting);
		all.addAll(finished);
		all.addAll(deadlocked);
		
		return all;
	}
	
	@Override
	public boolean hasRunningProcesses()
	{
		return running.size() > 0;
	}
	
	@Override
	public boolean hasWaitingProcesses()
	{
		return waiting.size() > 0;
	}
	
	@Override
	public boolean hasActiveProcesses()
	{
		return hasWaitingProcesses() || hasRunningProcesses();
	}
	
	@Override
	public void start(CmlSupervisorEnvironment sve) {
		
		stopped = false;
		if(null == sve)
			throw new NullPointerException("The supervisor cannot be set to null in the scheduler");
		
		CmlBehaviourThread topProcess = getTopLevelProcess();
		
		//continue until the top process is not finished and not deadlocked
		while(!stopped && !topProcess.finished() && !topProcess.deadlocked())
	//	while(!stopped && hasActiveProcesses())
		{
			//inspect the top process to get the next possible trace element
			CmlAlphabet topAlphabet = topProcess.inspect();
			//expand what's possible in the alphabet
			CmlAlphabet availableEvents = topAlphabet.expandAlphabet();
			
			CmlRuntime.logger().fine("Waiting for environment on : " + availableEvents.getAllEvents());
			
			for(CmlEvent event : availableEvents.getAllEvents())
			{
				//TODO this should be handled differently
				Context context = event.getEventSources().iterator().next().getExecutionState().second;

				String state;

				if(context.getSelf() != null)
					state = context.getSelf().toString();
				else if (context.outer != null)
					state = context.getRoot().toString();
				else
					state = context.toString();

				CmlRuntime.logger().fine("State for "+event+" : " +  state);
			}

			//Let the given decision function select one of the observable events 
			CmlEvent selectedEvent = sve.decisionFunction().select(availableEvents); 

			//Set the selected event on the supervisor
			sve.setSelectedObservableEvent(selectedEvent);
			
			CmlBehaviourSignal signal = topProcess.execute(sve);
			
			if(signal != CmlBehaviourSignal.EXEC_SUCCESS)
				throw new InterpreterRuntimeException("Return CMLBehaviourSignal was unsuccesful at : " + topProcess.nextStepToString());
			
			CmlTrace trace = topProcess.getTraceModel();
			
			if(CmlTrace.isObservableEvent(trace.getLastEvent()) && topProcess.getState() != CmlProcessState.WAIT_EVENT)
			{
				CmlRuntime.logger().fine("----------------observable step by '"+ topProcess +"'----------------");
				CmlRuntime.logger().fine("Observable trace of '"+topProcess+"': " + trace.getVisibleTrace());
				
			}
			else 
			{
				CmlRuntime.logger().fine("----------------Silent step by '"+ topProcess +"'----------------");
				CmlRuntime.logger().fine("Trace of '"+topProcess+"': " + trace);
			}
			CmlRuntime.logger().fine("Eval. Status={ " + topProcess.nextStepToString() + " }");
			
			///old scheduler logic down from here
			
//			//execute each of the RUNNABLE processes until they are either finished, stopped or in wait state
//			while(!stopped && hasRunningProcesses())
//			{
//				//schedule the next process to execute
//				CmlBehaviourThread p = policy.scheduleNextProcess(getRunningProcesses());
//				//execute the scheduled process
//				CmlBehaviourSignal signal = p.execute(sve);
//				
//				if(signal != CmlBehaviourSignal.EXEC_SUCCESS)
//					throw new InterpreterRuntimeException("Return CMLBehaviourSignal was unsuccesful at : " + p.nextStepToString());
//
//				CmlTrace trace = p.getTraceModel();
//				
//				if(CmlTrace.isObservableEvent(trace.getLastEvent()) && p.getState() != CmlProcessState.WAIT_EVENT)
//				{
//					CmlRuntime.logger().fine("----------------observable step by '"+ p +"'----------------");
//					CmlRuntime.logger().fine("Observable trace of '"+p+"': " + trace.getVisibleTrace());
//					
//				}
//				else 
//				{
//					CmlRuntime.logger().fine("----------------Silent step by '"+ p +"'----------------");
//					CmlRuntime.logger().fine("Trace of '"+p+"': " + trace);
//				}
//				CmlRuntime.logger().fine("Eval. Status={ " + p.nextStepToString() + " }");
//			}
//
//			/**
//			 * Now, all the processes are sleeping tight, so the selected decision strategy needs to 
//			 * decide which event should occur and wake them up.
//			 */
//			CmlBehaviourThread p = getTopLevelProcess();
//			
//			if(p.deadlocked())
//			{
//				CmlRuntime.logger().fine("Top process '" + p.name() + "' is deadlocked");
//				break;
//			}
//			else if(p.waiting())
//			{
//				CmlAlphabet availableEvents = p.inspect();
//
//				if(availableEvents.contains(CmlEventFactory.referenceTauEvent()))
//					throw new InterpreterRuntimeException("A silent transition '"+ availableEvents.getSpecialEvents() +"' has slipped through to a place where only observable events should be.");
//				else
//				{
//					availableEvents = availableEvents.expandAlphabet();
//					CmlRuntime.logger().fine("Waiting for environment on : " + availableEvents.getObservableEvents());
//					for(ObservableEvent obsEvent : availableEvents.getObservableEvents())
//					{
//						//TODO this should be handles differently
//						Context context = obsEvent.getEventSources().iterator().next().getExecutionState().second;
//						
//						String state;
//						
//						if(context.getSelf() != null)
//							state = context.getSelf().toString();
//						else
//							state = context.getRoot().toString();
//						
//						CmlRuntime.logger().fine("State for "+obsEvent+" : " +  state);
//					}
//					
//					//Let the given decision function select one of the observable events 
//					ObservableEvent selectedEvent = sve.decisionFunction().select(availableEvents); 
//					
//					if(stopped)
//					{
//				//		p.setAbort(null);
//						break;
//					}
//						
//					//Set the selected event on the supervisor
//					sve.setSelectedObservableEvent(selectedEvent);
//				}
//			}
		}
	}
	
	@Override
	public void stop() {
		stopped = true;
	}
	
	@Override
	public boolean isDeadlocked() {
		return getTopLevelProcess().deadlocked();
	}
	
	@Override
	public long steps() {

		return getTopLevelProcess().getTraceModel().getTrace().size();
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
		case STOPPED:
			deadlocked.add(stateEvent.getSource());
		}
	}
}
