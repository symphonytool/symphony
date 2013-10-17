package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPVarsetExpression;


public class MCAGeneralisedParallelismParallelAction extends
		MCSParallelActionBase {

	private MCPVarsetExpression chansetExpression;
	
	public MCAGeneralisedParallelismParallelAction(MCPAction leftAction,MCPVarsetExpression chansetExpression,MCPAction rightAction) {
		super(leftAction, null, null, rightAction);
		this.chansetExpression = chansetExpression;
	}

	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return super.toFormula(option);
	}

	public MCPVarsetExpression getChansetExpression() {
		return chansetExpression;
	}

	public void setChansetExpression(MCPVarsetExpression chansetExpression) {
		this.chansetExpression = chansetExpression;
	}

	
}
