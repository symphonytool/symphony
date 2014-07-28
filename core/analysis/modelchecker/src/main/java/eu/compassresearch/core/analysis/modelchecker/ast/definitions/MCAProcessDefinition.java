package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.Iterator;
import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAReadCommunicationParameter;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ActionChannelDependency;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ExpressionEvaluator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ParameterDependency;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCAActionProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCPProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.ArrayListSet;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAProcessDefinition implements MCPCMLDefinition {

	private String name;
	private LinkedList<MCPParametrisation> localState = new LinkedList<MCPParametrisation>();
	private MCPProcess process;
	private String parameterString = "void";
	
	public MCAProcessDefinition(String name,
			LinkedList<MCPParametrisation> localState, MCPProcess process) {
		
		this.name = name;
		this.localState = localState;
		this.process = process;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof MCAProcessDefinition){
			result = this.name.equals(((MCAProcessDefinition) obj).getName())
					 && this.localState != null && ((MCAProcessDefinition)obj).getLocalState() != null
					 && this.localState.equals(((MCAProcessDefinition) obj).getLocalState());
		}
		return result;

	}

	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		String actionString = "";
		context.mcProcOrActionsStack.push(this);
		
		result.append("  ProcDef(\"");
		result.append(this.name);
		result.append("\",");
		
		// parameters
		ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance(); 
		MCPCMLType paramType  = evaluator.instantiateMCTypeFromParams(this.localState);
		parameterString = paramType.toFormula(option); 
		result.append(parameterString);
		result.append(",");
		actionString = this.process.toFormula(option);
		result.append(actionString);
		result.append(")");

		if(this.localState.size() > 0){
			ParameterDependency paramDep = new ParameterDependency(this.name,this.localState.getFirst(), this.name); 
			context.parameterDependencies.add(paramDep);
		}
		
		addDependencies(option, context, result, actionString);
		
		result.append(".\n");

		/*
		if(this.localState.size() > 0){
			ParameterDependency paramDep = new ParameterDependency(this.name,this.localState.getFirst()); 
			context.parameterDependencies.add(paramDep);
			
			
			TypeManipulator typeHandler = TypeManipulator.getInstance();
			//for the moment we assume that processes have only one parameter
			MCPCMLType paramType = //expEvaluator.instantiateMCType(this.getLocalState().getFirst());
			    ((MCAValParametrisation)this.getLocalState().getFirst()).getDeclaration().getType();
			LinkedList<TypeValue> values = typeHandler.getValues(paramType);
			String variableName = ((MCAValParametrisation)this.getLocalState().getFirst()).getDeclaration().getName(); 
			NameValue mapping = new NameValue(variableName,null,((MCAValParametrisation)this.getLocalState().getFirst()).getDeclaration().getType());
			for (TypeValue typeValue : values) {
				mapping.setVariableValue(typeValue.toFormula(option));
				context.localVariablesMapping.add(mapping);
				result.append("  ProcDef(\"");
				result.append(this.name);
				result.append("\",");
				result.append(typeValue.toFormula(option));
				result.append(",");
				result.append(this.process.toFormula(option));
				result.append(")");
				context.localVariablesMapping.remove(mapping);
				
				//if the action has dependencies we get them from the context
				LinkedList<ActionChannelDependency> dependencies = context.getActionChannelDependendies(this.name);
				if(dependencies.size() > 0){
					result.append(" :- ");
					
					if(dependencies.size() == 1 && context.getNumberOfInstances() == 1){
						for (Iterator<ActionChannelDependency> iterator = dependencies.iterator(); iterator.hasNext();) {
							ActionChannelDependency actionChannelDependency = (ActionChannelDependency) iterator.next();
							result.append(actionChannelDependency.toFormula(option));
							if(iterator.hasNext()){
								result.append(",");
							}
						}
					} else {
						ActionChannelDependency mainDependency = dependencies.getFirst();
						dependencies = context.getInfiniteActionChannelDependendiesByChannelName(mainDependency.getChannelName());
						for (Iterator<ActionChannelDependency> iterator = dependencies.iterator(); iterator.hasNext();) {
							ActionChannelDependency actionChannelDependency = (ActionChannelDependency) iterator.next();
							result.append(actionChannelDependency.toFormula(option));
							if(iterator.hasNext()){
								result.append(",");
							}
						}
						//it adds the combination of differences among instantiated values 
						if(values.size() > 1){
							LinkedList<String> expressions = new LinkedList<String>();
							generateCombinations(dependencies, "!=", expressions);
							for (String string : expressions) {
								result.append(", ");
								result.append(string);
							}
						}
					}
				}
				result.append(".\n");
			}
			
		} else {		
			result.append("  ProcDef(\"");
			result.append(this.name);
			result.append("\",");
			// parameters
			ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance(); 
			MCPCMLType paramType  = evaluator.instantiateMCTypeFromParams(this.localState);
			parameterString = paramType.toFormula(option); 
			result.append(parameterString);
			result.append(",");
			actionString = this.process.toFormula(option);
			//int i = 0;
			result.append(actionString);
			result.append(")");
			
			addDependencies(option, context, result, actionString);
			
			result.append(".\n");
		}
		*/
		//it adds the local definition of this process
		if(this.process instanceof MCAActionProcess){
			MCPCMLDefinition definition = ((MCAActionProcess) this.process).getDefinition();
			if(definition instanceof MCAActionClassDefinition){
				LinkedList<MCPCMLDefinition> localActions = ((MCAActionClassDefinition) definition).getDefinitions();
				for (MCPCMLDefinition mcpcmlDefinition : localActions) {
					if(mcpcmlDefinition instanceof MCAActionDefinition){
						result.append(mcpcmlDefinition.toFormula(option));
					}
				}
			}
		}
		result.append("\n");
		context.mcProcOrActionsStack.pop();
		
		return result.toString();
	}

