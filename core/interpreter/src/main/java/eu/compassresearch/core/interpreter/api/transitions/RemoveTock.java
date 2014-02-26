package eu.compassresearch.core.interpreter.api.transitions;

public class RemoveTock implements Filter
{
	@Override
	public boolean isAccepted(CmlTransition transition)
	{
		return !(transition instanceof TimedTransition);
	}

}
