package eu.compassresearch.core.analysis.modelchecker.formula.graph.process;


public abstract class Choice implements Process {
	protected Process firstProcess;
	protected Process secondProcess;
	public Choice(Process firstProcess, Process secondProcess) {
		super();
		this.firstProcess = firstProcess;
		this.secondProcess = secondProcess;
	}
	public Process getFirstProcess() {
		return firstProcess;
	}
	public void setFirstProcess(Process firstProcess) {
		this.firstProcess = firstProcess;
	}
	public Process getSecondProcess() {
		return secondProcess;
	}
	public void setSecondProcess(Process secondProcess) {
		this.secondProcess = secondProcess;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((firstProcess == null) ? 0 : firstProcess.hashCode());
		result = prime * result
				+ ((secondProcess == null) ? 0 : secondProcess.hashCode());
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
		Choice other = (Choice) obj;
		if (firstProcess == null) {
			if (other.firstProcess != null)
				return false;
		} else if (!firstProcess.equals(other.firstProcess))
			return false;
		if (secondProcess == null) {
			if (other.secondProcess != null)
				return false;
		} else if (!secondProcess.equals(other.secondProcess))
			return false;
		return true;
	}
	
	
}
