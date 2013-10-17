package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPVarsetExpression;


public class MCAHidingAction implements MCPAction {
	
	private MCPAction action;
	private MCPVarsetExpression varSetExpression;
	
	public MCAHidingAction(MCPAction action, MCPVarsetExpression varSetExpression) {
		super();
		this.action = action;
		this.varSetExpression = varSetExpression;
	}


	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}


	public MCPAction getAction() {
		return action;
	}


	public void setAction(MCPAction action) {
		this.action = action;
	}


	public MCPVarsetExpression getVarSetExpression() {
		return varSetExpression;
	}


	public void setVarSetExpression(MCPVarsetExpression varSetExpression) {
		this.varSetExpression = varSetExpression;
	}
	
}
