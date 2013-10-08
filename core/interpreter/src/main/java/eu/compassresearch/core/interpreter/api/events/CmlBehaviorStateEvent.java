package eu.compassresearch.core.interpreter.api.events;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviorState;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;

public class CmlBehaviorStateEvent extends Event<CmlBehaviour>
{

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
