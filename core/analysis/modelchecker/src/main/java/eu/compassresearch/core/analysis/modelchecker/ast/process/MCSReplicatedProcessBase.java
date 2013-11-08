package eu.compassresearch.core.analysis.modelchecker.ast.process;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCPSingleDeclaration;


public class MCSReplicatedProcessBase implements MCPProcess{

	protected MCPProcess replicatedProcess;
	protected LinkedList<MCPSingleDeclaration> replicationDeclaration = new LinkedList<MCPSingleDeclaration>();
	
	public MCSReplicatedProcessBase(LinkedList<MCPSingleDeclaration> declarations, MCPProcess replicatedProc) {
		super();
		this.replicatedProcess = replicatedProc;
		this.replicationDeclaration = declarations;
	}
	
	
	public MCPProcess getReplicatedProcess() {
		return replicatedProcess;
	}


	public void setReplicatedProcess(MCPProcess replicatedProcess) {
		this.replicatedProcess = replicatedProcess;
	}


	public LinkedList<MCPSingleDeclaration> getReplicationDeclaration() {
		return replicationDeclaration;
	}


	public void setReplicationDeclaration(
			LinkedList<MCPSingleDeclaration> replicationDeclaration) {
		this.replicationDeclaration = replicationDeclaration;
	}


	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}

}
