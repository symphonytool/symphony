package eu.compassresearch.core.interpreter.api.behaviour;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import eu.compassresearch.core.interpreter.api.transitions.LabelledTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.ObservableEvent;

public class CmlTrace {

	private final List<CmlTransition> trace;
	
	public CmlTrace()
	{
		trace = new LinkedList<CmlTransition>();
	}
	
	public CmlTrace(CmlTrace src)
	{
		trace = new LinkedList<CmlTransition>(src.trace);
	}
	
	public void addEvent(CmlTransition event)
	{
		trace.add(event);
	}
	
	public CmlTransition getLastTransition()
	{
		if(trace.size() > 0)
			return trace.get(trace.size()-1);
		else 
			return null;
	}
	
	@Override
	public String toString() {
		
		
		StringBuilder strbuilder = new StringBuilder();
		strbuilder.append("<");
		for(Iterator<CmlTransition> iterator = trace.iterator(); iterator.hasNext();)
		{
			strbuilder.append(iterator.next());
			if(iterator.hasNext())
				strbuilder.append(",");
		}
		strbuilder.append(">");
		
		return strbuilder.toString();
	}
	
	public List<CmlTransition> getTrace()
	{
		return new LinkedList<CmlTransition>(trace);
	}
	
	public List<CmlTransition> getEventTrace()
	{
		List<CmlTransition> visibleEvents = new LinkedList<CmlTransition>();
			
		for(CmlTransition e : trace)
		{
			if(e instanceof LabelledTransition && e instanceof ObservableEvent)
				visibleEvents.add(e);
		}
		
		return visibleEvents;
	}
	
	public List<CmlTransition> getObservableTrace()
	{
		List<CmlTransition> visibleEvents = new LinkedList<CmlTransition>();
			
		for(CmlTransition e : trace)
		{
			if(e instanceof ObservableEvent)
				visibleEvents.add(e);
		}
		
		return visibleEvents;
	}
	
	public boolean equals(Object obj) { 
	
		if(!(obj instanceof CmlTrace))
			return false;
				
		CmlTrace otherTraceObj = (CmlTrace)obj;
		
		return otherTraceObj.trace.equals(trace);
	};
	
	@Override
	public int hashCode() {
		return trace.hashCode();
	}
}
