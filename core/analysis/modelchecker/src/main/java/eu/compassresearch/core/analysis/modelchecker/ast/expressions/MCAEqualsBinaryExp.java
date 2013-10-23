package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import org.overture.ast.expressions.AIntLiteralExp;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCCondition;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCPosGuardDef;
import eu.compassresearch.core.analysis.modelchecker.visitors.ExpressionEvaluator;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewMCExpressionEvaluator;

public class MCAEqualsBinaryExp extends MCNumericBinaryExp  {

	
	public MCAEqualsBinaryExp() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public MCAEqualsBinaryExp(MCPCMLExp left, MCPCMLExp right) {
		super(left, right);
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();

		result.append(this.getLeft().toFormula(option) + " = "+ this.getRight().toFormula(option));

		return result.toString();
	}


	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof MCAEqualsBinaryExp){
			result = this.getLeft().equals(((MCAEqualsBinaryExp) obj).getLeft())
					&& this.getRight().equals(((MCAEqualsBinaryExp) obj).getRight());
		}
		return result;
	}

	
}
