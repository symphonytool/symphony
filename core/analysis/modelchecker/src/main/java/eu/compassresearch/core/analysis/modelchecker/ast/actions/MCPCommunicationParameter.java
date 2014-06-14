package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public abstract class MCPCommunicationParameter implements MCNode {

	protected MCPCMLExp expression;

	public MCPCommunicationParameter(MCPCMLExp expression) {
		super();
		this.expression = expression;
	}

	public MCPCMLExp getExpression() {
		return expression;
	}

	public void setExpression(MCPCMLExp expression) {
		this.expression = expression;
	}
	
	

}
