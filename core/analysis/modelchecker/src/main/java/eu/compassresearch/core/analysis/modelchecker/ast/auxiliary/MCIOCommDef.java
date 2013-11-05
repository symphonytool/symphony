package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCACommunicationAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAChannelNameDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCANamedInvariantType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.CMLModelcheckerContext;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;
import eu.compassresearch.core.analysis.modelchecker.visitors.Utilities;

public class MCIOCommDef implements MCNode {

	protected Binding max;
	protected int counterId;
	protected MCACommunicationAction parentAction;
	protected LinkedList<MCAChannelNameDefinition> channels;
	
	
	public MCIOCommDef(int counterId, MCACommunicationAction parentAction,
			LinkedList<MCAChannelNameDefinition> channels) {
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
		
		result.append("  IOCommDef(0," + this.counterId + ",");
		result.append(evaluator.instantiateMCTypeFromCommParams(parentAction.getCommunicationParameters()).toFormula(option));
		result.append(",");
		result.append(max.toFormula(MCNode.NAMED)); //with variable names
		result.append(",");
		Binding maxCopy = max.copy();
		//we must update all communication variables in the binding with new valueNames so that the new values will come form the involved communication variables
		String varName = ""; 
		String newValueVarName = varName + "_";
		MCPCMLType newVarValue = new MCANamedInvariantType(newValueVarName);
		maxCopy.updateBinding(varName,newVarValue); //we must perform an update of all values in the bindings.
		result.append(maxCopy.toFormula(MCNode.GENERIC)); 
		result.append(")");
		
		result.append(" :- ");
		result.append("State(0,");
		result.append(max.toFormula(MCNode.NAMED));
		result.append(",");
		result.append("np,");
		result.append(this.parentAction.toFormula(option));

		//maybe it is necessary to add channel facts here (if the communication values are instantiated by formula)
		//if there is dependence with some channel
		
		//else
		result.append(").");
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


	public LinkedList<MCAChannelNameDefinition> getChannels() {
		return channels;
	}


	public void setChannels(LinkedList<MCAChannelNameDefinition> channels) {
		this.channels = channels;
	}

	
}
