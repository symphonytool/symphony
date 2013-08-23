package eu.compassresearch.core.analysis.modelchecker.graphBuilder.process;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.Binding;

public class Par implements ParallelProcess {
	
	public Binding getBinding1() {
		return binding1;
	}


	public void setBinding1(Binding binding1) {
		this.binding1 = binding1;
	}


	public Process getProcess1() {
		return process1;
	}


	public void setProcess1(Process process1) {
		this.process1 = process1;
	}


	public String getStr() {
		return str;
	}


	public void setStr(String str) {
		this.str = str;
	}


	public Binding getBinding2() {
		return binding2;
	}


	public void setBinding2(Binding binding2) {
		this.binding2 = binding2;
	}


	public Process getProcess2() {
		return process2;
	}


	public void setProcess2(Process process2) {
		this.process2 = process2;
	}


	private Binding binding1;
	private Process process1;
	private String str;
	private Binding binding2;
	private Process process2;
	
	
	public Par(Binding binding1, Process process1, String str, Process process2,Binding binding2) {
		this.binding1 = binding1;
		this.process1 = process1;
		this.str = str;
		this.binding2 = binding2;
		this.process2 = process2;
		
		
		
		
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof Par) {
			Par other = (Par) obj;
			result = //this.getBinding1().equals(other.getBinding1())
					//&& this.getBinding2().equals(other.getBinding2())
					this.getProcess1().equals(other.getProcess1())
					&& this.getProcess2().equals(other.getProcess2())
					&& this.getStr().equals(other.getStr());
		}
		return result;
	}
	
	
			

}
