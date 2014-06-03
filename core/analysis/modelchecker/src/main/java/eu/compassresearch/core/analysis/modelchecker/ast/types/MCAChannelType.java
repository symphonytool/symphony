package eu.compassresearch.core.analysis.modelchecker.ast.types;


public class MCAChannelType implements MCPCMLType {

	private MCPCMLType type;
	
	public MCAChannelType(MCPCMLType type) {
		this.type = type;
	}


	@Override
	public String toFormula(String option) {
		return this.type.toFormula(option);
	}


	public MCPCMLType getType() {
		return type;
	}


	public void setType(MCPCMLType type) {
		this.type = type;
	}


	@Override
	public MCPCMLType copy() {
		return new MCAChannelType(this.type.copy());
	}

	@Override
	public String getTypeAsName() {
		
		return this.type.getTypeAsName();
	}
	
}
