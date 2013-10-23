package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCType;

public class MCASeqEnumSeqExp implements MCPCMLExp{

	private LinkedList<MCPCMLExp> members;
	private LinkedList<MCType> types;
	
	public MCASeqEnumSeqExp(LinkedList<MCPCMLExp> memb, LinkedList<MCType> types) {
		this.members = memb;
		this.types = types;
	}
	
	@Override
	public String toFormula(String option) {
		return null;
	}

	public LinkedList<MCType> getTypes() {
		return types;
	}

	public void setTypes(LinkedList<MCType> types) {
		this.types = types;
	}

	public LinkedList<MCPCMLExp> getMembers() {
		return members;
	}

	public void setMembers(LinkedList<MCPCMLExp> members) {
		this.members = members;
	}

}
