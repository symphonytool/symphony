package eu.compassresearch.core.analysis.modelchecker.ast.types;

import eu.compassresearch.ast.types.AChannelType;

public class MCAChannelType implements MCPCMLType {

	private MCPCMLType type;
	
	
	public MCAChannelType(MCPCMLType type) {
		this.type = type;
	}


	@Override
	public String toFormula(String option) {
		
		// TODO Auto-generated method stub
		return null;
	}


	public MCPCMLType getType() {
		return type;
	}


	public void setType(MCPCMLType type) {
		this.type = type;
	}

	
}
