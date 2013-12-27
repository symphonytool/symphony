package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCPCMLPattern;

public class PatternValue {
	private MCPCMLPattern pattern;
	private MCPCMLExp value;
	public PatternValue(MCPCMLPattern pattern, MCPCMLExp value) {
		super();
		this.pattern = pattern;
		this.value = value;
	}
	public MCPCMLPattern getPattern() {
		return pattern;
	}
	public void setPattern(MCPCMLPattern pattern) {
		this.pattern = pattern;
	}
	public MCPCMLExp getValue() {
		return value;
	}
	public void setValue(MCPCMLExp value) {
		this.value = value;
	}
	
	
}
