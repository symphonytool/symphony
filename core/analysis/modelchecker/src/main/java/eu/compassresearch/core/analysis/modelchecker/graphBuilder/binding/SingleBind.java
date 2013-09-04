package eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Type;

public class SingleBind {
	protected String variableName;
	protected Type variableValue;
	public SingleBind(String variableName, Type variableValue) {
		super();
		this.variableName = variableName;
		this.variableValue = variableValue;
	}
	public String getVariableName() {
		return variableName;
	}
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	public Type getVariableValue() {
		return variableValue;
	}
	public void setVariableValue(Type variableValue) {
		this.variableValue = variableValue;
	}
	@Override
	public boolean equals(Object obj) {
		boolean resp = false;
		if(obj instanceof SingleBind){
			resp = this.variableName.equals(((SingleBind) obj).variableName) 
					&& this.variableValue.equals(((SingleBind) obj).variableValue);
		}
		return resp;
	}
	@Override
	public String toString() {
		return "(" + variableName + "," + variableValue.toString() + ")";
	}
	
	public String toFormula() {
		return "SingleBind(\"" + variableName + "\"," + variableValue.toString() + ")";
	}
	
	public String toFormulaWithUnderscore(){
		return "SingleBind(\"" + variableName + "\",_)";
	}
}
