package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.LinkedList;

public class MCAActionClassDefinition implements MCPCMLDefinition {

	private LinkedList<MCPCMLDefinition> definitions;

	public MCAActionClassDefinition(LinkedList<MCPCMLDefinition> definitions) {
		this.definitions = definitions;
	}


	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}


	public LinkedList<MCPCMLDefinition> getDefinitions() {
		return definitions;
	}


	public void setDefinitions(LinkedList<MCPCMLDefinition> definitions) {
		this.definitions = definitions;
	}

	
	
}
