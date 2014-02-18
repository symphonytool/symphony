package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.LinkedList;

import org.overture.ast.expressions.AIntLiteralExp;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.PatternValue;

public class MCAIntLiteralExp implements MCNumericExp {

	private String value;

	
	public MCAIntLiteralExp(String value) {
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
		if(obj instanceof MCAIntLiteralExp){
			result = this.value.equals(((MCAIntLiteralExp) obj).getValue());
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
		return new MCAIntLiteralExp(this.value);
	}


	@Override
	public void replacePatternWithValue(LinkedList<PatternValue> mapping) {
		
	}

	
}
