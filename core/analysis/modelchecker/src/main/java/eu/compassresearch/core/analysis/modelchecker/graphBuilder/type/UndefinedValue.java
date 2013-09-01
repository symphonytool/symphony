package eu.compassresearch.core.analysis.modelchecker.graphBuilder.type;

public class UndefinedValue implements Type {

	@Override
	public boolean equals(Object arg0) {
		return arg0 instanceof UndefinedValue;
	}

	@Override
	public String toString() {
		return "undef";
	}
	
}
