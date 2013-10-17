package eu.compassresearch.core.analysis.modelchecker.ast.actions;


public class MCAInternalChoiceAction implements MCPAction {
	
	private MCPAction left;
	private MCPAction right;
	
	public MCAInternalChoiceAction(MCPAction left, MCPAction right) {
		super();
		this.left = left;
		this.right = right;
	}

	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
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
