package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

public class MCANotEqualsBinaryExp extends MCNumericBinaryExp {

	public MCANotEqualsBinaryExp(MCPCMLExp left, MCPCMLExp right) {
		super(left, right);
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();

		result.append(this.getLeft().toFormula(option) + " != "+ this.getRight().toFormula(option));

		return result.toString();
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof MCANotEqualsBinaryExp){
			result = this.getLeft().equals(((MCANotEqualsBinaryExp) obj).getLeft())
					&& this.getRight().equals(((MCANotEqualsBinaryExp) obj).getRight());
		}
		return result;
	}


	@Override
	public MCPCMLExp copy() {
		// TODO Auto-generated method stub
		return null;
	}
}
