package eu.compassresearch.core.analysis.modelchecker.ast.process;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAProcessDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class MCAReferenceProcess implements MCPProcess {

	protected String name;
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

	public MCAProcessDefinition getProc() {
		return proc;
	}

	public void setProc(MCAProcessDefinition proc) {
		this.proc = proc;
	}

	protected LinkedList<MCPCMLExp> args;
	protected MCAProcessDefinition proc;
	
	public MCAReferenceProcess(String name, LinkedList<MCPCMLExp> args, MCAProcessDefinition procDef) {
		this.name = name;
		this.args = args;
		this.proc = procDef;
	}
	
	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}

}
