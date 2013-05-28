package eu.compassresearch.core.interpreter.api.transitions;

import java.util.HashSet;
import java.util.Set;

import eu.compassresearch.core.interpreter.api.behaviour.CmlAlphabet;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;

abstract class AbstractCmlTransition implements CmlTransition {

	final protected Set<CmlBehaviour> eventSources;
	
	public AbstractCmlTransition(CmlBehaviour eventSource)
	{
		this.eventSources = new HashSet<CmlBehaviour>();
		this.eventSources.add(eventSource);
	}
	
	public AbstractCmlTransition(Set<CmlBehaviour> eventSources)
	{
		this.eventSources = eventSources;
	}
	
	@Override
	public abstract CmlAlphabet getAsAlphabet();

	public Set<CmlBehaviour> getEventSources()
	{
		return eventSources;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		CmlTransition other = null;
		
		if(!(obj instanceof CmlTransition))
			return false;
		
		other = (CmlTransition)obj;
		
		 // other is subset of this or this is a subset of other
		return	(other.getEventSources().containsAll(getEventSources()) || 
					getEventSources().containsAll(other.getEventSources())); 
	}

}
