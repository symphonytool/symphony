package eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding;


public class NullBinding implements Binding {
	
	@Override
	public String toString() {
		return "{}";
	}

	
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof NullBinding;
	}
}
