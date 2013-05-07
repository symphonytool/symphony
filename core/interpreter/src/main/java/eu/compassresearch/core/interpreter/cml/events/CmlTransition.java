package eu.compassresearch.core.interpreter.cml.events;

import java.util.Set;

import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviour;


public interface CmlTransition {
	
	public abstract int hashCode();
	
	public abstract String toString();
	
	public abstract boolean equals(Object obj);
	
	/**
	 * Wraps the event in a CmlAlphabet
	 * @return
	 */
	public CmlAlphabet getAsAlphabet();
	
	/**
	 * Returns the set of CmlBehaviourThreads that are prepared to engage in this event
	 * @return
	 */
	public Set<CmlBehaviour> getEventSources();
}
