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
		LinkedList<MCPCMLExp> membersCopy = new LinkedList<MCPCMLExp>();
		for (MCPCMLExp member : members) {
			membersCopy.add(member.copy());
		}
		
		LinkedList<MCPCMLType> typesCopy = new LinkedList<MCPCMLType>();
		for (MCPCMLType type : types) {
			typesCopy.add(type.copy());
		}
		return new MCASeqEnumSeqExp(membersCopy,typesCopy);
	}

	@Override
	public void replacePatternWithValue(LinkedList<PatternValue> mapping) {
		for (MCPCMLExp member : members) {
			member.replacePatternWithValue(mapping);
		}
	}

	
}
