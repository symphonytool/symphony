package eu.compassresearch.core.interpreter.cml.events;

import eu.compassresearch.core.interpreter.cml.CmlAlphabet;


public abstract class CmlEvent {
	
	public abstract int hashCode();
	
	public abstract String toString();
	
	public abstract boolean equals(Object obj);
	
	/**
	 * Wraps the event in a CmlAlphabet
	 * @return
	 */
	public abstract CmlAlphabet getAsAlphabet();
}
