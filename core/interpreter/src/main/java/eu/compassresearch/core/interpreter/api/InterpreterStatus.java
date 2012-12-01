package eu.compassresearch.core.interpreter.api;

import java.util.List;

import eu.compassresearch.core.interpreter.cml.CmlTrace;
import eu.compassresearch.core.interpreter.cml.events.CmlEvent;

public class InterpreterStatus {

	final private CmlTrace trace;
	
	public InterpreterStatus(CmlTrace trace)
	{
		this.trace = trace;
	}
	
	public List<CmlEvent> getVisibleTrace()
	{
		return this.trace.getVisibleEvents();
	}
	
}
