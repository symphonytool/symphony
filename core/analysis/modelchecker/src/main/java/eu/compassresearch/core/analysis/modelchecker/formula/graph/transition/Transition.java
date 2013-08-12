package eu.compassresearch.core.analysis.modelchecker.formula.graph.transition;

import eu.compassresearch.core.analysis.modelchecker.formula.graph.State;
import eu.compassresearch.core.analysis.modelchecker.formula.graph.event.Event;


public class Transition {
	
	private State sourceState;
	private Event event;
	private State targetState;
	
	
	public Transition(State source,Event event,State target) {
		this.setSourceState(source);
		this.setEvent(event);
		this.setTargetState(target);
		
	}


	/**
	 * @return the sourceState
	 */
	public State getSourceState() {
		return sourceState;
	}


	/**
	 * @param sourceState the sourceState to set
	 */
	public void setSourceState(State sourceState) {
		this.sourceState = sourceState;
	}


	/**
	 * @return the event
	 */
	public Event getEvent() {
		return event;
	}


	/**
	 * @param event the event to set
	 */
	public void setEvent(Event event) {
		this.event = event;
	}


	/**
	 * @return the targetState
	 */
	public State getTargetState() {
		return targetState;
	}


	/**
	 * @param targetState the targetState to set
	 */
	public void setTargetState(State targetState) {
		this.targetState = targetState;
	}
	
	

}
