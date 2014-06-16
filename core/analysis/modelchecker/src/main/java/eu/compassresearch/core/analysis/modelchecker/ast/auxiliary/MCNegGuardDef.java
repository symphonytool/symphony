package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAOrBooleanBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class MCNegGuardDef extends MCGuardDef{

	
	public MCNegGuardDef(int counterId, MCPCMLExp condition, MCPAction action) {
		super(counterId, condition, action);
	}
	
	@Override
	public String toFormula(String option) {

		StringBuilder result = new StringBuilder();
		StringBuilder basicString = new StringBuilder();
		
		basicString.append("  guardNDef(" + this.counterId +","+ max.toFormula(MCNode.NAMED)+")");
		basicString.append(" :- State(" + max.toFormula(MCNode.NAMED) + "," + parentAction.toFormula(MCNode.DEFAULT) + ")");
		basicString.append(",");
		MCPCMLExp negCondition = ExpressionNegator.negate(condition); 
		if(negCondition instanceof MCAOrBooleanBinaryExp){
			//guardNDef must be replicated
			//first guardNDef
			result.append(basicString);
			result.append(((MCAOrBooleanBinaryExp) negCondition).getLeft().toFormula(option));
			result.append(".\n");
			//second guardNDef
			result.append(basicString);
			result.append(((MCAOrBooleanBinaryExp) negCondition).getRight().toFormula(option));
		}else{
			result.append(basicString);
			result.append(negCondition.toFormula(option));
		}
		//result.append(negCondition.toFormula(option));
		result.append(".\n");
		
		return result.toString();

	}

	
}
