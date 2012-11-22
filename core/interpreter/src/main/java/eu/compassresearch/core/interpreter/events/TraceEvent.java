package eu.compassresearch.core.interpreter.events;

import java.util.List;

import eu.compassresearch.core.interpreter.cml.CmlProcess;
import eu.compassresearch.core.interpreter.cml.events.CmlEvent;

public class TraceEvent extends Event<CmlProcess> {
	
	public final CmlEvent event;
	
	public TraceEvent(CmlProcess source, CmlEvent event)
	{
		super(source);
		this.event = event;
	}
	
	public TraceEvent(List<CmlProcess> sources, CmlEvent event)
	{
		super(sources);
		this.event = event;
	}
	
	public CmlEvent getEvent()
	{
		return event;
	}
	
	public static TraceEvent createRedirectedEvent(CmlProcess nextSource,TraceEvent traceEvent)
	{
		List<CmlProcess> sources = traceEvent.getSources();
		sources.add(0,nextSource);
		return new TraceEvent(sources,traceEvent.getEvent());
	}
}
