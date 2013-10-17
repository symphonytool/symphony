package eu.compassresearch.core.analysis.modelchecker.ast.declarations;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public class MCATypeSingleDeclaration implements MCPSingleDeclaration {

	private LinkedList<String> identifiers = new LinkedList<String>();
	private MCPCMLType type;
	
	
	public MCATypeSingleDeclaration(LinkedList<String> identifiers,
			MCPCMLType type) {
		super();
		this.identifiers = identifiers;
		this.type = type;
	}


	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}


	public LinkedList<String> getIdentifiers() {
		return identifiers;
	}


	public void setIdentifiers(LinkedList<String> identifiers) {
		this.identifiers = identifiers;
	}


	public MCPCMLType getType() {
		return type;
	}


	public void setType(MCPCMLType type) {
		this.type = type;
	}

	
}
