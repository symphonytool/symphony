package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.PatternValue;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public class MCASeqEnumSeqExp implements MCPCMLExp{

	private LinkedList<MCPCMLExp> members;
	private LinkedList<MCPCMLType> types;
	
	public MCASeqEnumSeqExp(LinkedList<MCPCMLExp> memb, LinkedList<MCPCMLType> types) {
		this.members = memb;
		this.types = types;
	}
	
	@Override
	public String toFormula(String option) {
		return null;
	}

	public LinkedList<MCPCMLExp> getMembers() {
		return members;
	}

	public void setMembers(LinkedList<MCPCMLExp> members) {
		this.members = members;
	}

	public LinkedList<MCPCMLType> getTypes() {
		return types;
	}

	public void setTypes(LinkedList<MCPCMLType> types) {
		this.types = types;
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
