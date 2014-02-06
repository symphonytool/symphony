package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCAPatternListTypePair;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCPCMLPattern;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public class MCImplicitOperationCall extends MCGenericCall{

	private LinkedList<MCAPatternListTypePair> paramPatterns;
	
	public MCImplicitOperationCall(String name, LinkedList<MCPCMLExp> args, LinkedList<MCAPatternListTypePair> paramPatterns) {
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
			if(this.paramPatterns == null){
				argsType = evaluator.instantiateMCType(this.args);
			}else{
				//argsType = evaluator.instantiateMCTypeFromPatterns(this.paramPatterns);
			}
		} else{
			argsType = evaluator.instantiateMCType(this.args);
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
