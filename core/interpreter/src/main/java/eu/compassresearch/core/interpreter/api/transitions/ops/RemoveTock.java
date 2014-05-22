package eu.compassresearch.core.interpreter.api.transitions.ops;

import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.TimedTransition;

public class RemoveTock implements Filter
{
	@Override
	public boolean isAccepted(CmlTransition transition)
	{
		return !(transition instanceof TimedTransition);
	}

}
