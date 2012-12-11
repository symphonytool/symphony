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
		process.registerOnStateChanged(this);
		if(process.waiting())
			waiting.add(process);
		else
			running.add(process);
	}
	
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
	
	private List<CmlProcess> getRunningProcesses()
	{
		return new Vector<CmlProcess>(running);
	}
	
	private boolean hasRunningProcesses()
	{
		return running.size() > 0;
	}
	
	private boolean hasWaitingProcesses()
	{
		return waiting.size() > 0;
	}
	
	private boolean hasActiveProcesses()
	{
		return hasWaitingProcesses() || hasRunningProcesses();
	}
	
	public void start() throws AnalysisException {
		
		//Active state
		while(hasActiveProcesses())
		{
			CmlRuntime.logger().fine("----------------step----------------");
			
			//execute each of the running pupils until they are either finished or in wait state
			for(CmlProcess p : getRunningProcesses())
			{
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
			if(!hasRunningProcesses())
			{
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
}
