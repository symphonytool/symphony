package eu.compassresearch.core.analysis.modelchecker.ast.statements;

import eu.compassresearch.ast.statements.AActionStm;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;

public class MCAActionStm implements MCPCMLStm {

	private MCPAction action;
	
	
	public MCAActionStm(MCPAction action) {
		this.action = action;
	}


	@Override
	public String toFormula(String option) {
		return this.action.toFormula(option);
	}


	public MCPAction getAction() {
		return action;
	}


	public void setAction(MCPAction action) {
		this.action = action;
	}

	
}
