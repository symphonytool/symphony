package eu.compassresearch.core.analysis.modelchecker.ast.statements;

public class MCASkipStm implements MCPCMLStm {

	@Override
	public String toFormula(String option) {
		return "Skip";
	}

}
