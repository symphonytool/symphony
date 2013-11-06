package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.LinkedList;

import org.overture.ast.definitions.SClassDefinition;

import eu.compassresearch.ast.definitions.AActionClassDefinition;

public class MCAActionClassDefinition implements MCPCMLDefinition {

	private String name;
	private LinkedList<MCPCMLDefinition> definitions;

	public MCAActionClassDefinition(String name,
			LinkedList<MCPCMLDefinition> definitions) {
		super();
		this.name = name;
		this.definitions = definitions;
	}


	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public LinkedList<MCPCMLDefinition> getDefinitions() {
		return definitions;
	}


	public void setDefinitions(LinkedList<MCPCMLDefinition> definitions) {
		this.definitions = definitions;
	}

	
	
}
