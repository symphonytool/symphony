package eu.compassresearch.core.interpreter.cml.events;

import java.util.HashSet;
import java.util.Set;

import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviour;

public abstract class AbstractCmlEvent implements CmlTransition {

	final protected Set<CmlBehaviour> eventSources;
	
	public AbstractCmlEvent(CmlBehaviour eventSource)
	{
		this.eventSources = new HashSet<CmlBehaviour>();
		this.eventSources.add(eventSource);
	}
	
	public AbstractCmlEvent(Set<CmlBehaviour> eventSources)
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
