package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public interface IExpressionEvaluator {

	public abstract boolean canEvaluate(MCPCMLExp expression);

	public abstract boolean evaluate(MCPCMLExp expression);

}