package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCPCMLPattern;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public class MCFunctionCall extends MCGenericCall{

	private LinkedList<MCPCMLPattern> paramPatterns;
	
	public MCFunctionCall(String name, LinkedList<MCPCMLExp> args, LinkedList<MCPCMLPattern> paramPatterns) {
		super(name,args);
		this.paramPatterns = paramPatterns;
	}


	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		result.append("operation(\"" + this.name + "\"");
		if(args != null){
			ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
			MCPCMLType argsType = null;
			if(option.equals(MCNode.DEFAULT)){
				argsType = evaluator.instantiateMCType(this.args);
			} else{
				argsType = evaluator.instantiateMCTypeFromPatterns(this.paramPatterns);
			}
			
			result.append(","+argsType.toFormula(option));
		
		/*
		switch (option) {
		case MCNode.DEFAULT:
			if(args.size()==0){
				result.append("void");
			} else if(args.size()==1){
				if(option.equals(MCNode.GENERIC)){
					result.append(paramPatterns.getFirst().toFormula(option));
				}else{
					result.append(args.getFirst().toFormula(option));
				}
			} else if(args.size() > 1){
				//TODO
			}
			break;

		case MCNode.NAMED:
			if(paramPatterns.size()==0){
				result.append("void");
			} else if(paramPatterns.size()==1){
				if(option.equals(MCNode.GENERIC)){
					result.append(paramPatterns.getFirst().toFormula(option));
				}else{
					result.append(paramPatterns.getFirst().toFormula(option));
				}
			} else if(args.size() > 1){
				//TODO
			}
			break;
			
		default:
			break;
		}
		*/
		}
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
