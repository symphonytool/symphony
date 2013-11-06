package eu.compassresearch.core.analysis.modelchecker.graphBuilder.type;

public class UndefinedValue implements Type {

	@Override
	public boolean equals(Object arg0) {
		return arg0 instanceof UndefinedValue;
	}

	@Override
	public UndefinedValue copy(){
		return new UndefinedValue();
	}
	
	@Override
	public String toString() {
		return "undef";
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
