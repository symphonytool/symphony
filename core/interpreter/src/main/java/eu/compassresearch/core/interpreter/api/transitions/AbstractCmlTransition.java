package eu.compassresearch.core.interpreter.api.transitions;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;

abstract class AbstractCmlTransition implements CmlTransition
{
	private static int transitionIdCounter = 0;
	/**
	 * 
	 */
	private static final long serialVersionUID = -5555627737673754975L;
	final protected SortedSet<CmlBehaviour> eventSources;
	private final int transitionsId = transitionIdCounter++;

	public AbstractCmlTransition(CmlBehaviour eventSource)
	{
		this.eventSources = new TreeSet<CmlBehaviour>();
		this.eventSources.add(eventSource);
	}

	public AbstractCmlTransition(SortedSet<CmlBehaviour> eventSources)
	{
		this.eventSources = eventSources;
	}

	public SortedSet<CmlBehaviour> getEventSources()
	{
		return eventSources;
	}

	@Override
	public boolean isSourcesSubset(CmlTransition other)
	{

		return other.getEventSources().containsAll(getEventSources())
				|| getEventSources().containsAll(other.getEventSources());

	}

	@Override
	public Set<INode> getSourceNodes()
	{
		HashSet<INode> nodes = new LinkedHashSet<INode>();
		for (CmlBehaviour s : this.eventSources)
		{
			nodes.add(s.getNextState().first);
		}

		return nodes;
	}

	@Override
	public boolean equals(Object obj)
	{

		CmlTransition other = null;

		if (!(obj instanceof CmlTransition))
		{
			return false;
		}

		other = (CmlTransition) obj;
		return eventSources.equals(other.getEventSources());
	}
	
	@Override
	public int getTransitionId()
	{
		return transitionsId;
	}

	/**
	 * We want to order the transitions some how, at this point we order them by
	 * an increasing id they get at creating time 
	 */
	@Override
	public int compareTo(CmlTransition o)
	{
		return Integer.compare(transitionsId, o.getTransitionId()); 
//		if(!this.equals(o) && this.eventSources.first().compareTo(o.getEventSources().first()) == 0)
//			return Integer.compare(transitionsId, o.getTransitionId());
//		else
//		{
//			return this.eventSources.first().compareTo(o.getEventSources().first());
//		}
	}
}
