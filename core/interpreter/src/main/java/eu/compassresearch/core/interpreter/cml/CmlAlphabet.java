package eu.compassresearch.core.interpreter.cml;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.cml.events.CmlEvent;
import eu.compassresearch.core.interpreter.cml.events.CmlSpecialEvent;
import eu.compassresearch.core.interpreter.cml.events.ObservableEvent;

/**
 * This represents a CML alphabet containing both silent and observable events
 * FIXME This is currently quite a mess, it needs comments and cleaning up!
 * @author akm
 *
 */
public class CmlAlphabet extends Value {

	//This contains the observable events
	private final Set<ObservableEvent> _observableEvents;
	//This contains all the special events like tau
	private final Set<CmlSpecialEvent> specialEvents;
	
	public CmlAlphabet()
	{
		this.specialEvents = new LinkedHashSet<CmlSpecialEvent>();
		this._observableEvents = new LinkedHashSet<ObservableEvent>();
	}
	
	public CmlAlphabet(ObservableEvent obsEvent)
	{
		this.specialEvents = new LinkedHashSet<CmlSpecialEvent>();
		this._observableEvents = new LinkedHashSet<ObservableEvent>();
		this._observableEvents.add(obsEvent);
	}
	
	public CmlAlphabet(Set<ObservableEvent> comms, Set<CmlSpecialEvent> specialEvents)
	{
		this.specialEvents = specialEvents;
		this._observableEvents = new LinkedHashSet<ObservableEvent>(comms);
	}
	
	public CmlAlphabet(Set<CmlEvent> events)
	{
		this.specialEvents = new LinkedHashSet<CmlSpecialEvent>();
		this._observableEvents = new LinkedHashSet<ObservableEvent>();
		
		for(CmlEvent e : events)
		{
			if(e instanceof ObservableEvent){
				_observableEvents.add((ObservableEvent)e);
			}
			else if(e instanceof CmlSpecialEvent)
				this.specialEvents.add((CmlSpecialEvent)e);
		}
	}
	
	/**
	 * Returns all the observable events in the alphabet
	 * @return
	 */
	public Set<ObservableEvent> getObservableEvents()
	{
		return new LinkedHashSet<ObservableEvent>(_observableEvents);
	}
	
	/**
	 * Returns all the special events in the alphabet
	 * @return
	 */
	public Set<CmlSpecialEvent> getSpecialEvents()
	{
		return new LinkedHashSet<CmlSpecialEvent>(specialEvents);
	}
	
	/**
	 * Returns all the observable and special events in the alphabet as a set.
	 * @return all the observable and special events. 
	 */
	protected Set<CmlEvent> getAllEvents()
	{
		Set<CmlEvent> allEvents = new LinkedHashSet<CmlEvent>();
		allEvents.addAll(_observableEvents);
		allEvents.addAll(specialEvents);
		
		return allEvents;
	}
	
	/**
	 * Calculate  the union of this alphabet and the given event
	 * @param event 
	 * @return The union of this alphabet and the given CmlEvent
	 */
	public CmlAlphabet union(CmlAlphabet other)
	{
		Set<CmlEvent> resultSet = this.getAllEvents();
		resultSet.addAll(other.getAllEvents());
		
		return new CmlAlphabet(resultSet);
	}
	
	/**
	 * Calculates the union of this alphabet and the given event.
	 * @param event 
	 * @return The union of this alphabet and the given CmlEvent
	 */
	public CmlAlphabet union(CmlEvent event)
	{
		Set<CmlEvent> resultSet = this.getAllEvents();
		resultSet.add(event);
		
		return new CmlAlphabet(resultSet);
	}
	
	/**
	 * Calculates the intersection between this and other 
	 * @param other
	 * @return
	 */
	public CmlAlphabet intersect(CmlAlphabet other)
	{
		Set<CmlEvent> commonSet  = new LinkedHashSet<CmlEvent>(_observableEvents);
		commonSet.retainAll(other._observableEvents);
		return new CmlAlphabet(commonSet);
	}
	
