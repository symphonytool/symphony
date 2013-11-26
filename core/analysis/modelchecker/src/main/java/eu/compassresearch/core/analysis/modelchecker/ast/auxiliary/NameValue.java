package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class NameValue {

	private String variableName;
	private String variableValue;
	
	
	public NameValue(String variableName, String variableValue) {
		super();
		this.variableName = variableName;
		this.variableValue = variableValue;
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

	
	
}
