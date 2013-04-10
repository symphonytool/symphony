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

}
