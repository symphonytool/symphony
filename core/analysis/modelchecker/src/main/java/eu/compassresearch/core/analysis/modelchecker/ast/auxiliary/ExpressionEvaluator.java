package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAEqualsBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAIntLiteralExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class ExpressionEvaluator implements IExpressionEvaluator {

	private static ExpressionEvaluator instance;
	
	
	public static ExpressionEvaluator getInstance(){
		if(instance == null){
			instance = new ExpressionEvaluator();
		}
		return instance;
	}
	
	public boolean canEvaluate(MCAEqualsBinaryExp expression){
		boolean result = false;
		MCPCMLExp left = expression.getLeft();
		MCPCMLExp right = expression.getRight();
		result = left instanceof MCAIntLiteralExp && right instanceof MCAIntLiteralExp;  
		return result;
	}

	public boolean evaluate(MCAEqualsBinaryExp expression){
		boolean resp = false;
		
		MCPCMLExp left = expression.getLeft();
		MCPCMLExp right = expression.getRight();
		if(left instanceof MCAIntLiteralExp && right instanceof MCAIntLiteralExp){
			String leftValue = ((MCAIntLiteralExp) left).getValue().toString();
			String rightValue = ((MCAIntLiteralExp) right).getValue().toString();
			resp = leftValue.equals(rightValue);
		} 
		return resp;
	}
	@Override
	public boolean canEvaluate(MCPCMLExp expression) {
		boolean resp = false;
		if(expression instanceof MCAEqualsBinaryExp){
			resp = this.canEvaluate((MCAEqualsBinaryExp)expression);
		} 
		return resp;
	}

	@Override
	public boolean evaluate(MCPCMLExp expression) {
		boolean resp = false;
		if(expression instanceof MCAEqualsBinaryExp){
			resp = this.evaluate((MCAEqualsBinaryExp)expression);
		} 
		return resp;
	}

}
