package eu.compassresearch.core.analysis.modelchecker.graphBuilder.process;



public class Stop extends BasicProcess {

	@Override
	public boolean equals(Object arg0) {
		return (arg0 instanceof Stop);
	}

	
	@Override
	public String toString() {
		return "Stop";
	}
	@Override
	public boolean isDeadlock(){
		return true;
	}

}
