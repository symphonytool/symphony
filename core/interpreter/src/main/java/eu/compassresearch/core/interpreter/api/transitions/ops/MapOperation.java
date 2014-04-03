package eu.compassresearch.core.interpreter.api.transitions.ops;

import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;

public interface MapOperation
{
	public CmlTransition apply(CmlTransition transition);
}
