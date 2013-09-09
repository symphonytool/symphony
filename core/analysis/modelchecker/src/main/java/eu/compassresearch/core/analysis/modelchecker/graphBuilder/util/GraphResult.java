package eu.compassresearch.core.analysis.modelchecker.graphBuilder.util;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.State;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.transition.Transition;

/**
 * The result of any graph construction. It contains all visited nodes and transitions
 * to be used to build a graph of the counterexample. 
 */
public class GraphResult {
	protected LinkedList<State> states;
	protected LinkedList<Transition> transitions;
	public GraphResult(LinkedList<State> states,
			LinkedList<Transition> transitions) {

		this.states = states;
		this.transitions = transitions;
	}
	public GraphResult() {
		this.states = new LinkedList<State>();
		this.transitions = new LinkedList<Transition>();
	}
	public LinkedList<State> getStates() {
		return states;
	}
	public void setStates(LinkedList<State> states) {
		this.states = states;
	}
	public LinkedList<Transition> getTransitions() {
		return transitions;
	}
	public void setTransitions(LinkedList<Transition> transitions) {
		this.transitions = transitions;
	}
	
	
}
