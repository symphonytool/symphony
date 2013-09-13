package eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Type;

public interface Binding {
	public String toFormula();
	public String toFormulaWithState();
	public String toFormulaWithUnderscore();
	public Binding addBinding(String procName, String varName, Type type);
	public void updateBinding(String varName, Type type);
	public Binding deleteBinding(String varName);
}
