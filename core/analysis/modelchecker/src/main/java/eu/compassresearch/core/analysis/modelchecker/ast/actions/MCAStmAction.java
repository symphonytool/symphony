package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.ast.actions.AStmAction;
import eu.compassresearch.core.analysis.modelchecker.ast.statements.MCPCMLStm;

public class MCAStmAction implements MCPAction {

	private MCPCMLStm statement;
	
	
	public MCAStmAction(MCPCMLStm statement) {
		super();
		this.statement = statement;
	}


	@Override
	public String toFormula(String option) {
		return this.statement.toFormula(option);
	}


	public MCPCMLStm getStatement() {
		return statement;
	}


	public void setStatement(MCPCMLStm statement) {
		this.statement = statement;
	}

	
}
