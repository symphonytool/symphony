package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.util.Iterator;
import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPCommunicationParameter;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.StateDependency;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAActionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAProcessDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCPCMLDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCPCMLPattern;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCOperationCall extends MCGenericCall{

	private LinkedList<MCPCMLPattern> paramPatterns;
	
	public MCOperationCall(String name, LinkedList<MCPCMLExp> args, LinkedList<MCPCMLPattern> paramPatterns) {
		super(name,args);
		this.paramPatterns = paramPatterns;
	}


	@Override
	public String toFormula(String option) {
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
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
				argsType = evaluator.instantiateMCTypeFromPatterns(this.paramPatterns);
			}
		} else{
			argsType = evaluator.instantiateMCType(this.args);
		}
		result.append(argsType.toFormula(option));
		result.append(")");
		
		if(argsHasStateVariable(this.args)){
			//it checks if the argument is a varaiable with infinite domain
			String varname = this.args.getFirst().toFormula(MCNode.DEFAULT);
			if(!context.variablesInfiniteDomain.contains(varname)){
				if(!context.mcProcOrActionsStack.isEmpty()){
					MCPCMLDefinition actionOrProc = context.mcProcOrActionsStack.peek();
					StateDependency stateDependency = new StateDependency(null);
					String name = "";
					if(actionOrProc instanceof MCAProcessDefinition){
						name = ((MCAProcessDefinition) actionOrProc).getName().toString();
					} else if(actionOrProc instanceof MCAActionDefinition){
						name = ((MCAActionDefinition) actionOrProc).getName().toString();
					}
					stateDependency.setActionOrProcessName(name);
					context.actionProcStateDependencies.add(stateDependency);
				}
			}
		}
		
		
		return result.toString();
	}

	public boolean argsHasStateVariable(LinkedList<MCPCMLExp> argsNames){
		boolean result = false;
		if(argsNames != null){
			NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
			Iterator<MCPCMLExp> it = argsNames.iterator();
			while (it.hasNext() && !result) {
				MCPCMLExp param = (MCPCMLExp) it.next();
				String varName = param.toFormula(MCNode.DEFAULT);
				if(context.maximalBinding.containsVariable(varName)){
					result = true;
				}
			}
		}

		return result;
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
