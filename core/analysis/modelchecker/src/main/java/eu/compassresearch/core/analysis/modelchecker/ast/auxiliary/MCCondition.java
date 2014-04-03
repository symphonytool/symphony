package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class MCCondition implements MCNode{
	
	protected MCPAction parentAction;
	protected MCPCMLExp expression;
	protected int counter;
	
	public MCCondition(MCPAction parentAction, MCPCMLExp expression, int counter) {
		this.parentAction = parentAction;
		this.expression = expression;
		this.counter = counter;
	}

	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof MCCondition){
			result = this.counter == ((MCCondition) obj).getCounter();
		}
		return result;
	}

	public MCPCMLExp getExpression() {
		return expression;
	}

	public void setExpression(MCPCMLExp expression) {
		this.expression = expression;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public MCPAction getParentAction() {
		return parentAction;
	}

	public void setParentAction(MCPAction parentAction) {
		this.parentAction = parentAction;
	}
	
}
