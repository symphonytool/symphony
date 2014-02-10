package eu.compassresearch.core.analysis.modelchecker.ast.pattern;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public class MCAPatternListTypePair implements MCNode {

	private LinkedList<MCPCMLPattern> patterns;
	private MCPCMLType type;

	

	public MCAPatternListTypePair(LinkedList<MCPCMLPattern> patterns,
			MCPCMLType type) {
		super();
		this.patterns = patterns;
		this.type = type;
	}



	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}



	public LinkedList<MCPCMLPattern> getPatterns() {
		return patterns;
	}



	public void setPatterns(LinkedList<MCPCMLPattern> patterns) {
		this.patterns = patterns;
	}



	public MCPCMLType getType() {
		return type;
	}



	public void setType(MCPCMLType type) {
		this.type = type;
	}
	
	

}
