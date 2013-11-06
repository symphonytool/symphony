package eu.compassresearch.core.analysis.modelchecker.ast.statements;

import java.util.LinkedList;

import org.overture.ast.statements.ABlockSimpleBlockStm;

public class MCABlockSimpleBlockStm implements MCPCMLStm {

	private LinkedList<MCPCMLStm> statements;
	
	
	public MCABlockSimpleBlockStm(LinkedList<MCPCMLStm> statements) {
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

	
}
