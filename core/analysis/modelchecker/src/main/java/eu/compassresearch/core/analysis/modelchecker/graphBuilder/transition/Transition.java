package eu.compassresearch.core.analysis.modelchecker.graphBuilder.transition;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.State;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.event.Event;

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
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Transition) {
			Transition other = (Transition) obj;
			return this.getSourceState().equals(other.getSourceState())
					&& this.getEvent().equals(other.getEvent())
					&& this.getTargetState().equals(other.getTargetState());			
		}
		return false;
	}
	

}
