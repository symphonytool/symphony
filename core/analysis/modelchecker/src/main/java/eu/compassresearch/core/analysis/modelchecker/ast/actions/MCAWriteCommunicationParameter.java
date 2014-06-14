package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class MCAWriteCommunicationParameter extends
		MCPCommunicationParameter {

	public MCAWriteCommunicationParameter(MCPCMLExp expression) {
		super(expression);
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

}
