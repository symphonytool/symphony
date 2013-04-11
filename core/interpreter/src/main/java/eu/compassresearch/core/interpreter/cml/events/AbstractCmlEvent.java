package eu.compassresearch.core.interpreter.cml.events;

import java.util.HashSet;
import java.util.Set;

import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;

public abstract class AbstractCmlEvent implements CmlEvent {

	final protected Set<CmlBehaviourThread> eventSources;
	
	public AbstractCmlEvent(CmlBehaviourThread eventSource)
	{
		this.eventSources = new HashSet<CmlBehaviourThread>();
		this.eventSources.add(eventSource);
	}
	
	public AbstractCmlEvent(Set<CmlBehaviourThread> eventSources)
	{
		this.eventSources = eventSources;
	}
	
	@Override
	public abstract CmlAlphabet getAsAlphabet();

	public Set<CmlBehaviourThread> getEventSources()
	{
		return eventSources;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		AbstractCmlEvent other = null;
		
		if(!(obj instanceof AbstractCmlEvent))
			return false;
		
		other = (AbstractCmlEvent)obj;
		
		 // other is subset of this or this is a subset of other
		return	(other.getEventSources().containsAll(getEventSources()) || 
					getEventSources().containsAll(other.getEventSources())); 
	}

}
