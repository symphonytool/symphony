package eu.compassresearch.core.interpreter;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviorFactory;

public class Config
{
	public final boolean filterTockEvents;
	public final CmlBehaviorFactory cmlBehaviorFactory;

	public Config(boolean filterTockEvents,
			CmlBehaviorFactory cmlBehaviorFactory)
	{
		this.filterTockEvents = filterTockEvents;
		this.cmlBehaviorFactory = cmlBehaviorFactory;
	}
}
