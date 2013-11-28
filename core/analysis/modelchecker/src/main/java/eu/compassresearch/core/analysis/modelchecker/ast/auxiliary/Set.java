package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public interface Set {
	public String toFormula(String option);
	public Set addElement(MCPCMLExp elem);
}
