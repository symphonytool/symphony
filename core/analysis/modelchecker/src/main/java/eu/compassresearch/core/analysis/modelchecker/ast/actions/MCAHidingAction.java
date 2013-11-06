package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;


public class MCAHidingAction implements MCPAction {
	
	private MCPAction action;
	private MCPVarsetExpression varSetExpression;
	
	public MCAHidingAction(MCPAction action, MCPVarsetExpression varSetExpression) {
		super();
		this.action = action;
		this.varSetExpression = varSetExpression;
	}


	@Override
	public String toFormula(String option) {
		StringBuffer result = new StringBuffer();
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance(); 
		
		//it puts the event set in the context so the internal process can access and generate lieIn
		context.setStack.add(varSetExpression);
				
		// "actions\ {ev}" hide(actions,"{ev}")
		result.append("hide(");
				
		//the internal action must check if there is some event set to generate lieInEvents
		result.append(this.action.toFormula(option));
		result.append(",");
		result.append(this.varSetExpression.toFormula(option));
		result.append(")");
				
		//it removes the event set from the context at the end
		context.setStack.pop();
		
		return result.toString();
	}


	public MCPAction getAction() {
		return action;
	}


	public void setAction(MCPAction action) {
		this.action = action;
	}


	public MCPVarsetExpression getVarSetExpression() {
		return varSetExpression;
	}


	public void setVarSetExpression(MCPVarsetExpression varSetExpression) {
		this.varSetExpression = varSetExpression;
	}
	
}
