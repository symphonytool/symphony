package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class MCPosGuardDef extends MCGuardDef{

	public MCPosGuardDef(int counterId, MCPCMLExp condition, MCPAction action) {
		super(counterId, condition, action);
	}
	
	@Override
	public String toFormula(String option) {

		StringBuilder result = new StringBuilder();
		result.append("  guardDef(l,"+ this.counterId +","+ max.toFormula(option)+")");
		result.append(" :- State(l," + max.toFormula(MCNode.GENERIC) + ",pName," + parentAction.toFormula(MCNode.GENERIC) + ")");
		if(!ExpressionEvaluator.getInstance().canEvaluate(condition)){
			result.append(",");
			result.append(condition.toFormula(option));
		}
		result.append(".\n");
		
		return result.toString();
	}

		
}
