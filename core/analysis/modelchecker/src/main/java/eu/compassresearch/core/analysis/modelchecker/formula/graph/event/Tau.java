package eu.compassresearch.core.analysis.modelchecker.formula.graph.event;

public class Tau implements Event {
	@Override
	public String toString() {
		return "tau";
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
