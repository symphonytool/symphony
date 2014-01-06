package eu.compassresearch.core.interpreter.api.transitions;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;

abstract class AbstractCmlTransition implements CmlTransition
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5555627737673754975L;
	final protected Set<CmlBehaviour> eventSources;

	public AbstractCmlTransition(CmlBehaviour eventSource)
	{
		this.eventSources = new HashSet<CmlBehaviour>();
		this.eventSources.add(eventSource);
	}

	public AbstractCmlTransition(Set<CmlBehaviour> eventSources)
	{
		this.eventSources = eventSources;
	}

	// @Override
	// public abstract CmlTransitionSet getAsAlphabet();

	public Set<CmlBehaviour> getEventSources()
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

	// @Override
	// public int compareTo(CmlTransition o)
	// {
	// if(equals(o))
	// return 0;
	// else if ()
	// }
}
