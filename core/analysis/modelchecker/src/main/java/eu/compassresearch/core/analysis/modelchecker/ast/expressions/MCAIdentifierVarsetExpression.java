package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAChansetDefinition;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAIdentifierVarsetExpression implements MCPVarsetExpression {

	private String identifier;

	public MCAIdentifierVarsetExpression(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public String toFormula(String option) {
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		StringBuilder result = new StringBuilder();
		MCAChansetDefinition chansetDef =  context.getChansetDefinition(this.identifier);
		if(chansetDef != null){
			result.append(chansetDef.toFormula(option));
		}else{
			result.append(this.identifier);
		}
		return result.toString();
	}

	@Override
	public LinkedList<MCANameChannelExp> getChannelNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	
}
