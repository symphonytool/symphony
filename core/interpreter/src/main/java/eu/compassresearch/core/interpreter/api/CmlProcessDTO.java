package eu.compassresearch.core.interpreter.api;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.intf.lex.ILexNameToken;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviorState;
import eu.compassresearch.core.interpreter.api.behaviour.CmlTrace;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;

public class CmlProcessDTO {

	private final ILexNameToken name;
	private final List<String> trace;
	private final long level;
	private final boolean isProcess;
	private final CmlBehaviorState state;
	private final ILexLocation location;

	protected CmlProcessDTO()
	{
		name = null;
		trace = new LinkedList<String>();
		level = 0;
		isProcess = false;
		state = null;
		location = null;		
	}
	
	public CmlProcessDTO(ILexNameToken name, CmlTrace trace,long level, 
			boolean isProcess, CmlBehaviorState state,
			ILexLocation currentLocation)
	{
		this.name = name;
		this.trace = convertCmlEventsToStringList(trace.getObservableTrace());
		this.level = level;
		this.isProcess = isProcess;
		this.state = state;
		this.location = currentLocation;
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
	
	public List<String> getTrace()
	{	
		return this.trace;
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
	
	public ILexLocation getLocation()
	{
		return this.location;
	}
	
	@Override
	public String toString() {
		return "Name: " + name + System.lineSeparator() +
				"trace :" + trace;
	}
}
