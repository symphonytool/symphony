package eu.compassresearch.core.analysis.modelchecker.ast.types;

import org.overture.ast.types.ASetType;

public class MCASetType implements MCPCMLType {

	private MCPCMLType setOf;
	
	
	public MCASetType(MCPCMLType setOf) {
		this.setOf = setOf;
	}

	@Override
	public String toFormula(String option) {
		return "emptySet";
	}

	
	
	@Override
	public MCPCMLType copy() {
		
		return new MCASetType(this.setOf.copy());
	}

	public MCPCMLType getSetOf() {
		return setOf;
	}

	public void setSetOf(MCPCMLType setOf) {
		this.setOf = setOf;
	}

	
}
