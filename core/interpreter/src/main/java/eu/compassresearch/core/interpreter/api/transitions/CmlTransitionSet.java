package eu.compassresearch.core.interpreter.api.transitions;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.api.values.ChannelNameSetValue;
import eu.compassresearch.core.interpreter.api.values.ChannelNameValue;

/**
 * This represents a CML alphabet containing both silent and observable events
 * 
 * @author akm
 */
public class CmlTransitionSet extends Value
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5192258370825756900L;

	// This contains the observable events
	private final SortedSet<ObservableTransition> _observableEvents;
	// This contains all the special events like tau
	private final SortedSet<AbstractSilentTransition> silentEvents;

	public CmlTransitionSet()
	{
		this.silentEvents = new TreeSet<AbstractSilentTransition>();
		this._observableEvents = new TreeSet<ObservableTransition>();
	}

	public CmlTransitionSet(ObservableTransition obsEvent)
	{
		this.silentEvents = new TreeSet<AbstractSilentTransition>();
		this._observableEvents = new TreeSet<ObservableTransition>();
		this._observableEvents.add(obsEvent);
	}

	public CmlTransitionSet(AbstractSilentTransition tauEvent)
	{
		this.silentEvents = new TreeSet<AbstractSilentTransition>();
		this._observableEvents = new TreeSet<ObservableTransition>();
		this.silentEvents.add(tauEvent);
	}

	public CmlTransitionSet(ObservableTransition obs,
			AbstractSilentTransition tauEvent)
	{
		this.silentEvents = new TreeSet<AbstractSilentTransition>();
		this.silentEvents.add(tauEvent);
		this._observableEvents = new TreeSet<ObservableTransition>();
		this._observableEvents.add(obs);
	}

	public CmlTransitionSet(SortedSet<ObservableTransition> comms,
			SortedSet<AbstractSilentTransition> specialEvents)
	{
		this.silentEvents = new TreeSet<AbstractSilentTransition>(specialEvents);
		this._observableEvents = new TreeSet<ObservableTransition>(comms);
	}

	public CmlTransitionSet(SortedSet<CmlTransition> events)
	{
		this.silentEvents = new TreeSet<AbstractSilentTransition>();
		this._observableEvents = new TreeSet<ObservableTransition>();

		for (CmlTransition e : events)
		{
			if (e instanceof AbstractSilentTransition)
			{
				this.silentEvents.add((AbstractSilentTransition) e);
			} else if (e instanceof ObservableTransition)
			{
				_observableEvents.add((ObservableTransition) e);
			}
		}
	}

	/**
	 * Returns all the observable events in the alphabet
	 * 
	 * @return
	 */
	public SortedSet<ObservableTransition> getObservableEvents()
	{
		return new TreeSet<ObservableTransition>(_observableEvents);
	}

	public SortedSet<ObservableTransition> getObservableChannelEvents()
	{
		SortedSet<ObservableTransition> observableChannelEvents = new TreeSet<ObservableTransition>();
		for (ObservableTransition obsEvent : _observableEvents)
		{
			if (obsEvent instanceof LabelledTransition)
			{
				observableChannelEvents.add(obsEvent);
			}
		}

		return observableChannelEvents;
	}

	/**
	 * Returns all the special events in the alphabet
	 * 
	 * @return
	 */
	public SortedSet<AbstractSilentTransition> getSilentTransitionsAsSet()
	{
		return new TreeSet<AbstractSilentTransition>(silentEvents);
	}

	public CmlTransitionSet getSilentTransitions()
	{
		return new CmlTransitionSet(new TreeSet<CmlTransition>(silentEvents));
	}

	/**
	 * Returns all the observable and special events in the alphabet as a set.
	 * 
	 * @return all the observable and special events.
	 */
	public SortedSet<CmlTransition> getAllEvents()
	{
		SortedSet<CmlTransition> allEvents = new TreeSet<CmlTransition>();
		allEvents.addAll(_observableEvents);
		allEvents.addAll(silentEvents);

		return allEvents;
	}
	
	public void displayAllAvaliableEvents(PrintStream out)
	{
		Iterator<CmlTransition> itr = getAllEvents().iterator();
		while(itr.hasNext())
		{
			CmlTransition event = itr.next();
			if(event instanceof TauTransition)
			{
				out.print("tau");
			}else{
				out.print(event);
			}
			if(itr.hasNext())
			{
				out.print(", ");
			}
		}
	}
	
	public int size()
	{
		return _observableEvents.size() + silentEvents.size();  
	}

	/**
	 * Calculate the union of this alphabet and the given event
	 * 
	 * @param event
	 * @return The union of this alphabet and the given CmlEvent
	 */
	public CmlTransitionSet union(CmlTransitionSet other)
	{
		SortedSet<CmlTransition> resultSet = this.getAllEvents();
		resultSet.addAll(other.getAllEvents());

		return new CmlTransitionSet(resultSet);
	}

	/**
	 * Calculates the union of this alphabet and the given event.
	 * 
	 * @param event
	 * @return The union of this alphabet and the given CmlEvent
	 */
	public CmlTransitionSet union(CmlTransition event)
	{
		SortedSet<CmlTransition> resultSet = this.getAllEvents();
		resultSet.add(event);

		return new CmlTransitionSet(resultSet);
	}

	/**
	 * Calculates the intersection with imprecision between this and other. Meaning that two events might intersect even
	 * though their value is not equal E.g. Assume that we have the following two alphabets A = {a.3,b} and B = {a.?}
	 * then the result after invoking this function would be A.intersectImprecise(B) == {a.3,a.?} As a.3 and a.?
	 * intersects even though a.? can be any value of the type of 'a'.
	 * 
	 * @param other
	 * @return
	 */
	public CmlTransitionSet intersect(CmlTransitionSet other)
	{
		SortedSet<CmlTransition> resultSet = new TreeSet<CmlTransition>();

		for (ObservableTransition thisEvent : _observableEvents)
		{
			for (ObservableTransition otherEvent : other._observableEvents)
			{
				if (thisEvent.isComparable(otherEvent)
						&& thisEvent.isSourcesSubset(otherEvent))
				{
					resultSet.add(thisEvent);
				}
			}
		}

		return new CmlTransitionSet(resultSet);
	}

	public CmlTransitionSet intersect(ObservableTransition other)
	{
		SortedSet<CmlTransition> resultSet = new TreeSet<CmlTransition>();

		for (ObservableTransition thisEvent : _observableEvents)
		{
			if (thisEvent.isComparable(other)
					&& thisEvent.isSourcesSubset(other))
			{
				resultSet.add(thisEvent);
			}
		}

		return new CmlTransitionSet(resultSet);
	}

	public CmlTransitionSet retainByChannelName(
			ChannelNameValue channelNameValue)
	{
		SortedSet<CmlTransition> resultSet = new TreeSet<CmlTransition>();

		for (ObservableTransition obsTransition : _observableEvents)
		{
			if (!(obsTransition instanceof LabelledTransition))
			{
				continue;
			}

			LabelledTransition obsChannelEvent = (LabelledTransition) obsTransition;
			if (obsChannelEvent.getChannelName().isComparable(channelNameValue)
					&& channelNameValue.isGTEQPrecise(obsChannelEvent.getChannelName()))
			{
				resultSet.add(obsTransition);
			}
		}

		return new CmlTransitionSet(resultSet);
	}

	public CmlTransitionSet retainByChannelNameSet(
			ChannelNameSetValue channelNameSetValue)
	{
		SortedSet<CmlTransition> resultSet = new TreeSet<CmlTransition>();

		for (ObservableTransition obsTransition : _observableEvents)
		{
			if (!(obsTransition instanceof LabelledTransition))
			{
				continue;
			}

			LabelledTransition obsChannelEvent = (LabelledTransition) obsTransition;

			for (ChannelNameValue channelNameValue : channelNameSetValue)
			{

				if (obsChannelEvent.getChannelName().isComparable(channelNameValue)
						&& channelNameValue.isGTEQPrecise(obsChannelEvent.getChannelName()))
				{
					resultSet.add(obsTransition);
				}
			}
		}

		return new CmlTransitionSet(resultSet);
	}

	public CmlTransitionSet removeByChannelName(
			ChannelNameValue channelNameValue)
	{
		SortedSet<ObservableTransition> resultSet = new TreeSet<ObservableTransition>();

		for (ObservableTransition obsTransition : _observableEvents)
		{
			if (!(obsTransition instanceof LabelledTransition))
			{
				continue;
			}

			LabelledTransition obsChannelEvent = (LabelledTransition) obsTransition;
			if (!(obsChannelEvent.getChannelName().isComparable(channelNameValue) && channelNameValue.isGTEQPrecise(obsChannelEvent.getChannelName())))
			{
				resultSet.add(obsTransition);
			}
		}

		return new CmlTransitionSet(resultSet, silentEvents);
	}

	public CmlTransitionSet removeByChannelNameSet(
			ChannelNameSetValue channelNameSetValue)
	{
		SortedSet<ObservableTransition> resultSet = new TreeSet<ObservableTransition>();

		for (ObservableTransition obsTransition : _observableEvents)
		{
			if (!(obsTransition instanceof LabelledTransition))
			{
				continue;
			}

			boolean retaintIt = true;

			for (ChannelNameValue channelNameValue : channelNameSetValue)
			{
				LabelledTransition obsChannelEvent = (LabelledTransition) obsTransition;
				if (obsChannelEvent.getChannelName().isComparable(channelNameValue)
						&& channelNameValue.isGTEQPrecise(obsChannelEvent.getChannelName()))
				{
					retaintIt = false;
					break;
				}

			}

			if (retaintIt)
			{
				resultSet.add(obsTransition);
			}
		}

		return new CmlTransitionSet(resultSet, silentEvents);
	}

	public TimedTransition getTockEvent()
	{

		TimedTransition tock = null;

		for (ObservableTransition obs : _observableEvents)
		{
			if (obs instanceof TimedTransition)
			{
				tock = (TimedTransition) obs;
				break;
			}
		}

		return tock;
	}

	public boolean hasTockEvent()
	{
		return getTockEvent() != null;
	}

	/**
	 * Subtract other from this
	 * 
	 * @param other
	 * @return An alphabet containing all the events that are this
	 */
	public CmlTransitionSet subtract(CmlTransitionSet other)
	{
		SortedSet<ObservableTransition> newReferenceEvents = new TreeSet<ObservableTransition>();
		newReferenceEvents.addAll(_observableEvents);
		newReferenceEvents.removeAll(other.getObservableEvents());

		return new CmlTransitionSet(newReferenceEvents, silentEvents);
	}

	public CmlTransitionSet subtract(ObservableTransition other)
	{
		SortedSet<ObservableTransition> newReferenceEvents = new TreeSet<ObservableTransition>();
		newReferenceEvents.addAll(_observableEvents);
		newReferenceEvents.remove(other);

		return new CmlTransitionSet(newReferenceEvents, silentEvents);
	}

	/**
	 * Subtract other from this
	 * 
	 * @param other
	 * @return An alphabet containing all the events that are this
	 */
	public CmlTransitionSet subtractImprecise(CmlTransitionSet other)
	{
		SortedSet<ObservableTransition> newReferenceEvents = new TreeSet<ObservableTransition>();
		newReferenceEvents.addAll(_observableEvents);
		newReferenceEvents.removeAll(this.intersect(other).getObservableEvents());

		return new CmlTransitionSet(newReferenceEvents, silentEvents);
	}

	/**
	 * This determines whether the alphabet contains an observable event.
	 * 
	 * @return true if the observable event is contained else false
	 */
	public boolean contains(CmlTransition event)
	{
		if (event instanceof ObservableTransition
				&& !intersect((ObservableTransition) event).isEmpty())
		{
			return true;
		} else if (event instanceof AbstractSilentTransition)
		{
			return containsSilentTransition((AbstractSilentTransition) event);
		} else
		{
			return _observableEvents.contains(event);
		}
	}

	private boolean containsSilentTransition(AbstractSilentTransition transition)
	{
		for (CmlTransition thisTransition : getAllEvents())
		{
			if (thisTransition.isSourcesSubset(transition))
			{
				return true;
			}
		}

		return false;
	}

	public boolean isEmpty()
	{
		return _observableEvents.isEmpty() && silentEvents.isEmpty();
	}

	@Override
	public String toString()
	{
		return getAllEvents().toString();
	}

	@Override
	public boolean equals(Object other)
	{

		if (!(other instanceof CmlTransitionSet))
		{
			return false;
		}

		return getAllEvents().equals(((CmlTransitionSet) other).getAllEvents());
	}

	@Override
	public int hashCode()
	{
		return getAllEvents().hashCode();
	}

	@Override
	public String kind()
	{
		return "CmlAlphabetValue";
	}

	/**
	 * This expands all the expandable events in the alphabet. E.g. if we have types switch = <ON> | <OFF> channels a :
	 * switch process Test = begin @ a?x -> Skip end then the immediate alphabet would be {a.AnyValue} when expanded
	 * this will be {a.<ON> , a.<OFF>}
	 * 
	 * @return The same alphabet but with all the expandable events expanded
	 */
	public CmlTransitionSet expandAlphabet()
	{
		SortedSet<CmlTransition> eventSet = new TreeSet<CmlTransition>();

		for (CmlTransition ev : getAllEvents())
		{
			if (ev instanceof LabelledTransition)
			{
				eventSet.addAll(((LabelledTransition) ev).expand());
			} else
			{
				eventSet.add(ev);
			}
		}

		return new CmlTransitionSet(eventSet);

	}

	@Override
	public Object clone()
	{

		return new CmlTransitionSet(new TreeSet<ObservableTransition>(_observableEvents), new TreeSet<AbstractSilentTransition>(silentEvents));
	}
}
