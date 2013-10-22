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
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		StringBuilder result = new StringBuilder();
		MCPCMLExp left = this.getLeft();
		MCPCMLExp right = this.getRight();
		String leftValue = "";
		String rightValue = "";
		if(left instanceof MCAIntLiteralExp){
			leftValue = ((MCAIntLiteralExp) left).getValue().toString();
			if(right instanceof MCAIntLiteralExp){
				rightValue = ((MCAIntLiteralExp) right).getValue().toString();
				if(NewMCExpressionEvaluator.evaluate(this)){
					result.append(getLeft().toFormula(option) + " = "+getRight().toFormula(option));
				}else{
					result.append(getLeft().toFormula(option) + " != "+getRight().toFormula(option));
				}
			}else if(right instanceof MCAVariableExp){
				rightValue = ((MCAVariableExp) right).toString();
				result.append(leftValue + " = " + rightValue);
				result.append(leftValue + " != " + rightValue);
			}
		} else if (left instanceof MCAVariableExp){
			leftValue = ((MCAVariableExp) left).toString();
			if(right instanceof MCAIntLiteralExp){
				rightValue = ((MCAIntLiteralExp) right).getValue().toString();
			}else if(right instanceof MCAVariableExp){
				rightValue = ((MCAVariableExp) right).toString();
			}
			result.append(leftValue + " = " + rightValue);
			result.append(leftValue + " != " + rightValue);
		}
		
		
		return result.toString();
	}

}
