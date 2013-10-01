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
	public Nat copy(){
		Nat result = new Nat(this.value);
		return result;
	}
	@Override
	public String toString() {
		return String.valueOf(value);
	}
	
	public String toFormula() {
		return "Nat("+String.valueOf(value)+")";
	}

	@Override
	public String toFormulaWithState() {
		return "Nat("+String.valueOf(value)+")";
	}
	public String toFormulaWithUnderscore(){
		return "Str(_)";
	}
}
