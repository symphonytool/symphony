package eu.compassresearch.core.analysis.modelchecker.ast.statements;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAAssignmentDefinition;

public class MCABlockSimpleBlockStm implements MCPCMLStm {

	private LinkedList<MCPCMLStm> statements;
	private LinkedList<MCAAssignmentDefinition> assignmentDefs; 
	
	public MCABlockSimpleBlockStm(LinkedList<MCAAssignmentDefinition> assignmentDefs, LinkedList<MCPCMLStm> statements) {
		this.assignmentDefs = assignmentDefs;
		this.statements = statements;
	}


	@Override
	public String toFormula(String option) {
		//for themoment we assume that an blocksimpleblock has only one internal statement 
		return this.statements.getFirst().toFormula(option);
	}


	public LinkedList<MCPCMLStm> getStatements() {
		return statements;
	}


	public void setStatements(LinkedList<MCPCMLStm> statements) {
		this.statements = statements;
	}


	public LinkedList<MCAAssignmentDefinition> getAssignmentDefs() {
		return assignmentDefs;
	}


	public void setAssignmentDefs(LinkedList<MCAAssignmentDefinition> assignmentDefs) {
		this.assignmentDefs = assignmentDefs;
	}

	
	
}
