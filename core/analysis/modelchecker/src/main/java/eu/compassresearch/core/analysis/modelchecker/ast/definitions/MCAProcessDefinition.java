package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.Iterator;
import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAReadCommunicationParameter;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAValParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPCommunicationParameter;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ActionChannelDependency;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ExpressionEvaluator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.NameValue;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.TypeManipulator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.TypeValue;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCATypeSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCAActionProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCPProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAProcessDefinition implements MCPCMLDefinition {

	private String name;
	private LinkedList<MCPParametrisation> localState = new LinkedList<MCPParametrisation>();
	private MCPProcess process;
	
	
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
		if(this.localState.size() > 0){
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
			//parameters (local state)
			ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
			MCPCMLType decls =  evaluator.instantiateMCTypeFromParams(this.localState);
			result.append(decls.toFormula(MCNode.NAMED));
			result.append(",");
			actionString = this.process.toFormula(option);
			result.append(actionString);
			result.append(")");
			
			//if the action has dependencies we get them from the context
			LinkedList<ActionChannelDependency> dependencies = context.getActionChannelDependendies(this.name);
			
			boolean hasNormalDependencies = dependencies.size() > 0;
			//it adds the normal channel dependencies
			if(hasNormalDependencies){
				result.append(" :- ");
				
				for (Iterator<ActionChannelDependency> iterator = dependencies.iterator(); iterator.hasNext();) {
					ActionChannelDependency actionChannelDependency = (ActionChannelDependency) iterator.next();
					result.append(actionChannelDependency.toFormula(option));
					if(iterator.hasNext()){
						result.append(",");
					}
				}
			}
			
			//it adds the infinite and unamed channel dependencies and clear them at the end
			boolean hasInfiniteUnamedDependencies = context.getInfiniteAndUnamedChannelDependencies().size() > 0;
			if(hasInfiniteUnamedDependencies){
				if(!hasNormalDependencies){
					result.append(" :- ");
				}
					Iterator<ActionChannelDependency> iterator = context.getInfiniteAndUnamedChannelDependencies().iterator();
					for (; iterator.hasNext();) {
						ActionChannelDependency actionChannelDependency = (ActionChannelDependency) iterator.next();
						String actionChannelDepStr = actionChannelDependency.toFormula(option).trim();
						if(result.indexOf(actionChannelDepStr) == -1){
							if(hasNormalDependencies){
								result.append(",");
							}
							result.append(actionChannelDependency.toFormula(option));
						}
						
					}
					
				
			}
		
			context.resetInfiniteChannelDependencies();
			context.resetUnamedChannelDependencies();
			
			//it adds the state dependencies if they exist
			if(context.actionProcStateDependencies.size() > 0){
				if(!hasNormalDependencies && !hasInfiniteUnamedDependencies){
					result.append(" :- ");
				}else{
					result.append(",");
				}
				result.append("State(");
				result.append(context.maximalBinding.toFormula(MCNode.NAMED));
				result.append(",");
				result.append(actionString);
				result.append(")");
			}
			context.resetStateDependencies();
			result.append(".\n");
		}
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

	private void generateCombinations(LinkedList<ActionChannelDependency> chanDefList, String operator, LinkedList<String> result){
		
		if(chanDefList.size() == 2){
			String firstExpression = chanDefList.getFirst().getParameters().getFirst().toString();
			String secondExpression = chanDefList.getLast().getParameters().getFirst().toString();
			String expression = firstExpression + " " + operator + " " + secondExpression;
			result.add(expression);
		} else if (chanDefList.size() > 2){
			String firstExpression = chanDefList.pollFirst().getChannelDefinition().getType().getTypeAsName();
			for (ActionChannelDependency chanDef : chanDefList) {
				String secondExpression = chanDef.getChannelDefinition().getType().getTypeAsName();
				String expression = firstExpression + " " + operator + " " + secondExpression;
				result.add(expression);
			}
			generateCombinations(chanDefList,operator,result);
		}
		
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
