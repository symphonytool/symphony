package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class EmptySet implements Set {

	@Override
	public String toFormula(String option) {
		return "emptySet";
	}

	@Override
	public Set addElement(MCPCMLExp elem) {
		return new NonEmptySet(elem, this);
	}

}
