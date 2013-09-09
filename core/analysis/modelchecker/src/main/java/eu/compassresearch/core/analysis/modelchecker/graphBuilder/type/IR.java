package eu.compassresearch.core.analysis.modelchecker.graphBuilder.type;

public class IR implements Type{
	
	private double value;
	
	
	
	public IR(double val) {
		this.value = val;
		
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}
	
	public String toFormula() {
		return "IR("+String.valueOf(value)+")";
	}

}
