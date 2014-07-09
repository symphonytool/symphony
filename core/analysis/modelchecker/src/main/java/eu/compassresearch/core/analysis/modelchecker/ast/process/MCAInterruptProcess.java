package eu.compassresearch.core.analysis.modelchecker.ast.process;

public class MCAInterruptProcess implements MCPProcess {

	private MCPProcess left;
	private MCPProcess right;
	
	
	public MCAInterruptProcess(MCPProcess left, MCPProcess right) {
		super();
		this.left = left;
		this.right = right;
	}


	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		
		result.append("intrpt(");
		result.append(this.left.toFormula(option));
		result.append(",");
		result.append(this.right.toFormula(option));
		result.append(")");

		return result.toString();

	}


	public MCPProcess getLeft() {
		return left;
	}


	public void setLeft(MCPProcess left) {
		this.left = left;
	}


	public MCPProcess getRight() {
		return right;
	}


	public void setRight(MCPProcess right) {
		this.right = right;
	}

	
	
}
