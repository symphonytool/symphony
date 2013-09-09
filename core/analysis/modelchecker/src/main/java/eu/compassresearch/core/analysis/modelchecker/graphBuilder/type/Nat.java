package eu.compassresearch.core.analysis.modelchecker.graphBuilder.type;

public class Nat implements Type {
	
	private int value;
	
	
	public Nat(int value) {
		if(value >= 0){
			this.value = value;
		}else{
			throw new IllegalArgumentException("Cannot create Nat object with negative values (" + value + ")");
		}
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
	
	public String toFormula() {
		return "Nat("+String.valueOf(value)+")";
	}

}
