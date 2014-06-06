package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCPCMLDefinition;

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
