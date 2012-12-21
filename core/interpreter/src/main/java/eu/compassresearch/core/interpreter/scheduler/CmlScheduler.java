package eu.compassresearch.core.interpreter.scheduler;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourSignal;
import eu.compassresearch.core.interpreter.cml.CmlProcess;
import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.events.CmlProcessStateEvent;
import eu.compassresearch.core.interpreter.events.CmlProcessStateObserver;
import eu.compassresearch.core.interpreter.events.TraceEvent;
import eu.compassresearch.core.interpreter.runtime.CmlRuntime;

public class CmlScheduler implements CmlProcessStateObserver , Scheduler{

	List<CmlProcess> running = new LinkedList<CmlProcess>();
	List<CmlProcess> waiting = new LinkedList<CmlProcess>();
	List<CmlProcess> finished = new LinkedList<CmlProcess>();
	
	private SchedulingPolicy policy;
	private CmlSupervisorEnvironment sve = null;
	
	public CmlScheduler(SchedulingPolicy policy)
	{
		this.policy = policy; 
	}
	
	@Override
	public void addProcess(CmlProcess process)
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
	private List<CmlProcess> getWaitingTopLevelProcesses()
	{
		List<CmlProcess> foundProcesses = new LinkedList<CmlProcess>();
		
		for(CmlProcess p : waiting)
		{
			if(p.level() == 0)
			{
				foundProcesses.add(p);
			}
		}
		
		return foundProcesses;
	}
	
	@Override
	public List<CmlProcess> getRunningProcesses()
	{
		return new Vector<CmlProcess>(running);
	}
	
	@Override
	public List<CmlProcess> getAllProcesses() {

		List<CmlProcess> all = new LinkedList<CmlProcess>(running);
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
			throw new RuntimeException("Change this!!!!, but now that you " +
					"haven't changed this the supervisor is not set in the scheduler");
		
		//Active state
		while(hasActiveProcesses())
		{
			CmlRuntime.logger().fine("----------------step----------------");
			
			//execute each of the running pupils until they are either finished or in wait state
			while(hasRunningProcesses())
			{
				CmlProcess p = policy.scheduleNextProcess(getRunningProcesses());
				
				CmlBehaviourSignal signal = p.execute(sve);
				
				if(signal != CmlBehaviourSignal.EXEC_SUCCESS)
					throw new RuntimeException("Change this!!!!, but now that you haven't changed this yet, " +
							"then let me tell you that the return CMLBehaviourSignal was unsuccesful");

				CmlRuntime.logger().fine("Trace of '"+p+"': " + p.getTraceModel());
				CmlRuntime.logger().fine("Observable trace of '"+p+"': " + p.getTraceModel().getVisibleTrace());
				CmlRuntime.logger().fine("next: " + p.nextStepToString());
			}

			/**
			 * Now, all the processes are sleeping tight, so the selected decision strategy needs to 
			 * decide which event should occur and wake them up.
			 */
			for(CmlProcess p : getWaitingTopLevelProcesses())
			{
				CmlAlphabet alpha = p.inspect();

				if(alpha.isEmpty())
					throw new RuntimeException("Change this!!!!, but now that you " +
							"haven't changed this yet a deadlock has occured");
				else
				{
					//Select and set the communication event
					sve.setSelectedCommunication(sve.decisionFunction().select(p.inspect()));
					//signal all the processes that are listening for events on this channel
					sve.selectedCommunication().getChannel().signal();
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
	
	
//	@Override
//	public void onTraceChange(TraceEvent traceEvent) {
//		//TODO: here the presenting logic should be for running process
//		//CmlProcess p = traceEvent.getSource();
//		//CmlRuntime.logger().fine("current trace: " + p.getTraceModel());
//		//CmlRuntime.logger().fine("next: " + p.nextStepToString());
//	}
}
