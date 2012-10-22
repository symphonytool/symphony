package eu.compassresearch.core.interpreter.api;

import java.util.List;

public class InterpreterStatus {

	final private List<String> visibleTrace;
	
	public InterpreterStatus(List<String> visibleTrace)
	{
		this.visibleTrace = visibleTrace;
	}
	
	public List<String> getVisibleTrace()
	{
		return this.visibleTrace;
	}
	
}
