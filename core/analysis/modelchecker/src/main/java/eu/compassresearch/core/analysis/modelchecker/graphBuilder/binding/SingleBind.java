package eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
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
	public SingleBind copy(){
		SingleBind copy = new SingleBind(variableName, variableValue.copy());
		return copy;
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
		return "SingleBind(\"" + variableName + "\"," + variableValue.toFormula() + ")";
	}
	
	public String toFormula(String option) {
		String result = toFormula();
		switch (option) {
		case MCNode.DEFAULT:
			result = "SingleBind(\"" + variableName + "\"," + variableValue.toFormula() + ")";
			break;

		case MCNode.NAMED:
			result = "SingleBind(\"" + variableName + "\"," + variableName + ")";
			break;
			
		default:
			break;
		}
		
		return result;
	}
	
	public String toFormulaWithState(){
		return "SingleBind(\"" + variableName + "\"," + variableValue.toFormulaWithState() + ")";
	}
	
	public String toFormulaGeneric(){
		return "SingleBind(\"" + variableName + "\"," + variableValue.toFormulaGeneric() + ")";
	}
	
	public String toFormulaWithUnderscore(){
		return "SingleBind(\"" + variableName + "\",_)";
	}
}
