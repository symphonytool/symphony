package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.LinkedList;

import org.overture.ast.expressions.AQuoteLiteralExp;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.PatternValue;

public class MCAQuoteLiteralExp implements MCPCMLExp {

	private String value;
	
	public MCAQuoteLiteralExp(String value) {
		super();
		this.value = value;
	}

	@Override
	public String toFormula(String option) {
		return this.value;
	}

	@Override
	public MCPCMLExp copy() {
		
		return new MCAQuoteLiteralExp(this.value);
	}

	@Override
	public void replacePatternWithValue(LinkedList<PatternValue> mapping) {
		// TODO Auto-generated method stub

	}
	
	

	@Override
	public String toString() {
		return this.value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
}
