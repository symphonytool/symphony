package eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Type;


public class NullBinding implements Binding {
	
	@Override
	public void setProcName(String procName){
		
	}
	
	@Override
	public String getProcName(){
		return "pName";
	}
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
	@Override
	public String toFormula(){
		return "nBind";
	}
	@Override
	public String toFormulaWithUnderscore(){
		return "nBind";
	}
	
	@Override
	public String toFormulaWithState() {
		return "nBind";
	}
	@Override
	public Binding addBinding(String procName, String varName, Type varValue){
		SingleBind newBind = new SingleBind(varName, varValue);
		Binding result = new BBinding(procName,newBind,this);
		return result;
	}
	@Override
	public void updateBinding(String varName, Type type){
		//it does nothig because empty binding cannot be updated 
	}
	@Override
	public Binding deleteBinding(String varName){
		//it does nothig because cannot delete from empty binding 
		return this;
	}
	@Override
	public StringBuilder generateAllFetchFacts(int number){
		return new StringBuilder();
	}
	
	public StringBuilder generateAllUpdFacts(int number){
		return new StringBuilder();
	}
	public StringBuilder generateAllDelFacts(int number){
		return new StringBuilder();
	}
}
