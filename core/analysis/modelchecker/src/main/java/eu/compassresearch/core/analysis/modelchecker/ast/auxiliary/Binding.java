package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public interface Binding {
	public String toFormula(String option);
	public Binding addBinding(String procName, String varName, MCPCMLExp value, MCPCMLType type);
	public void updateBinding(String varName, MCPCMLExp value);
	public Binding deleteBinding(String varName);
	public Binding copy();
	public StringBuilder generateAllFetchFacts(int number);
	public StringBuilder generateAllUpdFacts(int number);
	public StringBuilder generateAllDelFacts(int number);
	public boolean containsVariable(String name);
	public SingleBind getSingleBind(String varName);
}
