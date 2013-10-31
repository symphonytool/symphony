package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public class MCVoidType implements MCPCMLType {

	private String name;
	
	public MCVoidType() {
		this.name = "void";
	}

	@Override
	public String toFormula(String option) {
		return this.name;
	}

	
}
