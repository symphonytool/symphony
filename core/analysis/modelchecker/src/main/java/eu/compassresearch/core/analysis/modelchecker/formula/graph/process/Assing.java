package eu.compassresearch.core.analysis.modelchecker.formula.graph.process;

public class Assing implements Process {
	
	private String number;
	
	public Assing(String number) {

		this.number = number;
	}
	
	@Override
	public String toString() {
		return number;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
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
		Assing other = (Assing) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}
}
