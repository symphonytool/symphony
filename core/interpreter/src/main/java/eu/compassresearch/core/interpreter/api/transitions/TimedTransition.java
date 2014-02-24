package eu.compassresearch.core.interpreter.api.transitions;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;

public class TimedTransition extends AbstractCmlTransition implements
		ObservableTransition
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5584770741085270746L;
	private final long timeLimit;

	public TimedTransition(CmlBehaviour eventSource, long limit)
	{
		super(eventSource);
		this.timeLimit = limit;
	}

	public TimedTransition(CmlBehaviour eventSource)
	{
		this(eventSource, 0);
	}

	public TimedTransition()
	{
		super(new TreeSet<CmlBehaviour>());
		timeLimit = 0;
	}

	protected TimedTransition(SortedSet<CmlBehaviour> eventSources,
			long timeLimit)
	{
		super(eventSources);
		this.timeLimit = timeLimit;
	}

	@Override
	public ObservableTransition synchronizeWith(ObservableTransition syncEvent)
	{

		// if(!isComparable(other))
		// throw new NotComparableException();
		TimedTransition otherTock = (TimedTransition) syncEvent;
		SortedSet<CmlBehaviour> sources = new TreeSet<CmlBehaviour>();
		sources.addAll(this.getEventSources());
		sources.addAll(otherTock.getEventSources());
		long newLimit;
		// If they both have a time limit we take min of them
		// since a potential transition can happen at this point
		if (this.hasTimeLimit() && otherTock.hasTimeLimit())
		{
			newLimit = Math.min(this.timeLimit, otherTock.timeLimit);
			// if at least one of them has no time limit we take max of them since
			// zero means no timelimit so we always prefer the non-zero limit
		} else
		{
			newLimit = Math.max(this.timeLimit, otherTock.timeLimit);
		}

		return new TimedTransition(sources, newLimit);
	}

	// @Override
	public Set<INode> getDestinationNodes()
	{
		return this.getSourceNodes();
	}

	@Override
	public boolean isComparable(ObservableTransition other)
	{
		return other instanceof TimedTransition;
	}
	
	@Override
	public boolean isSynchronizedBy(ObservableTransition other)
	{
		return this.isComparable(other) && this.isSourcesSubset(other);
	}
	
	@Override
	public boolean isSynchronizableWith(ObservableTransition other)
	{
		return isComparable(other);
	}

	public long getTimeLimit()
	{
		return timeLimit;
	}

	public boolean hasTimeLimit()
	{
		return timeLimit != 0;
	}

	@Override
	public boolean equals(Object obj)
	{

		if (!(obj instanceof TimedTransition))
		{
			return false;
		}

		return super.equals(obj);
	}

	@Override
	public String toString()
	{
		return "tock";
	}

	@Override
	public int hashCode()
	{
		return "tock".hashCode() + this.eventSources.hashCode();
	}

	// @Override
	// public int compareTo(CmlTransition o)
	// {
	// if(o instanceof CmlTock)
	// return 0;
	// else
	// return -1;
	// }

}
