package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public class MCAInstanceVariableDefinition implements MCPCMLDefinition {

	private String name;
	private MCPCMLType type;
	private MCPCMLExp expression;
	
	
	
	public MCAInstanceVariableDefinition(String name, MCPCMLType type,
			MCPCMLExp expression) {
		super();
		this.name = name;
		this.type = type;
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



	public MCPCMLType getType() {
		return type;
	}



	public void setType(MCPCMLType type) {
		this.type = type;
	}



	public MCPCMLExp getExpression() {
		return expression;
	}



	public void setExpression(MCPCMLExp expression) {
		this.expression = expression;
	}

	
}
