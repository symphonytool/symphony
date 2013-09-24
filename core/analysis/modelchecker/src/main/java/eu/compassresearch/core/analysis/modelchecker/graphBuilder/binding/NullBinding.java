package eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Type;


public class NullBinding implements Binding {
	
	@Override
	public String toString() {
		return "{}";
	}

	@Override
	public NullBinding copy(){
		
		return new NullBinding();
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof NullBinding;
	}
	
	public String toFormula(){
		return "nBind";
	}
	
	public String toFormulaWithUnderscore(){
		return "nBind";
	}
	
	@Override
	public String toFormulaWithState() {
		return "nBind";
	}
	
	public Binding addBinding(String procName, String varName, Type varValue){
		SingleBind newBind = new SingleBind(varName, varValue);
		Binding result = new BBinding(procName,newBind,this);
		return result;
	}
	public void updateBinding(String varName, Type type){
		//it does nothig because empty binding cannot be updated 
	}
	public Binding deleteBinding(String varName){
		//it does nothig because cannot delete from empty binding 
		return this;
	}
}
