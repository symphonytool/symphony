package eu.compassresearch.core.interpreter.api.behaviour;

import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;

public class Inspection {
	private final CmlTrace 				trace;
	private final CmlTransitionSet 			transitions;
	private final CmlCalculationStep 	nextStep;
	
	public Inspection(CmlTrace trace, CmlTransitionSet transitions)
	{
		this.trace = trace;
		this.transitions = transitions;
		this.nextStep = null;
	}
	
	public Inspection(CmlTrace trace, CmlTransitionSet transitions, CmlCalculationStep nextStep)
	{
		this.trace = trace;
		this.transitions = transitions;
		this.nextStep = nextStep;
	}

	public CmlTransitionSet getTransitions() {
		return transitions;
	}

	public CmlTrace getTrace() {
		return trace;
	}

	public CmlCalculationStep getNextStep() {
		return nextStep;
	}
}
