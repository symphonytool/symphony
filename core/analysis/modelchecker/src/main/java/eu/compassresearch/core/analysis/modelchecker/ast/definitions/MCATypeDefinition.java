package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import org.overture.ast.definitions.AExplicitFunctionDefinition;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class MCATypeDefinition implements MCPCMLDefinition {
	
	private String name;
	//private MCAExplicitFunctionDefinition invDef;
	private MCPCMLExp invExpression;

	
	public MCATypeDefinition(String name, //MCAExplicitFunctionDefinition invDef,
			MCPCMLExp invExpression) {
		super();
		this.name = name;
		//this.invDef = invDef;
		this.invExpression = invExpression;
	}


	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		result.append(this.name + " ::= ");
		result.append(this.invExpression.toFormula(option));
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


	
}
