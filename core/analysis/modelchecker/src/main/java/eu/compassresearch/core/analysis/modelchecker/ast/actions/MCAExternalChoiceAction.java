package eu.compassresearch.core.analysis.modelchecker.ast.actions;


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
		StringBuffer result = new StringBuffer();
		
		result.append("eChoice(");
		result.append(this.left.toFormula(option));
		result.append(",");
		result.append(this.right.toFormula(option));
		result.append(")");

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
