package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCAExpressionSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCPSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCASetEnumSetExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.ast.statements.MCAActionStm;
import eu.compassresearch.core.analysis.modelchecker.ast.statements.MCPCMLStm;
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
		StringBuilder replicatedAction = buildReplicatedAction(context,indexes, option);
		result.append(replicatedAction.toString());

		return result.toString();
	}
	
	private StringBuilder buildReplicatedAction(NewCMLModelcheckerContext context,
			LinkedList<MCPCMLExp> indexes, String option) {

		 
		StringBuilder result = new StringBuilder();
		
		if(indexes.size() == 1){
			LinkedList<MCPCMLExp> realArgs = new LinkedList<MCPCMLExp>();
			MCPCMLExp firstArg = indexes.removeFirst();
			realArgs.add(firstArg);
			if(replicatedAction instanceof MCAStmAction) {
				MCPCMLStm stm = ((MCAStmAction) replicatedAction).getStatement();
				if(stm instanceof MCAActionStm){
					MCPAction realAction = ((MCAActionStm) stm).getAction();
					if (realAction instanceof MCACallAction){
						((MCACallAction) realAction).setArgs(realArgs);
					}
				}
			}
			result.append(this.getReplicatedAction().toFormula(option));
		}else if (indexes.size() > 1) {
			MCPCMLExp firstArg = indexes.removeFirst();
			result.append("genPar(");
			MCPAction replicatedAction = this.getReplicatedAction();
			if(replicatedAction instanceof MCAReferenceAction){
				LinkedList<MCPCMLExp> realArgs = new LinkedList<MCPCMLExp>();
				realArgs.add(firstArg);
				((MCAReferenceAction) replicatedAction).setArgs(realArgs);
			} else if (replicatedAction instanceof MCAStmAction){
				LinkedList<MCPCMLExp> realArgs = new LinkedList<MCPCMLExp>();
				realArgs.add(firstArg);
				MCPCMLStm stm = ((MCAStmAction) replicatedAction).getStatement();
				if(stm instanceof MCAActionStm){
					MCPAction realAction = ((MCAActionStm) stm).getAction();
					if (realAction instanceof MCACallAction){
						((MCACallAction) realAction).setArgs(realArgs);
					}
				}
			}
			result.append(this.getReplicatedAction().toFormula(option));
			result.append(",");
			result.append(this.chansetExpression.toFormula(option));
			result.append(",");
			StringBuilder rest = buildReplicatedAction(context,indexes,option);
			result.append(rest.toString());
			result.append(")");
		}
		return result;
	}

}
