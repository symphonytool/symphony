package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.util.LinkedList;

import org.overture.ast.types.AIntNumericBasicType;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAValueDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAEqualsBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAGreaterEqualNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAGreaterNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAIntLiteralExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCALessEqualNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCALessNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCANotEqualsBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAVariableExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAIntNumericBasicType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCANamedInvariantType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAProductType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLNumericType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCVoidType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class ExpressionEvaluator implements IExpressionEvaluator {

	private static ExpressionEvaluator instance;
	
	public static ExpressionEvaluator getInstance(){
		if(instance == null){
			instance = new ExpressionEvaluator();
		}
		return instance;
	}
	
	
	private MCPCMLType getTypeFor(MCPCMLExp exp){
		MCPCMLType result = null;
		if(exp instanceof MCAIntLiteralExp){
			result = this.getTypeFor((MCAIntLiteralExp)exp);
		} else if(exp instanceof MCAVariableExp){
			result = this.getTypeFor((MCAVariableExp)exp);
		}
		
		return result;
	}
	
	public MCPCMLType instantiateMCType(LinkedList<MCPCMLExp> exps){
		MCPCMLType result = null;
		
		if(exps.size() == 0){
			result = new MCVoidType();
		} else if (exps.size() == 1){
			result = this.getTypeFor(exps.getFirst());
		} else if (exps.size() > 1){
			LinkedList<MCPCMLType> types = new LinkedList<MCPCMLType>();
			for (MCPCMLExp exp : exps) {
				types.add(instantiateMCType(exp));
			}
			result = new MCAProductType(types);
		}
		
		return result;
	}
	public MCPCMLType instantiateMCType(MCPCMLExp exp){
		
		LinkedList<MCPCMLExp> exps = new LinkedList<MCPCMLExp>();
		exps.add(exp);
		
		return this.instantiateMCType(exps);
	}
	private MCPCMLType getTypeFor(MCAIntLiteralExp exp){
		MCPCMLType result = null;
		
		result = new MCAIntNumericBasicType(exp.getValue());
		
		return result;
	}
	private MCPCMLType getTypeFor(MCAVariableExp exp){
		MCPCMLType result = null;
		
		result = new MCANamedInvariantType(exp.getName());
		
		return result;
	}
	
	public String obtainValue(MCPCMLExp expression){
		String result = null;
		
		if(expression instanceof MCAIntLiteralExp){
			result = this.obtainValue((MCAIntLiteralExp)expression);
		} else if(expression instanceof MCAVariableExp){
			result = this.obtainValue((MCAVariableExp)expression);
		}
		
		return result;
	}
	
	private String obtainValue(MCAIntLiteralExp expression){
		return expression.getValue();
	} 

	private String obtainValue(MCAVariableExp expression){
		String result = null;
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		MCAValueDefinition valueDef = context.getValueDefinition(((MCAVariableExp) expression).getName());
		if(valueDef != null){
			result = valueDef.getExpression().toFormula(MCNode.GENERIC);
		}

		return result;
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
		} else if(expression instanceof MCAIntLiteralExp){
			resp = this.canEvaluate((MCAIntLiteralExp)expression);
		} else if(expression instanceof MCAVariableExp){
			resp = this.canEvaluate((MCAVariableExp)expression);
		}
		return resp;
	}

	public boolean canEvaluate(MCAIntLiteralExp expression){
		return true;
	}

	public boolean canEvaluate(MCAVariableExp expression){
		boolean result = false;
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		MCAValueDefinition valueDef = context.getValueDefinition(((MCAVariableExp) expression).getName());
		if(valueDef != null){
			result = true;
		}
		return result;
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
