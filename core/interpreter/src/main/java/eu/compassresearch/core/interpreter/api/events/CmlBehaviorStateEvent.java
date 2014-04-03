package eu.compassresearch.core.interpreter.api.events;

import eu.compassresearch.core.interpreter.api.CmlBehaviorState;
import eu.compassresearch.core.interpreter.api.CmlBehaviour;

public class CmlBehaviorStateEvent extends Event<CmlBehaviour>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final CmlBehaviorState state;

	public CmlBehaviorStateEvent(CmlBehaviour source, CmlBehaviorState newState)
	{
		super(source);
		this.state = newState;
	}

	public CmlBehaviorState getState()
	{
		return state;
	}
}
