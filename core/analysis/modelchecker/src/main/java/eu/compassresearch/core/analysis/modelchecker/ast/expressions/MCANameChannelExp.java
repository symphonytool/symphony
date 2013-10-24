package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.LinkedList;

public class MCANameChannelExp implements MCSChannelExp {

	private String identifier;
	private LinkedList<MCPCMLExp> expressions = new LinkedList<MCPCMLExp>();

	
	public MCANameChannelExp(String identifier,
			LinkedList<MCPCMLExp> expressions) {
		super();
		this.identifier = identifier;
		this.expressions = expressions;
	}


	@Override
	public String toFormula(String option) {
		return this.identifier;
	}


	public String getIdentifier() {
		return identifier;
	}


	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}


	public LinkedList<MCPCMLExp> getExpressions() {
		return expressions;
	}


	public void setExpressions(LinkedList<MCPCMLExp> expressions) {
		this.expressions = expressions;
	}

	
}
