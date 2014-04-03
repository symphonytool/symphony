package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.LinkedList;

public class MCAValuesDefinition implements MCPCMLDefinition {

	private LinkedList<MCPCMLDefinition> valueDefinitions = new LinkedList<MCPCMLDefinition>();
	
	
	public MCAValuesDefinition(LinkedList<MCPCMLDefinition> valueDefinitions) {
		super();
		this.valueDefinitions = valueDefinitions;
	}


	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}


	public LinkedList<MCPCMLDefinition> getValueDefinitions() {
		return valueDefinitions;
	}


	public void setValueDefinitions(LinkedList<MCPCMLDefinition> valueDefinitions) {
		this.valueDefinitions = valueDefinitions;
	}
	

}
