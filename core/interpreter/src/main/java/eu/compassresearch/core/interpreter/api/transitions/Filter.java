package eu.compassresearch.core.interpreter.api.transitions;

public interface Filter
{
	public boolean isAccepted(CmlTransition transition);
}
