package eu.compassresearch.core.analysis.modelchecker.ast.actions;

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

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof MCASignalCommunicationParameter){
			result = this.expression.equals(((MCASignalCommunicationParameter) obj).getExpression());
		}
		return result;
	}

	

	@Override
	public String toString() {
		return this.expression.toString();
	}


	public MCPCMLExp getExpression() {
		return expression;
	}


	public void setExpression(MCPCMLExp expression) {
		this.expression = expression;
	}

	
}
