package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.LinkedList;

public class MCAActionsDefinition implements MCPCMLDefinition {

	private LinkedList<MCAActionDefinition> actions = new LinkedList<MCAActionDefinition>();

	public MCAActionsDefinition(LinkedList<MCAActionDefinition> actions) {
		super();
		this.actions = actions;
	}



	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}



	public LinkedList<MCAActionDefinition> getActions() {
		return actions;
	}



	public void setActions(LinkedList<MCAActionDefinition> actions) {
		this.actions = actions;
	}

	
}
