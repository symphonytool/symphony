package eu.compassresearch.core.interpreter.cml;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.overture.interpreter.runtime.ValueException;

import eu.compassresearch.core.interpreter.cml.events.CmlEvent;
import eu.compassresearch.core.interpreter.cml.events.CmlSpecialEvent;
import eu.compassresearch.core.interpreter.cml.events.ObservableEvent;
import eu.compassresearch.core.interpreter.runtime.CmlContext;
import eu.compassresearch.core.interpreter.values.CmlValue;

/**
 * This represents a CML alphabet containing both silent and observable events
 * @author akm
 *
 */
public class CmlAlphabet extends CmlValue {

	private final Map<ObservableEvent,Set<ObservableEvent>> referenceEvents;
	private final Set<CmlSpecialEvent> specialEvents;
	
	public CmlAlphabet()
	{
		this.specialEvents = new LinkedHashSet<CmlSpecialEvent>();
		this.referenceEvents = new HashMap<ObservableEvent,Set<ObservableEvent>>();
	}
	
	public CmlAlphabet(ObservableEvent obsEvent)
	{
		this.specialEvents = new LinkedHashSet<CmlSpecialEvent>();
		this.referenceEvents = new HashMap<ObservableEvent,Set<ObservableEvent>>();
		Set<ObservableEvent> obsEvents = new HashSet<ObservableEvent>();
		obsEvents.add(obsEvent);
		referenceEvents.put(obsEvent.getReferenceEvent(), obsEvents);
	}
	
	protected CmlAlphabet(Map<ObservableEvent,Set<ObservableEvent>> referenceEvents, Set<CmlSpecialEvent> specialEvents)
	{
		this.specialEvents = specialEvents;
		this.referenceEvents = referenceEvents;
	}
	
	public CmlAlphabet(Set<ObservableEvent> comms, Set<CmlSpecialEvent> specialEvents)
	{
		this.specialEvents = specialEvents;
		this.referenceEvents = new HashMap<ObservableEvent,Set<ObservableEvent>>();
		initializeRefMap(comms);
	}
	
	public CmlAlphabet(Set<CmlEvent> events)
	{
		Set<ObservableEvent> observableEvents = new LinkedHashSet<ObservableEvent>();
		this.specialEvents = new LinkedHashSet<CmlSpecialEvent>();
		this.referenceEvents = new HashMap<ObservableEvent,Set<ObservableEvent>>();
		
		for(CmlEvent e : events)
		{
			if(e instanceof ObservableEvent){
				observableEvents.add((ObservableEvent)e);
			}
			else if(e instanceof CmlSpecialEvent)
				this.specialEvents.add((CmlSpecialEvent)e);
		}
		initializeRefMap(observableEvents);
	}
	
	private void initializeRefMap(Set<ObservableEvent> observableEvents)
	{
		for(ObservableEvent oe : observableEvents)
		{
			if(oe.isReferenceEvent())
				referenceEvents.put(oe, new LinkedHashSet<ObservableEvent>());
			else
			{
				ObservableEvent ref = oe.getReferenceEvent();
				
				if(referenceEvents.containsKey(ref))
					referenceEvents.get(ref).add(oe);
				else
				{
					Set<ObservableEvent> mapped = new LinkedHashSet<ObservableEvent>();
					mapped.add(oe);
					referenceEvents.put(ref, mapped);
				}
			}
		}
			
	}
	
	public Set<ObservableEvent> getObservableEvents()
	{
		Set<ObservableEvent> obsEvents = new LinkedHashSet<ObservableEvent>();
		for(Set<ObservableEvent> obsEventSet : referenceEvents.values())
			obsEvents.addAll(obsEventSet);
		return obsEvents;
	}
	
	public Set<ObservableEvent> getReferenceEvents()
	{
		return referenceEvents.keySet();
	}
	
	public Set<ObservableEvent> getObservableEventsByRef(ObservableEvent obsEvent)
	{
		return referenceEvents.get(obsEvent.getReferenceEvent());
	}
	
	public Set<CmlSpecialEvent> getSpecialEvents()
	{
		return new LinkedHashSet<CmlSpecialEvent>(specialEvents);
	}
	
	public Set<CmlEvent> getAllEvents()
	{
		Set<CmlEvent> allEvents = new LinkedHashSet<CmlEvent>();
		for(Set<ObservableEvent> obsEventSet : referenceEvents.values())
			allEvents.addAll(obsEventSet);
		allEvents.addAll(specialEvents);
		
		return allEvents;
	}
	
	public CmlAlphabet union(CmlAlphabet other)
	{
		Set<CmlEvent> resultSet = this.getAllEvents();
		resultSet.addAll(other.getAllEvents());
		
		return new CmlAlphabet(resultSet);
	}
	
