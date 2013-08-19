package eu.compassresearch.core.analysis.modelchecker.formula.graph.process;

import eu.compassresearch.core.analysis.modelchecker.formula.graph.param.Param;

public class ProcessCall implements Process {
	

	private String str;
	private Param param;

	
	
	public ProcessCall(String str, Param param) {
		this.str = str;
		this.param = param;
		
	}

}
