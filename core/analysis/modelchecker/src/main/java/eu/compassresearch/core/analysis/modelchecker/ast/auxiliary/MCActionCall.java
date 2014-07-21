package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAActionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAProcessDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAVariableExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.statements.MCAAssignmentStm;
import eu.compassresearch.core.analysis.modelchecker.ast.statements.MCAUnresolvedStateDesignator;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCActionCall extends MCGenericCall {

	public MCActionCall(String name, LinkedList<MCPCMLExp> args) {
		super(name,args);
	}


	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		
		//if the process has parameters, we must produce a sequential composition 
		//where the first process is an assignment of all variables in the bindings before calling
		//the target process.

		//we must get first the process definition in the context
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		
		//the parameter list
		LinkedList<MCPParametrisation> parameters = null;
		
		MCAActionDefinition actionDef = context.getActionByName(this.name);
		//Action calls can also refer to process calls
		if(actionDef != null){
			parameters = actionDef.getDeclarations();
		} else {
			MCAProcessDefinition procDef = context.getProcessByName(this.name);
			if(procDef != null){
				parameters = procDef.getLocalState();
			}
		}
		
		
		
		if(parameters.size() == 0){
			result.append("proc(\"" + this.name + "\"");
			if(args != null){
				ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
				MCPCMLType argsType = evaluator.instantiateMCType(this.args); 
				result.append(","+argsType.toFormula(option));
			}
			result.append(")");
		} else{
			
			//StringBuilder assignments = buildAssignments(parameters,args,context, option);
			
			//result.append("seqC(");
			//result.append(assignments.toString());
			//result.append(",proc(\"" + this.name + "\"");
			result.append("proc(\"" + this.name + "\"");
			result.append(",");
			ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
			MCPCMLType argsType = null;
			//if (option.equals(MCNode.DEFAULT)){
			argsType = evaluator.instantiateMCTypeFromParams(parameters);
			//argsType = evaluator.instantiateMCType(this.args);
			//} else if(option.equals(MCNode.NAMED) || option.equals(MCNode.GENERIC)){
			//	argsType = evaluator.instantiateMCTypeFromPatterns(this.paramPatterns);
			//}
			result.append(argsType.toFormula(option));
			result.append(")");
			//result.append(")");
			
		}
		return result.toString();
	}

	
	
}
