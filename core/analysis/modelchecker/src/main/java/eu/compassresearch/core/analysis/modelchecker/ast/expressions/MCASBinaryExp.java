package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.PatternValue;

public abstract class MCASBinaryExp implements MCPCMLExp {

	private MCPCMLExp left;
	private MCPCMLExp right;

	
	public MCASBinaryExp(MCPCMLExp left, MCPCMLExp right) {
		super();
		this.left = left;
		this.right = right;
	}


	@Override
	public void replacePatternWithValue(LinkedList<PatternValue> mapping) {
		this.left.replacePatternWithValue(mapping);
		this.right.replacePatternWithValue(mapping);
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

	
}
