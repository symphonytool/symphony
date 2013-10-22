package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCCondition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.visitors.CMLModelcheckerContext;
import eu.compassresearch.core.analysis.modelchecker.visitors.Condition;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAGuardedAction implements MCPAction {

	private int counterId;
	private MCPCMLExp expression;
	private MCPAction action;
	
	
	public MCAGuardedAction(MCPCMLExp expression, MCPAction action) {
		super();
		this.counterId = NewCMLModelcheckerContext.GUARD_COUNTER++;
		this.expression = expression;
		this.action = action;
	}


	@Override
	public String toFormula(String option) {
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		StringBuilder result = new StringBuilder();
		
		// it writes the conditional choice constructor
		result.append("condChoice(");
		
		// it writes the condition as an integer and puts the expression
		//to be evaluated in the context
		result.append(this.counterId + ",");
		
		MCCondition newCondition = new MCCondition(this,this.expression, this.counterId);
		if(!context.conditions.contains(newCondition)){
			context.conditions.add(newCondition);
		}

		// it writes the behaviour in the if-true branch
		result.append(this.action.toFormula(option));
		result.append(",");
		result.append((new MCAStopAction()).toFormula(option));
		result.append(")");
		
		return result.toString();
	}


	public MCPCMLExp getExpression() {
		return expression;
	}


	public void setExpression(MCPCMLExp expression) {
		this.expression = expression;
	}


	public MCPAction getAction() {
		return action;
	}


	public void setAction(MCPAction action) {
		this.action = action;
	}


	public int getCounterId() {
		return counterId;
	}


	public void setCounterId(int counterId) {
		this.counterId = counterId;
	}
	
}
