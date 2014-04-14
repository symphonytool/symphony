package eu.compassresearch.core.interpreter.api.transitions.ops;

import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;

public interface Filter
{
	public boolean isAccepted(CmlTransition transition);
}
