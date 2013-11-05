package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.LinkedList;

public class MCAOperationsDefinition implements MCPCMLDefinition {

	private LinkedList<MCSCmlOperationDefinition> operations = new LinkedList<MCSCmlOperationDefinition>();
	
	
	
	public MCAOperationsDefinition(
			LinkedList<MCSCmlOperationDefinition> operations) {
		super();
		this.operations = operations;
	}



	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}



	public LinkedList<MCSCmlOperationDefinition> getOperations() {
		return operations;
	}



	public void setOperations(LinkedList<MCSCmlOperationDefinition> operations) {
		this.operations = operations;
	}

	
}
