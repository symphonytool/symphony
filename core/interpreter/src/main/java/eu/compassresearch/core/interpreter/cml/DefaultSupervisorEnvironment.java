package eu.compassresearch.core.interpreter.cml;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;

public class DefaultSupervisorEnvironment implements CMLSupervisorEnvironment {

	private CMLCommunicationSelectionStrategy selectStrategy;
	private CMLCommunication selectedCommunication;
	List<CMLProcess> running = new LinkedList<CMLProcess>();
	List<CMLProcess> waiting = new LinkedList<CMLProcess>();
	List<CMLProcess> finished = new LinkedList<CMLProcess>();
	
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
	public void addPupil(CMLProcess process) {
		running.add(process);		
	}

	@Override
	public void removePupil(CMLProcess process) {
		running.remove(process);
	}

	@Override
	public void start() throws AnalysisException {
		
		//Active state
		while(waiting.size() > 0 || running.size() > 0)
		{
			//execute each of the running pupils until there are finished or in wait state
			for(Iterator<CMLProcess> iterator = running.iterator(); iterator.hasNext();)
			{
				CMLProcess p = iterator.next();

				while(p.getState() != ProcessState.FINISHED && 
						p.getState() != ProcessState.WAIT)
				{
					CMLBehaviourSignal signal = p.execute(this);

					if(signal != CMLBehaviourSignal.EXEC_SUCCESS)
						throw new RuntimeException("Change this!!!!");

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
			
			for(Iterator<CMLProcess> iterator = waiting.iterator(); iterator.hasNext();)
			{
				CMLProcess p = iterator.next();
				
				//Select the 
				setSelectedCommunication(decisionFunction().select(p.inspect()));
				
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
