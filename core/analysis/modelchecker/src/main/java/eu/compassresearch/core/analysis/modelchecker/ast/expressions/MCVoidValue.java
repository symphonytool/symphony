package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

public class MCVoidValue implements MCPCMLExp {

	@Override
	public String toFormula(String option) {
		return "void";
	}

	@Override
	public MCPCMLExp copy() {
		return new MCVoidValue();
	}

}
