package eu.compassresearch.core.analysis.modelchecker.ast.expressions;


public class MCAUnionVOpVarsetExpression implements MCPVarsetExpression{

	protected MCPVarsetExpression left;
	protected MCPVarsetExpression right;
	
	public MCAUnionVOpVarsetExpression(MCPVarsetExpression l, MCPVarsetExpression r) {
		super();
		this.left = l;
		this.right = r;
	}
	
	@Override
	public String toFormula(String option) {
		return null;
	}

	public MCPVarsetExpression getLeft() {
		return left;
	}

	public void setLeft(MCPVarsetExpression left) {
		this.left = left;
	}

	public MCPVarsetExpression getRight() {
		return right;
	}

	public void setRight(MCPVarsetExpression right) {
		this.right = right;
	}

}
