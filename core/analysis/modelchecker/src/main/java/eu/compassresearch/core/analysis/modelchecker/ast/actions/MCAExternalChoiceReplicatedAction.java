package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCAExpressionSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCPSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCASeqEnumSeqExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCASetEnumSetExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAExternalChoiceReplicatedAction extends MCSReplicatedActionBase {

	public MCAExternalChoiceReplicatedAction(
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
			} else if(pExp instanceof MCASeqEnumSeqExp){
				indexes = ((MCASeqEnumSeqExp) pExp).getMembers();
			}
		}
		
		// building combination of processses based on simple constructs
		StringBuilder replicatedAction = buildReplicatedAction(context,indexes, option);

		return replicatedAction.toString();
	}
	
	private StringBuilder buildReplicatedAction(NewCMLModelcheckerContext context,
			LinkedList<MCPCMLExp> indexes,String option) {

		 
		StringBuilder result = new StringBuilder();
		if(indexes.size() == 1){
			result.append(this.getReplicatedAction().toFormula(option));
		}else if (indexes.size() > 1) {
			MCPCMLExp firstArg = indexes.removeFirst();
			result.append("eChoice(");
			MCPAction replicatedAction = this.getReplicatedAction();
			if(replicatedAction instanceof MCAReferenceAction){
				//((MCAReferenceAction) replicatedAction).setArgs(first);
			}
			result.append(replicatedAction.toFormula(option));
			result.append(",");
			
			StringBuilder rest = buildReplicatedAction(context,indexes,option);
			result.append(rest.toString());
			result.append(")");
		}
		return result;
	}
	
}
