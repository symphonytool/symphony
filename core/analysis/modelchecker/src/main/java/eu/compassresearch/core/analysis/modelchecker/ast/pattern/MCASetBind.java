package eu.compassresearch.core.analysis.modelchecker.ast.pattern;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class MCASetBind implements MCPCMLPattern {

	private MCPCMLPattern pattern;
	private MCPCMLExp set;
	
	public MCASetBind(MCPCMLPattern pattern, MCPCMLExp set) {
		this.pattern = pattern;
		this.set = set;
	}

	@Override
	public String toFormula(String option) {
		//TODO 
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof MCASetBind){
			//TODO
			result = false;
		}
		return result;
	}



	public MCPCMLPattern getPattern() {
		return pattern;
	}



	public void setPattern(MCPCMLPattern pattern) {
		this.pattern = pattern;
	}



	public MCPCMLExp getSet() {
		return set;
	}



	public void setSet(MCPCMLExp set) {
		this.set = set;
	}
}
