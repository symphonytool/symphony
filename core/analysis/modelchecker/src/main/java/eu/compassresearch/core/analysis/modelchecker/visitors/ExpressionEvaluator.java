package eu.compassresearch.core.analysis.modelchecker.visitors;

import org.overture.ast.expressions.AAndBooleanBinaryExp;
import org.overture.ast.expressions.ABooleanConstExp;
import org.overture.ast.expressions.AEqualsBinaryExp;
import org.overture.ast.expressions.AGreaterEqualNumericBinaryExp;
import org.overture.ast.expressions.AGreaterNumericBinaryExp;
import org.overture.ast.expressions.AIntLiteralExp;
import org.overture.ast.expressions.ALessEqualNumericBinaryExp;
import org.overture.ast.expressions.ALessNumericBinaryExp;
import org.overture.ast.expressions.ANotEqualBinaryExp;
import org.overture.ast.expressions.ANotUnaryExp;
import org.overture.ast.expressions.AOrBooleanBinaryExp;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;

/**
 * A class intented to perform simple evaluations over expressions. 
 * @author Adalberto
 *
 */
public class ExpressionEvaluator {
	//a generic method that always returns false. if the nod does not matches with any 
	//node handled by the overloaded method, this evaluate method is invoked.
	public static boolean evaluate(PExp node){
		boolean resp = false;

		return resp;
	}
	
	public static boolean evaluate(AEqualsBinaryExp node){
		boolean resp = false;
		
		PExp left = node.getLeft();
		PExp right = node.getRight();
		if(left instanceof AIntLiteralExp){
			String leftValue = ((AIntLiteralExp) left).getValue().toString();
			if(right instanceof AIntLiteralExp){
				String rightValue = ((AIntLiteralExp) right).getValue().toString();
				if(leftValue.equals(rightValue)){
					resp = true;
				}
			}else if(right instanceof AVariableExp){
				String rightValue = ((AVariableExp) right).toString();
			}
		} else if(left instanceof AVariableExp){
			if(right instanceof AIntLiteralExp){
				String leftValue = ((AIntLiteralExp) left).getValue().toString();
				String rightValue = ((AIntLiteralExp) right).getValue().toString();
				if(leftValue.equals(rightValue)){
					resp = true;
				}
			}else if(right instanceof AVariableExp){
				
			}
		}
		return resp;
	}
	
	public static boolean evaluate(ANotEqualBinaryExp node){
		boolean resp = false;
		
		PExp left = node.getLeft();
		PExp right = node.getRight();
		if(left instanceof AIntLiteralExp){
			if(right instanceof AIntLiteralExp){
				String leftValue = ((AIntLiteralExp) left).getValue().toString();
				String rightValue = ((AIntLiteralExp) right).getValue().toString();
				if(!leftValue.equals(rightValue)){
					resp = true;
				}
			}
		}
		return resp;
	}
	public static boolean evaluate(AGreaterNumericBinaryExp node){
		boolean resp = false;
		
		PExp left = node.getLeft();
		PExp right = node.getRight();
		if(left instanceof AIntLiteralExp){
			if(right instanceof AIntLiteralExp){
				Integer leftValue = new Integer(((AIntLiteralExp) left).getValue().toString());
				Integer rightValue = new Integer(((AIntLiteralExp) right).getValue().toString());
				if(leftValue > rightValue){
					resp = true;
				}
			}
		}
		return resp;
	}
	
	public static boolean evaluate(AGreaterEqualNumericBinaryExp node){
		boolean resp = false;
		
		PExp left = node.getLeft();
		PExp right = node.getRight();
		if(left instanceof AIntLiteralExp){
			if(right instanceof AIntLiteralExp){
				Integer leftValue = new Integer(((AIntLiteralExp) left).getValue().toString());
				Integer rightValue = new Integer(((AIntLiteralExp) right).getValue().toString());
				if(leftValue >= rightValue){
					resp = true;
				}
			}
		}
		return resp;
	}
	
	public static boolean evaluate(ALessNumericBinaryExp node){
		boolean resp = false;
		
		PExp left = node.getLeft();
		PExp right = node.getRight();
		if(left instanceof AIntLiteralExp){
			if(right instanceof AIntLiteralExp){
				Integer leftValue = new Integer(((AIntLiteralExp) left).getValue().toString());
				Integer rightValue = new Integer(((AIntLiteralExp) right).getValue().toString());
				if(leftValue < rightValue){
					resp = true;
				}
			}
		}
		return resp;
	}
	
	public static boolean evaluate(ALessEqualNumericBinaryExp node){
		boolean resp = false;
		
		PExp left = node.getLeft();
		PExp right = node.getRight();
		if(left instanceof AIntLiteralExp){
			if(right instanceof AIntLiteralExp){
				Integer leftValue = new Integer(((AIntLiteralExp) left).getValue().toString());
				Integer rightValue = new Integer(((AIntLiteralExp) right).getValue().toString());
				if(leftValue <= rightValue){
					resp = true;
				}
			}
		}
		return resp;
	}
	
	public static boolean evaluate(ABooleanConstExp node){
		boolean resp = false;
		
		Boolean b = new Boolean(node.toString());
		
		return b.booleanValue();
	}
	
	public static boolean evaluate(ANotUnaryExp node){
		boolean resp = false;
		
		PExp exp = node.getExp();
		resp = !evaluate(exp) ;
		return resp;
	}
	public static boolean evaluate(AAndBooleanBinaryExp node){
		boolean resp = false;
		
		PExp left = node.getLeft();
		PExp right = node.getRight();
		resp = evaluate(left) && evaluate(right);
		return resp;
	}
	public static boolean evaluate(AOrBooleanBinaryExp node){
		boolean resp = false;
		
		PExp left = node.getLeft();
		PExp right = node.getRight();
		resp = evaluate(left) || evaluate(right);
		return resp;
	}
	
	
	
}
