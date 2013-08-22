package eu.compassresearch.core.analysis.modelchecker.formula.graph.process;

public class SeqComposition implements Process {
	private Process firstProcess;
	private Process secondProcess;
	
	
	public SeqComposition(Process firstProcess ,Process secondProcess) {
		this.firstProcess = firstProcess;
		this.secondProcess = secondProcess;
	}
	
	@Override
	public String toString() {
		return firstProcess.toString() + ";" + secondProcess.toString();
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
		SeqComposition other = (SeqComposition) obj;
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
