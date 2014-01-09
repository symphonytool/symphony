package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class MCATimedInterruptAction extends MCAInterruptAction {

	private MCPCMLExp timeExpression;
	
	public MCATimedInterruptAction(MCPAction left, MCPAction right, MCPCMLExp timeExpression) {
		super(left, right);
		this.timeExpression = timeExpression;
	}

	
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		
		result.append("tIntrpt(");
		result.append(this.getLeft().toFormula(option));
		result.append(",");
		result.append(this.timeExpression.toFormula(option));
		result.append(",");
		result.append(this.getRight().toFormula(option));
		result.append(")");

		return result.toString();
	}


	public MCPCMLExp getTimeExpression() {
		return timeExpression;
	}

	public void setTimeExpression(MCPCMLExp timeExpression) {
		this.timeExpression = timeExpression;
	}

	
}
