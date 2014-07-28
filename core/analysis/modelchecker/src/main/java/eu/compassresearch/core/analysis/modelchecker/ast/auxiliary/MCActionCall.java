package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAActionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAProcessDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAVariableExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCPCMLPattern;
import eu.compassresearch.core.analysis.modelchecker.ast.statements.MCAAssignmentStm;
import eu.compassresearch.core.analysis.modelchecker.ast.statements.MCAUnresolvedStateDesignator;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCVoidType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCActionCall extends MCGenericCall {

	private LinkedList<MCPCMLPattern> paramPatterns;
	
	public MCActionCall(String name, LinkedList<MCPCMLExp> args, LinkedList<MCPCMLPattern> paramPatterns) {
		super(name,args);
		this.paramPatterns = paramPatterns;
	}


	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		
		//if the process has parameters, we must produce a sequential composition 
		//where the first process is an assignment of all variables in the bindings before calling
		//the target process.

		//we must get first the process definition in the context
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		String nameToSearch = context.reverseNameMapping.get(this.name);
		//MCAActionDefinition actionDef = context.getActionByName(nameToSearch);
		MCAActionDefinition actionDef = context.getActionByName(this.name);
		
		//the parameter list
		LinkedList<MCPParametrisation> parameters = actionDef.getDeclarations();
		
		//if the argument is a global value, a state variable, a communication variable
		ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
		//MCPCMLType type = evaluator.getTypeFor(this.args.getFirst());
		
		
		//MCAActionDefinition actionDef = context.getActionByName(this.name);
		//Action calls cannot refer to process calls
		//if(actionDef != null){
		//	parameters = actionDef.getDeclarations();
		//} //else {
			//MCAProcessDefinition procDef = context.getProcessByName(this.name);
			//if(procDef != null){
			//	parameters = procDef.getLocalState();
			//}
		//}
		
		
		
		if(parameters.size() == 0){
			result.append("proc(\"" + this.name + "\"");
			result.append(",");
			MCPCMLType argsType = new MCVoidType();
			result.append(argsType.toFormula(option));
			result.append(")");
			/*
			result.append("proc(\"" + this.name + "\"");
			if(args != null){
				MCPCMLType argsType = evaluator.instantiateMCType(this.args); 
				result.append(","+argsType.toFormula(option));
			}
			result.append(")");
			*/
		} else{
			result.append("proc(\"" + this.name + "\"");
			result.append(",");
			MCPCMLType argsType = null;
			if (option.equals(MCNode.DEFAULT)){
				
				argsType = evaluator.instantiateMCType(args.getFirst()); 
				//argsType = evaluator.instantiateMCTypeFromParams(parameters);
				//argsType = evaluator.instantiateMCTypeFromPatterns(this.paramPatterns);
			} else if(option.equals(MCNode.NAMED) || option.equals(MCNode.GENERIC)){
				argsType = evaluator.instantiateMCTypeFromPatterns(this.paramPatterns);
			}
			result.append(argsType.toFormula(option));
			result.append(")");
			
			/*
			StringBuilder assignments = buildAssignments(parameters,args,context, option);
			result.append("proc(\"" + this.name + "\"");
			result.append(",");
			MCPCMLType argsType = null;
			argsType = evaluator.instantiateMCTypeFromParams(parameters);
			result.append(argsType.toFormula(option));
			result.append(")");
			*/
			
		}
		return result.toString();
	}


	public LinkedList<MCPCMLPattern> getParamPatterns() {
		return paramPatterns;
	}


	public void setParamPatterns(LinkedList<MCPCMLPattern> paramPatterns) {
		this.paramPatterns = paramPatterns;
	}

	
	
	
}
