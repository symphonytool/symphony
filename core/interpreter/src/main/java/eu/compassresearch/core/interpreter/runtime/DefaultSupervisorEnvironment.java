package eu.compassresearch.core.interpreter.runtime;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.core.interpreter.cml.CmlCommunicationSelectionStrategy;
import eu.compassresearch.core.interpreter.cml.CmlProcess;
import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.cml.events.CmlCommunicationEvent;
import eu.compassresearch.core.interpreter.events.CmlProcessObserver;
import eu.compassresearch.core.interpreter.events.CmlProcessStateEvent;
import eu.compassresearch.core.interpreter.events.TraceEvent;
import eu.compassresearch.core.interpreter.scheduler.CmlScheduler;

public class DefaultSupervisorEnvironment implements CmlSupervisorEnvironment, CmlProcessObserver {

	private CmlCommunicationSelectionStrategy selectStrategy;
	private CmlCommunicationEvent selectedCommunication;
//	List<CmlProcess> running = new LinkedList<CmlProcess>();
//	List<CmlProcess> waiting = new LinkedList<CmlProcess>();
//	List<CmlProcess> finished = new LinkedList<CmlProcess>();
	
	List<CmlProcess> pupils = new LinkedList<CmlProcess>();
	private CmlScheduler scheduler = new CmlScheduler(this);
	
	public DefaultSupervisorEnvironment(CmlCommunicationSelectionStrategy selectStrategy)
	{
		this.selectStrategy = selectStrategy;
	}
	
	@Override
	public CmlCommunicationSelectionStrategy decisionFunction() {

		return this.selectStrategy;
	}

//	@Override
//	public void setDecisionFunction(CMLCommunicationSelectionStrategy cdf)
//			throws NullPointerException {
//
//	}

	@Override
	public boolean communicationSelected() {
		return selectedCommunication != null;
	}

	@Override
	public CmlCommunicationEvent selectedCommunication() {
		return selectedCommunication;
	}

	@Override
	public void setSelectedCommunication(CmlCommunicationEvent comm) {
		selectedCommunication = comm;
	}

	@Override
	public void clearSelectedCommunication() {
		selectedCommunication = null;
	}

	@Override
	public void addPupil(CmlProcess process) {
		//process.registerOnStateChanged(this);
		//put them as running so they will get a chance to run
		//running.add(process);
		pupils.add(process);
		scheduler.addProcess(process);
	}

	@Override
	public void removePupil(CmlProcess process) {
		//process.unregisterOnStateChanged(this);
						
//		running.remove(process);
//		waiting.remove(process);
//		finished.remove(process);
		pupils.remove(process);
	}
	
	@Override
	public void clearPupils()
	{
//		running.clear();
//		waiting.clear();
//		finished.clear();
		pupils.clear();
	}
	
	@Override
	public List<CmlProcess> getPupils() {
		
//		List<CmlProcess> all = new LinkedList<CmlProcess>();
//		all.addAll(running);
//		all.addAll(waiting);
//		all.addAll(finished);
		
//		return all;
		return pupils;
	}
	
	@Override
	public CmlProcess findNamedProcess(String name)
	{
		CmlProcess resultProcess = null;
		List<CmlProcess> all = getPupils();
		
		for(CmlProcess p : all )
		{
			if(p.name().toString().equals(name))
			{
				resultProcess = p;
				break;
			}
		}
				
		return resultProcess;
	}

	/**
	 * This is actually the main execution loop/scheduler, 
	 * FIXME: I think this should be moved into a scheduler class and out of the
	 * the supervisor 
	 */
	@Override
	public void start() throws AnalysisException {
		
		scheduler.start();
//		//Active state
//		while( waiting.size() > 0 || running.size() > 0)
//		{
//			
//			CmlRuntime.logger().fine("----------------step----------------");
//			
//			//execute each of the running pupils until they are either finished or in wait state
//			for(Iterator<CmlProcess> iterator = new Vector<CmlProcess>(running).iterator(); iterator.hasNext();)
//			{
//				CmlProcess p = iterator.next();
//				while(!p.finished() && 
//						!p.waiting())
//				{
//					CmlBehaviourSignal signal = p.execute(this);
//					
//					if(signal != CmlBehaviourSignal.EXEC_SUCCESS)
//						throw new RuntimeException("Change this!!!!, but now that you haven't changed this yet, " +
//								"then let me tell you that the return CMLBehaviourSignal was unsuccesful");
//
//					CmlRuntime.logger().fine("current trace of '"+p+"': " + p.getTraceModel());
//					CmlRuntime.logger().fine("next: " + p.nextStepToString());
//				}
//			}
//
//			//Since we can have newly created children, must might have to go back another round before inspecting
//			if(running.size() == 0)
//			{
//
//				/**
//				 * Now, all the processes are sleeping tight, so the selected decision strategy needs to 
//				 * decide which event should occur and wake them up.
//				 */
//				for(Iterator<CmlProcess> iterator = new Vector<CmlProcess>(waiting).iterator(); iterator.hasNext();)
//				{
//					CmlProcess p = iterator.next();
//					if(p.level() == 0)
//					{
//						CmlAlphabet alpha = p.inspect();
//
//						if(alpha.isEmpty())
//							throw new RuntimeException("Change this!!!!, but now that you " +
//									"haven't changed this yet a deadlock has occured");
//						else
//						{
//							//Select and set the communication event
//							setSelectedCommunication(decisionFunction().select(p.inspect()));
//							//signal all the processes that are listening for this channel
//							selectedCommunication.getChannel().signal();
//						}
//					}
//				}
//			}
//		}
	}
	
	/**
	 * CmlProcessObserver interface methods
	 */
	
	@Override
	public void onStateChange(CmlProcessStateEvent stateEvent) {

//		switch(stateEvent.getFrom())
//		{
//		case WAIT_CHILD:
//		case WAIT_EVENT:
//			waiting.remove(stateEvent.getSource());
//			break;
//		case INITIALIZED:
//		case RUNNABLE:
//		case RUNNING:
//			running.remove(stateEvent.getSource());
//			break;
//		}
//		
//		switch(stateEvent.getTo())
//		{
//		case WAIT_CHILD:
//		case WAIT_EVENT:
//			waiting.add(stateEvent.getSource());
//			break;
//		case INITIALIZED:
//		case RUNNABLE:
//		case RUNNING:
//			running.add(stateEvent.getSource());
//			break;
//		case FINISHED:
//			finished.add(stateEvent.getSource());
//			break;
//		}
	}
	
	
	@Override
	public void onTraceChange(TraceEvent traceEvent) {
		//TODO: here the presenting logic should be for running process
		//CmlProcess p = traceEvent.getSource();
		//CmlRuntime.logger().fine("current trace: " + p.getTraceModel());
		//CmlRuntime.logger().fine("next: " + p.nextStepToString());
	}
	
	/**
	 * String representation methods
	 */

	@Override
	public String toString() {
		return "Default Supervisor Environment";
	}

//	@Override
//	public boolean hasSupervisionFrame(CMLProcessNew process) {
//		return false;
//	}

}
