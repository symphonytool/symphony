package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCPCMLPattern;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public class MCOperationCall extends MCGenericCall{

	private LinkedList<MCPCMLPattern> paramPatterns;
	
	public MCOperationCall(String name, LinkedList<MCPCMLExp> args, LinkedList<MCPCMLPattern> paramPatterns) {
		super(name,args);
		this.paramPatterns = paramPatterns;
	}


	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		result.append("operation(\"" + this.name + "\"");
		result.append(",");
		ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
		MCPCMLType argsType = null;
		if (option.equals(MCNode.DEFAULT)){
			argsType = evaluator.instantiateMCType(this.args);
		} else if(option.equals(MCNode.NAMED) || option.equals(MCNode.GENERIC)){
			argsType = evaluator.instantiateMCTypeFromPatterns(this.paramPatterns);
		}
		result.append(argsType.toFormula(option));
		result.append(")");
		
		return result.toString();
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public LinkedList<MCPCMLExp> getArgs() {
		return args;
	}


	public void setArgs(LinkedList<MCPCMLExp> args) {
		this.args = args;
	}

	
}
