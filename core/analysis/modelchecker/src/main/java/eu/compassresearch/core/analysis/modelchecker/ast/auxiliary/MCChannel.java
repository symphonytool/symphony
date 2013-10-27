package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public class MCChannel implements MCNode {

	private String name;
	private MCPCMLType type;
	
	
	public MCChannel(String name, MCPCMLType type) {
		super();
		this.name = name;
		this.type = type;
	}


	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		result.append("Channel(0,\"");
		result.append(this.name);
		result.append("\"");
		result.append(",");
		if(this.type != null){
			result.append(this.type.toFormula(option));
		}
		
		return result.toString();
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public MCPCMLType getType() {
		return type;
	}


	public void setType(MCPCMLType type) {
		this.type = type;
	}


	
	
}
