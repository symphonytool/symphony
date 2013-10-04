package eu.compassresearch.core.interpreter.api.events;

import java.util.List;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;

public class TraceEvent extends Event<CmlBehaviour>
{

	public final CmlTransition event;

	public TraceEvent(CmlBehaviour source, CmlTransition event)
	{
		super(source);
		this.event = event;
	}

	public TraceEvent(List<CmlBehaviour> sources, CmlTransition event)
	{
		super(sources);
		this.event = event;
	}

	public CmlTransition getEvent()
	{
		return event;
	}

	public static TraceEvent createRedirectedEvent(CmlBehaviour nextSource,
			TraceEvent traceEvent)
	{
		List<CmlBehaviour> sources = traceEvent.getSources();
		sources.add(0, nextSource);
		return new TraceEvent(sources, traceEvent.getEvent());
	}
}
