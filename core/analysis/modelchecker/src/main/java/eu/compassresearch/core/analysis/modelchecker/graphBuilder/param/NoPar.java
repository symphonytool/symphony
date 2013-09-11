package eu.compassresearch.core.analysis.modelchecker.graphBuilder.param;


public class NoPar implements Param {

	@Override
	public String toString() {
		return "()";
	}
	
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof NoPar;
	}

}
