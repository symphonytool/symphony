package eu.compassresearch.core.interpreter.cml;

import java.util.HashSet;
import java.util.Set;

public class CMLAlphabet {

	private final Set<CMLCommunication> communicationEvents;
	private final Set<SpecialEvent> specialEvents;
	
	public CMLAlphabet()
	{
		this.communicationEvents = new HashSet<CMLCommunication>();
		this.specialEvents = new HashSet<SpecialEvent>();
	}
	
	public CMLAlphabet(Set<CMLCommunication> comms, Set<SpecialEvent> specialEvents)
	{
		this.communicationEvents = comms;
		this.specialEvents = specialEvents;
	}
	
	public CMLAlphabet(Set<CmlEvent> events)
	{
		this.communicationEvents = new HashSet<CMLCommunication>();
		this.specialEvents = new HashSet<SpecialEvent>();
		
		for(CmlEvent e : events)
		{
			if(e instanceof CMLCommunication)
				this.communicationEvents.add((CMLCommunication)e);
			else if(e instanceof SpecialEvent)
				this.specialEvents.add((SpecialEvent)e);
			
		}
		
	}
	
	public Set<CMLCommunication> getCommunicationEvents()
	{
		return new HashSet<CMLCommunication>(communicationEvents);
	}
	
	public Set<SpecialEvent> getSpecialEvents()
	{
		return new HashSet<SpecialEvent>(specialEvents);
	}
	
	public Set<CmlEvent> getAllEvents()
	{
		HashSet<CmlEvent> allEvents = new HashSet<CmlEvent>();
		
		allEvents.addAll(communicationEvents);
		allEvents.addAll(specialEvents);
		
		return allEvents;
	}
	
	
	/**
	 * This determines whether the alphabet contains a specific communication event. 
	 * Communication events are both read, write and signaling channel event (synchronization events)
	 * @return true if the special event is contained else false
	 */
	public boolean containsCommunication(CMLCommunication com)
	{
		return communicationEvents.contains(com);
	}
	
	/**
	 * This determines whether the alphabet contains a specific special event like e.g. the tau event
	 * @return true if the special event is contained else false
	 */
	public boolean containsSpecialEvent(SpecialEvent specialEvent)
	{
		return specialEvents.contains(specialEvent);
	}
	
	
	
}
