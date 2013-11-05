package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class MCNegGuardDef extends MCGuardDef{

	
	public MCNegGuardDef(int counterId, MCPCMLExp condition, MCPAction action) {
		super(counterId, condition, action);
	}
	
	@Override
	public String toFormula(String option) {

		StringBuilder result = new StringBuilder();
		result.append("  guardNDef(" + this.counterId +","+ max.toFormula(option)+")");
		result.append(" :- State(" + max.toFormula(option) + "," + parentAction.toFormula(MCNode.DEFAULT) + ")");
		result.append(",");
		MCPCMLExp negCondition = ExpressionNegator.negate(condition); 
		result.append(negCondition.toFormula(option));
		result.append(".\n");
		
		return result.toString();

	}

	
}
