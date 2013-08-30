package eu.compassresearch.core.analysis.modelchecker.graphBuilder.process;


public class Skip extends BasicProcess {
	
	
	@Override
	public boolean equals(Object arg0) {
		return (arg0 instanceof Skip);
	}

	@Override
	public String toString() {
		return "Skip";
	}
	@Override
	public boolean isDeadlock(){
		return false;
	}

}
