package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.PatternValue;

public class MCBooleanBinaryExp implements MCPCMLExp{

	private MCPCMLExp left;
	private MCPCMLExp right;
	
	
	public MCBooleanBinaryExp(MCPCMLExp left, MCPCMLExp right) {
		super();
		this.left = left;
		this.right = right;
	}


	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}


	public MCPCMLExp getLeft() {
		return left;
	}


	public void setLeft(MCPCMLExp left) {
		this.left = left;
	}


	public MCPCMLExp getRight() {
		return right;
	}


	public void setRight(MCPCMLExp right) {
		this.right = right;
	}


	@Override
	public MCPCMLExp copy() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void replacePatternWithValue(LinkedList<PatternValue> mapping) {
		this.left.replacePatternWithValue(mapping);
		this.right.replacePatternWithValue(mapping);
	}

	

}
