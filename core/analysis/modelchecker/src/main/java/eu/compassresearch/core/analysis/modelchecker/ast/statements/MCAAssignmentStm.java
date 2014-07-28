package eu.compassresearch.core.analysis.modelchecker.ast.statements;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAAssignmentStm implements MCPCMLStm {

	private int counterId;
	private MCPCMLExp expression;
	private MCPStateDesignator target;

	
	public MCAAssignmentStm(MCPCMLExp expression, MCPStateDesignator target) {
		this.counterId = NewCMLModelcheckerContext.ASSIGN_COUNTER++;
		this.expression = expression;
		this.target = target;
	}


	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		
		result.append("assign("+ counterId +")");
		
		return result.toString();
	}


	public MCPCMLExp getExpression() {
		return expression;
	}


	public void setExpression(MCPCMLExp expression) {
		this.expression = expression;
	}


	public MCPStateDesignator getTarget() {
		return target;
	}


	public void setTarget(MCPStateDesignator target) {
		this.target = target;
	}


	public int getCounterId() {
		return counterId;
	}


	public void setCounterId(int counterId) {
		this.counterId = counterId;
	}

	
}
