package eu.compassresearch.core.analysis.modelchecker.ast.statements;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAIfStm implements MCPCMLStm {

	private int counterId;
	private MCPCMLExp ifExp;
	private MCPCMLStm thenStm;
	private MCPCMLStm elseStm;

	
	public MCAIfStm(MCPCMLExp ifExp, MCPCMLStm thenStm,
			MCPCMLStm elseStm) {
		this.counterId = NewCMLModelcheckerContext.GUARD_COUNTER++;
		this.ifExp = ifExp;
		this.thenStm = thenStm;
		this.elseStm = elseStm;
	}


	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		
		// it writes the conditional choice constructor
		result.append("condChoice(");
		
		// it writes the condition as an integer and puts the expression
		result.append(this.counterId + ",");

		switch (option) {
		case MCNode.DEFAULT:
			// it writes the behaviour in the if-true branch
			result.append(this.thenStm.toFormula(option));
			result.append(",");
			result.append(this.elseStm.toFormula(option));
			
			break;

		case MCNode.GENERIC:
			// it writes the behaviour in the if-true branch generically
			result.append("_");
			result.append(",");
			result.append("_");
			
			break;
		default:
			break;
		}
		
		result.append(")");
		
		return result.toString();
	}


	public int getCounterId() {
		return counterId;
	}


	public void setCounterId(int counterId) {
		this.counterId = counterId;
	}


	public MCPCMLExp getIfExp() {
		return ifExp;
	}


	public void setIfExp(MCPCMLExp ifExp) {
		this.ifExp = ifExp;
	}


	public MCPCMLStm getThenStm() {
		return thenStm;
	}


	public void setThenStm(MCPCMLStm thenStm) {
		this.thenStm = thenStm;
	}


	public MCPCMLStm getElseStm() {
		return elseStm;
	}


	public void setElseStm(MCPCMLStm elseStm) {
		this.elseStm = elseStm;
	}

}
