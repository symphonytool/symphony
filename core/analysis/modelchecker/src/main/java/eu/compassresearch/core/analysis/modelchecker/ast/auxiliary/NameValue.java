package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public class NameValue {

	private String variableName;
	private String variableValue;
	private MCPCMLType type;
	
	
	public NameValue(String variableName, String variableValue,MCPCMLType type) {
		super();
		this.variableName = variableName;
		this.variableValue = variableValue;
		this.type = type;
	}


	@Override
	public boolean equals(Object obj) {
		boolean resp = false;
		if(obj instanceof SingleBind){
			resp = this.variableName.equals(((SingleBind) obj).variableName);
		}
		return resp;
	}


	public String getVariableName() {
		return variableName;
	}


	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}


	public String getVariableValue() {
		return variableValue;
	}


	public void setVariableValue(String variableValue) {
		this.variableValue = variableValue;
	}


	public MCPCMLType getType() {
		return type;
	}


	public void setType(MCPCMLType type) {
		this.type = type;
	}

	
	
}
