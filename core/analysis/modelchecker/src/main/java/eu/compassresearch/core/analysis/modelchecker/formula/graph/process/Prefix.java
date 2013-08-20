package eu.compassresearch.core.analysis.modelchecker.formula.graph.process;

import eu.compassresearch.core.analysis.modelchecker.formula.graph.event.Event;


public class Prefix implements Process {
	protected Event event;
	protected Process process;
	
	
	public Prefix(Event event, Process process) {
		this.event = event;
		this.process = process; 

	}

}

