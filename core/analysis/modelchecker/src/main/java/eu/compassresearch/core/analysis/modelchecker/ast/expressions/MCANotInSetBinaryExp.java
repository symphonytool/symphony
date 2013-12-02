package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

public class MCANotInSetBinaryExp extends MCNumericBinaryExp {

	
	public MCANotInSetBinaryExp(MCPCMLExp left, MCPCMLExp right) {
		super(left, right);
	}

	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();

		result.append("fail member(");
		result.append(this.getLeft().toFormula(option));
		result.append(",");
		result.append(this.getRight().toFormula(option));
		result.append(")");
		
		return result.toString();
	}

	@Override
	public MCPCMLExp copy() {
		// TODO Auto-generated method stub
		return null;
	}

}
