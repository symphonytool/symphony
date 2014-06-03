package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAChannelDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCPCMLPattern;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAChannelType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAReadCommunicationParameter implements MCPCommunicationParameter {

	private MCPCMLExp expression;
	private MCPCMLPattern pattern;
	private MCACommunicationAction parentAction;
	
	
	public MCAReadCommunicationParameter(MCPCMLExp expression,
			MCPCMLPattern pattern) {
		this.expression = expression;
		this.pattern = pattern;
	}


	@Override
	public String toFormula(String option) {
		return "?" + this.pattern.toFormula(option);
	}

	

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof MCAReadCommunicationParameter){
			result = this.pattern.equals(((MCAReadCommunicationParameter) obj).getPattern());
		}
		return result;

	}

	@Override
	public String toString() {
		String result = null;
		if(this.expression != null){
			result = this.expression.toString();
		} else if(this.pattern != null){
			result = this.pattern.toFormula(MCNode.DEFAULT);
		}
		
		return result;
	}
	
	public MCPCMLType getOriginalType(){
		MCPCMLType result = null;
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		MCAChannelDefinition chanDef =  context.getChannelDefinition(this.parentAction.getIdentifier());
		
		if (chanDef.getType() instanceof MCAChannelType){
			result = ((MCAChannelType)chanDef.getType()).getType(); 
		}
		
		return result;
	}

	public MCPCMLExp getExpression() {
		return expression;
	}


	public void setExpression(MCPCMLExp expression) {
		this.expression = expression;
	}


	public MCPCMLPattern getPattern() {
		return pattern;
	}


	public void setPattern(MCPCMLPattern pattern) {
		this.pattern = pattern;
	}


	public MCACommunicationAction getParentAction() {
		return parentAction;
	}


	public void setParentAction(MCACommunicationAction parentAction) {
		this.parentAction = parentAction;
	}

	
	
}
