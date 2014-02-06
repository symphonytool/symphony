package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.PatternValue;

public class MCABooleanConstExp implements MCBooleanExp {

	private boolean value;
	
	public MCABooleanConstExp(boolean value) {
		this.value = value;
	}

	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	@Override
	public MCPCMLExp copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void replacePatternWithValue(LinkedList<PatternValue> mapping) {
		
	}
	
	

}
