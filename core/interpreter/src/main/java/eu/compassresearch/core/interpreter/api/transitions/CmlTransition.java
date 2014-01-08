package eu.compassresearch.core.interpreter.api.transitions;

import java.io.Serializable;
import java.util.Set;
import java.util.SortedSet;

import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;

public interface CmlTransition extends Serializable , Comparable<CmlTransition>
{

	/**
	 * Returns the set of CmlBehaviourThreads that participates in this transition
	 * 
	 * @return
	 */
	public SortedSet<CmlBehaviour> getEventSources();

	/**
	 * Convenience methods to get all the current nodes of the source
	 * 
	 * @return
	 */
	public Set<INode> getSourceNodes();

	public boolean isSourcesSubset(CmlTransition other);
	
	public int getTransitionId();
}
