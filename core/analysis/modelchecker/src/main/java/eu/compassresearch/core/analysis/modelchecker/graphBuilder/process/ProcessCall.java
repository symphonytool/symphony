package eu.compassresearch.core.analysis.modelchecker.graphBuilder.process;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.param.Param;

public class ProcessCall implements Process {
	

	private String str;
	private Param param;

	
	
	public ProcessCall(String str, Param param) {
		this.str = str;
		this.param = param;
		
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public Param getParam() {
		return param;
	}

	public void setParam(Param param) {
		this.param = param;
	}

	@Override
	public String toString() {
		return str + param.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof ProcessCall){
			ProcessCall aux = (ProcessCall) obj;
			result = this.getParam().equals(aux.getParam()) && this.getStr().equals(aux.getStr());
		}
		return result;
		
	}
	@Override
	public boolean isDeadlock(){
		return false;
	}

}
