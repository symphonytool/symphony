package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

public class MCALessNumericBinaryExp extends MCNumericBinaryExp {

	public MCALessNumericBinaryExp(MCPCMLExp left, MCPCMLExp right) {
		super(left, right);
		// TODO Auto-generated constructor stub
	}

	public MCALessNumericBinaryExp() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();

		result.append(this.getLeft().toFormula(option) + " < "+ this.getRight().toFormula(option));

		return result.toString();
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof MCALessNumericBinaryExp){
			result = this.getLeft().equals(((MCALessNumericBinaryExp) obj).getLeft())
					&& this.getRight().equals(((MCALessNumericBinaryExp) obj).getRight());
		}
		return result;
	}
}
