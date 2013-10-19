package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;


public class MCAGeneralisedParallelismParallelAction extends
		MCSParallelActionBase {

	private MCPVarsetExpression chansetExpression;
	
	public MCAGeneralisedParallelismParallelAction(MCPAction leftAction,MCPVarsetExpression chansetExpression,MCPAction rightAction) {
		super(leftAction, null, null, rightAction);
		this.chansetExpression = chansetExpression;
	}

	@Override
	public String toFormula(String option) {
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		StringBuffer result = new StringBuffer();
		//it puts the event set in the context so the internal process can access and generate lieIn
		context.setStack.add(this.chansetExpression);
	
		// it writes the left process into the buffer
		result.append("genPar(");
		result.append(this.leftAction.toFormula(option));
		result.append(",");
		result.append(this.chansetExpression.toFormula(option));
		result.append(",");
		result.append(this.rightAction.toFormula(option));
		result.append(")");

		context.setStack.pop();
		
		return result.toString();

	}

	public MCPVarsetExpression getChansetExpression() {
		return chansetExpression;
	}

	public void setChansetExpression(MCPVarsetExpression chansetExpression) {
		this.chansetExpression = chansetExpression;
	}

	
}
