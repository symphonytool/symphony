package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class MCAWriteCommunicationParameter implements
		MCPCommunicationParameter {

	private MCPCMLExp expression;
	
	
	public MCAWriteCommunicationParameter(MCPCMLExp expression) {
		super();
		this.expression = expression;
	}


	@Override
	public String toFormula(String option) {
		return "!" + this.expression.toFormula(option);
	}

	

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof MCAWriteCommunicationParameter){
			result = this.expression.equals(((MCAWriteCommunicationParameter) obj).getExpression());
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
