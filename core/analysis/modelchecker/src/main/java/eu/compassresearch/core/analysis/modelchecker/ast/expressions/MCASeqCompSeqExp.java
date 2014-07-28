package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.PatternValue;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCASetBind;

public class MCASeqCompSeqExp implements MCPCMLExp{

	private MCPCMLExp first;
	private MCASetBind setBind;

	
	public MCASeqCompSeqExp(MCPCMLExp first, MCASetBind setBind) {
		super();
		this.first = first;
		this.setBind = setBind;
	}

	@Override
	public String toFormula(String option) {
		//TODO
		return null;
	}

	@Override
	public MCPCMLExp copy() {
		return new MCASeqCompSeqExp(first.copy(),setBind);
	}

	@Override
	public void replacePatternWithValue(LinkedList<PatternValue> mapping) {
		this.first.replacePatternWithValue(mapping);
	}

	public MCPCMLExp getFirst() {
		return first;
	}

	public void setFirst(MCPCMLExp first) {
		this.first = first;
	}

	public MCASetBind getSetBind() {
		return setBind;
	}

	public void setSetBind(MCASetBind setBind) {
		this.setBind = setBind;
	}

	
	
}
