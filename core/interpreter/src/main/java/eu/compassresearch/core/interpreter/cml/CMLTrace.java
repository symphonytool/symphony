package eu.compassresearch.core.interpreter.cml;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import eu.compassresearch.ast.actions.ACommunicationAction;

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
		
		
		StringBuilder strbuilder = new StringBuilder();
		strbuilder.append("<");
		for(Iterator<CMLEvent> iterator = trace.iterator(); iterator.hasNext();)
		{
			strbuilder.append(iterator.next());
			if(iterator.hasNext())
				strbuilder.append(",");
		}
		strbuilder.append(">");
		
		return strbuilder.toString();
	}
}
