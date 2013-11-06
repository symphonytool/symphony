package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

public class MCAPlusNumericBinaryExp extends MCNumericBinaryExp {

	public MCAPlusNumericBinaryExp(MCPCMLExp left, MCPCMLExp right) {
		super(left, right);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		result.append(this.getLeft().toFormula(option));
		result.append(" + ");
		result.append(this.getRight().toFormula(option));
		
		return result.toString();	
	}

}
