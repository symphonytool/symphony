package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;


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
	@Override
	public String toFormula(String option){
		return "nBind";
	}
	
	@Override
	public Binding addBinding(String procName, String varName, MCPCMLExp varValue){
		SingleBind newBind = new SingleBind(varName, varValue);
		Binding result = new BBinding(procName,newBind,this);
		return result;
	}
	@Override
	public void updateBinding(String varName, MCPCMLExp type){
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
