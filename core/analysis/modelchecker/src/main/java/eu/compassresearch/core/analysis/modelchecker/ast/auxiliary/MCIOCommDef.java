package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCACommunicationAction;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAChannelDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAVariableExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCANamedInvariantType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCIOCommDef implements MCNode {

	protected Binding max;
	protected int counterId;
	protected MCACommunicationAction parentAction;
	protected LinkedList<MCAChannelDefinition> channels;
	
	
	public MCIOCommDef(int counterId, MCACommunicationAction parentAction,
			LinkedList<MCAChannelDefinition> channels) {
		super();
		this.max = NewCMLModelcheckerContext.getInstance().maximalBinding;
		this.counterId = counterId;
		this.parentAction = parentAction;
		this.channels = channels;
	}


	@Override
	public String toFormula(String option) {
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		StringBuilder result = new StringBuilder();
		ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
		
		result.append("  IOCommDef(" + this.counterId + ",");
		result.append(evaluator.instantiateMCTypeFromCommParams(parentAction.getCommunicationParameters()).toFormula(option));
		result.append(",");
		result.append(max.toFormula(MCNode.NAMED)); //with variable names
		result.append(",");
		Binding maxCopy = max.copy();
		//we must update all communication variables in the binding with new valueNames so that the new values will come form the involved communication variables
		String varName = ""; 
		String newValueVarName = varName + "_";
		MCPCMLExp newVarValue = new MCAVariableExp(newValueVarName);
		maxCopy.updateBinding(varName,newVarValue); //we must perform an update of all values in the bindings.
		result.append(maxCopy.toFormula(MCNode.GENERIC)); 
		result.append(")");
		
		result.append(" :- ");
		result.append("State(");
		result.append(max.toFormula(MCNode.NAMED));
		result.append(",");
		result.append(this.parentAction.toFormula(option));
		result.append(")");

		result.append(".");
		//we still have to generate the modified binding containing values (parameters) communicated
		//result.append(",");
		//result.append("State()");
		
		return result.toString();
	}


	@Override
	public String toString() {
		return "MCIOCommDef [counterId=" + counterId + ", parentAction="
				+ parentAction + ", channels=" + channels + "]";
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


	public LinkedList<MCAChannelDefinition> getChannels() {
		return channels;
	}


	public void setChannels(LinkedList<MCAChannelDefinition> channels) {
		this.channels = channels;
	}

	
}
