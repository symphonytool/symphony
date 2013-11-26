package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import org.overture.ast.definitions.AExplicitFunctionDefinition;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public class MCATypeDefinition implements MCPCMLDefinition {
	
	private String name;
	//private MCAExplicitFunctionDefinition invDef;
	private MCPCMLExp invExpression;
	private MCPCMLType type;

	
	

	public MCATypeDefinition(String name, MCPCMLExp invExpression,
			MCPCMLType type) {
		super();
		this.name = name;
		this.invExpression = invExpression;
		this.type = type;
	}


	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		result.append(this.name + " ::= ");
		if(this.invExpression != null){
			result.append(this.invExpression.toFormula(option));
		} else if (this.type != null){
			result.append(this.type.toFormula(option));
		}
		result.append(".");
		
		return result.toString();
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	
	public MCPCMLExp getInvExpression() {
		return invExpression;
	}


	public void setInvExpression(MCPCMLExp invExpression) {
		this.invExpression = invExpression;
	}


	public MCPCMLType getType() {
		return type;
	}


	public void setType(MCPCMLType type) {
		this.type = type;
	}

	

	
}
