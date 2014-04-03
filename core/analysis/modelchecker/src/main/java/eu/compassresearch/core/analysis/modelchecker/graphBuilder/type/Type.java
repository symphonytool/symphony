package eu.compassresearch.core.analysis.modelchecker.graphBuilder.type;

public interface Type {
	public String toFormula();
	public String toFormulaWithState();
	public String toFormulaWithUnderscore();
	public Type copy();
	public String toFormulaGeneric();
}
