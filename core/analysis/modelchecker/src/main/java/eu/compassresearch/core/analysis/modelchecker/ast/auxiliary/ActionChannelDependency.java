package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.util.Iterator;
import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAReadCommunicationParameter;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPCommunicationParameter;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAChannelDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAVariableExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class ActionChannelDependency {
	
	private String actionName;
	private String channelName;
	private MCAChannelDefinition channelDefinition;
	private LinkedList<MCPCommunicationParameter> parameters;
	
	public ActionChannelDependency(String actionName, String channelName, LinkedList<MCPCommunicationParameter> parameters) {
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		this.actionName = actionName;
		this.channelName = channelName;
		this.parameters = parameters;
		this.channelDefinition = context.getChannelDefinition(channelName);
	}
	
	public String toFormula(String option){
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		StringBuilder result = new StringBuilder();

		this.channelDefinition = context.getChannelDefinition(channelName);
		if(channelDefinition != null){
			StringBuilder temp = new StringBuilder();
			
			temp.append(channelDefinition.toFormula(MCNode.GENERIC));
			int index = temp.lastIndexOf("_");
			ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
			
			MCPCMLType paramTypes = evaluator.instantiateMCTypeFromCommParams(parameters);
			if(index != -1){
				temp = temp.replace(index,index + 1, paramTypes.toFormula(option));
				
			}
			result.append(temp.toString());
		}
		
		return result.toString();
	}
	
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		int size = this.parameters.size();
		if(obj instanceof ActionChannelDependency){
			result = this.channelName.equals(((ActionChannelDependency) obj).getChannelName())
					&& size == ((ActionChannelDependency) obj).getParameters().size();
			
			for (int i = 0; i < size && result; i++) {
				MCPCommunicationParameter thisParam = this.parameters.get(i);
				MCPCommunicationParameter otherParam = ((ActionChannelDependency) obj).getParameters().get(i);
				if (thisParam instanceof MCAReadCommunicationParameter && otherParam instanceof MCAReadCommunicationParameter){
					result = result && ((MCAReadCommunicationParameter)thisParam).getExpression().equals(((MCAReadCommunicationParameter)otherParam).getExpression());
				}
			}
			
		}
		return result;
		
	}

	public boolean usesNonConstantVariable(){
		boolean result = false;
		for (MCPCommunicationParameter param : this.getParameters()) {
			if (param.getExpression() instanceof MCAVariableExp){
				result = true;
			}
		}
		return result;
	}
	
	public boolean hasInfiniteTypedChannel(){
		return this.channelDefinition.isInfiniteType();
	}
	
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public MCAChannelDefinition getChannelDefinition() {
		return channelDefinition;
	}

	public void setChannelDefinition(MCAChannelDefinition channelDefinition) {
		this.channelDefinition = channelDefinition;
	}

	public LinkedList<MCPCommunicationParameter> getParameters() {
		return parameters;
	}

	public void setParameters(LinkedList<MCPCommunicationParameter> parameters) {
		this.parameters = parameters;
	}


	
}
