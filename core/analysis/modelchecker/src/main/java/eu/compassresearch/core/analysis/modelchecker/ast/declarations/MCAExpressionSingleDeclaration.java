package eu.compassresearch.core.analysis.modelchecker.ast.declarations;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class MCAExpressionSingleDeclaration implements MCPSingleDeclaration {

	private LinkedList<String> identifiers;
	private MCPCMLExp expression;

	public MCAExpressionSingleDeclaration(LinkedList<String> identifiers,
			MCPCMLExp expression) {
		super();
		this.identifiers = identifiers;
		this.expression = expression;
	}


	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}


	public LinkedList<String> getIdentifiers() {
		return identifiers;
	}


	public void setIdentifiers(LinkedList<String> identifiers) {
		this.identifiers = identifiers;
	}


	public MCPCMLExp getExpression() {
		return expression;
	}


	public void setExpression(MCPCMLExp expression) {
		this.expression = expression;
	}

	
}
