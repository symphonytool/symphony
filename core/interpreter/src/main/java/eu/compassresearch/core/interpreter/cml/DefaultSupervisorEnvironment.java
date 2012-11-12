package eu.compassresearch.core.interpreter.cml;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.core.interpreter.runtime.CmlRuntime;

public class DefaultSupervisorEnvironment implements CMLSupervisorEnvironment {

	private CMLCommunicationSelectionStrategy selectStrategy;
	private CMLCommunication selectedCommunication;
	List<CmlProcess> running = new LinkedList<CmlProcess>();
	List<CmlProcess> waiting = new LinkedList<CmlProcess>();
	List<CmlProcess> finished = new LinkedList<CmlProcess>();
	
	public DefaultSupervisorEnvironment(CMLCommunicationSelectionStrategy selectStrategy)
	{
		this.selectStrategy = selectStrategy;
	}
	
	@Override
	public CMLCommunicationSelectionStrategy decisionFunction() {

		return this.selectStrategy;
	}

//	@Override
//	public void setDecisionFunction(CMLCommunicationSelectionStrategy cdf)
//			throws NullPointerException {
//		// TODO Auto-generated method stub
//
//	}

	@Override
	public boolean communicationSelected() {
		return selectedCommunication != null;
	}

	@Override
	public CMLCommunication selectedCommunication() {
		return selectedCommunication;
	}

	@Override
	public void setSelectedCommunication(CMLCommunication comm) {
		selectedCommunication = comm;

	}

	@Override
	public void clearSelectedCommunication() {
		selectedCommunication = null;

	}

	@Override
	public void addPupil(CmlProcess process) {
		running.add(process);		
	}

	@Override
	public void removePupil(CmlProcess process) {
		running.remove(process);
	}

	@Override
	public void start() throws AnalysisException {
		
		//Active state
		while(waiting.size() > 0 || running.size() > 0)
		{
			
			CmlRuntime.logger().fine("----------------step----------------");
			
			//execute each of the running pupils until there are finished or in wait state
			for(Iterator<CmlProcess> iterator = running.iterator(); iterator.hasNext();)
			{
				CmlProcess p = iterator.next();
				while(!p.finished() && 
						!p.waiting())
				{
					CMLBehaviourSignal signal = p.execute(this);
					
					if(signal != CMLBehaviourSignal.EXEC_SUCCESS)
						throw new RuntimeException("Change this!!!!, but now that you haven't changed this yet, then let me tell you that the return CMLBehaviourSignal was unsuccesful");

					//List<CMLProcess> all = new LinkedList<CMLProcess>(running); 
					//all.addAll(waiting);
					//all.addAll(finished);
					CmlRuntime.logger().fine("current trace: " + p.getTraceModel());
					CmlRuntime.logger().fine("next: " + p);
					
					switch(p.getState())
					{
					case FINISHED:
						finished.add(p);
						iterator.remove();
						break;
					case WAIT:
						waiting.add(p);
						iterator.remove();
						break;
					}
				}
			}
			
			for(Iterator<CmlProcess> iterator = waiting.iterator(); iterator.hasNext();)
			{
				CmlProcess p = iterator.next();
				
				//Select the 
				setSelectedCommunication(decisionFunction().select(p.inspect()));
				selectedCommunication.getChannel().signal();
				running.add(p);
				iterator.remove();
				
			}
		}
	}

//	@Override
//	public boolean hasSupervisionFrame(CMLProcessNew process) {
//		// TODO Auto-generated method stub
//		return false;
//	}

}
