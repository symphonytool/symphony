package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;

public interface MCPVarsetExpression extends MCNode {
	public LinkedList<MCANameChannelExp> getChannelNames();
}
