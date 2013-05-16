package eu.compassresearch.core.interpreter;

import java.util.LinkedList;
import java.util.List;

import eu.compassresearch.core.interpreter.api.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.api.SelectionStrategy;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.transitions.ChannelEvent;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;

class DefaultSupervisorEnvironment implements CmlSupervisorEnvironment {

	private SelectionStrategy selectStrategy;
	private CmlTransition selectedCommunication;
	
	private List<CmlBehaviour> pupils = new LinkedList<CmlBehaviour>();
	
	public DefaultSupervisorEnvironment(SelectionStrategy selectStrategy)
	{
		this.selectStrategy = selectStrategy;
	}
	
	@Override
	public SelectionStrategy decisionFunction() {

		return this.selectStrategy;
	}

	@Override
	public boolean isSelectedEventValid() {
		
		//selectedCommunication.getChannel().onSelect()
		return selectedCommunication != null;
	}

	@Override
	public CmlTransition selectedObservableEvent() {
		return selectedCommunication;
	}

	@Override
	public void setSelectedObservableEvent(CmlTransition comm) {
		selectedCommunication = comm;
		//signal all the processes that are listening for events on this channel
		if(selectedCommunication instanceof ChannelEvent)
			((ChannelEvent) selectedCommunication).getChannel().select();
	}

	@Override
	public void clearSelectedCommunication() {
		selectedCommunication = null;
	}

	@Override
	public void addPupil(CmlBehaviour process) {
		pupils.add(process);
	}

	@Override
	public void removePupil(CmlBehaviour process) {
		pupils.remove(process);
	}
	
	@Override
	public void clearPupils()
	{
		pupils.clear();
	}
	
	@Override
	public List<CmlBehaviour> getPupils() {
		return pupils;
	}
	
	@Override
	public CmlBehaviour findNamedProcess(String name)
	{
		CmlBehaviour resultProcess = null;
		List<CmlBehaviour> all = getPupils();
		
		for(CmlBehaviour p : all )
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
