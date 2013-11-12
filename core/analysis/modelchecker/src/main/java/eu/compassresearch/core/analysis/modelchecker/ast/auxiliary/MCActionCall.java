package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public class MCActionCall extends MCGenericCall {

	public MCActionCall(String name, LinkedList<MCPCMLExp> args) {
		super(name,args);
	}


	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		result.append("proc(\"" + this.name + "\"");
		if(args != null){
			ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
			MCPCMLType argsType = evaluator.instantiateMCType(this.args); 
			result.append(","+argsType.toFormula(option));
		}
		result.append(")");
		
		return result.toString();
	}

	
}