	/**
	 * Calculates the intersection with imprecision between this and other. Meaning that
	 * two events might intersect even though their value is not equal E.g.
	 * Assume that we have the following two alphabets A = {a.3,b} and B = {a.?}
	 * then the result after invoking this function would be
	 *  A.intersectImprecise(B) == {a.3}
	 *  As a.3 and a.? intersects even though a.? can be any value of the type of 'a'. 
	 *  When two events intersect, then the most precise will be returned in the result alphabet.   
	 * @param other
	 * @return
	 */
	public CmlAlphabet intersectImprecise(CmlAlphabet other)
	{
		Set<CmlEvent> resultSet = new LinkedHashSet<CmlEvent>();
		
		for(ObservableEvent thisEvent : _observableEvents)
		{
			for(ObservableEvent otherEvent : other._observableEvents)
			{
				//if the events are comparable (the) and one of the values are imprecise
				// and they are not equal
				if(thisEvent.isComparable(otherEvent) && 
						(!thisEvent.isValuePrecise() || !otherEvent.isValuePrecise() ) && 
						!thisEvent.equals(otherEvent) )
				{
					//find the meet of the two values, meaning the most precise
					resultSet.add(thisEvent.meet(otherEvent));
					//resultSet.add(otherRefEvent);
				}
				else if(thisEvent.isComparable(otherEvent) && thisEvent.equals(otherEvent))
				{
					resultSet.add(thisEvent);
					//resultSet.add(otherRefEvent);
				}
			}
		}
		
		return new CmlAlphabet(resultSet);
	}

	/**
	 * Subtract other from this
	 * @param other
	 * @return An alphabet containing all the events that are this
	 */
	public CmlAlphabet subtract(CmlAlphabet other)
	{
		Set<ObservableEvent> newReferenceEvents = new LinkedHashSet<ObservableEvent>();
		newReferenceEvents.addAll(_observableEvents);
		newReferenceEvents.removeAll(other.getObservableEvents());
		
		return new CmlAlphabet(newReferenceEvents,specialEvents);
	}
	
	/**
	 * Subtract other from this
	 * @param other
	 * @return An alphabet containing all the events that are this
	 */
	public CmlAlphabet subtractImprecise(CmlAlphabet other)
	{
		Set<ObservableEvent> newReferenceEvents = new LinkedHashSet<ObservableEvent>();
		newReferenceEvents.addAll(_observableEvents);
		newReferenceEvents.removeAll(other.intersectImprecise(this).getObservableEvents());
		
		return new CmlAlphabet(newReferenceEvents,specialEvents);
	}
	
	
	/**
	 * This determines whether the alphabet contains an observable event.
	 * @return true if the observable event is contained else false
	 */
	public boolean contains(CmlEvent comevent)
	{
		return _observableEvents.contains(comevent) || specialEvents.contains(comevent);
	}
	
	/**
	 * This determines whether the alphabet contains an observable event.
	 * @return true if the observable event is contained else false
	 */
	public boolean containsImprecise(CmlEvent event)
	{
		return !intersectImprecise(event.getAsAlphabet()).isEmpty();
	}
	
	public boolean isEmpty(){
		return _observableEvents.isEmpty() && specialEvents.isEmpty();
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

	/**
	 * This expands all the expandable events in the alphabet.
	 * E.g. if we have
	 * 
	 * types
	 * 	switch = <ON> | <OFF> 
	 * 
	 * channels
	 * 	a : switch
	 * 
	 * process Test = begin @ a?x -> Skip end
	 * 
	 * then the immediate alphabet would be {a.AnyValue}
	 * when expanded this will be {a.<ON> , a.<OFF>}
	 * 
	 * @return The same alphabet but with all the expandable events expanded
	 */
	public CmlAlphabet expandAlphabet()
	{
		Set<CmlEvent> eventSet = new HashSet<CmlEvent>();
		
		for(ObservableEvent ev : getObservableEvents())
			eventSet.addAll(ev.expand());
		
		return new CmlAlphabet(eventSet);
		
	}
	
	@Override
	public Object clone() {

		return new CmlAlphabet(new LinkedHashSet<ObservableEvent>(_observableEvents), 
				new HashSet<CmlSpecialEvent>(specialEvents));
	}
}
