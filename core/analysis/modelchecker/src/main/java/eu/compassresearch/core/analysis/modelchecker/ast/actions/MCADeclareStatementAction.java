package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCPCMLDefinition;

public class MCADeclareStatementAction implements MCPAction {

	private LinkedList<MCPCMLDefinition> assignmentDefs = new LinkedList<MCPCMLDefinition>();
	
	public MCADeclareStatementAction(LinkedList<MCPCMLDefinition> assignmentDefs) {
		this.assignmentDefs = assignmentDefs;
	}

	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		result.append(assignmentDefs.getFirst().toFormula(option));
		return result.toString();
	}

	public LinkedList<MCPCMLDefinition> getAssignmentDefs() {
		return assignmentDefs;
	}

	public void setAssignmentDefs(LinkedList<MCPCMLDefinition> assignmentDefs) {
		this.assignmentDefs = assignmentDefs;
	}
	
}
