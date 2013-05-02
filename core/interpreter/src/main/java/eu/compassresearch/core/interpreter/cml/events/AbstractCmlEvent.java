package eu.compassresearch.core.interpreter.cml.events;

import java.util.HashSet;
import java.util.Set;

import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviour;

public abstract class AbstractCmlEvent implements CmlEvent {

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
		
		CmlEvent other = null;
		
		if(!(obj instanceof CmlEvent))
			return false;
		
		other = (CmlEvent)obj;
		
		 // other is subset of this or this is a subset of other
		return	(other.getEventSources().containsAll(getEventSources()) || 
					getEventSources().containsAll(other.getEventSources())); 
	}

}
