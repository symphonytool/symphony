package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.NameValue;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.PatternValue;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAValueDefinition;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;


public class MCAVariableExp implements MCNumericExp {

	private String name;
	

	public MCAVariableExp(String name) {
		super();
		this.name = name;
	}

	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		//if this variable is defined as a value in the context we must return its value in default toFormula
		MCAValueDefinition valueDef =  context.getValueDefinition(this.name);
			
		//switch (option) {
		//case MCNode.DEFAULT:
			if(valueDef != null){
				result.append(valueDef.getExpression().toFormula(option));
			} else {
				//if this variable is local and has been put in the local mapping
				//LinkedList<NameValue> mapping = context.getLocalVariableMapping(this.getName());
				//if(mapping.size() > 0){
				//	result.append(mapping.getFirst().getVariableValue());
				//} else{	
					result.append(this.getName());
				//}
			}
		//	break;

		//case MCNode.NAMED:
		//	result.append(this.getName());
		//	break;

		//default:
		//	break;
		//}
		
		
		return result.toString();
	}

	@Override
	public String toString() {
		return this.name;
	}

	

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof MCAVariableExp){
			result = this.name.equals(((MCAVariableExp) obj).getName());
		}
		return result;
	}

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}

	@Override
	public MCPCMLExp copy() {
		
		return new MCAVariableExp(this.name);
	}

	@Override
	public void replacePatternWithValue(LinkedList<PatternValue> mapping) {
		for (PatternValue patternValue : mapping) {
			if(patternValue.getPattern().toFormula(MCNode.DEFAULT).equals(this.name)){
				this.name = patternValue.getValue().toFormula(MCNode.DEFAULT);
				break;
			}
		}
	}

}
