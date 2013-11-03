package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCActionCall;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class MCAReferenceAction implements MCPAction {

	private String name;
	private LinkedList<MCPCMLExp> args;
	
	
	public MCAReferenceAction(String name, LinkedList<MCPCMLExp> args) {
		super();
		this.name = name;
		this.args = args;
	}


	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		
		MCActionCall call = new MCActionCall(name, args);
		
		result.append(call.toFormula(option));

		return result.toString();

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
