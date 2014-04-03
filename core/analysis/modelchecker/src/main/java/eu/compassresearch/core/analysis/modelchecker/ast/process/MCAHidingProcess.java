package eu.compassresearch.core.analysis.modelchecker.ast.process;

import eu.compassresearch.ast.process.AHidingProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAHidingProcess implements MCPProcess{

	protected MCPVarsetExpression chansetExpression;
	protected MCPProcess process;
	
	public MCAHidingProcess(MCPVarsetExpression chansetExp, MCPProcess proc) {
		super();
		this.process = proc;
		this.chansetExpression = chansetExp;
	}
	
	@Override
	public String toFormula(String option) {
		StringBuffer result = new StringBuffer();
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance(); 
		
		//it puts the event set in the context so the internal process can access and generate lieIn
		context.setStack.add(chansetExpression);
				
		// "actions\ {ev}" hide(actions,"{ev}")
		result.append("hide(");
				
		//the internal action must check if there is some event set to generate lieInEvents
		result.append(this.process.toFormula(option));
		result.append(",");
		result.append(this.chansetExpression.toFormula(option));
		result.append(")");
				
		//it removes the event set from the context at the end
		context.setStack.pop();
		
		return result.toString();
	}

	public MCPVarsetExpression getChansetExpression() {
		return chansetExpression;
	}

	public void setChansetExpression(MCPVarsetExpression chansetExpression) {
		this.chansetExpression = chansetExpression;
	}

	public MCPProcess getProcess() {
		return process;
	}

	public void setProcess(MCPProcess process) {
		this.process = process;
	}

	
}