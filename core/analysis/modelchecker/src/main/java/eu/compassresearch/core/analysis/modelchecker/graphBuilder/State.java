package eu.compassresearch.core.analysis.modelchecker.graphBuilder;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.Binding;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Process;

public class State {
	

	private int level;
	private Binding binding;
	private String name;
	private Process process;
	private int number;
	private boolean visited;
	private String shape = "circle"; 
	private String fillCollor = "lightgrey";
	
	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public State(Binding binding, Process process) {
		this.binding = binding;
		//this.name = str;
		this.process = process;
		this.number = -1;
		//this.level = level;
		this.visited = false;
	}
	
	public Binding getBinding() {
		return binding;
	}


	public void setBinding(Binding binding) {
		this.binding = binding;
	}


	public String getName() {
		return name;
	}


	public void setName	(String name) {
		this.name = name;
	}


	public Process getProcess() {
		return process;
	}


	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof State){
			result = this.process.equals(((State) obj).getProcess());
		}
		return result;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	public String getFillCollor() {
		return fillCollor;
	}

	public void setFillCollor(String fillCollor) {
		this.fillCollor = fillCollor;
	}

	@Override
	public String toString() {
		
		return "\"(" + this.binding.toString() + "," + this.process.toString() + ")\"";
	}

}
