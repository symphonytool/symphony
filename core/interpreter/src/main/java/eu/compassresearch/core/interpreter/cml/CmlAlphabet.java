package eu.compassresearch.core.interpreter.cml;

import java.util.HashSet;
import java.util.Set;

import eu.compassresearch.core.interpreter.cml.events.CmlCommunicationEvent;
import eu.compassresearch.core.interpreter.cml.events.CmlEvent;
import eu.compassresearch.core.interpreter.cml.events.CmlSpecialEvent;

public class CmlAlphabet {

	private final Set<CmlCommunicationEvent> communicationEvents;
	private final Set<CmlSpecialEvent> specialEvents;
	
	public CmlAlphabet()
	{
		this.communicationEvents = new HashSet<CmlCommunicationEvent>();
		this.specialEvents = new HashSet<CmlSpecialEvent>();
	}
	
	public CmlAlphabet(Set<CmlCommunicationEvent> comms, Set<CmlSpecialEvent> specialEvents)
	{
		this.communicationEvents = comms;
		this.specialEvents = specialEvents;
	}
	
	public CmlAlphabet(Set<CmlEvent> events)
	{
		this.communicationEvents = new HashSet<CmlCommunicationEvent>();
		this.specialEvents = new HashSet<CmlSpecialEvent>();
		
		for(CmlEvent e : events)
		{
			if(e instanceof CmlCommunicationEvent)
				this.communicationEvents.add((CmlCommunicationEvent)e);
			else if(e instanceof CmlSpecialEvent)
				this.specialEvents.add((CmlSpecialEvent)e);
		}
	}
	
	public Set<CmlCommunicationEvent> getCommunicationEvents()
	{
		return new HashSet<CmlCommunicationEvent>(communicationEvents);
	}
	
	public Set<CmlSpecialEvent> getSpecialEvents()
	{
		return new HashSet<CmlSpecialEvent>(specialEvents);
	}
	
	public Set<CmlEvent> getAllEvents()
	{
		HashSet<CmlEvent> allEvents = new HashSet<CmlEvent>();
		
		allEvents.addAll(communicationEvents);
		allEvents.addAll(specialEvents);
		
		return allEvents;
	}
	
	public CmlAlphabet union(CmlAlphabet other)
	{
		Set<CmlEvent> resultSet = this.getAllEvents();
		resultSet.addAll(other.getAllEvents());
		
		return new CmlAlphabet(resultSet);
	}
	
	/**
	 * This determines whether the alphabet contains a specific communication event. 
	 * Communication events are both read, write and signaling channel event (synchronization events)
	 * @return true if the special event is contained else false
	 */
	public boolean containsCommunication(CmlCommunicationEvent com)
	{
		return communicationEvents.contains(com);
	}
	
	/**
	 * This determines whether the alphabet contains a specific special event like e.g. the tau event
	 * @return true if the special event is contained else false
	 */
	public boolean containsSpecialEvent(CmlSpecialEvent specialEvent)
	{
		return specialEvents.contains(specialEvent);
	}
	
	public boolean isEmpty()
	{
		return communicationEvents.isEmpty() && specialEvents.isEmpty();
	}
	
}
