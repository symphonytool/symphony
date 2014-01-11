package eu.compassresearch.core.analysis.modelchecker.graphBuilder.event;

public class Tock implements Event {
	@Override
	public String toString() {
		return "tock";
	}
	
	@Override
	public int hashCode() {
		return "tock".hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Tock;
	}


}
