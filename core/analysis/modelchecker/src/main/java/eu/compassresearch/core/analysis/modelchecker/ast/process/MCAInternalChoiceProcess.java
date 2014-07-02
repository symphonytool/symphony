package eu.compassresearch.core.analysis.modelchecker.ast.process;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCSemanticExternalChoiceAction;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;



public class MCAInternalChoiceProcess implements MCPProcess {

	protected MCPProcess leftProcess;
	protected MCPProcess rightProcess;
	
	public MCAInternalChoiceProcess(
			MCPProcess leftProcess, 
			MCPProcess rightProcess) {
		super();
		this.leftProcess = leftProcess;
		this.rightProcess = rightProcess;
	}



	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		
		result.append("iChoice(");
		result.append(this.leftProcess.toFormula(option));
		result.append(",");
		result.append(this.rightProcess.toFormula(option));
		result.append(")");

		return result.toString();
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
