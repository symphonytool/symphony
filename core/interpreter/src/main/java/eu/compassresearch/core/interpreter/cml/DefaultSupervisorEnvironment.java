package eu.compassresearch.core.interpreter.cml;

import java.util.LinkedList;
import java.util.List;

import eu.compassresearch.core.interpreter.cml.events.CmlEvent;
import eu.compassresearch.core.interpreter.cml.events.ObservableEvent;

public class DefaultSupervisorEnvironment implements CmlSupervisorEnvironment {

	private CmlCommunicationSelectionStrategy selectStrategy;
	private CmlEvent selectedCommunication;
	
	private List<CmlBehaviourThread> pupils = new LinkedList<CmlBehaviourThread>();
	
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
	public boolean isSelectedEventValid() {
		
		//selectedCommunication.getChannel().onSelect()
		return selectedCommunication != null;
	}

	@Override
	public CmlEvent selectedObservableEvent() {
		return selectedCommunication;
	}

	@Override
	public void setSelectedObservableEvent(CmlEvent comm) {
		selectedCommunication = comm;
		//signal all the processes that are listening for events on this channel
		if(selectedCommunication instanceof ObservableEvent)
			((ObservableEvent) selectedCommunication).getChannel().select();
	}

	@Override
	public void clearSelectedCommunication() {
		selectedCommunication = null;
	}

	@Override
	public void addPupil(CmlBehaviourThread process) {
		pupils.add(process);
	}

	@Override
	public void removePupil(CmlBehaviourThread process) {
		pupils.remove(process);
//		scheduler.removeProcess(process);
	}
	
	@Override
	public void clearPupils()
	{
		pupils.clear();
	}
	
	@Override
	public List<CmlBehaviourThread> getPupils() {
		return pupils;
	}
	
	@Override
	public CmlBehaviourThread findNamedProcess(String name)
	{
		CmlBehaviourThread resultProcess = null;
		List<CmlBehaviourThread> all = getPupils();
		
		for(CmlBehaviourThread p : all )
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
