package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCPCMLPattern;

public class MCAReadCommunicationParameter implements MCPCommunicationParameter {

	private MCPCMLExp expression;
	private MCPCMLPattern pattern;
	
	
	public MCAReadCommunicationParameter(MCPCMLExp expression,
			MCPCMLPattern pattern) {
		this.expression = expression;
		this.pattern = pattern;
	}


	@Override
	public String toFormula(String option) {
		return "?" + this.pattern.toFormula(option);
	}

	

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof MCAReadCommunicationParameter){
			result = this.pattern.equals(((MCAReadCommunicationParameter) obj).getPattern());
		}
		return result;

	}

	@Override
	public String toString() {
		String result = null;
		if(this.expression != null){
			result = this.expression.toString();
		} else if(this.pattern != null){
			result = this.pattern.toFormula(MCNode.DEFAULT);
		}
		
		return result;
	}

	public MCPCMLExp getExpression() {
		return expression;
	}


	public void setExpression(MCPCMLExp expression) {
		this.expression = expression;
	}


	public MCPCMLPattern getPattern() {
		return pattern;
	}


	public void setPattern(MCPCMLPattern pattern) {
		this.pattern = pattern;
	}

	
}
