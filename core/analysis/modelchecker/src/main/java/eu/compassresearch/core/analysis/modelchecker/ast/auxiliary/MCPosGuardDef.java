package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAOrBooleanBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAVariableExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class MCPosGuardDef extends MCGuardDef{

	public MCPosGuardDef(int counterId, MCPCMLExp condition, MCPAction action) {
		super(counterId, condition, action);
	}
	
	@Override
	public String toFormula(String option) {

		StringBuilder result = new StringBuilder();
		StringBuilder basicString = new StringBuilder();
		
		basicString.append("  guardDef("+ this.counterId +","+ max.toFormula(MCNode.NAMED)+")");
		basicString.append(" :- State(" + max.toFormula(MCNode.NAMED) + "," + parentAction.toFormula(MCNode.DEFAULT) + ")");
		if(!ExpressionEvaluator.getInstance().canEvaluate(condition)){
			basicString.append(",");
			if(condition instanceof MCAVariableExp){
				basicString.append(condition.toFormula(option) + " = " + "true");
				result.append(basicString);
			}else{
				if(condition instanceof MCAOrBooleanBinaryExp){
					//guardDef must be replicated
					//first guardDef
					result.append(basicString);
					result.append(((MCAOrBooleanBinaryExp) condition).getLeft().toFormula(option));
					result.append(".\n");
					//second guardDef
					result.append(basicString);
					result.append(((MCAOrBooleanBinaryExp) condition).getRight().toFormula(option));
				} else{
					result.append(basicString);
					result.append(condition.toFormula(option));
				}
			}
		}else{
			result.append(basicString);
		}
		result.append(".\n");
		
		return result.toString();
	}

		
}
