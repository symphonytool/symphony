package eu.compassresearch.core.analysis.modelchecker.ast.types;


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
	public String getTypeAsName() {
		// TODO Auto-generated method stub
		return null;
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
