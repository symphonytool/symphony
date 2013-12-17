package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.LinkedList;

import org.overture.ast.expressions.AUnaryMinusUnaryExp;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.PatternValue;

public class MCAUnaryMinusUnaryExp implements MCPCMLExp {

	private MCPCMLExp exp; 
	
	
	public MCAUnaryMinusUnaryExp(MCPCMLExp exp) {
		super();
		this.exp = exp;
	}

	@Override
	public String toFormula(String option) {
		return "-" + this.exp.toFormula(option);
	}

	@Override
	public MCPCMLExp copy() {
		
		return new MCAUnaryMinusUnaryExp(this.exp.copy());
	}

	@Override
	public void replacePatternWithValue(LinkedList<PatternValue> mapping) {
		this.exp.replacePatternWithValue(mapping);

	}

	public MCPCMLExp getExp() {
		return exp;
	}

	public void setExp(MCPCMLExp exp) {
		this.exp = exp;
	}

	
}
