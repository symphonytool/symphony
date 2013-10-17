package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import java.util.LinkedList;

public class MCACommunicationAction implements MCPAction {

	private String identifier;
	private LinkedList<MCPCommunicationParameter> communicationParameters = new LinkedList<MCPCommunicationParameter>();
	private MCPAction action;
	
	
	
	public MCACommunicationAction(String identifier,
			LinkedList<MCPCommunicationParameter> communicationParameters,
			MCPAction action) {
		super();
		this.identifier = identifier;
		this.communicationParameters = communicationParameters;
		this.action = action;
	}



	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}



	public String getIdentifier() {
		return identifier;
	}



	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}



	public LinkedList<MCPCommunicationParameter> getCommunicationParameters() {
		return communicationParameters;
	}



	public void setCommunicationParameters(
			LinkedList<MCPCommunicationParameter> communicationParameters) {
		this.communicationParameters = communicationParameters;
	}



	public MCPAction getAction() {
		return action;
	}



	public void setAction(MCPAction action) {
		this.action = action;
	}

	
}
