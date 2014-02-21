package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.util.Iterator;
import java.util.LinkedList;

import org.overture.ast.expressions.AVariableExp;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCACommunicationAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAReadCommunicationParameter;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCASignalCommunicationParameter;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAWriteCommunicationParameter;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPCommunicationParameter;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAChannelDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAVariableExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCAIdentifierPattern;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCPCMLPattern;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCANamedInvariantType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCIOCommDef implements MCNode {

	protected Binding max;
	protected int counterId;
	protected MCACommunicationAction parentAction;
	
	
	public MCIOCommDef(int counterId, MCACommunicationAction parentAction) {
		super();
		this.max = NewCMLModelcheckerContext.getInstance().maximalBinding;
		this.counterId = counterId;
		this.parentAction = parentAction;
	}


	@Override
	public String toFormula(String option) {
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		StringBuilder result = new StringBuilder();
		ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
		this.max = context.maximalBinding;
		
		result.append("  IOCommDef(" + this.counterId + ",");
		//precisa ter um instantiateMCTypeFromCommParamsForIoCommDef para instanciar tipos com nomes modificados varOld 
		result.append(evaluator.instantiateMCTypeFromCommParamsForIOCommDef(parentAction.getCommunicationParameters(), "Old").toFormula(option));
		result.append(",");
		Binding maxOldCopy = max.copy();
		replaceParamValues(maxOldCopy);
		//result.append(max.toFormula(MCNode.PARAM_RENAMED)); //with variable names
		result.append(maxOldCopy.toFormula(MCNode.NAMED)); //with variable names
		result.append(",");
		//Binding maxCopy = max.copy();
		//we must update all communication variables in the binding with new valueNames so that the new values will come form the involved communication variables
		if(parentAction.getCommunicationParameters().size() > 0){
			//for (MCPCommunicationParameter var : parentAction.getCommunicationParameters()) {
			//	String varName = var.toFormula(option); 
			//	String newValueVarName = varName + "_";
			//	MCPCMLExp newVarValue = new MCAVariableExp(newValueVarName);
			//	maxCopy.updateBinding(varName,newVarValue); //we must perform an update of all values in the bindings.
			//}
			
			//result.append(maxCopy.toFormula(MCNode.GENERIC));
			//for the moment communication variables are not put into the bindings until we resolve the link between inputs in formula
			result.append(max.toFormula(MCNode.NAMED));
		} else{
			result.append(maxOldCopy.toFormula(MCNode.NAMED)); //with variable names
		}
		result.append(")");
		
		result.append(" :- ");
		result.append("State(");
		//result.append(max.toFormula(MCNode.NAMED));
		result.append(maxOldCopy.toFormula(MCNode.NAMED));
		result.append(",");
		
		result.append(this.parentAction.toFormula(MCNode.MINIMAL_GENERIC));
		//result.append(this.parentAction.toFormula(MCNode.NAMED));
		result.append(")");

		if(parentAction.getCommunicationParameters().size() > 0){
			
			Iterator<MCPCommunicationParameter> parameters = parentAction.getCommunicationParameters().iterator();
			while (parameters.hasNext()) {
				result.append(", ");
				MCPCommunicationParameter communicationParam = (MCPCommunicationParameter) parameters.next();
				String varName = communicationParam.toString();
				result.append(varName);
				result.append(" = ");
				//String value = buildIOCommActualParam(communicationParam, option);
				//result.append(value);
				result.append(varName);
				
				
			}
			
		}
		result.append(".");
		//we still have to generate the modified binding containing values (parameters) communicated
		//result.append(",");
		//result.append("State()");
		
		return result.toString();
	}
	
	public String buildIOCommActualParam(MCPCommunicationParameter communicationParam, String option){
		
		StringBuilder result = new StringBuilder();
		ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
			
		if(option.equals(MCNode.MINIMAL_GENERIC)){
			MCPCMLType type = evaluator.getTypeForIOComm(communicationParam);
			result.append(type.toFormula(option));
		}else{
			MCPCMLType type = evaluator.getTypeFor(communicationParam);
			result.append(type.toFormula(option));
		}
		
		return result.toString();
	}

	//private void replaceCommunicatedValues(Binding binding, )
	
	private void replaceParamValues(Binding binding){
		if(parentAction != null){
			LinkedList<MCPCommunicationParameter> parameters = parentAction.getCommunicationParameters();
			for (MCPCommunicationParameter param : parameters) {
				if(param instanceof MCASignalCommunicationParameter){
					MCPCMLExp expression = ((MCASignalCommunicationParameter) param).getExpression();
					if(expression instanceof MCAVariableExp){
						MCPCMLExp newValue = new MCAVariableExp(((MCAVariableExp) expression).getName()+ "Old");
						binding.updateBinding(((MCAVariableExp) expression).getName(), newValue);
					}
				} else if(param instanceof MCAReadCommunicationParameter){
					MCPCMLPattern pattern = ((MCAReadCommunicationParameter) param).getPattern();
					if(pattern instanceof MCAIdentifierPattern){
						MCPCMLExp newValue = new MCAVariableExp(((MCAIdentifierPattern) pattern).getName()+ "Old");
						binding.updateBinding(((MCAIdentifierPattern) pattern).getName(), newValue);
					}
				}else if(param instanceof MCAWriteCommunicationParameter){
					MCPCMLExp expression = ((MCAWriteCommunicationParameter) param).getExpression();
					if(expression instanceof MCAVariableExp){
						MCPCMLExp newValue = new MCAVariableExp(((MCAVariableExp) expression).getName()+ "Old");
						binding.updateBinding(((MCAVariableExp) expression).getName(), newValue);
					}
				}
			}
		}
	}

	@Override
	public String toString() {
		return "MCIOCommDef [counterId=" + counterId + ", parentAction="
				+ parentAction + "]";
	}


	public int getCounterId() {
		return counterId;
	}


	public void setCounterId(int counterId) {
		this.counterId = counterId;
	}


	public MCACommunicationAction getParentAction() {
		return parentAction;
	}


	public void setParentAction(MCACommunicationAction parentAction) {
		this.parentAction = parentAction;
	}


	
	
}
