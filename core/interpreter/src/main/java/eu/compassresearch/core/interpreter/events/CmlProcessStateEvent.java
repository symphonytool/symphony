package eu.compassresearch.core.interpreter.events;

import eu.compassresearch.core.interpreter.cml.CmlBehaviour;
import eu.compassresearch.core.interpreter.cml.CmlProcessState;

public class CmlProcessStateEvent extends Event<CmlBehaviour> {

	private final CmlProcessState state;
	
	public CmlProcessStateEvent(CmlBehaviour source, CmlProcessState newState)
	{
		super(source);
		this.state = newState;
	}
	
	public CmlProcessState getState() {
		return state;
	}
}
