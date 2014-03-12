package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

public class MCAModNumericBinaryExp extends MCNumericBinaryExp {

	public MCAModNumericBinaryExp(MCPCMLExp left, MCPCMLExp right) {
		super(left, right);
	}

	
	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();

		result.append(this.getLeft().toFormula(option) + " % "+ this.getRight().toFormula(option));

		return result.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof MCAModNumericBinaryExp){
			result = this.getLeft().equals(((MCAModNumericBinaryExp) obj).getLeft())
					&& this.getRight().equals(((MCAModNumericBinaryExp) obj).getRight());
		}
		return result;
	}


	@Override
	public MCPCMLExp copy() {
		
		return new MCAModNumericBinaryExp(this.getLeft(), this.getRight());
	}

}
