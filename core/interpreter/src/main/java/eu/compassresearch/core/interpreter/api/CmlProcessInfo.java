package eu.compassresearch.core.interpreter.api;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviorState;
import eu.compassresearch.core.interpreter.api.behaviour.CmlTrace;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;

public class CmlProcessInfo {

	private final String name;
	private final String[] trace;
	private final long level;
	private final boolean isProcess;
	private final CmlBehaviorState state;

	public CmlProcessInfo(String name, CmlTrace trace,long level, 
			boolean isProcess, CmlBehaviorState state)
	{
		this.name = name;
		List<String> evs = convertCmlEventsToStringList(trace.getObservableTrace());
		this.trace = evs.toArray(new String[evs.size()]);
		this.level = level;
		this.isProcess = isProcess;
		this.state = state;
	}
	
	public String getName() {
		return (isProcess ? "Process:" : "Action:") + " "+ name;
	}
	
	public long level()
	{
		return level;
	}
	
	public CmlBehaviorState getState() {
		return state;
	}
	
	public List<String> getVisibleTrace()
	{
		return Arrays.asList(this.trace); 
	}
	
	private List<String> convertCmlEventsToStringList(List<CmlTransition> events)
	{
		List<String> result = new LinkedList<String>();

		for(CmlTransition e : events)
		{
			result.add(e.toString());
		}

		return result;
	}
	
}
