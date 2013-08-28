package eu.compassresearch.core.analysis.modelchecker.formula.graph.process;

public class Hide implements Process {
	private Process firstProcess;
	private String str;
	
	
	public Hide(Process firstProcess ,String str) {
		this.firstProcess = firstProcess;
		this.str = str;
	}
	
	@Override
	public String toString() {
		return firstProcess.toString() + "\\" + str;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hide other = (Hide) obj;
		if (firstProcess == null) {
			if (other.firstProcess != null)
				return false;
		} else if (!firstProcess.equals(other.firstProcess))
			return false;
		if (str == null) {
			if (other.str != null)
				return false;
		} else if (!str.equals(other.str))
			return false;
		return true;
	}
}
