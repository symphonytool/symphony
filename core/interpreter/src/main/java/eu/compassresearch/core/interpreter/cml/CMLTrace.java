package eu.compassresearch.core.interpreter.cml;

import java.util.LinkedList;
import java.util.List;

public class CMLTrace {

	private List<CMLCommunication> trace;
	
	public CMLTrace()
	{
		trace = new LinkedList<CMLCommunication>();
	}
	
	//public void addEvent(CMLCommunication)
	
	@Override
	public String toString() {
		return "";
	}
	
	
}
