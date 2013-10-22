package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCCondition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.visitors.CMLModelcheckerContext;
import eu.compassresearch.core.analysis.modelchecker.visitors.Condition;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAGuardedAction implements MCPAction {

	private MCPCMLExp expression;
	private MCPAction action;
	
	
	public MCAGuardedAction(MCPCMLExp expression, MCPAction action) {
		super();
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
		result.append(NewCMLModelcheckerContext.GUARD_COUNTER + ",");
		
		MCCondition newCondition = new MCCondition(this.expression,NewCMLModelcheckerContext.GUARD_COUNTER++);
		context.guards.add(newCondition);

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
	
}
