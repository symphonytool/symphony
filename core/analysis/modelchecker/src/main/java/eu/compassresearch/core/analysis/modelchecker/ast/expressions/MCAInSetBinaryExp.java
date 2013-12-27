package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

public class MCAInSetBinaryExp extends MCASBinaryExp {

	
	public MCAInSetBinaryExp(MCPCMLExp left, MCPCMLExp right) {
		super(left, right);
	}

	@Override
	public String toFormula(String option) {
		//return this.getRight().toFormula(option);
		StringBuilder result = new StringBuilder();

		result.append("member(");
		result.append(this.getLeft().toFormula(option));
		result.append(",");
		result.append(this.getRight().toFormula(option));
		result.append(")");
		
		return result.toString();
	}

	@Override
	public MCPCMLExp copy() {
		
		return new MCAInSetBinaryExp(this.getLeft().copy(), this.getRight().copy());
	}

}
