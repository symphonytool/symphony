package eu.compassresearch.core.interpreter.runtime;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.core.interpreter.cml.CmlCommunicationSelectionStrategy;
import eu.compassresearch.core.interpreter.cml.CmlProcess;
import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.cml.events.CmlCommunicationEvent;
import eu.compassresearch.core.interpreter.scheduler.Scheduler;

public class DefaultSupervisorEnvironment implements CmlSupervisorEnvironment {

	private CmlCommunicationSelectionStrategy selectStrategy;
	private CmlCommunicationEvent selectedCommunication;
	
	private List<CmlProcess> pupils = new LinkedList<CmlProcess>();
	private Scheduler scheduler;
	
	public DefaultSupervisorEnvironment(CmlCommunicationSelectionStrategy selectStrategy, Scheduler scheduler)
	{
		this.selectStrategy = selectStrategy;
		this.scheduler = scheduler;
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
		pupils.add(process);
		scheduler.addProcess(process);
	}

	@Override
	public void removePupil(CmlProcess process) {
		pupils.remove(process);
	}
	
	@Override
	public void clearPupils()
	{
		pupils.clear();
	}
	
	@Override
	public List<CmlProcess> getPupils() {
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
