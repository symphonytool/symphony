package eu.compassresearch.core.interpreter.events;

import java.util.List;

import eu.compassresearch.core.interpreter.cml.CmlBehaviour;
import eu.compassresearch.core.interpreter.cml.events.CmlEvent;

public class TraceEvent extends Event<CmlBehaviour> {
	
	public final CmlEvent event;
	
	public TraceEvent(CmlBehaviour source, CmlEvent event)
	{
		super(source);
		this.event = event;
	}
	
	public TraceEvent(List<CmlBehaviour> sources, CmlEvent event)
	{
		super(sources);
		this.event = event;
	}
	
	public CmlEvent getEvent()
	{
		return event;
	}
	
	public static TraceEvent createRedirectedEvent(CmlBehaviour nextSource,TraceEvent traceEvent)
	{
		List<CmlBehaviour> sources = traceEvent.getSources();
		sources.add(0,nextSource);
		return new TraceEvent(sources,traceEvent.getEvent());
	}
}
