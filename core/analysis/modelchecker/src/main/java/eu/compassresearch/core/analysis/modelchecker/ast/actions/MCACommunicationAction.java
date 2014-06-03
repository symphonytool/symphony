package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import java.util.Iterator;
import java.util.LinkedList;

import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ActionChannelDependency;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ExpressionEvaluator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCCommEv;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCLieInFact;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.NameValue;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.TypeManipulator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.TypeValue;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCAExpressionSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCATypeSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCPSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAChannelDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAEnumVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAFatEnumVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCANameChannelExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAUnionVOpVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAVariableExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCANamedInvariantType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCVoidType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewSetStack;
import eu.compassresearch.core.analysis.modelchecker.visitors.Utilities;

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
					((MCAReadCommunicationParameter) param).setExpression(expValue);
				}
			} else if (param instanceof MCAWriteCommunicationParameter){
				
				if(((MCAWriteCommunicationParameter) param).getExpression() instanceof MCAVariableExp){
					MCAVariableExp paramExp = (MCAVariableExp) ((MCAWriteCommunicationParameter)param).getExpression();
					if(paramExp.getName().equals(name)){
						paramExp.setName(expValue.toFormula(MCNode.DEFAULT));
					}
				}
			} else if (param instanceof MCASignalCommunicationParameter){
				if(((MCASignalCommunicationParameter) param).getExpression() instanceof MCAVariableExp){
					MCAVariableExp paramExp = (MCAVariableExp) ((MCASignalCommunicationParameter)param).getExpression();
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
		LinkedList<TypeValue> values = typeHandler.getValues(paramType);
		LinkedList<TypeValue> valuesCopy = new LinkedList<TypeValue>();
		valuesCopy.addAll(values);
		
		if(chanDef.isInfiniteType() && context.getNumberOfInstances() == 1){
			result.append(buildReplicatedExternalChoice(context, option));
			for (TypeValue typeValue : valuesCopy) {
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
		} else{
			result.append(buildReplicatedExternalChoice(context, values, option));
		}
		
		
		
		
		return result.toString();
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
			LinkedList<TypeValue> values,String option) {

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
				mapping.setVariableValue(firstValue.toFormula(option));
				context.localIndexedVariablesMapping.push(mapping);
				result.append(this.buildPrefix(option, context, firstValue));
				context.localIndexedVariablesMapping.pop();
				context.localIndexedVariablesDiscarded.add(identifier);
			}else if (values.size() > 1) {
				TypeValue firstValue = values.removeFirst();
				mapping.setVariableValue(firstValue.toFormula(option));
				context.localIndexedVariablesMapping.push(mapping);
				result.append("eChoice(");
				result.append(this.buildPrefix(option, context, firstValue));
				result.append(",");
				context.localIndexedVariablesMapping.pop();
				
				StringBuilder rest = buildReplicatedExternalChoice(context,values,option);
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
