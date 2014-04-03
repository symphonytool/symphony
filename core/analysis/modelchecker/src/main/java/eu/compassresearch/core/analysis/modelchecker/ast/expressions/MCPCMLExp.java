package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.PatternValue;

public interface MCPCMLExp extends MCNode{
	public MCPCMLExp copy();
	public void replacePatternWithValue(LinkedList<PatternValue> mapping);
}
