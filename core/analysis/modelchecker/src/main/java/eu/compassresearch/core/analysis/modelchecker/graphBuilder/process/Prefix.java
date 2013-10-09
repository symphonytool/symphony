package eu.compassresearch.core.analysis.modelchecker.graphBuilder.process;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.event.Event;

public class Prefix implements Process {
	protected Event event;
	protected Process process;
	
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;	
	}

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	
	
	
	public Prefix(Event event,Process process) {
		this.event = event;
		this.process = process; 

	}
	
	@Override
	public String toString() {
		return event.toString() + " -> " + process.toString();
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof Prefix) {
			Prefix other = (Prefix) obj;
			result = this.getEvent().equals(other.getEvent()) 
					 && this.getProcess().equals(other.getProcess());
		}
		return result;
			
	}

	@Override
	public boolean isDeadlock(){
		return false;
	}

}

