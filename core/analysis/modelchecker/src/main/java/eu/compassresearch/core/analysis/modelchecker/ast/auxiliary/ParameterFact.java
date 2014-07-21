package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class ParameterFact {
	private String name;
	private MCPCMLExp value;
	
	
	public ParameterFact(String name, MCPCMLExp value) {
		super();
		this.name = name;
		this.value = value;
	}


	public String toFormula(String option){
		StringBuilder result = new StringBuilder();
		
		result.append("  ParamW(\"");
		result.append(this.name);
		result.append("\",");
		result.append(value.toFormula(option));
		result.append(")");
		
		return result.toString();
	}

	
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof ParameterFact){
			result = this.name.equals(((ParameterFact) obj).getName());
		}
		return result;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public MCPCMLExp getValue() {
		return value;
	}


	public void setValue(MCPCMLExp value) {
		this.value = value;
	}
	
	
}
