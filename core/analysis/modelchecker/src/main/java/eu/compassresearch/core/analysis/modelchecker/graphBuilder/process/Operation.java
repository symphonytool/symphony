package eu.compassresearch.core.analysis.modelchecker.graphBuilder.process;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.param.Param;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Type;

public class Operation implements Process {
	
	private String str;
	private Type param;
	
	
	public Operation(String str,Type par) {
		this.str = str;
		this.param = par;
	}
	
	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	
	@Override
	public String toString() {
		return str + "(" + param.toString() + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof Operation){
			Operation other = (Operation) obj;
			result = this.str.equals(other.str) &&
					 this.param.equals(((Operation) obj).getParam());
		}
		return result;
		
	}
	
	
	public Type getParam() {
		return param;
	}

	public void setParam(Type param) {
		this.param = param;
	}

	@Override
	public boolean isDeadlock(){
		return false;
	}

}
	
