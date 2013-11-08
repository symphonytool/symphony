package eu.compassresearch.core.analysis.modelchecker.graphBuilder.type;

public class Void implements Type {

	@Override
	public boolean equals(Object arg0) {
		return arg0 instanceof Void;
	}

	@Override
	public Void copy(){
		return new Void();
	}
	
	@Override
	public String toString() {
		return "void";
	}
	
	public String toFormula() {
		return this.toString();
	}

	@Override
	public String toFormulaWithState() {
		return this.toString();
	}
	
	public String toFormulaWithUnderscore(){
		return toString();
	}

	@Override
	public String toFormulaGeneric() {
		return this.toFormula();
	}
}
