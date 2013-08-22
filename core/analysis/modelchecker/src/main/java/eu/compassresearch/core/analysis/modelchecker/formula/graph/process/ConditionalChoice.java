package eu.compassresearch.core.analysis.modelchecker.formula.graph.process;


public class ConditionalChoice extends Choice {
	private int number;
	
	
	public ConditionalChoice(int number,Process firstProcess, Process secondProcess) {
		super(firstProcess, secondProcess);
		this.number = number;
	}
	
	@Override
	public String toString() {
		return "if " + number + " then " + firstProcess.toString() + " else " + secondProcess.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
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
		ConditionalChoice other = (ConditionalChoice) obj;
		if (number != other.number)
			return false;
		return true;
	}

}
