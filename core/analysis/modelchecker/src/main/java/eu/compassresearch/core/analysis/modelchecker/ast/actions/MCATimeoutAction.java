package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class MCATimeoutAction extends MCAUntimedTimeoutAction {
	private MCPCMLExp timeoutExpression;

	public MCATimeoutAction(MCPAction left, MCPAction right, MCPCMLExp timeoutExpression) {
		super(left, right);
		this.timeoutExpression = timeoutExpression;
	}

	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		
		result.append("tTimeout(");
		result.append(this.getLeft().toFormula(option));
		result.append(",");
		result.append(this.timeoutExpression.toFormula(option));
		result.append(",");
		result.append(this.getRight().toFormula(option));
		result.append(")");

		return result.toString();
	}
	public MCPCMLExp getTimeoutExpression() {
		return timeoutExpression;
	}

	public void setTimeoutExpression(MCPCMLExp timeoutExpression) {
		this.timeoutExpression = timeoutExpression;
	}

	
}
