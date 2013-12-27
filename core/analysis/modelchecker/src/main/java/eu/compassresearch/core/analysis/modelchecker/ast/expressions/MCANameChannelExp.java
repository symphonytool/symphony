package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.PatternValue;

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

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof MCANameChannelExp){
			result = this.identifier.equals(((MCANameChannelExp) obj).getIdentifier());
		}
		return result;
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


	@Override
	public MCPCMLExp copy() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void replacePatternWithValue(LinkedList<PatternValue> mapping) {
		// TODO Auto-generated method stub
		
	}

	
}
