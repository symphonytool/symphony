package eu.compassresearch.core.interpreter.api.transitions;

public interface Filter<T extends CmlTransition>
{
	public boolean apply(CmlTransition transition);
}
