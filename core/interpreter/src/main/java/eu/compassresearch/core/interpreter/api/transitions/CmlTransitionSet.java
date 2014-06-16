package eu.compassresearch.core.interpreter.api.transitions;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import eu.compassresearch.core.interpreter.api.transitions.ops.Filter;
import eu.compassresearch.core.interpreter.api.transitions.ops.MapOperation;
import eu.compassresearch.core.interpreter.api.transitions.ops.RemoveChannelNames;
import eu.compassresearch.core.interpreter.api.transitions.ops.RemoveTock;
import eu.compassresearch.core.interpreter.api.transitions.ops.RetainChannelNamesAndTime;
import eu.compassresearch.core.interpreter.api.values.ChannelNameSetValue;

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

		if (filterByTypeAsSet(TimedTransition.class).size() > 1)
		{
			throw new RuntimeException();
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends CmlTransition> SortedSet<T> filterByTypeAsSet(
			Class<T> type)
	{
		TreeSet<T> foundTransitions = new TreeSet<T>();

		for (CmlTransition t : this.transitions)
		{
			if (type.isInstance(t))
			{
				foundTransitions.add((T) t);
			}
		}

		return foundTransitions;
	}

	public <T extends CmlTransition> boolean hasType(Class<T> type)
	{
		for (CmlTransition t : this.transitions)
		{
			if (type.isInstance(t))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Return the first element of type T
	 * @param <T> 
	 * 
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends CmlTransition> T firstOfType(Class<T> type)
	{
		for (CmlTransition t : this.transitions)
		{
			if (type.isInstance(t))
			{
				return (T) t;
			}
		}

		return null;
	}

	public <T extends CmlTransition> CmlTransitionSet removeAllType(
			Class<T> type)
	{
		TreeSet<CmlTransition> result = new TreeSet<CmlTransition>();
		for (CmlTransition t : this.transitions)
		{
			if (!type.isInstance(t))
			{
				result.add(t);
			}
		}

		return new CmlTransitionSet(result);
	}

	@SuppressWarnings("unchecked")
	public <T extends CmlTransition> CmlTransitionSet filterByType(Class<T> type)
	{
		return new CmlTransitionSet((SortedSet<CmlTransition>) filterByTypeAsSet(type));
	}

	/**
	 * Returns all the observable and special events in the alphabet as a set.
	 * 
	 * @return all the observable and special events.
	 */
	public SortedSet<CmlTransition> asSet()
	{
		return new TreeSet<CmlTransition>(this.transitions);
	}

	public void displayAllAvaliableEvents(PrintStream out)
	{
		Iterator<CmlTransition> itr = this.transitions.iterator();
		while (itr.hasNext())
		{
			CmlTransition event = itr.next();
			if (event instanceof TauTransition)
			{
				out.print("tau");
			} else
			{
				out.print(event);
			}
			if (itr.hasNext())
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
	 * @param other 
	 * 
	 * @return The union of this alphabet and the given CmlEvent
	 */
	public CmlTransitionSet union(CmlTransitionSet other)
	{
		SortedSet<CmlTransition> resultSet = new TreeSet<CmlTransition>(this.transitions);
		resultSet.addAll(other.transitions);
		return new CmlTransitionSet(resultSet);
	}

	public CmlTransitionSet dunion(CmlTransitionSet... others)
	{
		SortedSet<CmlTransition> resultSet = new TreeSet<CmlTransition>(this.transitions);

		for (CmlTransitionSet other : others)
		{
			resultSet.addAll(other.transitions);
		}

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
		SortedSet<CmlTransition> resultSet = asSet();
		resultSet.add(event);

		return new CmlTransitionSet(resultSet);
	}

	public CmlTransitionSet synchronizeOn(CmlTransitionSet other,
			ChannelNameSetValue cs)
	{
		return synchronizeOn(other, cs, false);
	}

	public CmlTransitionSet synchronizeOn(final CmlTransitionSet other,
			ChannelNameSetValue cs, final boolean allowNonSynchedTime)
	{
		// create a filter that only accepts the cs channels or time
		final Filter f = new RetainChannelNamesAndTime(cs);

		CmlTransitionSet synchedAndThisAllowed = this.map(new MapOperation()
		{
			CmlTransitionSet remainingOther = other;

			@Override
			public CmlTransition apply(CmlTransition transition)
			{

				if (f.isAccepted(transition))
				{
					// since got through the filter, we now its at least observable
					ObservableTransition thisOT = (ObservableTransition) transition;
					// So go through all the other transitions and see if they can be
					// synched on
					for (CmlTransition otherT : remainingOther)
					{
						if (otherT instanceof ObservableTransition
								&& thisOT.isSynchronizableWith((ObservableTransition) otherT))
						{
							// remove the synched element so we dont need to check it again
							remainingOther = remainingOther.subtract(otherT);
							return thisOT.synchronizeWith((ObservableTransition) otherT);
						}
					}

					// If we allow time to pass without synching then we add it independently
					if (allowNonSynchedTime
							&& transition instanceof TimedTransition)
					{
						return transition;
						// else we take it out since the transition are not allowed to be
						// performed if it cannot be synced and is in cs
					} else
					{
						return null;
					}
				} else
				{
					// just return the same transition if not in cs
					return transition;
				}
			}
		});

		// again if we allow nonsynched time then we need to keep it only if
		// synchedAndThisAllowed does not have it
		if (allowNonSynchedTime
				&& !synchedAndThisAllowed.hasType(TimedTransition.class))
		{
			return synchedAndThisAllowed.union(other.filter(new RemoveChannelNames(cs)));
		} else
		{
			return synchedAndThisAllowed.union(other.filter(new RemoveChannelNames(cs), new RemoveTock()));
		}
	}

	public CmlTransitionSet filter(Filter... filters)
	{
		SortedSet<CmlTransition> resultSet = new TreeSet<CmlTransition>();

		for (CmlTransition t : transitions)
		{
			boolean filterResult = true;
			for (Filter filter : filters)
			{
				if (!(filterResult &= filter.isAccepted(t)))
				{
					break;
				}
			}
			if (filterResult)
			{
				resultSet.add(t);
			}
		}

		return new CmlTransitionSet(resultSet);
	}

	public CmlTransitionSet map(MapOperation... mapOps)
	{
		SortedSet<CmlTransition> resultSet = new TreeSet<CmlTransition>();

		for (CmlTransition t : transitions)
		{
			CmlTransition mapResult = t;
			for (MapOperation mapOp : mapOps)
			{
				mapResult = mapOp.apply(mapResult);
			}

			if (mapResult != null)
			{
				resultSet.add(mapResult);
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
	 * This determines whether the alphabet contains the given transition or a transition that is part of a
	 * synchronization.
	 * @param transition 
	 * 
	 * @return true if the given is contained else false
	 */
	public boolean containsEqualOrSyncPart(CmlTransition transition)
	{
		for (CmlTransition thisTransition : transitions)
		{
			if (thisTransition.equals(transition))
			{
				return true;
			} else if (transition instanceof ObservableTransition
					&& thisTransition instanceof ObservableTransition
					&& ((ObservableTransition) thisTransition).isSynchronizedBy((ObservableTransition) transition))
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
