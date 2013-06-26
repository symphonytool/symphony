package eu.compassresearch.core.interpreter.api.behaviour;

public class Inspection {
	private final CmlTrace trace;
	private final CmlAlphabet transitions;
	
	public Inspection(CmlTrace trace, CmlAlphabet transitions)
	{
		this.trace = trace;
		this.transitions = transitions;
	}

	public CmlAlphabet getTransitions() {
		return transitions;
	}

	public CmlTrace getTrace() {
		return trace;
	}
}
