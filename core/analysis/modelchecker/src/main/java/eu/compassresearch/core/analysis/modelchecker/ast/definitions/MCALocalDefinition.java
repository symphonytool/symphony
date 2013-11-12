package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import org.overture.ast.definitions.ALocalDefinition;

import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCPSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public class MCALocalDefinition implements MCPSingleDeclaration {

	private String name;
	private MCPCMLType type;
	
	
	public MCALocalDefinition(String name, MCPCMLType type) {
		this.name = name;
		this.type = type;
	}


	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
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
