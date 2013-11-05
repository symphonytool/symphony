package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCASingleGeneralAssignmentStatementAction implements MCPAction {

	private int counterId;
	private MCPCMLExp stateDesignator;
	private MCPCMLExp expression;
	
	public MCASingleGeneralAssignmentStatementAction(MCPCMLExp stateDesignator,
			MCPCMLExp expression) {
		
		this.counterId = NewCMLModelcheckerContext.ASSIGN_COUNTER++;
		this.stateDesignator = stateDesignator;
		this.expression = expression;
	}

	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		
		result.append("assign("+ counterId +")");
		
		return result.toString();
	}

	public MCPCMLExp getStateDesignator() {
		return stateDesignator;
	}

	public void setStateDesignator(MCPCMLExp stateDesignator) {
		this.stateDesignator = stateDesignator;
	}

	public MCPCMLExp getExpression() {
		return expression;
	}

	public void setExpression(MCPCMLExp expression) {
		this.expression = expression;
	}

	public int getCounterId() {
		return counterId;
	}

	public void setCounterId(int counterId) {
		this.counterId = counterId;
	}

	
	
}
