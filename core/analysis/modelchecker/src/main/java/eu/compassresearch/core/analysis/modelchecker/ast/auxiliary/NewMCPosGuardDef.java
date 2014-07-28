package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAOrBooleanBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.statements.MCPCMLStm;

public class NewMCPosGuardDef extends NewMCGuardDef{

	public NewMCPosGuardDef(int counterId, MCPCMLExp condition, MCPCMLStm action) {
		super(counterId, condition, action);
	}
	
	@Override
	public String toFormula(String option) {

		StringBuilder result = new StringBuilder();
		StringBuilder basicString = new StringBuilder();
		
		basicString.append("  guardDef("+ this.counterId +","+ max.toFormula(NAMED)+")");
		basicString.append(" :- State(" + max.toFormula(MCNode.NAMED) + "," + parentStm.toFormula(MCNode.DEFAULT) + ")");
		if(!ExpressionEvaluator.getInstance().canEvaluate(condition)){
			basicString.append(",");
			if(condition instanceof MCAOrBooleanBinaryExp){
				//guardNDef must be replicated
				//first guardNDef
				//result.append(basicString);
				//result.append(((MCAOrBooleanBinaryExp) condition).getLeft().toFormula(option));
				//result.append(".\n");
				basicString.append(((MCAOrBooleanBinaryExp) condition).getLeft().toFormula(option));
				basicString.append(".\n");
				//second guardNDef
				result.append(basicString);
				result.append(((MCAOrBooleanBinaryExp) condition).getRight().toFormula(option));
			}else{
				result.append(basicString);
				result.append(condition.toFormula(option));
			}
		}else{
			result.append(basicString);
		}
		result.append(".\n");
		
		return result.toString();
	}
}
