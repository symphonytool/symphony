package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.LinkedList;

import org.overture.ast.expressions.AIfExp;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.PatternValue;
import eu.compassresearch.core.analysis.modelchecker.ast.statements.MCPCMLStm;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAIfExp implements MCPCMLExp {

	private int counterId;
	private MCPCMLExp test;
	private MCPCMLExp thenExp;
	private MCPCMLExp elseExp;

	

	public MCAIfExp(MCPCMLExp test, MCPCMLExp thenExp, MCPCMLExp elseExp) {
		this.counterId = NewCMLModelcheckerContext.GUARD_COUNTER++;		
		this.test = test;
		this.thenExp = thenExp;
		this.elseExp = elseExp;
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
			result.append(this.thenExp.toFormula(option));
			result.append(",");
			result.append(this.elseExp.toFormula(option));
			
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

	@Override
	public MCPCMLExp copy() {
		return new MCAIfExp(test, thenExp, elseExp);
	}

	@Override
	public void replacePatternWithValue(LinkedList<PatternValue> mapping) {
		// TODO Auto-generated method stub

	}

	public int getCounterId() {
		return counterId;
	}

	public void setCounterId(int counterId) {
		this.counterId = counterId;
	}

	public MCPCMLExp getTest() {
		return test;
	}

	public void setTest(MCPCMLExp test) {
		this.test = test;
	}

	public MCPCMLExp getThenExp() {
		return thenExp;
	}

	public void setThenExp(MCPCMLExp thenExp) {
		this.thenExp = thenExp;
	}

	public MCPCMLExp getElseExp() {
		return elseExp;
	}

	public void setElseExp(MCPCMLExp elseExp) {
		this.elseExp = elseExp;
	}

	
	
}
