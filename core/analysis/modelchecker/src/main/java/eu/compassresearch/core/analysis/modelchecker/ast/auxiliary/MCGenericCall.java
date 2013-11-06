package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public abstract class MCGenericCall implements MCPAction{

	protected String name;
	protected LinkedList<MCPCMLExp> args;

	public MCGenericCall(String name,LinkedList<MCPCMLExp> args) {
		this.name = name;
		this.args = args;
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