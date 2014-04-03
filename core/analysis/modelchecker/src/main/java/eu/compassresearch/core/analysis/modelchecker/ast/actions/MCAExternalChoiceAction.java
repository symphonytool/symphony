package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;


public class MCAExternalChoiceAction implements MCPAction {

	private MCPAction left;
	private MCPAction right;

	
	public MCAExternalChoiceAction(MCPAction left, MCPAction right) {
		super();
		this.left = left;
		this.right = right;
	}

	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		
		switch (option) {
		case MCNode.DEFAULT:
			result.append("eChoice(");
			result.append(this.left.toFormula(option));
			result.append(",");
			result.append(this.right.toFormula(option));
			result.append(")");
			
			
			break;

		case MCNode.SEMANTIC_NAMED:
			NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
			
			MCSemanticExternalChoiceAction extraChoice = 
				new MCSemanticExternalChoiceAction(this.left, this.right, context.maximalBinding, context.maximalBinding);

			result.append(extraChoice.toFormula(SEMANTIC_NAMED));
			
			break;
		default:
			result.append("eChoice(");
			result.append(this.left.toFormula(option));
			result.append(",");
			result.append(this.right.toFormula(option));
			result.append(")");
			break;
		}
		
		

		return result.toString();
	}

	public MCPAction getLeft() {
		return left;
	}

	public void setLeft(MCPAction left) {
		this.left = left;
	}

	public MCPAction getRight() {
		return right;
	}

	public void setRight(MCPAction right) {
		this.right = right;
	}
	
}
