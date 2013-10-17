package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class MCAGuardedAction implements MCPAction {

	private MCPCMLExp expression;
	private MCPAction action;
	
	
	public MCAGuardedAction(MCPCMLExp expression, MCPAction action) {
		super();
		this.expression = expression;
		this.action = action;
	}


	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}


	public MCPCMLExp getExpression() {
		return expression;
	}


	public void setExpression(MCPCMLExp expression) {
		this.expression = expression;
	}


	public MCPAction getAction() {
		return action;
	}


	public void setAction(MCPAction action) {
		this.action = action;
	}
	
}
