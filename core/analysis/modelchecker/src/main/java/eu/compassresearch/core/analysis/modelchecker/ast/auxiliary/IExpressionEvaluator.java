package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public interface IExpressionEvaluator {

	public abstract boolean canEvaluate(MCPCMLExp expression);

	public abstract boolean evaluate(MCPCMLExp expression);
	
	public abstract String obtainValue(MCPCMLExp expression);

	public abstract MCPCMLType instantiateMCType(LinkedList<MCPCMLExp> exps);
}