package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCPCMLPattern;

public class MCAReadCommunicationParameter implements MCPCommunicationParameter {

	private MCPCMLExp expression;
	private MCPCMLPattern pattern;
	
	
	public MCAReadCommunicationParameter(MCPCMLExp expression,
			MCPCMLPattern pattern) {
		super();
		this.expression = expression;
		this.pattern = pattern;
	}


	@Override
	public String toFormula(String option) {
		return "?" + this.pattern.toFormula(option);
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
