package eu.compassresearch.core.interpreter.cml;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import eu.compassresearch.ast.actions.ACommunicationAction;

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
}
