package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.PatternValue;

public class MCABracketedExp implements MCPCMLExp {

	private MCPCMLExp exp;
	
	
	public MCABracketedExp(MCPCMLExp exp) {
		super();
		this.exp = exp;
	}


	@Override
	public String toFormula(String option) {
		return getExp().toFormula(option);
	}


	public MCPCMLExp getExp() {
		return exp;
	}


	public void setExp(MCPCMLExp exp) {
		this.exp = exp;
	}


	@Override
	public MCPCMLExp copy() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void replacePatternWithValue(LinkedList<PatternValue> mapping) {
		this.exp.replacePatternWithValue(mapping);
	}
	
	

}
