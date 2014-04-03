package eu.compassresearch.core.analysis.modelchecker.graphBuilder.process;

public class Chaos extends BasicProcess {
	@Override
	public String toString() {
		return "Chaos";
	}
	
	@Override
	public boolean equals(Object arg0) {
		return (arg0 instanceof Chaos);
	}
	
	@Override
	public boolean isDeadlock(){
		return false;
	}

}
