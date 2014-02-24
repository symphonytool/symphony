package eu.compassresearch.core.interpreter.api.transitions;

import org.overture.ast.analysis.AnalysisException;

public interface ObservableTransition extends CmlTransition
{

	/**
	 * This creates a synchronized event between this and other.
	 * 
	 * @param other
	 * @return The synchronized event
	 */
	public ObservableTransition synchronizeWith(ObservableTransition other)
			throws AnalysisException;

	
	public boolean isSynchronizableWith(ObservableTransition other);
	/**
	 * Two Observable events are comparable if they are a tock events or if the are occurring on the same channel and
	 * the sources of one must either be a subset of the other. Values in the channel name do not have to be identical
	 * 
	 * @param other
	 * @return
	 */
	public boolean isComparable(ObservableTransition other);

}
