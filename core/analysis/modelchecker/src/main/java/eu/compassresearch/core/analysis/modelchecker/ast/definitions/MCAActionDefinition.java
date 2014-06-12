package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.Iterator;
import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAValParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.StateDependency;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ActionChannelDependency;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ExpressionEvaluator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.NameValue;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.TypeManipulator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.TypeValue;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAProductType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.ArrayListSet;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAActionDefinition implements MCPCMLDefinition {

	private String name;
	private LinkedList<MCPParametrisation> declarations = new LinkedList<MCPParametrisation>();
	private MCPAction action;

	
	public MCAActionDefinition(String name, LinkedList<MCPParametrisation> declarations,
			MCPAction action) {
		
		this.name = name;
		this.declarations = declarations;
		this.action = action;
	}

	

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof MCAActionDefinition){
			result = this.name.equals(((MCAActionDefinition) obj).getName())
					 && this.declarations.equals(((MCAActionDefinition) obj).getDeclarations());
		}
		return result;
	}



	@Override
	public String toFormula(String option) {
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		StringBuilder result = new StringBuilder();
		String actionString = "";
		context.mcProcOrActionsStack.push(this);
		if(declarations.size() > 0){
			
			 //for the moment we assume that processes have only one parameter
			MCPParametrisation param = declarations.getFirst();
			if(param instanceof MCAValParametrisation){
				MCALocalDefinition localDef = ((MCAValParametrisation) param).getDeclaration();
				NameValue mapping = new NameValue(localDef.getName(),null,localDef.getType());
				TypeManipulator typeHandler = TypeManipulator.getInstance();
				LinkedList<TypeValue> values = typeHandler.getValues(localDef.getType());
				
				for (TypeValue typeValue : values) {
					mapping.setVariableValue(typeValue.toFormula(option));
					context.localVariablesMapping.add(mapping);
					result.append("  ProcDef(\"");
					result.append(this.name);
					result.append("\",");
					result.append(typeValue.toFormula(option));
					result.append(",");
					actionString = this.getAction().toFormula(option);
					result.append(actionString);
					result.append(")");
					context.localVariablesMapping.remove(mapping);
					
					addDependencies(option, context, result, actionString);
					
					/*
					//if the action has dependencies we get them from the context
					LinkedList<ActionChannelDependency> dependencies = context.getActionChannelDependendies(this.name);
					if(NewCMLModelcheckerContext.hasInfiniteChannelInDependencies(dependencies)){
						result.append(" :- ");
						for (Iterator<ActionChannelDependency> iterator = dependencies.iterator(); iterator.hasNext();) {
							ActionChannelDependency actionChannelDependency = (ActionChannelDependency) iterator.next();
							if(actionChannelDependency.hasInfiniteTypedChannel()){
								result.append(",");
								result.append(actionChannelDependency.toFormula(option));
							}
						}
					}
					*/
					result.append(".\n");
				}
			}
			
			
			
			
		}else{
			result.append("  ProcDef(\"");
			result.append(this.name);
			result.append("\",");
			// parameters
			ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance(); 
			MCPCMLType paramType  = evaluator.instantiateMCTypeFromParams(this.declarations);
			result.append(paramType.toFormula(option));
			result.append(",");
			actionString = this.getAction().toFormula(option);
			result.append(actionString);
			result.append(")");
			
			addDependencies(option, context, result, actionString);
			
			result.append(".\n");
		}
		
		context.mcProcOrActionsStack.pop();
		return result.toString();
	}



	private void addDependencies(String option,NewCMLModelcheckerContext context, StringBuilder result,String actionString) {
		
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
	

	@Override
	public String toString() {
		
		return this.name;
	}



	public LinkedList<MCPParametrisation> getDeclarations() {
		return declarations;
	}


	public void setDeclarations(LinkedList<MCPParametrisation> declarations) {
		this.declarations = declarations;
	}


	public MCPAction getAction() {
		return action;
	}


	public void setAction(MCPAction action) {
		this.action = action;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	
}
