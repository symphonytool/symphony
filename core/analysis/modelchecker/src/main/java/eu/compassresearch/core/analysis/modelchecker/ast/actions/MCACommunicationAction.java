package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import java.util.Iterator;
import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ActionChannelDependency;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ExpressionEvaluator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCCommEv;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCLieInFact;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.NameValue;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.SingleTypeValue;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.TypeManipulator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.TypeValue;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAActionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAChannelDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAProcessDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCPCMLDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCANameChannelExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAVariableExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewSetStack;

public class MCACommunicationAction implements MCPAction {

	private int counterId;
	private String identifier;
	private LinkedList<MCPCommunicationParameter> communicationParameters = new LinkedList<MCPCommunicationParameter>();
	private MCPAction action;
	
	public MCACommunicationAction(String identifier,
			LinkedList<MCPCommunicationParameter> communicationParameters,
			MCPAction action) {
		this.counterId = NewCMLModelcheckerContext.IOCOMM_COUNTER++;
		this.identifier = identifier;
		this.communicationParameters = communicationParameters;
		this.action = action;
	}
	
	private MCACommunicationAction(int counterId, String identifier,
			LinkedList<MCPCommunicationParameter> communicationParameters,
			MCPAction action) {
		this.counterId = counterId;
		this.identifier = identifier;
		this.communicationParameters = communicationParameters;
		this.action = action;
	}

	public MCACommunicationAction copy(){
		MCACommunicationAction result = new MCACommunicationAction(this.counterId, this.identifier, (LinkedList<MCPCommunicationParameter>)communicationParameters.clone(), this.action);
		
		return result;
	}

	public void setParameter(String name, MCPCMLExp expValue){
		for (MCPCommunicationParameter param : communicationParameters) {
			if(param instanceof MCAReadCommunicationParameter){
				if(((MCAReadCommunicationParameter) param).getPattern().toFormula(MCNode.DEFAULT).equals(name)){
					param.setExpression(expValue);
				}
			} else if (param instanceof MCAWriteCommunicationParameter){
				
				if(((MCAWriteCommunicationParameter) param).getExpression() instanceof MCAVariableExp){
					MCAVariableExp paramExp = (MCAVariableExp) ((MCAWriteCommunicationParameter)param).getExpression();
					if(paramExp.getName().equals(name)){
						paramExp.setName(expValue.toFormula(MCNode.DEFAULT));
					}
				}
			} else if (param instanceof MCASignalCommunicationParameter){
				if(((MCPCommunicationParameter) param).getExpression() instanceof MCAVariableExp){
					MCAVariableExp paramExp = (MCAVariableExp) ((MCPCommunicationParameter)param).getExpression();
					if(paramExp.getName().equals(name)){
						paramExp.setName(expValue.toFormula(MCNode.DEFAULT));
					}
				}
			}
		}
	}


