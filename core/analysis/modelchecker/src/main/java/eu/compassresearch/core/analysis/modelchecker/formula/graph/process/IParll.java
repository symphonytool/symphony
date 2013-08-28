package eu.compassresearch.core.analysis.modelchecker.formula.graph.process;

import eu.compassresearch.core.analysis.modelchecker.formula.graph.binding.Binding;

public class IParll implements ParallelProcess {
	
	private Binding  binding1;
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


	private Process process1;
	private String str;
	private Binding binding2;
	private Process process2;
	
	
	public IParll(Binding binding1, Process process1, String str, Process process2, Binding binding2) {
		
		this.binding1 = binding1;
		this.process1 = process1;
		this.str = str;
		this.binding2 = binding2;
		this.process2 = process2;
		
		
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((binding1 == null) ? 0 : binding1.hashCode());
		result = prime * result
				+ ((binding2 == null) ? 0 : binding2.hashCode());
		result = prime * result
				+ ((process1 == null) ? 0 : process1.hashCode());
		result = prime * result
				+ ((process2 == null) ? 0 : process2.hashCode());
		result = prime * result + ((str == null) ? 0 : str.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IParll other = (IParll) obj;
		if (binding1 == null) {
			if (other.binding1 != null)
				return false;
		} else if (!binding1.equals(other.binding1))
			return false;
		if (binding2 == null) {
			if (other.binding2 != null)
				return false;
		} else if (!binding2.equals(other.binding2))
			return false;
		if (process1 == null) {
			if (other.process1 != null)
				return false;
		} else if (!process1.equals(other.process1))
			return false;
		if (process2 == null) {
			if (other.process2 != null)
				return false;
		} else if (!process2.equals(other.process2))
			return false;
		if (str == null) {
			if (other.str != null)
				return false;
		} else if (!str.equals(other.str))
			return false;
		return true;
	}
	
	

}
