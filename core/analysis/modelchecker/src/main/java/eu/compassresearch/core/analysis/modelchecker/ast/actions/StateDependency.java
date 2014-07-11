package eu.compassresearch.core.analysis.modelchecker.ast.actions;


public class StateDependency {
	private String actionOrProcessName;

	public StateDependency(String actionOrProcessName) {
		super();
		this.actionOrProcessName = actionOrProcessName;
	}

	public String getActionOrProcessName() {
		return actionOrProcessName;
	}

	public void setActionOrProcessName(String actionOrProcessName) {
		this.actionOrProcessName = actionOrProcessName;
	}
}
