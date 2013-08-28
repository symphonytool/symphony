package eu.compassresearch.core.analysis.modelchecker.formula.graph.type;

public class IR implements Type{
	
	private Object val;
	
	
	
	public IR(Object val) {
		this.val = val;
		
	}
	
	@Override
	public String toString() {
		return val.toString();
	}

}
