package eu.compassresearch.core.analysis.modelchecker.formula.graph.process;

import eu.compassresearch.core.analysis.modelchecker.formula.graph.binding.Binding;


public class IParll implements ParallelProcess {
	
	private Binding  binding1;
	private Process process1;
	private String str;
	private Binding binding2;
	private Process process2;
	
	
	public IParll(Binding binding2, Process process2, String str, Process process1, Binding binding1) {
		
		this.binding1 = binding1;
		this.process1 = process1;
		this.str = str;
		this.binding2 = binding2;
		this.process2 = process2;
		
		
	}
	
	

}
