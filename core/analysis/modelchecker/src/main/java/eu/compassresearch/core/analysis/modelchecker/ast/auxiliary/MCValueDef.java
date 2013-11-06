package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAIntLiteralExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCValueDef implements MCNode {

	private String name;
	private MCPCMLType type;
	private MCPCMLExp value;
	
	
	public MCValueDef(String name, MCPCMLType type, MCPCMLExp value) {
		super();
		this.name = name;
		this.type = type;
		this.value = value;
	}


	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		
		result.append(this.name);
		result.append("(");
		result.append(this.value.toFormula(option));
		result.append(")");
		
		return result.toString();
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


	public MCPCMLType getType() {
		return type;
	}


	public void setType(MCPCMLType type) {
		this.type = type;
	}

	
	
}
