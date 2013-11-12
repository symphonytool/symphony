package eu.compassresearch.core.analysis.modelchecker.graphBuilder.process;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.param.Param;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Type;

public class ProcessCall implements Process {
	

	private String str;
	private Type type;

	
	
	public ProcessCall(String str, Type type) {
		this.str = str;
		this.type = type;
		
	}

	
	public Type getType() {
		return type;
	}


	public void setType(Type type) {
		this.type = type;
	}


	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	


	@Override
	public String toString() {
		return str + type.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof ProcessCall){
			ProcessCall aux = (ProcessCall) obj;
			result = this.getType().equals(aux.getType()) && this.getStr().equals(aux.getStr());
		}
		return result;
		
	}
	@Override
	public boolean isDeadlock(){
		return false;
	}

}
