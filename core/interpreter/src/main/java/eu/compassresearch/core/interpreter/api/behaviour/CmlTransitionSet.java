package eu.compassresearch.core.interpreter.api.behaviour;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.api.transitions.ChannelEvent;
import eu.compassresearch.core.interpreter.api.transitions.CmlTock;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.ObservableEvent;
import eu.compassresearch.core.interpreter.api.transitions.SilentTransition;
import eu.compassresearch.core.interpreter.api.values.ChannelNameSetValue;
import eu.compassresearch.core.interpreter.api.values.ChannelNameValue;

/**
 * This represents a CML alphabet containing both silent and observable events
 * @author akm
 *
 */
public class CmlTransitionSet extends Value {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5192258370825756900L;
	
	//This contains the observable events
	private final Set<ObservableEvent> _observableEvents;
	//This contains all the special events like tau
	private final Set<SilentTransition> specialEvents;
	
	public CmlTransitionSet()
	{
		this.specialEvents = new LinkedHashSet<SilentTransition>();
		this._observableEvents = new LinkedHashSet<ObservableEvent>();
	}
	
	public CmlTransitionSet(ObservableEvent obsEvent)
	{
		this.specialEvents = new LinkedHashSet<SilentTransition>();
		this._observableEvents = new LinkedHashSet<ObservableEvent>();
		this._observableEvents.add(obsEvent);
	}
	
	public CmlTransitionSet(SilentTransition tauEvent)
	{
		this.specialEvents = new LinkedHashSet<SilentTransition>();
		this._observableEvents = new LinkedHashSet<ObservableEvent>();
		this.specialEvents.add(tauEvent);
	}
	
	public CmlTransitionSet(ObservableEvent obs, SilentTransition tauEvent)
	{
		this.specialEvents = new LinkedHashSet<SilentTransition>();
		this.specialEvents.add(tauEvent);
		this._observableEvents = new LinkedHashSet<ObservableEvent>();
		this._observableEvents.add(obs);
	}
	
	public CmlTransitionSet(Set<ObservableEvent> comms, Set<SilentTransition> specialEvents)
	{
		this.specialEvents = specialEvents;
		this._observableEvents = new LinkedHashSet<ObservableEvent>(comms);
	}
	
	public CmlTransitionSet(Set<CmlTransition> events)
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
	
