package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCPSingleDeclaration;

public class MCAExternalChoiceReplicatedAction extends MCSReplicatedActionBase {

	public MCAExternalChoiceReplicatedAction(
			LinkedList<MCPSingleDeclaration> replicationDeclaration,
			MCPAction replicatedAction) {
		super(replicationDeclaration, replicatedAction);
	}

	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return super.toFormula(option);
	}
	
	
}
