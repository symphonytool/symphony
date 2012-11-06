package eu.compassresearch.core.interpreter.cml;

import java.util.LinkedList;
import java.util.List;

public class CMLTrace {

	private List<CMLEvent> trace;
	
	public CMLTrace()
	{
		trace = new LinkedList<CMLEvent>();
	}
	
	public void addEvent(CMLEvent event)
	{
		trace.add(event);
	}
	
	@Override
	public String toString() {
		return trace.toString();
	}
}
