package eu.compassresearch.core.interpreter.scheduler;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourSignal;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.CmlProcessState;
import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.cml.CmlTrace;
import eu.compassresearch.core.interpreter.cml.events.ObservableEvent;
import eu.compassresearch.core.interpreter.events.CmlProcessStateEvent;
import eu.compassresearch.core.interpreter.events.CmlProcessStateObserver;
import eu.compassresearch.core.interpreter.runtime.CmlRuntime;

public class CmlScheduler implements CmlProcessStateObserver , Scheduler{

	List<CmlBehaviourThread> running = new LinkedList<CmlBehaviourThread>();
	List<CmlBehaviourThread> waiting = new LinkedList<CmlBehaviourThread>();
	List<CmlBehaviourThread> finished = new LinkedList<CmlBehaviourThread>();
	
	private SchedulingPolicy policy;
	private CmlSupervisorEnvironment sve = null;
	
	public CmlScheduler(SchedulingPolicy policy)
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
	}
	
	/**
	 * Creates a list with all the currently waiting top-level processes
	 * @return Currently Waiting top-level processes
	 */
	private List<CmlBehaviourThread> getWaitingTopLevelProcesses()
	{
		List<CmlBehaviourThread> foundProcesses = new LinkedList<CmlBehaviourThread>();
		
		for(CmlBehaviourThread p : waiting)
		{
			if(p.level() == 0)
			{
				foundProcesses.add(p);
			}
		}
		
		return foundProcesses;
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
	public void setCmlSupervisorEnvironment(CmlSupervisorEnvironment sve) {
		this.sve = sve;
	}

	@Override
	public CmlSupervisorEnvironment getCmlSupervisorEnvironment() {
		return this.sve;
	}
	
	@Override
	public void start() throws AnalysisException {
		
		if(null == sve)
			throw new NullPointerException("The supervisor is not set in the scheduler");
		
//		CmlTrace lastTrace = null;
		
		//Active state
		while(hasActiveProcesses())
		{
			//execute each of the running pupils until they are either finished or in wait state
			while(hasRunningProcesses())
			{
				CmlBehaviourThread p = policy.scheduleNextProcess(getRunningProcesses());
				
				CmlBehaviourSignal signal = p.execute(sve);
				
				if(signal != CmlBehaviourSignal.EXEC_SUCCESS)
					throw new InterpreterRuntimeException("Change this!!!!, but now that you haven't changed this yet, " +
							"then let me tell you that the return CMLBehaviourSignal was unsuccesful");

				CmlTrace trace = p.getTraceModel();
				
				if(CmlTrace.isObservableEvent(trace.getLastEvent()) && p.getState() != CmlProcessState.WAIT_EVENT)
				{
					CmlRuntime.logger().fine("----------------observable step by '"+ p +"'----------------");
					CmlRuntime.logger().fine("Observable trace of '"+p+"': " + trace.getVisibleTrace());
					
				}
				else 
				{
					CmlRuntime.logger().fine("----------------Silent step by '"+ p +"'----------------");
					CmlRuntime.logger().fine("Trace of '"+p+"': " + trace);
				}
				CmlRuntime.logger().fine("Eval. Status={ " + p.nextStepToString() + " }");
			}

			/**
			 * Now, all the processes are sleeping tight, so the selected decision strategy needs to 
			 * decide which event should occur and wake them up.
			 */
			for(CmlBehaviourThread p : getWaitingTopLevelProcesses())
			{
				CmlAlphabet alpha = p.inspect();

				if(alpha.isEmpty())
					throw new InterpreterRuntimeException("A deadlock has occured. To developer: Change this be handled differently!!!!");
				else
				{
					CmlAlphabet availableEvents = p.inspect();
					
					CmlRuntime.logger().fine("Waiting for environment on : " + availableEvents.getObservableEvents());
					//Select and set the communication event
					
					ObservableEvent selectedEvent = sve.decisionFunction().select(availableEvents); 
					
//					if(sve.isObservableEventSelected() && 
//							sve.selectedObservableEvent().equals(selectedEvent) &&
//							p.getTraceModel().getLastEvent().equals(selectedEvent))
//						throw new InterpreterRuntimeException("");
					
//					if(p.getTraceModel().equals(lastTrace))
//						throw new InterpreterRuntimeException("");
//					
//					lastTrace = p.getTraceModel();
//						
					sve.setSelectedObservableEvent(selectedEvent);
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
}
