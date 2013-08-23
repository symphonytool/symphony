package eu.compassresearch.core.analysis.modelchecker.graphBuilder.event;

public class Tau implements Event {
	@Override
	public String toString() {
		return "tau";
	}
	
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Tau;
	}


}