	public Set<ObservableEvent> getObservableChannelEvents()
	{
		Set<ObservableEvent> observableChannelEvents = new LinkedHashSet<ObservableEvent>(_observableEvents);
		observableChannelEvents.remove(new CmlTock());
		
		return observableChannelEvents;
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
	public CmlTransitionSet union(CmlTransitionSet other)
	{
		Set<CmlTransition> resultSet = this.getAllEvents();
		resultSet.addAll(other.getAllEvents());
		
		return new CmlTransitionSet(resultSet);
	}
	
	/**
	 * Calculates the union of this alphabet and the given event.
	 * @param event 
	 * @return The union of this alphabet and the given CmlEvent
	 */
	public CmlTransitionSet union(CmlTransition event)
	{
		Set<CmlTransition> resultSet = this.getAllEvents();
		resultSet.add(event);
		
		return new CmlTransitionSet(resultSet);
	}
	
	/**
	 * Calculates the intersection between this and other 
	 * @param other
	 * @return
	 */
	public CmlTransitionSet intersect(CmlTransitionSet other)
	{
		Set<CmlTransition> commonSet  = new LinkedHashSet<CmlTransition>(_observableEvents);
		commonSet.retainAll(other._observableEvents);
		return new CmlTransitionSet(commonSet);
	}
	
	/**
	 * Calculates the intersection with imprecision between this and other. Meaning that
	 * two events might intersect even though their value is not equal E.g.
	 * Assume that we have the following two alphabets A = {a.3,b} and B = {a.?}
	 * then the result after invoking this function would be
	 *  A.intersectImprecise(B) == {a.3,a.?}
	 *  As a.3 and a.? intersects even though a.? can be any value of the type of 'a'. 
	 * @param other
	 * @return
	 */
	public CmlTransitionSet intersectImprecise(CmlTransitionSet other)
	{
		Set<CmlTransition> resultSet = new LinkedHashSet<CmlTransition>();
		
		for(ObservableEvent thisEvent : _observableEvents)
		{
			for(ObservableEvent otherEvent : other._observableEvents)
			{
				if(thisEvent.isComparable(otherEvent) && thisEvent.isSourcesSubset(otherEvent)){
				//if(thisEvent.isComparable(otherEvent)){
					resultSet.add(thisEvent);
					//resultSet.add(otherEvent);
				}
			}
		}
		
		return new CmlTransitionSet(resultSet);
	}
	
	public CmlTransitionSet intersectImprecise(ObservableEvent other)
	{
		Set<CmlTransition> resultSet = new LinkedHashSet<CmlTransition>();
		
		for(ObservableEvent thisEvent : _observableEvents)
		{
			if(thisEvent.isComparable(other)  && thisEvent.isSourcesSubset(other)){
			//if(thisEvent.isComparable(other) ){
				resultSet.add(thisEvent);
				//resultSet.add(otherEvent);
			}
		}
		
		return new CmlTransitionSet(resultSet);
	}
	
	public CmlTransitionSet retainByChannelName(ChannelNameValue channelNameValue)
	{
		Set<CmlTransition> resultSet = new LinkedHashSet<CmlTransition>();
		
		for(ObservableEvent obsTransition : _observableEvents)
		{
//			if(obsTransition instanceof CmlTock)
//			{
//				resultSet.add(obsTransition);
//				continue;
//			}
//			else 
				if(!(obsTransition instanceof ChannelEvent))
				continue;
			
			ChannelEvent obsChannelEvent = (ChannelEvent)obsTransition;   
			if(obsChannelEvent.getChannelName().isComparable(channelNameValue) && 
					channelNameValue.isGTEQPrecise(obsChannelEvent.getChannelName())){
				resultSet.add(obsTransition);
			}
		}
		
		return new CmlTransitionSet(resultSet);
	}
	
	public CmlTransitionSet retainByChannelNameSet(ChannelNameSetValue channelNameSetValue)
	{
		Set<CmlTransition> resultSet = new LinkedHashSet<CmlTransition>();
		
		for(ObservableEvent obsTransition : _observableEvents)
		{
//			if(obsTransition instanceof CmlTock){
//				resultSet.add(obsTransition);
//				continue;
//			}
//			else 
				if(!(obsTransition instanceof ChannelEvent))
				continue;

			ChannelEvent obsChannelEvent = (ChannelEvent)obsTransition;

			for(ChannelNameValue channelNameValue : channelNameSetValue)
			{

				if(obsChannelEvent.getChannelName().isComparable(channelNameValue) && 
						channelNameValue.isGTEQPrecise(obsChannelEvent.getChannelName())){
					resultSet.add(obsTransition);
				}
			}
		}
		
		return new CmlTransitionSet(resultSet);
	}
	
	public CmlTransitionSet removeByChannelName(ChannelNameValue channelNameValue)
	{
		Set<ObservableEvent> resultSet = new LinkedHashSet<ObservableEvent>();
		
		for(ObservableEvent obsTransition : _observableEvents)
		{
			if(!(obsTransition instanceof ChannelEvent))
				continue;
			
			ChannelEvent obsChannelEvent = (ChannelEvent)obsTransition;   
			if(!(obsChannelEvent.getChannelName().isComparable(channelNameValue) && 
					channelNameValue.isGTEQPrecise(obsChannelEvent.getChannelName()))){
				resultSet.add(obsTransition);
			}
		}
		
		return new CmlTransitionSet(resultSet,specialEvents);
	}
	
	public CmlTransitionSet removeByChannelNameSet(ChannelNameSetValue channelNameSetValue)
	{
		Set<ObservableEvent> resultSet = new LinkedHashSet<ObservableEvent>();
		
		for(ObservableEvent obsTransition : _observableEvents)
		{
			if(!(obsTransition instanceof ChannelEvent))
				continue;
			
			boolean retaintIt = true; 
			
			for(ChannelNameValue channelNameValue : channelNameSetValue)
			{
				ChannelEvent obsChannelEvent = (ChannelEvent)obsTransition;   
				if(obsChannelEvent.getChannelName().isComparable(channelNameValue) && 
						channelNameValue.isGTEQPrecise(obsChannelEvent.getChannelName()))
				{
					retaintIt = false;
					break;
				}
					
			}
			
			if(retaintIt)
				resultSet.add(obsTransition);
		}
		
		return new CmlTransitionSet(resultSet,specialEvents);
	}
	
	public CmlTock getTockEvent()
	{
		
		CmlTock tock = null;
				
		for(ObservableEvent obs : _observableEvents)
			if(obs instanceof CmlTock)
			{
				tock = (CmlTock)obs;
				break;
			}
		
		return tock;
	}

	/**
	 * Subtract other from this
	 * @param other
	 * @return An alphabet containing all the events that are this
	 */
	public CmlTransitionSet subtract(CmlTransitionSet other)
	{
		Set<ObservableEvent> newReferenceEvents = new LinkedHashSet<ObservableEvent>();
		newReferenceEvents.addAll(_observableEvents);
		newReferenceEvents.removeAll(other.getObservableEvents());
		
		return new CmlTransitionSet(newReferenceEvents,specialEvents);
	}
	
	public CmlTransitionSet subtract(ObservableEvent other)
	{
		Set<ObservableEvent> newReferenceEvents = new LinkedHashSet<ObservableEvent>();
		newReferenceEvents.addAll(_observableEvents);
		newReferenceEvents.remove(other);
		
		return new CmlTransitionSet(newReferenceEvents,specialEvents);
	}
	
	/**
	 * Subtract other from this
	 * @param other
	 * @return An alphabet containing all the events that are this
	 */
	public CmlTransitionSet subtractImprecise(CmlTransitionSet other)
	{
		Set<ObservableEvent> newReferenceEvents = new LinkedHashSet<ObservableEvent>();
		newReferenceEvents.addAll(_observableEvents);
		newReferenceEvents.removeAll(this.intersectImprecise(other).getObservableEvents());
		
		return new CmlTransitionSet(newReferenceEvents,specialEvents);
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
		return (event instanceof ObservableEvent && !intersectImprecise((ObservableEvent)event).isEmpty()) || contains(event);
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

		if(!(other instanceof CmlTransitionSet))
			return false;
			
		return getAllEvents().equals(((CmlTransitionSet)other).getAllEvents());
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
	public CmlTransitionSet expandAlphabet()
	{
		Set<CmlTransition> eventSet = new HashSet<CmlTransition>();
		
		for(CmlTransition ev : getAllEvents())
			if(ev instanceof ChannelEvent)
				eventSet.addAll(((ChannelEvent)ev).expand());
			else
				eventSet.add(ev);
		
		return new CmlTransitionSet(eventSet);
		
	}
	
	@Override
	public Object clone() {

		return new CmlTransitionSet(new LinkedHashSet<ObservableEvent>(_observableEvents), 
				new HashSet<SilentTransition>(specialEvents));
	}
}
