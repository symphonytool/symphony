package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ActionChannelDependency;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ExpressionEvaluator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCCommEv;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCLieInFact;
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
			String infiniteVarName = this.communicationParameters.getFirst().toString();
			context.variablesInfiniteDomain.add(infiniteVarName);
			result.append(buildPrefix(option, context, true));
			/*
			if(!parameterIsConstantValue()){
				String actionNameToUse = ""; 
				LinkedList<MCPCommunicationParameter> paramsCopy = new LinkedList<MCPCommunicationParameter>();
				if(this.getCommunicationParameters().getFirst() instanceof MCAReadCommunicationParameter){
					MCACommunicationAction originalParentAction =  ((MCAReadCommunicationParameter) this.getCommunicationParameters().getFirst()).getParentAction();
					String paramName = ((MCAReadCommunicationParameter) this.getCommunicationParameters().getFirst()).getPattern().toFormula(MCNode.DEFAULT);
					//MCAReadCommunicationParameter newParam = 
								new MCAReadCommunicationParameter(new MCAVariableExp(paramName), ((MCAReadCommunicationParameter) this.getCommunicationParameters().getFirst()).getPattern());
					//}
					//newParam.setParentAction(originalParentAction);
					paramsCopy.add(this.getCommunicationParameters().getFirst());
					ActionChannelDependency actionChanDep = new ActionChannelDependency(actionNameToUse, this.identifier, paramsCopy);
					context.infiniteChannelDependencies.add(actionChanDep);
				}
			}
			*/
			context.variablesInfiniteDomain.remove(infiniteVarName);
		} else{
			//int i = 0;
			//result.append(buildReplicatedExternalChoice(context, values, option, allParamsCopy,false));
			
			result.append(buildPrefix(option, context, false));
			
		}
		if(this.communicationParameters.size() > 0){
			if(this.communicationParameters.getFirst() instanceof MCAReadCommunicationParameter){
				String localVarName = this.communicationParameters.getFirst().toString();
				context.localInputVariables.add(localVarName);
			}
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
	private String buildPrefix(String option, NewCMLModelcheckerContext context, boolean infinite) {
		StringBuilder result = new StringBuilder();
		
		result.append("Prefix(IOComm(" + this.counterId + ",");
		result.append("\"" + this.identifier + "\"");
		result.append(",");
		result.append("\"" + buildIOCommExp(option) + "\"");
		result.append(",");
		result.append(buildIOCommActualParams(option));
		//if(currParam != null){
		if(this.communicationParameters.size() > 0){
			if(parametersHasStateVariable(this.communicationParameters)){
					MCPCommunicationParameter param = this.communicationParameters.getFirst();
					if(!(param instanceof MCAReadCommunicationParameter)){
						StateDependency stateDependency = new StateDependency(null);
						String name = "";
						if(!context.mcProcOrActionsStack.isEmpty()){
							MCPCMLDefinition actionOrProc = context.mcProcOrActionsStack.peek();
							if(actionOrProc instanceof MCAProcessDefinition){
								name = ((MCAProcessDefinition) actionOrProc).getName().toString();
							} else if(actionOrProc instanceof MCAActionDefinition){
								name = ((MCAActionDefinition) actionOrProc).getName().toString();
							}
						}
						stateDependency.setActionOrProcessName(name);
						//if(infinite){
							context.actionProcStateDependencies.add(stateDependency);
						//}
					}else{
						//if(!parameterIsConstantValue()){
							String actionNameToUse = ""; 
							LinkedList<MCPCommunicationParameter> paramsCopy = new LinkedList<MCPCommunicationParameter>();
							//if(param instanceof MCAReadCommunicationParameter){
							paramsCopy.add(param);
							ActionChannelDependency actionChanDep = new ActionChannelDependency(actionNameToUse, this.identifier, paramsCopy);
							context.unamedChannelDependencies.add(actionChanDep);
							//}
						//}
					}
					
					
					
				}
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
				
			} 
			
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
			
		}
		return result.toString();
	}
	
	public boolean parametersHasStateVariable(LinkedList<MCPCommunicationParameter> parameters){
		//it works only for one parameter
		boolean result = false;
		
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		MCPCommunicationParameter param = parameters.getFirst();
		String varName = param.toString();
		//System.out.println("VARNAME: " + varName + " CONTAINS: " + context.maximalBinding.containsVariable(varName));
		if(context.maximalBinding.containsVariable(varName)){
			result = true;
			
		}
		
		/*
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		Iterator<MCPCommunicationParameter> it = parameters.iterator();
		
		while (it.hasNext() && !result) {
			MCPCommunicationParameter param = (MCPCommunicationParameter) it.next();
			//if(!(param instanceof MCAReadCommunicationParameter)){
				String varName = param.toString();
				if(context.maximalBinding.containsVariable(varName)){
					result = true;
					break;
				}
			//}else{
			//	break;
			//}
		}
		*/

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
