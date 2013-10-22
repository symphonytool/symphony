package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class MCCondition implements MCNode{
	protected MCPCMLExp expression;
	protected int counter;
	
	public MCCondition(MCPCMLExp expression, int counter) {
		super();
		this.expression = expression;
		this.counter = counter;
	}

	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