	public CmlAlphabet union(CmlEvent event)
	{
		Set<CmlEvent> resultSet = this.getAllEvents();
		resultSet.add(event);
		
		return new CmlAlphabet(resultSet);
	}
	
	/**
	 * Calculates the intersection between this and other.
	 * @param other The CmlAlphabet that this will be intersected with
	 * @return the intersection of this and other
	 */
	public CmlAlphabet intersectRefsAndJoin(CmlAlphabet other)
	{
		
		//first we find the common reference event set
		Set<ObservableEvent> commonReferenceSet  = new LinkedHashSet<ObservableEvent>(referenceEvents.keySet());
		commonReferenceSet.retainAll(other.referenceEvents.keySet());

		Set<CmlEvent> resultSet = new LinkedHashSet<CmlEvent>();
		for(ObservableEvent refEvent : commonReferenceSet)
		{
			resultSet.addAll( referenceEvents.get(refEvent) );
			resultSet.addAll( other.referenceEvents.get(refEvent) );
		}
		
		return new CmlAlphabet(resultSet);
		
	}
	
	public CmlAlphabet intersect(CmlAlphabet other)
	{
		
		//first we find the common reference event set
		Set<ObservableEvent> commonReferenceSet  = new LinkedHashSet<ObservableEvent>(referenceEvents.keySet());
		commonReferenceSet.retainAll(other.referenceEvents.keySet());

		Set<CmlEvent> resultSet = new LinkedHashSet<CmlEvent>();
		for(ObservableEvent refEvent : commonReferenceSet)
		{
			resultSet.addAll( referenceEvents.get(refEvent) );
			resultSet.retainAll(other.referenceEvents.get(refEvent));
		}
		
		return new CmlAlphabet(resultSet);
		
	}
	
	public CmlAlphabet substract(CmlAlphabet other)
	{
		Map<ObservableEvent,Set<ObservableEvent>> newReferenceEvents = 
				new HashMap<ObservableEvent,Set<ObservableEvent>>(referenceEvents);
		
		for(Entry<ObservableEvent,Set<ObservableEvent>> entry : other.referenceEvents.entrySet())
		{
			if(entry.getValue().isEmpty())
				newReferenceEvents.remove(entry.getKey());
			else if (newReferenceEvents.containsKey(entry.getKey())) 	
			{
				newReferenceEvents.get(entry.getKey()).removeAll(entry.getValue());
			}
		}
		
		return new CmlAlphabet(newReferenceEvents,specialEvents);
	}
	
	/**
	 * This determines whether the alphabet contains an observable event where the
	 * ChannelValue of this is equal to the ChannelValue of comevent. 
	 * @return true if the communication event is contained else false
	 */
	public boolean containsObservableEvent(ObservableEvent comevent)
	{
//		if(comevent.isReferenceEvent())
//			return referenceEvents.containsKey(comevent);
//		else if(comevent instanceof SynchronisationEvent)
//		{
//			
//		}
//		else
		return referenceEvents.containsKey(comevent.getReferenceEvent()) && 
				referenceEvents.get(comevent.getReferenceEvent()).contains(comevent);

		
		//return (findCommunicationsByChannel(comevent.getChannel()).isEmpty() ? false : true);
	}
	
	/**
	 * This determines whether the alphabet contains a specific special event like e.g. the tau event
	 * @return true if the special event is contained else false
	 */
	public boolean containsSpecialEvent(CmlSpecialEvent specialEvent)
	{
		return specialEvents.contains(specialEvent);
	}
	
	public boolean isEmpty(){
		return referenceEvents.isEmpty() && specialEvents.isEmpty();
	}
	
	public CmlAlphabet flattenSyncEvents()
	{
		CmlAlphabet alpha = new CmlAlphabet();
		for(Set<ObservableEvent> obsEvs : referenceEvents.values())
			for(ObservableEvent obsEv : obsEvs)
				alpha = alpha.union(obsEv.getAsAlphabet());
		return alpha;
	}
	
	@Override
	public CmlAlphabet cmlAlphabetValue(CmlContext ctxt) throws ValueException
	{
		return this;
	}
	
	@Override
	public String toString() {
		return getAllEvents().toString();
	}

	@Override
	public boolean equals(Object other) {

		if(!(other instanceof CmlAlphabet))
			return false;
			
		return getAllEvents().equals(((CmlAlphabet)other).getAllEvents());
	}

	@Override
	public int hashCode() {
		return getAllEvents().hashCode();
	}

	@Override
	public String kind() {
		return "CmlAlphabetValue";
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return null;
	}
}