private void addDependencies(String option,NewCMLModelcheckerContext context, StringBuilder result,String actionString) {
		
		//if the action has dependencies we get them from the context
		
		LinkedList<ActionChannelDependency> dependencies = context.getActionChannelDependendies(this.name);
		ArrayListSet<ActionChannelDependency> allDependencies = new ArrayListSet<ActionChannelDependency>(); 
		
		boolean hasNormalDependencies = dependencies.size() > 0;
		//it adds the normal channel dependencies
		if(hasNormalDependencies){
			for (Iterator<ActionChannelDependency> iterator = dependencies.iterator(); iterator.hasNext();) {
				ActionChannelDependency actionChannelDependency = (ActionChannelDependency) iterator.next();
					if(actionChannelDependency.getParameters().getFirst() instanceof MCAReadCommunicationParameter){
						allDependencies.add(actionChannelDependency);
					}
			}
		}
		
		//it adds the infinite and unamed channel dependencies and clear them at the end
		boolean hasInfiniteUnamedDependencies = context.getInfiniteAndUnamedChannelDependencies().size() > 0;

		if(hasInfiniteUnamedDependencies){
			allDependencies.addAll(context.getInfiniteAndUnamedChannelDependencies());
		}
			
		if(allDependencies.size() > 0){
			result.append(" :- ");
			for (Iterator<ActionChannelDependency> iterator = allDependencies.iterator(); iterator.hasNext();) {
				ActionChannelDependency actionChannelDependency = (ActionChannelDependency) iterator.next();
				String actionChannelDepStr = actionChannelDependency.toFormula(option);
				//if(result.indexOf(actionChannelDepStr) == -1){
				result.append(actionChannelDepStr);
				//}
				if(iterator.hasNext()){
					result.append(",");
				}
			}
		}
		
		//it adds the state dependencies if they exist
		boolean hasStateDependencies = context.actionProcStateDependencies.size() > 0; 
		if(hasStateDependencies){
			if(result.indexOf(":-") == -1 && hasStateDependencies){
				result.append(" :- ");
			}else{
				//if(hasNormalDependenciesReal || hasInfiniteUnamedDependenciesReal){
				if(allDependencies.size() > 0 && hasStateDependencies){
					result.append(",");
				}
				//}
			}
			result.append("State(");
			result.append(context.maximalBinding.toFormula(MCNode.STATE_DEPENDENCY_WITHOUT_INF_VARS));
			result.append(",");
			//result.append(actionString);
			result.append("proc(\"" + this.name + "\"," + parameterString + ")");
			result.append(")");
		}
		
		
		//it adds the parameter dependencies if they exist
		boolean hasParamDependencies = context.hasParameterDependencies(this.name); 
		if(hasParamDependencies){
			if(result.indexOf(":-") == -1){
				result.append(" :- ");
			}else{
				if((allDependencies.size() > 0 || hasStateDependencies) && hasParamDependencies){
					result.append(",");
				}
			}
			LinkedList<ParameterDependency> paramDependencies = context.getParameterDependenciesByParentName(this.name);
			
			ParameterDependency paramDep = context.getParameterDependency(this.name);
			if(paramDep != null){
				paramDependencies.add(paramDep);
			}
			Iterator<ParameterDependency> it = paramDependencies.iterator();
			while (it.hasNext()) {
				ParameterDependency parameterDependency = (ParameterDependency) it.next();
				String paramDepStr = parameterDependency.toFormula(option);
				//if(result.indexOf(paramDepStr) == -1){
				result.append(paramDepStr);
				//}
				if(it.hasNext()){
					result.append(",");
				}
				
			}
			//result.append(paramDep.toFormula(option));
		}
		context.resetStateDependencies();
		context.resetLocalVarNames();
		if(/*!hasNormalDependenciesReal && !hasInfiniteUnamedDependencies*/ allDependencies.size() == 0 && !hasStateDependencies && !hasParamDependencies){
			String separator = " :- ";
			int index = result.indexOf(separator);
			if(index != -1){
				result.replace(index, index + separator.length(), "");
			}
		}
		
	}

	
	@Override
	public String toString() {
		return this.name;
	}

	public LinkedList<MCPParametrisation> getLocalState() {
		return localState;
	}

	public void setLocalState(LinkedList<MCPParametrisation> localState) {
		this.localState = localState;
	}

	public MCPProcess getProcess() {
		return process;
	}



	public void setProcess(MCPProcess process) {
		this.process = process;
	}

	
}
