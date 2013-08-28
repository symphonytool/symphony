package eu.compassresearch.core.analysis.modelchecker.formula.graph.process;


public class Parll implements ParallelProcess {
	
	
	public Process getStartProcess() {
		return startProcess;
	}


	public void setStartProcess(Process startProcess) {
		this.startProcess = startProcess;
	}


	public String getStr1() {
		return str1;
	}


	public void setStr1(String str1) {
		this.str1 = str1;
	}


	public String getStr2() {
		return str2;
	}


	public void setStr2(String str2) {
		this.str2 = str2;
	}


	public String getStr3() {
		return str3;
	}


	public void setStr3(String str3) {
		this.str3 = str3;
	}


	public Process getEndProcess() {
		return endProcess;
	}


	public void setEndProcess(Process endProcess) {
		this.endProcess = endProcess;
	}


	private Process startProcess;
	private String str1;
	private String str2;
	private String str3;
	private Process endProcess;
	
	
	public Parll(Process startProcess, String str3, String str2, String str1, Process endProcess) {
		this.startProcess = startProcess;
		this.str1 = str1;
		this.str2 = str2;
		this.str3 = str3;
		this.endProcess = endProcess;
		
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((endProcess == null) ? 0 : endProcess.hashCode());
		result = prime * result
				+ ((startProcess == null) ? 0 : startProcess.hashCode());
		result = prime * result + ((str1 == null) ? 0 : str1.hashCode());
		result = prime * result + ((str2 == null) ? 0 : str2.hashCode());
		result = prime * result + ((str3 == null) ? 0 : str3.hashCode());
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
		Parll other = (Parll) obj;
		if (endProcess == null) {
			if (other.endProcess != null)
				return false;
		} else if (!endProcess.equals(other.endProcess))
			return false;
		if (startProcess == null) {
			if (other.startProcess != null)
				return false;
		} else if (!startProcess.equals(other.startProcess))
			return false;
		if (str1 == null) {
			if (other.str1 != null)
				return false;
		} else if (!str1.equals(other.str1))
			return false;
		if (str2 == null) {
			if (other.str2 != null)
				return false;
		} else if (!str2.equals(other.str2))
			return false;
		if (str3 == null) {
			if (other.str3 != null)
				return false;
		} else if (!str3.equals(other.str3))
			return false;
		return true;
	}
	
	
	

}
