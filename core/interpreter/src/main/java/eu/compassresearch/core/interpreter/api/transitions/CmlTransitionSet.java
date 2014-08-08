package eu.compassresearch.core.interpreter.api.transitions;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import org.overture.interpreter.values.SetValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.api.transitions.ops.Filter;
import eu.compassresearch.core.interpreter.api.transitions.ops.MapOperation;
import eu.compassresearch.core.interpreter.api.transitions.ops.RemoveChannelNames;
import eu.compassresearch.core.interpreter.api.transitions.ops.RemoveTock;
import eu.compassresearch.core.interpreter.api.transitions.ops.RetainChannelNamesAndTime;
import eu.compassresearch.core.interpreter.api.values.ChannelValue;
import eu.compassresearch.core.interpreter.api.values.LatticeTopValue;
import eu.compassresearch.core.interpreter.api.values.LooseValue;

/**
 * This represents a set of CmlTransition objects
 * 
 * @author akm & Kel
 */
public class CmlTransitionSet implements Iterable<CmlTransition>
{

	/**
	 * serial
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
	 * 
	 * @param <T>
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
	 * 
	 * @param other
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

	public CmlTransitionSet synchronizeOn(CmlTransitionSet other, SetValue cs)
	{
		return synchronizeOn(other, cs, false);
	}

	public CmlTransitionSet synchronizeOn(final CmlTransitionSet other,
			SetValue cs, final boolean allowNonSynchedTime)
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
							// remove the synched element so we dont need to check it again, but only if it doesnt
							// contain a .?

							boolean containsLatticeValue = false;
							if (otherT instanceof ObservableLabelledTransition)
							{
								ObservableLabelledTransition event = (ObservableLabelledTransition) otherT;
								for (Value val : event.channelName.getValues())
								{
									if (val instanceof LatticeTopValue)
									{
										containsLatticeValue = true;
										break;
									}
								}
							}

							if (!containsLatticeValue)
							{
								remainingOther = remainingOther.subtract(otherT);
							}
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
	 * 
	 * @param transition
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

	/**
	 * Creates a new {@link CmlTransitionSet} object from self that is specialised based on the constraint set. So,
	 * unknown values in synchronisations (i.e. c.?) will be specialised by allowed channel values in the constraint
	 * set. I.e., if the constraint set has c.1, and this set has c.?, then the resulting set will have c.1. If there
	 * are possible synchronisations on channels not in the constraint set, they will be not be present in the result.
	 * 
	 * @param constraints
	 * @return
	 */
	public CmlTransitionSet constrainedExpand(SetValue constraints)
	{
		SortedSet<CmlTransition> eventSet = new TreeSet<CmlTransition>();

		for (CmlTransition ev : transitions)
		{
			if (ev instanceof ObservableLabelledTransition)
			{
				ObservableLabelledTransition lev = (ObservableLabelledTransition) ev;
				for (Value val : constraints.values)
				{
					ChannelValue constraint = (ChannelValue) val;
					final ChannelValue value = lev.getChannelName();
					if (value.isComparable(constraint))
					{
						if (value.isPrecise())
						{
							eventSet.add(ev);
						} else
						{
							List<Value> preciseValues = new Vector<Value>();

							Iterator<Value> itrValue = value.getValues().iterator();
							Iterator<Value> itrConstraint = constraint.getValues().iterator();

							while (itrValue.hasNext()
									&& itrConstraint.hasNext())
							{
								Value tVal = itrValue.next();
								Value cVal = itrConstraint.next();

								if (tVal instanceof LooseValue)
								{
									preciseValues.add(cVal);
								} else
								{
									preciseValues.add(tVal);
								}
							}

							final ChannelValue channelName = new ChannelValue(value.getChannel(), preciseValues, value.getConstraints());
							eventSet.add(new ObservableLabelledTransition(lev.getEventSources(), channelName));
						}
					}
				}

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

	/**
	 * This compresses a transition set such that events of the form c.1 and c.? will be merged into c.? where the new
	 * c.? will contain the sources of both
	 * 
	 * @return
	 */
	public CmlTransitionSet compress()
	{
		SortedSet<CmlTransition> eventSet = new TreeSet<CmlTransition>();
		SortedSet<CmlTransition> discardSet = new TreeSet<CmlTransition>();

		for (CmlTransition ev : transitions)
		{
			if (ev instanceof ObservableLabelledTransition)
			{
				ObservableLabelledTransition oev = (ObservableLabelledTransition) ev;
				if (!oev.getChannelName().isPrecise())
				{
					CmlTransition tmpTransition = oev;
					for (CmlTransition ev2 : transitions)
					{
						if (ev!=ev2 && ev instanceof ObservableLabelledTransition)
						{
							ObservableLabelledTransition oev2 = (ObservableLabelledTransition) ev2;
							if (oev.getChannelName().isEquallyOrMorePrecise(oev2.getChannelName()))
							{
								tmpTransition = new ObservableLabelledTransition(tmpTransition, ev2, oev.getChannelName());
								discardSet.add(oev2);
							}
						}

					}

					eventSet.add(tmpTransition);
				} else
				{
					eventSet.add(ev);
				}
			} else
			{
				eventSet.add(ev);
			}
		}
		
		if(!discardSet.isEmpty())
		{
			eventSet.removeAll(discardSet);
		}

		return new CmlTransitionSet(eventSet);
	}
}
