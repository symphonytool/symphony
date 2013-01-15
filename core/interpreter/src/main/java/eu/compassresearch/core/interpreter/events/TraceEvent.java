package eu.compassresearch.core.interpreter.events;

import java.util.List;

import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.events.CmlEvent;

public class TraceEvent extends Event<CmlBehaviourThread> {
	
	public final CmlEvent event;
	
	public TraceEvent(CmlBehaviourThread source, CmlEvent event)
	{
		super(source);
		this.event = event;
	}
	
	public TraceEvent(List<CmlBehaviourThread> sources, CmlEvent event)
	{
		super(sources);
		this.event = event;
	}
	
	public CmlEvent getEvent()
	{
		return event;
	}
	
	public static TraceEvent createRedirectedEvent(CmlBehaviourThread nextSource,TraceEvent traceEvent)
	{
		List<CmlBehaviourThread> sources = traceEvent.getSources();
		sources.add(0,nextSource);
		return new TraceEvent(sources,traceEvent.getEvent());
	}
}
