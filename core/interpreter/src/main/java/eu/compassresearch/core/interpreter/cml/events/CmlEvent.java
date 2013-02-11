package eu.compassresearch.core.interpreter.cml.events;

import eu.compassresearch.core.interpreter.cml.CmlAlphabet;


public abstract class CmlEvent {
	
	@Override 
	public abstract int hashCode();
	
	@Override
	public abstract String toString();
	
	@Override
	public abstract boolean equals(Object obj);
	
	public abstract CmlAlphabet getAsAlphabet();
}
