package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPParametrisation;

public class MCAActionDefinition implements MCPCMLDefinition {

	private LinkedList<MCPParametrisation> declarations = new LinkedList<MCPParametrisation>();
	private MCPAction action;

	
	public MCAActionDefinition(LinkedList<MCPParametrisation> declarations,
			MCPAction action) {
		super();
		this.declarations = declarations;
		this.action = action;
	}


	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}


	public LinkedList<MCPParametrisation> getDeclarations() {
		return declarations;
	}


	public void setDeclarations(LinkedList<MCPParametrisation> declarations) {
		this.declarations = declarations;
	}


	public MCPAction getAction() {
		return action;
	}


	public void setAction(MCPAction action) {
		this.action = action;
	}

	
}
