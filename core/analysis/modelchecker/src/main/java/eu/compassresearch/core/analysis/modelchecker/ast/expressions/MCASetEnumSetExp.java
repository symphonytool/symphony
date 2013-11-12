package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.PatternValue;

public class MCASetEnumSetExp implements MCPCMLExp {

	private LinkedList<MCPCMLExp> members;

	
	public MCASetEnumSetExp(LinkedList<MCPCMLExp> members) {
		super();
		this.members = members;
	}


	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}


	public LinkedList<MCPCMLExp> getMembers() {
		return members;
	}


	public void setMembers(LinkedList<MCPCMLExp> members) {
		this.members = members;
	}


	@Override
	public MCPCMLExp copy() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void replacePatternWithValue(LinkedList<PatternValue> mapping) {
		for (MCPCMLExp member : members) {
			member.replacePatternWithValue(mapping);
		}
	}

	
}
