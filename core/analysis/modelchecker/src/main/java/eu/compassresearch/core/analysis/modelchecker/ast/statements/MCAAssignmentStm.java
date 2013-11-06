package eu.compassresearch.core.analysis.modelchecker.ast.statements;

import org.overture.ast.statements.AAssignmentStm;

import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAAssignmentStm implements MCPCMLStm {

	private int counterId;
	private MCPCMLExp expression;
	private MCPStateDesignator target;

	
	public MCAAssignmentStm(MCPCMLExp expression, MCPStateDesignator target) {
		this.counterId = NewCMLModelcheckerContext.ASSIGN_COUNTER++;
		this.expression = expression;
		this.target = target;
	}


	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}


	public MCPCMLExp getExpression() {
		return expression;
	}


	public void setExpression(MCPCMLExp expression) {
		this.expression = expression;
	}


	public MCPStateDesignator getTarget() {
		return target;
	}


	public void setTarget(MCPStateDesignator target) {
		this.target = target;
	}


	public int getCounterId() {
		return counterId;
	}


	public void setCounterId(int counterId) {
		this.counterId = counterId;
	}

	
}
