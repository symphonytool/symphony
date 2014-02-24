package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ExpressionEvaluator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCCommEv;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCLieInFact;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.TypeManipulator;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAEnumVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAFatEnumVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCANameChannelExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAUnionVOpVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCVoidType;
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



	@Override
	public String toFormula(String option) {
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		StringBuilder result = new StringBuilder();
		result.append("Prefix(IOComm(" + this.counterId + ",");
		result.append("\"" + this.identifier + "\"");
		result.append(",");
		result.append("\"" + buildIOCommExp(option) + "\"");
		result.append(",");
		result.append(buildIOCommActualParams(option));
		result.append(")"); //closes IOComm
		result.append(",");
		if(option.equals(MCNode.MINIMAL_GENERIC)){
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
	
		if(option.equals(MCNode.MINIMAL_GENERIC)){
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
