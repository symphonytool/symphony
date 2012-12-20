package eu.compassresearch.core.interpreter.cml;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.core.interpreter.cml.events.CmlCommunicationEvent;
import eu.compassresearch.core.interpreter.cml.events.CmlEvent;
import eu.compassresearch.core.interpreter.cml.events.ObservableEvent;

public class CmlTrace {

	private List<CmlEvent> trace;
	
	public CmlTrace()
	{
		trace = new LinkedList<CmlEvent>();
	}
	
	public void addEvent(CmlEvent event)
	{
		trace.add(event);
	}
	
	@Override
	public String toString() {
		
		
		StringBuilder strbuilder = new StringBuilder();
		strbuilder.append("<");
		for(Iterator<CmlEvent> iterator = trace.iterator(); iterator.hasNext();)
		{
			strbuilder.append(iterator.next());
			if(iterator.hasNext())
				strbuilder.append(",");
		}
		strbuilder.append(">");
		
		return strbuilder.toString();
	}
	
	public List<CmlEvent> getTrace()
	{
		return new LinkedList<CmlEvent>(trace);
	}
	
	public List<CmlEvent> getVisibleTrace()
	{
		List<CmlEvent> visibleEvents = new LinkedList<CmlEvent>();
			
		for(CmlEvent e : trace)
		{
			if(e instanceof ObservableEvent)
				visibleEvents.add(e);
		}
		
		return visibleEvents;
	}
}
