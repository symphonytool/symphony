package eu.compassresearch.core.analysis.modelchecker.formula.graph.binding;

public class BBinding implements Binding {
	
	private String begin;
	private String middle;
	private String end;

	
	public BBinding(String begin,String middle,String end) {
		
		this.begin = begin;
		this.middle = middle;
		this.end = end;
		
	}

}
