package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCATypeSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCPProcess;

public class MCAProcessDefinition implements MCPCMLDefinition {

	private LinkedList<MCATypeSingleDeclaration> localState = new LinkedList<MCATypeSingleDeclaration>();
	private MCPProcess process;

	
	
	public MCAProcessDefinition(
			LinkedList<MCATypeSingleDeclaration> localState, MCPProcess process) {
		super();
		this.localState = localState;
		this.process = process;
	}



	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}



	public LinkedList<MCATypeSingleDeclaration> getLocalState() {
		return localState;
	}



	public void setLocalState(LinkedList<MCATypeSingleDeclaration> localState) {
		this.localState = localState;
	}



	public MCPProcess getProcess() {
		return process;
	}



	public void setProcess(MCPProcess process) {
		this.process = process;
	}

	
}
