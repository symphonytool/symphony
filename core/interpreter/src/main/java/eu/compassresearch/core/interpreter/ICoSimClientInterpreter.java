package eu.compassresearch.core.interpreter;

import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.core.interpreter.api.CmlInterpreterState;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;

public interface ICoSimClientInterpreter
{

	/**
	 * Cosim interface for inspection
	 * 
	 * @return
	 * @throws AnalysisException
	 */
	public abstract CmlTransitionSet inspect() throws AnalysisException;

	public abstract void stop();

	public CmlInterpreterState getState();

}