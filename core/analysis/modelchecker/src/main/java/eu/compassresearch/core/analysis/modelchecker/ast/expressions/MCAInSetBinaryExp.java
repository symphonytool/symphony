package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

public class MCAInSetBinaryExp extends MCNumericBinaryExp {

	
	public MCAInSetBinaryExp(MCPCMLExp left, MCPCMLExp right) {
		super(left, right);
	}

	@Override
	public String toFormula(String option) {
		return this.getRight().toFormula(option);
	}

	@Override
	public MCPCMLExp copy() {
		// TODO Auto-generated method stub
		return null;
	}

}
