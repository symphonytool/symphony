package eu.compassresearch.core.analysis.modelchecker.formula.graph.param;


public class NoPar implements Param {

	@Override
	public String toString() {
		return "()";
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

}
