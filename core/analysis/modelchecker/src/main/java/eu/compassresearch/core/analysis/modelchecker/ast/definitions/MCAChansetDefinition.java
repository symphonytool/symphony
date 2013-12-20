package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPVarsetExpression;

public class MCAChansetDefinition implements MCPCMLDefinition {

	private String identifier;
	private MCPVarsetExpression chansetExpression;
	
	
	
	public MCAChansetDefinition(String identifier,
			MCPVarsetExpression chansetExpression) {
		
		this.identifier = identifier;
		this.chansetExpression = chansetExpression;
	}



	@Override
	public String toFormula(String option) {
		
		return this.chansetExpression.toFormula(option);
	}



	public String getIdentifier() {
		return identifier;
	}



	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}



	public MCPVarsetExpression getChansetExpression() {
		return chansetExpression;
	}



	public void setChansetExpression(MCPVarsetExpression chansetExpression) {
		this.chansetExpression = chansetExpression;
	}

	
}
