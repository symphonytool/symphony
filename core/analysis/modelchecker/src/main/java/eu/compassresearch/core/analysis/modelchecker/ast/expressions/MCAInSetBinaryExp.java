package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

public class MCAInSetBinaryExp extends MCNumericBinaryExp {

	@Override
	public String toFormula(String option) {
		return this.getRight().toFormula(option);
	}

}
