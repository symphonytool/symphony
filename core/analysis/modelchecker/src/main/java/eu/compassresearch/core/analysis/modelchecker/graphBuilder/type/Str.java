package eu.compassresearch.core.analysis.modelchecker.graphBuilder.type;

public class Str implements Type {
	
	private String str;
	
	
	public Str(String str) {
		this.str = str;
	}


	@Override
	public String toString() {
		return str;
	}
	

}
