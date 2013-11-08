package eu.compassresearch.core.analysis.modelchecker.ast.process;

import java.util.LinkedList;

import eu.compassresearch.ast.process.AAlphabetisedParallelismReplicatedProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCAExpressionSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCPSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCASetEnumSetExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAAlphabetisedParallelismReplicatedProcess extends MCSReplicatedProcessBase {

	private MCPVarsetExpression chansetExpression;
	
	public MCAAlphabetisedParallelismReplicatedProcess(
			LinkedList<MCPSingleDeclaration> declarations,
			MCPProcess replicatedProc, MCPVarsetExpression exp) {
		super(declarations, replicatedProc);
		this.chansetExpression = exp;
	}
	
	@Override
	public String toFormula(String option) {
		AAlphabetisedParallelismReplicatedProcess a = null;
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
		StringBuilder replicatedProcess = buildReplicatedProcess(context, indexes.size(), option);
		result.append(replicatedProcess.toString());
		
		return result.toString();
	}
	
	private StringBuilder buildReplicatedProcess(NewCMLModelcheckerContext context,
			int times,String option) {
		 
		StringBuilder result = new StringBuilder();
		
		if(times == 1){
			result.append(this.getReplicatedProcess().toFormula(option));
		}else if (times > 1) {
			result.append("genPar(");
			result.append(this.getReplicatedProcess().toFormula(option));
			result.append(",");
			result.append(this.chansetExpression.toFormula(option));
			result.append(",");
			StringBuilder rest = buildReplicatedProcess(context,times-1,option);
			result.append(rest.toString());
			result.append(")");
		}
		return result;
	}

}
