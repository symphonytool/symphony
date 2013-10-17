package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class MCAValueDefinition implements MCPCMLDefinition {

	private String name;
	private LinkedList<MCPCMLDefinition> definitions;
	private MCPCMLExp expression;
	
	public MCAValueDefinition(String name,
			LinkedList<MCPCMLDefinition> definitions, MCPCMLExp expression) {
		super();
		this.name = name;
		this.definitions = definitions;
		this.expression = expression;
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

	public MCPCMLExp getExpression() {
		return expression;
	}

	public void setExpression(MCPCMLExp expression) {
		this.expression = expression;
	}

	
}
