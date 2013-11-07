package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPVarsetExpression;

public abstract class MCSParallelActionBase implements MCPAction {

	protected MCPAction leftAction;
	protected MCPVarsetExpression leftNamesetExpression;
	protected MCPVarsetExpression rightNamesetExpression;
	protected MCPAction rightAction;
	
	
	public MCSParallelActionBase(MCPAction leftAction,
			MCPVarsetExpression leftNamesetExpression,
			MCPVarsetExpression rightNamesetExpression, MCPAction rightAction) {
		super();
		this.leftAction = leftAction;
		this.leftNamesetExpression = leftNamesetExpression;
		this.rightNamesetExpression = rightNamesetExpression;
		this.rightAction = rightAction;
	}


	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}


	public MCPAction getLeftAction() {
		return leftAction;
	}


	public void setLeftAction(MCPAction leftAction) {
		this.leftAction = leftAction;
	}


	public MCPVarsetExpression getLeftNamesetExpression() {
		return leftNamesetExpression;
	}


	public void setLeftNamesetExpression(MCPVarsetExpression leftNamesetExpression) {
		this.leftNamesetExpression = leftNamesetExpression;
	}


	public MCPVarsetExpression getRightNamesetExpression() {
		return rightNamesetExpression;
	}


	public void setRightNamesetExpression(MCPVarsetExpression rightNamesetExpression) {
		this.rightNamesetExpression = rightNamesetExpression;
	}


	public MCPAction getRightAction() {
		return rightAction;
	}


	public void setRightAction(MCPAction rightAction) {
		this.rightAction = rightAction;
	}
	
}
