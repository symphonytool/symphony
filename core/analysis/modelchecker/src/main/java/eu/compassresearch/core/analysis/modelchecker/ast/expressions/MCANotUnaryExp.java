package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.PatternValue;


public class MCANotUnaryExp implements MCBooleanExp {

	private MCPCMLExp exp;

	
	public MCANotUnaryExp(MCPCMLExp exp) {
		super();
		this.exp = exp;
	}


	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public MCPCMLExp copy() {
		// TODO Auto-generated method stub
		return null;
	}


	public MCPCMLExp getExp() {
		return exp;
	}


	public void setExp(MCPCMLExp exp) {
		this.exp = exp;
	}


	@Override
	public void replacePatternWithValue(LinkedList<PatternValue> mapping) {
		this.exp.replacePatternWithValue(mapping);
	}

	
	
}
