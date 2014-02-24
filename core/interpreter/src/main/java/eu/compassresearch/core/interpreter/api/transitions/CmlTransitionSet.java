package eu.compassresearch.core.interpreter.api.transitions;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import eu.compassresearch.core.interpreter.api.values.ChannelNameSetValue;
import eu.compassresearch.core.interpreter.api.values.ChannelNameValue;

/**
 * This represents a set of CmlTransition objects
 * 
 * @author akm
 */
public class CmlTransitionSet implements Iterable<CmlTransition>
{

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 5192258370825756900L;

	private final SortedSet<CmlTransition> transitions;

	public CmlTransitionSet()
	{
		transitions = new TreeSet<CmlTransition>();
	}

	public CmlTransitionSet(CmlTransition transition)
	{
		transitions = new TreeSet<CmlTransition>();
		transitions.add(transition);
	}

	public CmlTransitionSet(CmlTransition transition1, CmlTransition transition2)
	{
		transitions = new TreeSet<CmlTransition>();
		transitions.add(transition1);
		transitions.add(transition2);
		
	}

	public CmlTransitionSet(SortedSet<CmlTransition> transitions)
	{
		this.transitions = new TreeSet<CmlTransition>(transitions);
		
		if( getTransitionsOfType(TimedTransition.class).size() > 1)
			throw new RuntimeException();
	}

	@SuppressWarnings("unchecked")
	public <T extends CmlTransition> SortedSet<T> getTransitionsOfType(Class<T> type)
	{
		TreeSet<T> foundTransitions = new TreeSet<T>();
		
		for(CmlTransition t : this.transitions)
			if(type.isInstance(t))
				foundTransitions.add((T)t);
		
		return foundTransitions;
	}
	
