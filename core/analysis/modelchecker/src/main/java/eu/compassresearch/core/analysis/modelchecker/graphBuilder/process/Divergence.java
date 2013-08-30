package eu.compassresearch.core.analysis.modelchecker.graphBuilder.process;

public class Divergence extends BasicProcess {
	@Override
	public String toString() {
		return "Div";
	}
	
	@Override
	public boolean equals(Object arg0) {
		return (arg0 instanceof Divergence);
	}
	
	@Override
	public boolean isDeadlock(){
		return false;
	}


}
