package eu.compassresearch.core.analysis.modelchecker.graphBuilder.process;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.param.Param;

public class Operation implements Process {
	
	private String str;
	private Param param;
	
	
	public Operation(String str,Param par) {
		this.str = str;
		this.param = par;
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
		if(obj instanceof Operation){
			Operation other = (Operation) obj;
			result = this.str.equals(other.str);
		}
		return result;
		
	}
	
	@Override
	public boolean isDeadlock(){
		return false;
	}

}
	
