package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.ast.actions.ASignalCommunicationParameter;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class MCASignalCommunicationParameter implements
		MCPCommunicationParameter {

	private MCPCMLExp expression;
	
	
	public MCASignalCommunicationParameter(MCPCMLExp expression) {
		super();
		this.expression = expression;
	}


	@Override
	public String toFormula(String option) {
		return "." + this.expression.toFormula(option);
	}


	public MCPCMLExp getExpression() {
		return expression;
	}


	public void setExpression(MCPCMLExp expression) {
		this.expression = expression;
	}

	
}
