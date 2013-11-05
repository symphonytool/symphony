package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCPSingleDeclaration;

public class MCSReplicatedActionBase implements MCPAction {

	protected LinkedList<MCPSingleDeclaration> replicationDeclaration = new LinkedList<MCPSingleDeclaration>();
	protected MCPAction replicatedAction;
	
	
	public MCSReplicatedActionBase(
			LinkedList<MCPSingleDeclaration> replicationDeclaration,
			MCPAction replicatedAction) {
		super();
		this.replicationDeclaration = replicationDeclaration;
		this.replicatedAction = replicatedAction;
	}


	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}


	public LinkedList<MCPSingleDeclaration> getReplicationDeclaration() {
		return replicationDeclaration;
	}


	public void setReplicationDeclaration(
			LinkedList<MCPSingleDeclaration> replicationDeclaration) {
		this.replicationDeclaration = replicationDeclaration;
	}


	public MCPAction getReplicatedAction() {
		return replicatedAction;
	}


	public void setReplicatedAction(MCPAction replicatedAction) {
		this.replicatedAction = replicatedAction;
	}
	
}
