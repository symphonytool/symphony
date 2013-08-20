package eu.compassresearch.core.analysis.modelchecker.formula.graph.process;

import eu.compassresearch.core.analysis.modelchecker.formula.graph.binding.Binding;


public class Par implements ParallelProcess {
	
	private Binding binding1;
	private Process process1;
	private String str;
	private Binding binding2;
	private Process process2;
	
	
	public Par(Binding binding1, Process process1, String str, Binding binding2, Process process2) {
		this.binding1 = binding1;
		this.process1 = process1;
		this.str = str;
		this.binding2 = binding2;
		this.process2 = process2;
		
		
		
		
	}
	
	
			

}
