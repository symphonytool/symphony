package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAGuardedAction implements MCPAction {

	private int counterId;
	private MCPCMLExp expression;
	private MCPAction action;
	private String formulaCodeDefault;
	
	public MCAGuardedAction(MCPCMLExp expression, MCPAction action) {
		super();
		this.counterId = NewCMLModelcheckerContext.GUARD_COUNTER++;
		this.expression = expression;
		this.action = action;
	}


	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		
		
			NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		
			// it writes the conditional choice constructor
			result.append("condChoice(");
			
			// it writes the condition as an integer and puts the expression
			result.append(this.counterId + ",");
	
			switch (option) {
			case MCNode.DEFAULT:
				if(this.formulaCodeDefault == null){
					// it writes the behaviour in the if-true branch
					result.append(this.action.toFormula(option));
					result.append(",");
					
					result.append((new MCAStopAction()).toFormula(option));
					result.append(")");
					this.formulaCodeDefault = result.toString();
				}else{
					result = new StringBuilder();
					result.append(this.formulaCodeDefault);
				}
				
				break;
	
			case MCNode.GENERIC:
				// it writes the behaviour in the if-true branch generically
				result.append("_");
				result.append(",");
				result.append("_");
				result.append(")");
				break;
			default:
				break;
			}
			
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
