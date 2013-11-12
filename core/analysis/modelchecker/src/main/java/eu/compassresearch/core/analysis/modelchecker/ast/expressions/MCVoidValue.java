package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.PatternValue;

public class MCVoidValue implements MCPCMLExp {

	@Override
	public String toFormula(String option) {
		return "void";
	}

	@Override
	public MCPCMLExp copy() {
		return new MCVoidValue();
	}

	@Override
	public void replacePatternWithValue(LinkedList<PatternValue> mapping) {
		
	}

}
