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

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCABooleanConstExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAEqualsBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAGreaterEqualNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAGreaterNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAIntLiteralExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCALessEqualNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCALessNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCANotEqualBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCANotUnaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAVariableExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

/**
 * A class intented to perform simple evaluations over expressions. 
 * @author Adalberto
 *
 */
public class NewMCExpressionEvaluator {
	//a generic method that always returns false. if the nod does not matches with any 
	//node handled by the overloaded method, this evaluate method is invoked.
	public static boolean evaluate(MCPCMLExp node){
		boolean resp = false;

		return resp;
	}
	
	public static boolean evaluate(MCAEqualsBinaryExp node){
		boolean resp = false;
		
		MCPCMLExp left = node.getLeft();
		MCPCMLExp right = node.getRight();
		if(left instanceof MCAIntLiteralExp){
			String leftValue = ((MCAIntLiteralExp) left).getValue().toString();
			if(right instanceof MCAIntLiteralExp){
				String rightValue = ((MCAIntLiteralExp) right).getValue().toString();
				if(leftValue.equals(rightValue)){
					resp = true;
				}
			}else if(right instanceof MCAVariableExp){
				String rightValue = ((MCAVariableExp) right).toString();
				//TODO something to do here
			}
		} else if(left instanceof MCAVariableExp){
			if(right instanceof MCAIntLiteralExp){
				String leftValue = ((MCAIntLiteralExp) left).getValue().toString();
				String rightValue = ((MCAIntLiteralExp) right).getValue().toString();
				if(leftValue.equals(rightValue)){
					resp = true;
				}
			}else if(right instanceof MCAVariableExp){
				//TODO something to do here
			}
		}
		return resp;
	}
	
	public static boolean evaluate(MCANotEqualBinaryExp node){
		boolean resp = false;
		
		MCPCMLExp left = node.getLeft();
		MCPCMLExp right = node.getRight();
		if(left instanceof MCAIntLiteralExp){
			if(right instanceof MCAIntLiteralExp){
				String leftValue = ((MCAIntLiteralExp) left).getValue().toString();
				String rightValue = ((MCAIntLiteralExp) right).getValue().toString();
				if(!leftValue.equals(rightValue)){
					resp = true;
				}
			}
		}
		return resp;
	}
	public static boolean evaluate(MCAGreaterNumericBinaryExp node){
		boolean resp = false;
		
		MCPCMLExp left = node.getLeft();
		MCPCMLExp right = node.getRight();
		if(left instanceof MCAIntLiteralExp){
			if(right instanceof MCAIntLiteralExp){
				Integer leftValue = new Integer(((MCAIntLiteralExp) left).getValue().toString());
				Integer rightValue = new Integer(((MCAIntLiteralExp) right).getValue().toString());
				if(leftValue > rightValue){
					resp = true;
				}
			}
		}
		return resp;
	}
	
	public static boolean evaluate(MCAGreaterEqualNumericBinaryExp node){
		boolean resp = false;
		
		MCPCMLExp left = node.getLeft();
		MCPCMLExp right = node.getRight();
		if(left instanceof MCAIntLiteralExp){
			if(right instanceof MCAIntLiteralExp){
				Integer leftValue = new Integer(((MCAIntLiteralExp) left).getValue().toString());
				Integer rightValue = new Integer(((MCAIntLiteralExp) right).getValue().toString());
				if(leftValue >= rightValue){
					resp = true;
				}
			}
		}
		return resp;
	}
	
	public static boolean evaluate(MCALessNumericBinaryExp node){
		boolean resp = false;
		
		MCPCMLExp left = node.getLeft();
		MCPCMLExp right = node.getRight();
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
	
	public static boolean evaluate(MCALessEqualNumericBinaryExp node){
		boolean resp = false;
		
		MCPCMLExp left = node.getLeft();
		MCPCMLExp right = node.getRight();
		if(left instanceof MCAIntLiteralExp){
			if(right instanceof AIntLiteralExp){
				Integer leftValue = new Integer(((MCAIntLiteralExp) left).getValue().toString());
				Integer rightValue = new Integer(((MCAIntLiteralExp) right).getValue().toString());
				if(leftValue <= rightValue){
					resp = true;
				}
			}
		}
		return resp;
	}
	
	public static boolean evaluate(MCABooleanConstExp node){
		Boolean b = new Boolean(node.toString());
		return b.booleanValue();
	}
	
	public static boolean evaluate(MCANotUnaryExp node){
		boolean resp = false;
		
		MCPCMLExp exp = node.getExp();
		resp = !evaluate(exp) ;
		return resp;
	}
	/*
	public static boolean evaluate(MCAAndBooleanBinaryExp node){
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
	*/
	
	
}
