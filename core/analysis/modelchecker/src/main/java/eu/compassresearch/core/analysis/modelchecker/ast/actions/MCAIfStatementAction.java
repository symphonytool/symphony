package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCCondition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.visitors.CMLModelcheckerContext;
import eu.compassresearch.core.analysis.modelchecker.visitors.Condition;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAIfStatementAction implements MCPAction {

	private int counterId;
	private MCPCMLExp ifExp;
	private MCPAction thenStm;
	//private NodeList<AElseIfStatementAction> _elseIf = new NodeList<AElseIfStatementAction>(this);
	private MCPAction elseStm;
	
	
	public MCAIfStatementAction(MCPCMLExp ifExp, MCPAction thenStm,
			MCPAction elseStm) {
		this.counterId = NewCMLModelcheckerContext.GUARD_COUNTER++;
		this.ifExp = ifExp;
		this.thenStm = thenStm;
		this.elseStm = elseStm;
	}


	@Override
	public String toFormula(String option) {
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
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


	public MCPCMLExp getIfExp() {
		return ifExp;
	}


	public void setIfExp(MCPCMLExp ifExp) {
		this.ifExp = ifExp;
	}


	public MCPAction getThenStm() {
		return thenStm;
	}


	public void setThenStm(MCPAction thenStm) {
		this.thenStm = thenStm;
	}


	public MCPAction getElseStm() {
		return elseStm;
	}


	public void setElseStm(MCPAction elseStm) {
		this.elseStm = elseStm;
	}


	public int getCounterId() {
		return counterId;
	}


	public void setCounterId(int counterId) {
		this.counterId = counterId;
	}

	
}
