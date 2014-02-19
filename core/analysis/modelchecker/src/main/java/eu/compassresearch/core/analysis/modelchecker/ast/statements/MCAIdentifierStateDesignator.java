package eu.compassresearch.core.analysis.modelchecker.ast.statements;

import org.overture.ast.statements.AIdentifierStateDesignator;

import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public class MCAIdentifierStateDesignator implements MCPStateDesignator {

	private String name;
	private MCPCMLType type;
	
	public MCAIdentifierStateDesignator(String name, MCPCMLType type) {
		super();
		this.name = name;
		this.type = type;
	}


	@Override
	public String toFormula(String option) {
		return name;
	
	}

	@Override
	public String toString() {
		return name;
	
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
