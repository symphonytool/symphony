package eu.compassresearch.core.analysis.modelchecker.ast.actions;


public class MCAStopAction implements MCPAction {

	@Override
	public String toFormula(String option) {
		return "Stop";
	}

}
