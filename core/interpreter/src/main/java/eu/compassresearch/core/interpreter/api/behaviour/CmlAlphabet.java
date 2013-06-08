package eu.compassresearch.core.interpreter.api.behaviour;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.api.transitions.ChannelEvent;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.ObservableEvent;
import eu.compassresearch.core.interpreter.api.transitions.SilentTransition;

/**
 * This represents a CML alphabet containing both silent and observable events
 * FIXME This is currently quite a mess, it needs comments and cleaning up!
 * @author akm
 *
 */
public class CmlAlphabet extends Value {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5192258370825756900L;
	
	//This contains the observable events
	private final Set<ObservableEvent> _observableEvents;
	//This contains all the special events like tau
	private final Set<SilentTransition> specialEvents;
	
	public CmlAlphabet()
	{
		this.specialEvents = new LinkedHashSet<SilentTransition>();
		this._observableEvents = new LinkedHashSet<ObservableEvent>();
	}
	
	public CmlAlphabet(ObservableEvent obsEvent)
	{
		this.specialEvents = new LinkedHashSet<SilentTransition>();
		this._observableEvents = new LinkedHashSet<ObservableEvent>();
		this._observableEvents.add(obsEvent);
	}
	
	public CmlAlphabet(SilentTransition tauEvent)
	{
		this.specialEvents = new LinkedHashSet<SilentTransition>();
		this._observableEvents = new LinkedHashSet<ObservableEvent>();
		this.specialEvents.add(tauEvent);
	}
	
	public CmlAlphabet(ObservableEvent obs, SilentTransition tauEvent)
	{
		this.specialEvents = new LinkedHashSet<SilentTransition>();
		this.specialEvents.add(tauEvent);
		this._observableEvents = new LinkedHashSet<ObservableEvent>();
		this._observableEvents.add(obs);
	}
	
	public CmlAlphabet(Set<ObservableEvent> comms, Set<SilentTransition> specialEvents)
	{
		this.specialEvents = specialEvents;
		this._observableEvents = new LinkedHashSet<ObservableEvent>(comms);
	}
	
	public CmlAlphabet(Set<CmlTransition> events)
	{
		this.specialEvents = new LinkedHashSet<SilentTransition>();
		this._observableEvents = new LinkedHashSet<ObservableEvent>();
		
		for(CmlTransition e : events)
		{
			if(e instanceof ObservableEvent){
				_observableEvents.add((ObservableEvent)e);
			}
			else if(e instanceof SilentTransition)
				this.specialEvents.add((SilentTransition)e);
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
	public Set<SilentTransition> getSilentTransitions()
	{
		return new LinkedHashSet<SilentTransition>(specialEvents);
	}
	
	/**
	 * Returns all the observable and special events in the alphabet as a set.
	 * @return all the observable and special events. 
	 */
	public Set<CmlTransition> getAllEvents()
	{
		Set<CmlTransition> allEvents = new LinkedHashSet<CmlTransition>();
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
		Set<CmlTransition> resultSet = this.getAllEvents();
		resultSet.addAll(other.getAllEvents());
		
		return new CmlAlphabet(resultSet);
	}
	
	/**
	 * Calculates the union of this alphabet and the given event.
	 * @param event 
	 * @return The union of this alphabet and the given CmlEvent
	 */
	public CmlAlphabet union(CmlTransition event)
	{
		Set<CmlTransition> resultSet = this.getAllEvents();
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
		Set<CmlTransition> commonSet  = new LinkedHashSet<CmlTransition>(_observableEvents);
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
		Set<CmlTransition> resultSet = new LinkedHashSet<CmlTransition>();
		
		for(ObservableEvent thisEvent : _observableEvents)
		{
			for(ObservableEvent otherEvent : other._observableEvents)
			{
				//if the events are comparable (the) and one of the values are imprecise
				// and they are not equal
				if(thisEvent instanceof ChannelEvent &&
						otherEvent instanceof ChannelEvent &&
						thisEvent.isComparable(otherEvent) && 
						(!((ChannelEvent)thisEvent).isPrecise() || !((ChannelEvent)otherEvent).isPrecise() ) && 
						!thisEvent.equals(otherEvent) )
				{
					//find the meet of the two values, meaning the most precise
					resultSet.add(((ChannelEvent)thisEvent).meet(otherEvent));
				}
				else if(thisEvent.isComparable(otherEvent) && thisEvent.equals(otherEvent))
					resultSet.add(thisEvent);
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
		newReferenceEvents.removeAll(this.intersectImprecise(other).getObservableEvents());
		
		return new CmlAlphabet(newReferenceEvents,specialEvents);
	}
	
	
	/**
	 * This determines whether the alphabet contains an observable event.
	 * @return true if the observable event is contained else false
	 */
	public boolean contains(CmlTransition comevent)
	{
		return _observableEvents.contains(comevent) || specialEvents.contains(comevent);
	}
	
	/**
	 * This determines whether the alphabet contains an observable event.
	 * @return true if the observable event is contained else false
	 */
	public boolean containsImprecise(CmlTransition event)
	{
		return !intersectImprecise(event.getAsAlphabet()).isEmpty() || contains(event);
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
		Set<CmlTransition> eventSet = new HashSet<CmlTransition>();
		
		for(CmlTransition ev : getAllEvents())
			if(ev instanceof ChannelEvent)
				eventSet.addAll(((ChannelEvent)ev).expand());
			else
				eventSet.add(ev);
		
		return new CmlAlphabet(eventSet);
		
	}
	
	@Override
	public Object clone() {

		return new CmlAlphabet(new LinkedHashSet<ObservableEvent>(_observableEvents), 
				new HashSet<SilentTransition>(specialEvents));
	}
}
