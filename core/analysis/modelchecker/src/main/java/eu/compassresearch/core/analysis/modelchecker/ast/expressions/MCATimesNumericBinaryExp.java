package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ExpressionEvaluator;

public class MCATimesNumericBinaryExp extends MCNumericBinaryExp {

	
	public MCATimesNumericBinaryExp(MCPCMLExp left, MCPCMLExp right) {
		super(left, right);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toFormula(String option) {
		//if this expression involves user defined values its translation must consider such values
		StringBuilder result = new StringBuilder();
		ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
		String leftValue = null;
		String rightValue = null;
		if(evaluator.canEvaluate(this.getLeft())){
			leftValue = evaluator.obtainValue(this.getLeft());
		}
		if(evaluator.canEvaluate(this.getRight())){
			rightValue = evaluator.obtainValue(this.getRight());
		}
		if(leftValue != null && rightValue != null){
			int timesResult = Integer.parseInt(leftValue)*Integer.parseInt(rightValue);
			result.append(timesResult);
		}else{
			result.append(this.getLeft().toFormula(option) + "*" + this.getRight().toFormula(option));
		}
		
		return result.toString();
	}

	@Override
	public MCPCMLExp copy() {
		// TODO Auto-generated method stub
		return null;
	}

}
