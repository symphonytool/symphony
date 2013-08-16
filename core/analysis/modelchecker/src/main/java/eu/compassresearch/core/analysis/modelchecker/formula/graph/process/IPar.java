package eu.compassresearch.core.analysis.modelchecker.formula.graph.process;

public class IPar implements ParallelProcess {
	
	private Process process1;
	private String str;
	private Process process2;
	
	
	public IPar(Process process1, String str, Process process2) {

		this.process1 = process1;
		this.str = str;
		this.process2 = process2;
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



	public Process getProcess2() {
		return process2;
	}



	public void setProcess2(Process process2) {
		this.process2 = process2;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		IPar other = (IPar) obj;
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
