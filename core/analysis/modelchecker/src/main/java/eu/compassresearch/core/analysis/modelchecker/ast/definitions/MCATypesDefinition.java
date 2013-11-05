package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.LinkedList;

public class MCATypesDefinition implements MCPCMLDefinition {

	private LinkedList<MCATypeDefinition> types = new LinkedList<MCATypeDefinition>();
	
	
	
	public MCATypesDefinition(LinkedList<MCATypeDefinition> types) {
		super();
		this.types = types;
	}



	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}



	public LinkedList<MCATypeDefinition> getTypes() {
		return types;
	}



	public void setTypes(LinkedList<MCATypeDefinition> types) {
		this.types = types;
	}

	
}
