package eu.compassresearch.core.analysis.modelchecker.ast.types;

import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAValueDefinition;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAFixedInvariantType implements MCPCMLType {

	private String name;
		
	public MCAFixedInvariantType(String name) {
		this.name = name;
	}

	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		MCAValueDefinition valueDef = context.getValueDefinition(name);
		if(valueDef != null){
			result.append(valueDef.getExpression().toFormula(option));
		}else{
			result.append(this.name);
		}
		return result.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	@Override
	public MCPCMLType copy() {
	
		return new MCAFixedInvariantType(new String(this.getName()));
	}

	@Override
	public String getTypeAsName() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
