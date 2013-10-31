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
		// TODO Auto-generated method stub
		return null;
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
