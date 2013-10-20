package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.ASetEnumSetExp;
import org.overture.ast.expressions.PExp;

import eu.compassresearch.ast.actions.AGeneralisedParallelismReplicatedAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.SReplicatedActionBase;
import eu.compassresearch.ast.declarations.AExpressionSingleDeclaration;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCAExpressionSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCPSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCASetEnumSetExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;
import eu.compassresearch.core.analysis.modelchecker.visitors.Utilities;

public class MCASequentialCompositionReplicatedAction extends
		MCSReplicatedActionBase {

	public MCASequentialCompositionReplicatedAction(
			LinkedList<MCPSingleDeclaration> replicationDeclaration,
			MCPAction replicatedAction) {
		super(replicationDeclaration, replicatedAction);
	}

	@Override
	public String toFormula(String option) {

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

		return replicatedAction.toString();
	}
	
	private StringBuilder buildReplicatedAction(NewCMLModelcheckerContext context,
			int times,String option) {

		 
		StringBuilder result = new StringBuilder();
		
		if(times == 1){
			result.append(this.getReplicatedAction().toFormula(option));
		}else if (times > 1) {
			result.append("seqC(");
			result.append(this.getReplicatedAction().toFormula(option));
			result.append(",");
			StringBuilder rest = buildReplicatedAction(context,times-1,option);
			result.append(rest.toString());
			result.append(")");
		}
		return result;
	}
}
