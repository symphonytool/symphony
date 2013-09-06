package eu.compassresearch.core.interpreter.api.transitions;

import org.overture.ast.analysis.AnalysisException;


public interface ObservableEvent extends CmlTransition{

	/**
	 * This creates a synchronized event between this and other.
	 * @param other
	 * @return The synchronized event 
	 */
	public ObservableEvent synchronizeWith(ObservableEvent other) throws AnalysisException;
	
	/**
	 * Two Observable events are comparable if the are occurring on the same channel and
	 * the sources of one must either be a subset of the other.
	 * 
	 * Values do not have to be identical
	 * @param other
	 * @return
	 */
	public boolean isComparable(ObservableEvent other);
	
}
