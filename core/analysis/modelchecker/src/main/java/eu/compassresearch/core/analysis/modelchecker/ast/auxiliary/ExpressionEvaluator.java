package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import org.overture.ast.types.AIntNumericBasicType;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAEqualsBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAGreaterEqualNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAGreaterNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAIntLiteralExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCALessEqualNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCALessNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCANotEqualsBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class ExpressionEvaluator implements IExpressionEvaluator {

	private static ExpressionEvaluator instance;
	
	
	public static ExpressionEvaluator getInstance(){
		if(instance == null){
			instance = new ExpressionEvaluator();
		}
		return instance;
	}
	
	public String obtainValue(MCPCMLExp expression){
		String result = "";
		if(expression instanceof MCAIntLiteralExp){
			result = this.obtainValue((MCAIntLiteralExp)expression);
		}
		
		return result;
	}
	
	private String obtainValue(MCAIntLiteralExp expression){
		return expression.getValue();
	} 
	
	public boolean canEvaluate(MCAEqualsBinaryExp expression){
		boolean result = false;
		MCPCMLExp left = expression.getLeft();
		MCPCMLExp right = expression.getRight();
		result = left instanceof MCAIntLiteralExp && right instanceof MCAIntLiteralExp;  
		return result;
	}
	
	public boolean canEvaluate(MCANotEqualsBinaryExp expression){
		boolean result = false;
		MCPCMLExp left = expression.getLeft();
		MCPCMLExp right = expression.getRight();
		result = left instanceof MCAIntLiteralExp && right instanceof MCAIntLiteralExp;  
		return result;
	}
	
	public boolean canEvaluate(MCAGreaterEqualNumericBinaryExp expression){
		boolean result = false;
		MCPCMLExp left = expression.getLeft();
		MCPCMLExp right = expression.getRight();
		result = left instanceof MCAIntLiteralExp && right instanceof MCAIntLiteralExp;  
		return result;
	}
	
	public boolean canEvaluate(MCALessNumericBinaryExp expression){
		boolean result = false;
		MCPCMLExp left = expression.getLeft();
		MCPCMLExp right = expression.getRight();
		result = left instanceof MCAIntLiteralExp && right instanceof MCAIntLiteralExp;  
		return result;
	}
	
	public boolean canEvaluate(MCAGreaterNumericBinaryExp expression){
		boolean result = false;
		MCPCMLExp left = expression.getLeft();
		MCPCMLExp right = expression.getRight();
		result = left instanceof MCAIntLiteralExp && right instanceof MCAIntLiteralExp;  
		return result;
	}
	
	public boolean canEvaluate(MCALessEqualNumericBinaryExp expression){
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
	
	public boolean evaluate(MCANotEqualsBinaryExp expression){
		boolean resp = false;
		
		MCPCMLExp left = expression.getLeft();
		MCPCMLExp right = expression.getRight();
		if(left instanceof MCAIntLiteralExp && right instanceof MCAIntLiteralExp){
			String leftValue = ((MCAIntLiteralExp) left).getValue().toString();
			String rightValue = ((MCAIntLiteralExp) right).getValue().toString();
			resp = !(leftValue.equals(rightValue));
		} 
		return resp;
	}
	
	public boolean evaluate(MCAGreaterEqualNumericBinaryExp expression){
		boolean resp = false;
		
		MCPCMLExp left = expression.getLeft();
		MCPCMLExp right = expression.getRight();
		if(left instanceof MCAIntLiteralExp && right instanceof MCAIntLiteralExp){
			Integer leftValue = new Integer(((MCAIntLiteralExp) left).getValue().toString());
			Integer rightValue = new Integer(((MCAIntLiteralExp) right).getValue().toString());
			resp = leftValue >= rightValue;
		} 
		return resp;
	}
	
	public boolean evaluate(MCALessNumericBinaryExp expression){
		boolean resp = false;
		
		MCPCMLExp left = expression.getLeft();
		MCPCMLExp right = expression.getRight();
		if(left instanceof MCAIntLiteralExp && right instanceof MCAIntLiteralExp){
			Integer leftValue = new Integer(((MCAIntLiteralExp) left).getValue().toString());
			Integer rightValue = new Integer(((MCAIntLiteralExp) right).getValue().toString());
			resp = leftValue < rightValue;
		} 
		return resp;
	}
	
	public boolean evaluate(MCAGreaterNumericBinaryExp expression){
		boolean resp = false;
		
		MCPCMLExp left = expression.getLeft();
		MCPCMLExp right = expression.getRight();
		if(left instanceof MCAIntLiteralExp && right instanceof MCAIntLiteralExp){
			Integer leftValue = new Integer(((MCAIntLiteralExp) left).getValue().toString());
			Integer rightValue = new Integer(((MCAIntLiteralExp) right).getValue().toString());
			resp = leftValue > rightValue;
		} 
		return resp;
	}
	
	public boolean evaluate(MCALessEqualNumericBinaryExp expression){
		boolean resp = false;
		
		MCPCMLExp left = expression.getLeft();
		MCPCMLExp right = expression.getRight();
		if(left instanceof MCAIntLiteralExp && right instanceof MCAIntLiteralExp){
			Integer leftValue = new Integer(((MCAIntLiteralExp) left).getValue().toString());
			Integer rightValue = new Integer(((MCAIntLiteralExp) right).getValue().toString());
			resp = leftValue <= rightValue;
		} 
		return resp;
	}
	
	@Override
	public boolean canEvaluate(MCPCMLExp expression) {
		boolean resp = false;
		if(expression instanceof MCAEqualsBinaryExp){
			resp = this.canEvaluate((MCAEqualsBinaryExp)expression);
		} else if(expression instanceof MCAGreaterEqualNumericBinaryExp){
			resp = this.canEvaluate((MCAGreaterEqualNumericBinaryExp)expression);
		} else if(expression instanceof MCANotEqualsBinaryExp){
			resp = this.canEvaluate((MCANotEqualsBinaryExp)expression);
		} else if(expression instanceof MCALessNumericBinaryExp){
			resp = this.canEvaluate((MCALessNumericBinaryExp)expression);
		} else if(expression instanceof MCALessEqualNumericBinaryExp){
			resp = this.canEvaluate((MCALessEqualNumericBinaryExp)expression);
		} else if(expression instanceof MCAGreaterNumericBinaryExp){
			resp = this.canEvaluate((MCAGreaterNumericBinaryExp)expression);
		}
		return resp;
	}

	@Override
	public boolean evaluate(MCPCMLExp expression) {
		boolean resp = false;
		if(expression instanceof MCAEqualsBinaryExp){
			resp = this.evaluate((MCAEqualsBinaryExp)expression);
		} else if(expression instanceof MCAGreaterEqualNumericBinaryExp){
			resp = this.evaluate((MCAGreaterEqualNumericBinaryExp)expression);
		} else if(expression instanceof MCANotEqualsBinaryExp){
			resp = this.evaluate((MCANotEqualsBinaryExp)expression);
		} else if(expression instanceof MCALessNumericBinaryExp){
			resp = this.evaluate((MCALessNumericBinaryExp)expression);
		} else if(expression instanceof MCALessEqualNumericBinaryExp){
			resp = this.evaluate((MCALessEqualNumericBinaryExp)expression);
		} else if(expression instanceof MCAGreaterNumericBinaryExp){
			resp = this.evaluate((MCAGreaterNumericBinaryExp)expression);
		}
		return resp;
	}

}
