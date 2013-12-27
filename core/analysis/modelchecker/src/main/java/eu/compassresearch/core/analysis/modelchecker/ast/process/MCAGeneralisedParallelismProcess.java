package eu.compassresearch.core.analysis.modelchecker.ast.process;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;



public class MCAGeneralisedParallelismProcess implements MCPProcess {

	protected MCPProcess leftProcess;
	private MCPVarsetExpression chansetExpression;
	protected MCPProcess rightProcess;
	
	public MCAGeneralisedParallelismProcess(
			MCPProcess leftProcess, MCPVarsetExpression chansetExpression,
			MCPProcess rightProcess) {
		super();
		this.chansetExpression = chansetExpression;
		this.leftProcess = leftProcess;
		this.rightProcess = rightProcess;
	}



	@Override
	public String toFormula(String option) {
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		StringBuffer result = new StringBuffer();
		
		//it puts the event set in the context so the internal process can access and generate lieIn
		context.setStack.add(this.chansetExpression);
	
		// it writes the left process into the buffer
		result.append("genPar(");
		result.append(this.leftProcess.toFormula(option));
		result.append(",");
		result.append(this.chansetExpression.toFormula(option));
		result.append(",");
		result.append(this.rightProcess.toFormula(option));
		result.append(")");

		//it removes the synchronisation set form the context
		context.setStack.pop();
		
		return result.toString();
	}



	public MCPVarsetExpression getChansetExpression() {
		return chansetExpression;
	}



	public void setChansetExpression(MCPVarsetExpression chansetExpression) {
		this.chansetExpression = chansetExpression;
	}



	public MCPProcess getLeftProcess() {
		return leftProcess;
	}



	public void setLeftProcess(MCPProcess leftProcess) {
		this.leftProcess = leftProcess;
	}



	public MCPProcess getRightProcess() {
		return rightProcess;
	}



	public void setRightProcess(MCPProcess rightProcess) {
		this.rightProcess = rightProcess;
	}


	

	
}
