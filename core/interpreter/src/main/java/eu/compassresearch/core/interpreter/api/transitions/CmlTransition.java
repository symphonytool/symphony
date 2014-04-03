package eu.compassresearch.core.interpreter.api.transitions;

import java.io.Serializable;
import java.util.Set;
import java.util.SortedSet;

import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.api.CmlBehaviour;

public interface CmlTransition extends Serializable, Comparable<CmlTransition>
{

	/**
	 * Returns the set of CmlBehaviourThreads that participates in this transition
	 * 
	 * @return
	 */
	public SortedSet<CmlBehaviour> getEventSources();

	public SortedSet<Integer> getHashedEventSources();

	/**
	 * Convenience methods to get all the current nodes of the source
	 * 
	 * @return
	 */
	public Set<INode> getSourceNodes();

	public boolean isSourcesSubset(CmlTransition other);

	/**
	 * Get the transition if to be used in this simulator
	 * 
	 * @return
	 */
	public int getTransitionId();

	/**
	 * Gets the originally generated transition id. This may differ from {@link CmlTransition#getTransitionId()} if
	 * generated remotely.
	 * 
	 * @return
	 */
	public int getRawTransitionId();
}