	@Override
	public String toFormula(String option) {
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		StringBuilder result = new StringBuilder();

		//if the action parameter has more than one value we must generate an external choice
		//replicating this action as much as possible
		MCAChannelDefinition chanDef = context.getChannelDefinition(this.identifier);
				
		ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
		MCPCMLType paramType = evaluator.instantiateMCTypeFromCommParams(this.communicationParameters);
		TypeManipulator typeHandler =  TypeManipulator.getInstance();
		//this method must return generic values in variables when the type is infinite 
		//and according to the number of instances that FORMULA must instantiate
		LinkedList<MCPCommunicationParameter> allParamsCopy = new LinkedList<MCPCommunicationParameter>();
		allParamsCopy.addAll(this.communicationParameters);
		
		LinkedList<TypeValue> values = typeHandler.getValues(paramType);
		LinkedList<TypeValue> valuesCopy = new LinkedList<TypeValue>();
		valuesCopy.addAll(values);
		
		if(chanDef.isInfiniteType()){
			if(context.getNumberOfInstances() == 1){
				
				result.append(buildReplicatedExternalChoice(context, option));
				if(!parameterIsConstantValue()){
					String actionNameToUse = ""; 
					LinkedList<MCPCommunicationParameter> paramsCopy = new LinkedList<MCPCommunicationParameter>();
					if(this.getCommunicationParameters().getFirst() instanceof MCAReadCommunicationParameter){
						MCACommunicationAction originalParentAction =  ((MCAReadCommunicationParameter) this.getCommunicationParameters().getFirst()).getParentAction();
						String paramName = ((MCAReadCommunicationParameter) this.getCommunicationParameters().getFirst()).getPattern().toFormula(MCNode.DEFAULT);
						MCAReadCommunicationParameter newParam = 
									new MCAReadCommunicationParameter(new MCAVariableExp(paramName), ((MCAReadCommunicationParameter) this.getCommunicationParameters().getFirst()).getPattern());
						//}
						newParam.setParentAction(originalParentAction);
						paramsCopy.add(newParam);
						ActionChannelDependency actionChanDep = new ActionChannelDependency(actionNameToUse, this.identifier, paramsCopy);
						context.infiniteChannelDependencies.add(actionChanDep);
					}
				}
			}else{
				for (TypeValue typeValue : valuesCopy) {
					if(!parameterIsConstantValue()){
						String actionNameToUse = ""; 
						LinkedList<MCPCommunicationParameter> paramsCopy = new LinkedList<MCPCommunicationParameter>();
						//for (MCPCommunicationParameter mcpCommunicationParameter : this.communicationParameters) {
						MCACommunicationAction originalParentAction =  ((MCAReadCommunicationParameter) this.getCommunicationParameters().getFirst()).getParentAction();
						MCAReadCommunicationParameter newParam = 
									new MCAReadCommunicationParameter(new MCAVariableExp(typeValue.getTypeAsName()), ((MCAReadCommunicationParameter) this.getCommunicationParameters().getFirst()).getPattern());
						//}
						newParam.setParentAction(originalParentAction);
						paramsCopy.add(newParam);
						ActionChannelDependency actionChanDep = new ActionChannelDependency(actionNameToUse, this.identifier, paramsCopy);
						context.infiniteChannelDependencies.add(actionChanDep);
					}
				}
				result.append(buildReplicatedExternalChoice(context, values, option, allParamsCopy,true));
			}
		} else{
			result.append(buildReplicatedExternalChoice(context, values, option, allParamsCopy,false));
		}
		
		
		
		
		return result.toString();
	}

