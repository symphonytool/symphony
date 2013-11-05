package eu.compassresearch.core.analysis.modelchecker.ast.declarations;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class MCAExpressionSingleDeclaration implements MCPSingleDeclaration {

	private String identifier;
	private MCPCMLExp expression;

	public MCAExpressionSingleDeclaration(String identifier,
			MCPCMLExp expression) {
		super();
		this.identifier = identifier;
		this.expression = expression;
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


	public MCPCMLExp getExpression() {
		return expression;
	}


	public void setExpression(MCPCMLExp expression) {
		this.expression = expression;
	}

	
}