	public <T extends CmlTransition> boolean hasTransitionsOfType(Class<T> type)
	{
		for(CmlTransition t : this.transitions)
			if(type.isInstance(t))
				return true;
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends CmlTransition> T getTransitionOfType(Class<T> type)
	{
		for(CmlTransition t : this.transitions)
			if(type.isInstance(t))
				return (T)t;
		
		return null;
	}
	
	
	public <T extends CmlTransition> CmlTransitionSet removeByType(Class<T> type)
	{
		TreeSet<CmlTransition> result = new TreeSet<CmlTransition>();
		for(CmlTransition t : this.transitions)
			if(!type.isInstance(t))
				result.add(t);
		
		return new CmlTransitionSet(result);
	}
	
	public <T extends CmlTransition> CmlTransitionSet filterTransitionsOfType(Class<T> type)
	{
		TreeSet<CmlTransition> foundTransitions = new TreeSet<CmlTransition>();
		
		for(CmlTransition t : this.transitions)
			if(type.isInstance(t))
				foundTransitions.add(t);
		
		return new CmlTransitionSet(foundTransitions);
	}

	/**
	 * Returns all the observable and special events in the alphabet as a set.
	 * 
	 * @return all the observable and special events.
	 */
	public SortedSet<CmlTransition> getTransitionsAsSet()
	{
		return new TreeSet<CmlTransition>(this.transitions);
	}
	
	public void displayAllAvaliableEvents(PrintStream out)
	{
		Iterator<CmlTransition> itr = this.transitions.iterator();
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
		return transitions.size();  
	}

	/**
	 * Calculate the union of this alphabet and the given event
	 * 
	 * @param event
	 * @return The union of this alphabet and the given CmlEvent
	 */
	public CmlTransitionSet union(CmlTransitionSet other)
	{
		SortedSet<CmlTransition> resultSet = new TreeSet<CmlTransition>(this.transitions);
		resultSet.addAll(other.transitions);
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
		SortedSet<CmlTransition> resultSet = getTransitionsAsSet();
		resultSet.add(event);

		return new CmlTransitionSet(resultSet);
	}

//	/**
//	 * Calculates the intersection with imprecision between this and other. Meaning that two events might intersect even
//	 * though their value is not equal E.g. Assume that we have the following two alphabets A = {a.3,b} and B = {a.?}
//	 * then the result after invoking this function would be A.intersectImprecise(B) == {a.3,a.?} As a.3 and a.?
//	 * intersects even though a.? can be any value of the type of 'a'.
//	 * 
//	 * @param other
//	 * @return
//	 */
//	public CmlTransitionSet intersect(CmlTransitionSet other)
//	{
//		SortedSet<CmlTransition> resultSet = new TreeSet<CmlTransition>();
//
//		for (CmlTransition thisEvent : this.transitions)
//		{
//			if(thisEvent instanceof ObservableTransition)
//			{
//			
//				for (CmlTransition otherEvent : other.transitions)
//				{
//					if(otherEvent instanceof ObservableTransition)
//					{
//						if (((ObservableTransition)thisEvent).isComparable((ObservableTransition)otherEvent)
//								&& thisEvent.isSourcesSubset(otherEvent))
//						{
//							resultSet.add(thisEvent);
//						}
//					}
//				}
//			}
//		}
//
//		return new CmlTransitionSet(resultSet);
//	}

//	public CmlTransitionSet intersectWithObservable(ObservableTransition other)
//	{
//		SortedSet<CmlTransition> resultSet = new TreeSet<CmlTransition>();
//
//		for (CmlTransition thisEvent : transitions)
//		{
//			if(thisEvent instanceof ObservableTransition)
//			{
//				if (((ObservableTransition)thisEvent).isComparable(other)
//						&& thisEvent.isSourcesSubset(other))
//				{
//					resultSet.add(thisEvent);
//				}
//			}
//		}
//
//		return new CmlTransitionSet(resultSet);
//	}

	public CmlTransitionSet retainByChannelName(
			ChannelNameValue channelNameValue)
	{
		SortedSet<CmlTransition> resultSet = new TreeSet<CmlTransition>();

		for (CmlTransition obsTransition : transitions)
		{
			if(obsTransition instanceof LabelledTransition)
			{
				LabelledTransition obsChannelEvent = (LabelledTransition) obsTransition;
				if (obsChannelEvent.getChannelName().isComparable(channelNameValue)
						&& channelNameValue.isGTEQPrecise(obsChannelEvent.getChannelName()))
				{
					resultSet.add(obsTransition);
				}
			}
		}

		return new CmlTransitionSet(resultSet);
	}

	public CmlTransitionSet retainByChannelNameSet(
			ChannelNameSetValue channelNameSetValue)
	{
		SortedSet<CmlTransition> resultSet = new TreeSet<CmlTransition>();

		for (CmlTransition obsTransition : transitions)
		{
			if(obsTransition instanceof LabelledTransition)
			{
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
		}

		return new CmlTransitionSet(resultSet);
	}

	public CmlTransitionSet removeByChannelName(
			ChannelNameValue channelNameValue)
	{
		SortedSet<CmlTransition> resultSet = new TreeSet<CmlTransition>();

		for (CmlTransition obsTransition : transitions)
		{
			if (obsTransition instanceof LabelledTransition)
			{
				LabelledTransition obsChannelEvent = (LabelledTransition) obsTransition;
				if (!(obsChannelEvent.getChannelName().isComparable(channelNameValue) && channelNameValue.isGTEQPrecise(obsChannelEvent.getChannelName())))
				{
					resultSet.add(obsTransition);
				}
			}
			else
			{
				resultSet.add(obsTransition);
			}
		}

		return new CmlTransitionSet(resultSet);
	}

	/**
	 * Returns a new CmlTransitionSet where all the LabelledTransitions with a channel in channelNameSetValue
	 * are removed 
	 * 
	 * @param channelNameSetValue
	 * @return
	 */
	public CmlTransitionSet removeByChannelNameSet(
			ChannelNameSetValue channelNameSetValue)
	{
		SortedSet<CmlTransition> resultSet = new TreeSet<CmlTransition>();

		for (CmlTransition obsTransition : transitions)
		{
			if (obsTransition instanceof LabelledTransition)
			{

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
			//TODO: this should be there
			else if(!(obsTransition instanceof TimedTransition))
			{
				resultSet.add(obsTransition);
			}
		}

		return new CmlTransitionSet(resultSet);
	}

	/**
	 * Subtract other from this
	 * 
	 * @param other
	 * @return An alphabet containing all the events that are this
	 */
	public CmlTransitionSet subtract(CmlTransitionSet other)
	{
		SortedSet<CmlTransition> result = new TreeSet<CmlTransition>();
		result.addAll(transitions);
		result.removeAll(other.transitions);

		return new CmlTransitionSet(result);
	}

	public CmlTransitionSet subtract(CmlTransition other)
	{
		SortedSet<CmlTransition> result = new TreeSet<CmlTransition>();
		result.addAll(transitions);
		result.remove(other);

		return new CmlTransitionSet(result);
	}

	/**
	 * This determines whether the alphabet contains the given transition or
	 * a transition that is part of a synchronization.
	 * 
	 * @return true if the given is contained else false
	 */
	public boolean containsEqualOrSyncPart(CmlTransition transition)
	{
		for (CmlTransition thisTransition : transitions)
		{
			if(thisTransition.equals(transition))
			{
				return true;
			}
			else if(transition instanceof ObservableTransition && 
					thisTransition instanceof ObservableTransition &&
					((ObservableTransition)thisTransition).isSynchronizedBy((ObservableTransition)transition) 
					)
			{
				return true;
			}
		}

		return false;
	}


	public boolean isEmpty()
	{
		return transitions.isEmpty();
	}

	@Override
	public String toString()
	{
		return transitions.toString();
	}

	@Override
	public boolean equals(Object other)
	{

		if (!(other instanceof CmlTransitionSet))
		{
			return false;
		}

		return transitions.equals(((CmlTransitionSet) other).transitions);
	}

	@Override
	public int hashCode()
	{
		return transitions.hashCode();
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

		for (CmlTransition ev : transitions)
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
	public Iterator<CmlTransition> iterator()
	{
		return transitions.iterator();
	}
}
