package eu.compassresearch.core.interpreter.api.behaviour;

public class Inspection {
	private final CmlTrace 				trace;
	private final CmlAlphabet 			transitions;
	private final CmlCalculationStep 	nextStep;
	
	public Inspection(CmlTrace trace, CmlAlphabet transitions)
	{
		this.trace = trace;
		this.transitions = transitions;
		this.nextStep = null;
	}
	
	public Inspection(CmlTrace trace, CmlAlphabet transitions, CmlCalculationStep nextStep)
	{
		this.trace = trace;
		this.transitions = transitions;
		this.nextStep = nextStep;
	}

	public CmlAlphabet getTransitions() {
		return transitions;
	}

	public CmlTrace getTrace() {
		return trace;
	}

	public CmlCalculationStep getNextStep() {
		return nextStep;
	}
}
