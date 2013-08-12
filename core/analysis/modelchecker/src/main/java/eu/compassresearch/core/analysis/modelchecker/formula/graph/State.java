package eu.compassresearch.core.analysis.modelchecker.formula.graph;

import eu.compassresearch.core.analysis.modelchecker.formula.graph.binding.Binding;


public class State {
	private int number;
	private Binding binding;
	private String str;
	private Process process;
	
	
	public State(Process process, int number, Binding binding, String str) {
		this.number = number;
		this.binding = binding;
		this.str = str;
		this.process = process;
		

	}

	
	
	

}
