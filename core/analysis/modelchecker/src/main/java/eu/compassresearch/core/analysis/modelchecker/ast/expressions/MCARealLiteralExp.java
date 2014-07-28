package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.PatternValue;

public class MCARealLiteralExp implements MCNumericExp {

	private String value;

	
	public MCARealLiteralExp(String value) {
		super();
		this.value = value;
	}


	@Override
	public String toFormula(String option) {
		return this.value;
	}

	

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof MCARealLiteralExp){
			result = this.value.equals(((MCARealLiteralExp) obj).getValue());
		}
		return result;
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


	@Override
	public MCPCMLExp copy() {
		return new MCARealLiteralExp(this.value);
	}


	@Override
	public void replacePatternWithValue(LinkedList<PatternValue> mapping) {
		
	}

	
}
