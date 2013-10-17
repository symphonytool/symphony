package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class MCACallStatementAction implements MCPAction {

	private String name;
	private LinkedList<MCPCMLExp> args;

	public MCACallStatementAction(String name, LinkedList<MCPCMLExp> args) {
		this.name = name;
		this.args = args;
	}

	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedList<MCPCMLExp> getArgs() {
		return args;
	}

	public void setArgs(LinkedList<MCPCMLExp> args) {
		this.args = args;
	}

}
