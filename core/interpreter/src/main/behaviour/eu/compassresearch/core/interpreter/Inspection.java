package eu.compassresearch.core.interpreter;

import eu.compassresearch.core.interpreter.api.CmlTrace;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;

class Inspection
{
	private final CmlTrace trace;
	private final CmlTransitionSet transitions;
	private final CmlCalculationStep transitionFunction;

	public Inspection(CmlTrace trace, CmlTransitionSet transitions)
	{
		this.trace = trace;
		this.transitions = transitions;
		this.transitionFunction = null;
	}

	public Inspection(CmlTrace trace, CmlTransitionSet transitions,
			CmlCalculationStep nextStep)
	{
		this.trace = trace;
		this.transitions = transitions;
		this.transitionFunction = nextStep;
	}

	public CmlTransitionSet getTransitions()
	{
		return transitions;
	}

	public CmlTrace getTrace()
	{
		return trace;
	}

	public CmlCalculationStep getTransitionFunction()
	{
		return transitionFunction;
	}
}
