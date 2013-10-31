package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

public abstract class MCNumericBinaryExp implements MCPCMLExp {

	private MCPCMLExp left;
	private MCPCMLExp right;
	
	public MCNumericBinaryExp(MCPCMLExp left, MCPCMLExp right) {
		this.left = left;
		this.right = right;
	}

	public MCPCMLExp getLeft() {
		return left;
	}
	public void setLeft(MCPCMLExp left) {
		this.left = left;
	}
	public MCPCMLExp getRight() {
		return right;
	}
	public void setRight(MCPCMLExp right) {
		this.right = right;
	}

	
}
