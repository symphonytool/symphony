package eu.compassresearch.core.analysis.modelchecker.ast.process;

import java.util.LinkedList;

import eu.compassresearch.ast.process.AAlphabetisedParallelismReplicatedProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ExpressionEvaluator;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCATypeSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCPSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCATypeDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAInSetBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCASeqEnumSeqExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCASetEnumSetExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCANamedInvariantType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
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
		if (sDecl instanceof MCATypeSingleDeclaration) {
			MCPCMLType type = ((MCATypeSingleDeclaration) sDecl).getType();
			if(type instanceof MCANamedInvariantType){
				MCATypeDefinition typeDef = context.getTypeDefinition(((MCANamedInvariantType) type).getName());
				MCPCMLExp pExp = typeDef.getInvExpression();
				if (pExp instanceof MCASetEnumSetExp) {
					indexes = ((MCASetEnumSetExp) pExp).getMembers();
				} else if(pExp instanceof MCASeqEnumSeqExp){
					indexes = ((MCASeqEnumSeqExp) pExp).getMembers();
				} else if (pExp instanceof MCAInSetBinaryExp){
					ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
					pExp = ((MCAInSetBinaryExp) pExp).getRight();
					indexes.addAll(evaluator.getValues(pExp));
				}
			}
		}
		
		StringBuilder replicatedProcess = buildReplicatedProcess(context, indexes, option);
		result.append(replicatedProcess.toString());
		
		return result.toString();
	}
	
	private StringBuilder buildReplicatedProcess(NewCMLModelcheckerContext context,
			LinkedList<MCPCMLExp> indexes,String option) {
		 
		StringBuilder result = new StringBuilder();
		
		if(indexes.size() == 1){
			LinkedList<MCPCMLExp> realArgs = new LinkedList<MCPCMLExp>();
			MCPCMLExp firstArg = indexes.removeFirst();
			realArgs.add(firstArg);
			MCPProcess replicatedProc = this.getReplicatedProcess();
			if(replicatedProc instanceof MCAReferenceProcess){
				((MCAReferenceProcess) replicatedProcess).setArgs(realArgs);
			}
			result.append(this.getReplicatedProcess().toFormula(option));
		}else if (indexes.size() > 1) {
			MCPCMLExp firstArg = indexes.removeFirst();
			result.append("genPar(");
			MCPProcess replicatedProc = this.getReplicatedProcess();
			if(replicatedProc instanceof MCAReferenceProcess){
				LinkedList<MCPCMLExp> realArgs = new LinkedList<MCPCMLExp>();
				realArgs.add(firstArg);
				((MCAReferenceProcess) replicatedProcess).setArgs(realArgs);
			}
			result.append(this.getReplicatedProcess().toFormula(option));
			result.append(",");
			result.append(this.chansetExpression.toFormula(option));
			result.append(",");
			StringBuilder rest = buildReplicatedProcess(context,indexes,option);
			result.append(rest.toString());
			result.append(")");
		}
		return result;
	}

}
