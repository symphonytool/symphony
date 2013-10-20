package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCAExpressionSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCPSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCASetEnumSetExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAGeneralisedParallelismReplicatedAction extends
		MCSReplicatedActionBase {

	private MCPVarsetExpression chansetExpression;
	
	public MCAGeneralisedParallelismReplicatedAction(
			LinkedList<MCPSingleDeclaration> replicationDeclaration,
			MCPAction replicatedAction, MCPVarsetExpression chansetExpression) {
		super(replicationDeclaration, replicatedAction);
		this.chansetExpression = chansetExpression;
	}

	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		
		MCPSingleDeclaration sDecl = this.getReplicationDeclaration().getFirst();
		LinkedList<MCPCMLExp> indexes = new LinkedList<MCPCMLExp>();
		if (sDecl instanceof MCAExpressionSingleDeclaration) {
			MCPCMLExp pExp = ((MCAExpressionSingleDeclaration) sDecl).getExpression();
			if (pExp instanceof MCASetEnumSetExp) {
				indexes = ((MCASetEnumSetExp) pExp).getMembers();
			}
		}
		
		// building combination of processses based on simple constructs
		StringBuilder replicatedAction = buildReplicatedAction(context,indexes.size(), option);
		result.append(replicatedAction.toString());

		return result.toString();
	}
	
	private StringBuilder buildReplicatedAction(NewCMLModelcheckerContext context,
			int times,String option) {

		 
		StringBuilder result = new StringBuilder();
		
		if(times == 1){
			result.append(this.getReplicatedAction().toFormula(option));
		}else if (times > 1) {
			result.append("genPar(");
			result.append(this.getReplicatedAction().toFormula(option));
			result.append(",");
			result.append(this.chansetExpression.toFormula(option));
			result.append(",");
			StringBuilder rest = buildReplicatedAction(context,times-1,option);
			result.append(rest.toString());
			result.append(")");
		}
		return result;
	}

}
