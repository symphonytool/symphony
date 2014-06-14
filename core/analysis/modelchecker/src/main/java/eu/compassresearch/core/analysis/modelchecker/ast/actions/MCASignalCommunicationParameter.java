package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class MCASignalCommunicationParameter extends
		MCPCommunicationParameter {

	public MCASignalCommunicationParameter(MCPCMLExp expression) {
		super(expression);
	}


	@Override
	public String toFormula(String option) {
		return "." + this.expression.toFormula(option);
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof MCASignalCommunicationParameter){
			result = this.expression.equals(((MCPCommunicationParameter) obj).getExpression());
		}
		return result;
	}

	

	@Override
	public String toString() {
		return this.expression.toString();
	}


	@Override
	public MCPCMLExp getExpression() {
		return expression;
	}


}
