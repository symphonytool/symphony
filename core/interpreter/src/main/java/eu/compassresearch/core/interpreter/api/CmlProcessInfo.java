package eu.compassresearch.core.interpreter.api;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import eu.compassresearch.core.interpreter.cml.CmlTrace;
import eu.compassresearch.core.interpreter.cml.events.CmlEvent;

public class CmlProcessInfo {

	private final String name;
	private final String[] trace;
	private final long level;
	private final boolean isProcess;

	public CmlProcessInfo(String name, CmlTrace trace,long level, boolean isProcess)
	{
		this.name = name;
		List<String> evs = convertCmlEventsToStringList(trace.getVisibleEvents());
		this.trace = evs.toArray(new String[evs.size()]);
		this.level = level;
		this.isProcess = isProcess;
	}
	
	public String getName() {
		return (isProcess ? "Process:" : "Action:") + " "+ name;
	}
	
	public long level()
	{
		return level;
	}
	
	public List<String> getVisibleTrace()
	{
		return Arrays.asList(this.trace); 
	}
	
	private List<String> convertCmlEventsToStringList(List<CmlEvent> events)
	{
		List<String> result = new LinkedList<String>();

		for(CmlEvent e : events)
		{
			result.add(e.toString());
		}

		return result;
	}
	
}