	private boolean parameterIsConstantValue(){
		boolean result = false;
		if(!this.communicationParameters.isEmpty()){
			MCPCommunicationParameter param = this.communicationParameters.getFirst();
			if(param.getExpression() != null){
				if(! (param.getExpression() instanceof MCAVariableExp)){
					result = true;
				}
			}
		}
		
		return result;
	}
	private void generateCombinations(LinkedList<MCAChannelDefinition> chanDefList, String operator, LinkedList<String> result){
		
		if(chanDefList.size() == 2){
			String firstExpression = chanDefList.getFirst().getType().getTypeAsName();
			String secondExpression = chanDefList.getLast().getType().getTypeAsName();
			String expression = firstExpression + " " + operator + " " + secondExpression;
			result.add(expression);
		} else if (chanDefList.size() > 2){
			String firstExpression = chanDefList.pollFirst().getType().getTypeAsName();
			for (MCAChannelDefinition chanDef : chanDefList) {
				String secondExpression = chanDef.getType().getTypeAsName();
				String expression = firstExpression + " " + operator + " " + secondExpression;
				result.add(expression);
			}
			generateCombinations(chanDefList,operator,result);
		}
		
	}
	private String buildPrefix(String option, NewCMLModelcheckerContext context, TypeValue currParam) {
		StringBuilder result = new StringBuilder();
		
		result.append("Prefix(IOComm(" + this.counterId + ",");
		result.append("\"" + this.identifier + "\"");
		result.append(",");
		result.append("\"" + buildIOCommExp(option) + "\"");
		result.append(",");
		if(currParam != null){
			if(parametersHasStateVariable(this.communicationParameters)){
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
			result.append(currParam.toFormula(option));
		} else{
			result.append(buildIOCommActualParams(option));
		}
		result.append(")"); //closes IOComm
		result.append(",");
		if(option.equals(MCNode.MINIMAL_GENERIC) || option.equals(MCNode.STATE_DEFAULT_PROCESS_NAMED)){
			result.append("_");
		}else{
			result.append(this.action.toFormula(option));
			
		}
		
		result.append(")"); //closes Prefix
		//if there is some set of event in the context we must generate lieIn events.
		NewSetStack<MCPVarsetExpression> chanSetStack = context.setStack.copy();
		chanSetStack.addAll(context.globalChanSets);
		while(!chanSetStack.isEmpty()){
			MCPVarsetExpression setExp = (MCPVarsetExpression)chanSetStack.pop();
			LinkedList<MCANameChannelExp> chanNames = setExp.getChannelNames();
			if(chanNames != null){
				boolean generateLieIn = false;
				for (MCANameChannelExp aNameChannelExp : chanNames) {
					if(aNameChannelExp.getIdentifier().toString().equals(this.identifier.toString())){
						//generateLieIn = true;
						ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
						MCPCMLType value = evaluator.instantiateMCTypeFromCommParams(this.communicationParameters);
						
						MCCommEv commEv = new MCCommEv(this.identifier,this.communicationParameters, value);
						MCLieInFact lieIn = new MCLieInFact(commEv,setExp); 
						if(!context.lieIn.contains(lieIn)){
							context.lieIn.add(lieIn);
						}
						//break;
					}
				}
				//if(!generateLieIn && chanSetStack.size()==0){
				//	break;
			} /*else{
					if (chanNames.size() > 0){
						ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
						MCPCMLType value = evaluator.instantiateMCTypeFromCommParams(this.communicationParameters);
						
						MCCommEv commEv = new MCCommEv(this.identifier,this.communicationParameters, value);
						MCLieInFact lieIn = new MCLieInFact(commEv,setExp); 
						if(!context.lieIn.contains(lieIn)){
							context.lieIn.add(lieIn);
						}
					}
				}
			}*/
			
		}
		
		for (MCPVarsetExpression setExp : context.globalChanSets) {
			LinkedList<MCANameChannelExp> chanNames = setExp.getChannelNames();
			if(chanNames != null){
				boolean generateLieIn = false;
				for (MCANameChannelExp aNameChannelExp : chanNames) {
					if(aNameChannelExp.getIdentifier().toString().equals(this.identifier.toString())){
						generateLieIn = true;
						break;
					}
				}
				if(!generateLieIn && chanSetStack.size()==0){
					break;
				}else{
					ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
					MCPCMLType value = evaluator.instantiateMCTypeFromCommParams(this.communicationParameters);
					
					MCCommEv commEv = new MCCommEv(this.identifier,this.communicationParameters, value);
					MCLieInFact lieIn = new MCLieInFact(commEv,setExp); 
					if(!context.lieIn.contains(lieIn)){
						context.lieIn.add(lieIn);
					}
				}
			}
		}
		return result.toString();
	}

	private StringBuilder buildReplicatedExternalChoice(NewCMLModelcheckerContext context,
			LinkedList<TypeValue> values,String option, LinkedList<MCPCommunicationParameter> params, boolean infinite) {

		StringBuilder result = new StringBuilder();
		String identifier = null;
		MCPCMLType identifierType = null;
		
		
		NameValue mapping = new NameValue(identifier,null,identifierType);
		
		if(option.equals(MCNode.STATE_DEFAULT_PROCESS_NAMED)){
			result.append(this.buildPrefix(option, context, null));
		}else{
			
			if(values.size() == 0){
				result.append(this.buildPrefix(option, context, null));
			} else if(values.size() == 1){
				TypeValue firstValue = values.removeFirst();
				MCPCommunicationParameter firstParam = params.getFirst();
				if(!infinite){
					try {
						Integer.parseInt(((SingleTypeValue)firstValue).getValue());
					} catch (NumberFormatException e) {
						((SingleTypeValue)firstValue).setValue(firstParam.getExpression().toFormula(MCNode.DEFAULT));
					}
				}
				mapping.setVariableName(firstParam.toString());
				identifier = firstParam.toString();
				mapping.setVariableValue(firstValue.toFormula(option));
				context.localIndexedVariablesMapping.push(mapping);
				result.append(this.buildPrefix(option, context, firstValue));
				context.localIndexedVariablesMapping.pop();
				context.localIndexedVariablesDiscarded.add(identifier);
			}else if (values.size() > 1) {
				TypeValue firstValue = values.removeFirst();
				MCPCommunicationParameter firstParam = params.getFirst(); 
				mapping.setVariableName(firstParam.toFormula(MCNode.DEFAULT));
				mapping.setVariableValue(firstValue.toFormula(option));
				context.localIndexedVariablesMapping.push(mapping);
				result.append("eChoice(");
				result.append(this.buildPrefix(option, context, firstValue));
				result.append(",");
				context.localIndexedVariablesMapping.pop();
				
				StringBuilder rest = buildReplicatedExternalChoice(context,values,option,params,infinite);
				result.append(rest.toString());
				result.append(")");
			}
		}
		return result;
	}
	
	private StringBuilder buildReplicatedExternalChoice(NewCMLModelcheckerContext context,String option) {

		StringBuilder result = new StringBuilder();

		if(option.equals(MCNode.STATE_DEFAULT_PROCESS_NAMED)){
			result.append(this.buildPrefix(option, context, null));
		}else{
			result.append(this.buildPrefix(option, context, null));
		}
		return result;
	}
	
	private String buildIOCommExp(String option){
		StringBuilder result = new StringBuilder();
		result.append("");
		
		for (MCPCommunicationParameter param : this.communicationParameters) {
			result.append(param.toFormula(option));
		}
		
		return result.toString();
	}

	
	 public String buildIOCommActualParams(String option){
	 
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		StringBuilder result = new StringBuilder();
		ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
	
		if(option.equals(MCNode.MINIMAL_GENERIC) || option.equals(MCNode.STATE_DEFAULT_PROCESS_NAMED)){
			MCPCMLType type = evaluator.instantiateMCTypeFromCommParamsForIOCommDef(this.communicationParameters,"");
			result.append(type.toFormula(option));
		}else{
			MCPCMLType type = evaluator.instantiateMCTypeFromCommParams(this.communicationParameters);
			result.append(type.toFormula(option));
			
			if(parametersHasStateVariable(this.communicationParameters)){
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
		return result.toString();
	}
	
	public boolean parametersHasStateVariable(LinkedList<MCPCommunicationParameter> parameters){
		//it works only for one parameter
		boolean result = false;
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		Iterator<MCPCommunicationParameter> it = parameters.iterator();
		
		while (it.hasNext() && !result) {
			MCPCommunicationParameter param = (MCPCommunicationParameter) it.next();
			if(!(param instanceof MCAReadCommunicationParameter)){
				String varName = param.getExpression().toFormula(MCNode.DEFAULT);
				if(context.maximalBinding.containsVariable(varName)){
					result = true;
				}
			}else{
				break;
			}
		}

		return result;
	}
	
	
	public String getIdentifier() {
		return identifier;
	}



	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}



	public LinkedList<MCPCommunicationParameter> getCommunicationParameters() {
		return communicationParameters;
	}



	public void setCommunicationParameters(
			LinkedList<MCPCommunicationParameter> communicationParameters) {
		this.communicationParameters = communicationParameters;
	}



	public MCPAction getAction() {
		return action;
	}



	public void setAction(MCPAction action) {
		this.action = action;
	}



	public int getCounterId() {
		return counterId;
	}



	public void setCounterId(int counterId) {
		this.counterId = counterId;
	}

	
	
}
