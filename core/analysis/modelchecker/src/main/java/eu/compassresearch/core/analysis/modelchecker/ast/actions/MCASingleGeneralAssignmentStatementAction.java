package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class MCASingleGeneralAssignmentStatementAction implements MCPAction {

	private MCPCMLExp stateDesignator;
	private MCPCMLExp expression;
	
	public MCASingleGeneralAssignmentStatementAction(MCPCMLExp stateDesignator,
			MCPCMLExp expression) {
		super();
		this.stateDesignator = stateDesignator;
		this.expression = expression;
	}

	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
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

	
}
