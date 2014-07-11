package eu.compassresearch.core.analysis.modelchecker.ast.process;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;



public class MCAExternalChoiceProcess implements MCPProcess {

	protected MCPProcess leftProcess;
	protected MCPProcess rightProcess;
	
	public MCAExternalChoiceProcess(
			MCPProcess leftProcess, 
			MCPProcess rightProcess) {
		super();
		this.leftProcess = leftProcess;
		this.rightProcess = rightProcess;
	}



	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		
		switch (option) {
		case MCNode.DEFAULT:
			result.append("eChoice(");
			result.append(this.leftProcess.toFormula(option));
			result.append(",");
			result.append(this.rightProcess.toFormula(option));
			result.append(")");
			
			
			break;

		case MCNode.SEMANTIC_NAMED:
			NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
			
			MCSemanticExternalChoiceProcess extraChoice = 
				new MCSemanticExternalChoiceProcess(this.leftProcess, this.rightProcess, context.maximalBinding, context.maximalBinding);

			result.append(extraChoice.toFormula(SEMANTIC_NAMED));
			
			break;
		default:
			result.append("eChoice(");
			result.append(this.leftProcess.toFormula(option));
			result.append(",");
			result.append(this.rightProcess.toFormula(option));
			result.append(")");
			break;
		}
		
		

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
