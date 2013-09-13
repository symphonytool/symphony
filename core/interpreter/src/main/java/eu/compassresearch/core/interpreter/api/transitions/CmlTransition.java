package eu.compassresearch.core.interpreter.api.transitions;

import java.io.Serializable;
import java.util.Set;

import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;


public interface CmlTransition extends Serializable {

	/**
	 * Returns the set of CmlBehaviourThreads that are prepared to engage in this event
	 * @return
	 */
	public Set<CmlBehaviour> getEventSources();

	/**
	 * Convenience methods to get all the current nodes of the source
	 * @return
	 */
	public Set<INode> getSourceNodes();
	
	public boolean isSourcesSubset(CmlTransition other);
}
