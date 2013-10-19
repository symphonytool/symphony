package eu.compassresearch.core.analysis.modelchecker.ast.actions;


public class MCASkipAction extends MCPActionBase {

	@Override
	public String toFormula(String option) {
		return "Skip";
	}

}
