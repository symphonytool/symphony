package eu.compassresearch.core.analysis.modelchecker.visitors;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
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
import org.overture.ast.expressions.APlusNumericBinaryExp;
import org.overture.ast.expressions.ASubtractNumericBinaryExp;
import org.overture.ast.expressions.ATimesNumericBinaryExp;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.AEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AFatEnumVarsetExpression;
import eu.compassresearch.ast.expressions.PCMLExp;

public class MCExpressionVisitor extends
		QuestionAnswerCMLAdaptor<CMLModelcheckerContext, StringBuilder> {
	
	final private QuestionAnswerAdaptor<CMLModelcheckerContext, StringBuilder> rootVisitor;

	public MCExpressionVisitor(QuestionAnswerAdaptor<CMLModelcheckerContext, StringBuilder> parentVisitor){
		this.rootVisitor = parentVisitor;
	}
	
	
	//FOR THE CASE OF EXPRESSIONS WHOSE VISIT METHOD IS NOT IMPLEMENTED
	@Override
	public StringBuilder defaultPCMLExp(PCMLExp node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		throw new ModelcheckerRuntimeException(ModelcheckerErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
	}
	
	@Override
	public StringBuilder caseAPlusNumericBinaryExp(APlusNumericBinaryExp node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		CMLModelcheckerContext localCtxt = new CMLModelcheckerContext(); 
		//String result = node.getLeft().apply(this, localCtxt).toString();
		String result = node.getLeft().apply(this, localCtxt).toString();
		result = eu.compassresearch.core.analysis.modelchecker.graphBuilder.util.Utilities.extractConstructor(result);
		question.getScriptContent().append(result);
		question.getScriptContent().append(" + ");
		localCtxt.reset();
		//result = node.getRight().apply(this, localCtxt).toString();
		result = node.getRight().apply(this, localCtxt).toString();
		result = eu.compassresearch.core.analysis.modelchecker.graphBuilder.util.Utilities.extractConstructor(result);
		question.getScriptContent().append(result);
		
		return question.getScriptContent();
	}
		


	@Override
	public StringBuilder caseASubtractNumericBinaryExp(
			ASubtractNumericBinaryExp node, CMLModelcheckerContext question)
			throws AnalysisException {
		
		CMLModelcheckerContext localCtxt = new CMLModelcheckerContext(); 
		//String result = node.getLeft().apply(this, localCtxt).toString();
		String result = node.getLeft().apply(this, localCtxt).toString();
		result = eu.compassresearch.core.analysis.modelchecker.graphBuilder.util.Utilities.extractConstructor(result);
		question.getScriptContent().append(result);
		question.getScriptContent().append(" - ");
		localCtxt.reset();
		//result = node.getRight().apply(this, localCtxt).toString();
		result = node.getRight().apply(this, localCtxt).toString();
		result = eu.compassresearch.core.analysis.modelchecker.graphBuilder.util.Utilities.extractConstructor(result);
		question.getScriptContent().append(result);
		
		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseAEnumVarsetExpression(AEnumVarsetExpression node,
			CMLModelcheckerContext question) throws AnalysisException {

		question.getScriptContent().append("\"" + node.toString() + "\"");

		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseAFatEnumVarsetExpression(
			AFatEnumVarsetExpression node, CMLModelcheckerContext question)
			throws AnalysisException {

		question.getScriptContent().append("\"" + node.toString() + "\"");
		
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAVariableExp(AVariableExp node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		question.getScriptContent().append("Int("+node.getName()+")");
		
		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseAEqualsBinaryExp(AEqualsBinaryExp node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		PExp left = node.getLeft();
		PExp right = node.getRight();
		String leftValue = "";
		String rightValue = "";
		if(left instanceof AIntLiteralExp){
			leftValue = ((AIntLiteralExp) left).getValue().toString();
			if(right instanceof AIntLiteralExp){
				rightValue = ((AIntLiteralExp) right).getValue().toString();
				if(ExpressionEvaluator.evaluate(node)){
				   question.positiveGuardExps.put(node, leftValue + " = " + rightValue);
				}else{
					question.negativeGuardExps.put(node, leftValue + " != " + rightValue);
				}
			}else if(right instanceof AVariableExp){
				rightValue = ((AVariableExp) right).toString();
				question.positiveGuardExps.put(node, leftValue + " = " + rightValue);
				question.negativeGuardExps.put(node, leftValue + " != " + rightValue);
			}
		} else if (left instanceof AVariableExp){
			leftValue = ((AVariableExp) left).toString();
			if(right instanceof AIntLiteralExp){
				rightValue = ((AIntLiteralExp) right).getValue().toString();
			}else if(right instanceof AVariableExp){
				rightValue = ((AVariableExp) right).toString();
			}
			question.positiveGuardExps.put(node, leftValue + " = " + rightValue);
			question.negativeGuardExps.put(node, leftValue + " != " + rightValue);
		}
		
		
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseANotEqualBinaryExp(ANotEqualBinaryExp node,
			CMLModelcheckerContext question) throws AnalysisException {

		PExp left = node.getLeft();
		PExp right = node.getRight();
		String leftValue = "";
		String rightValue = "";
		if(left instanceof AIntLiteralExp){
			leftValue = ((AIntLiteralExp) left).getValue().toString();
			if(right instanceof AIntLiteralExp){
				rightValue = ((AIntLiteralExp) right).getValue().toString();
				if(ExpressionEvaluator.evaluate(node)){
				   question.positiveGuardExps.put(node, leftValue + " != " + rightValue);
				}else{
					question.negativeGuardExps.put(node, leftValue + " = " + rightValue);
				}
			}else if(right instanceof AVariableExp){
				rightValue = ((AVariableExp) right).toString();
				question.positiveGuardExps.put(node, leftValue + " != " + rightValue);
				question.negativeGuardExps.put(node, leftValue + " = " + rightValue);
			}
		} else if (left instanceof AVariableExp){
			leftValue = ((AVariableExp) left).toString();
			if(right instanceof AIntLiteralExp){
				rightValue = ((AIntLiteralExp) right).getValue().toString();
			}else if(right instanceof AVariableExp){
				rightValue = ((AVariableExp) right).toString();
			}
			question.positiveGuardExps.put(node, leftValue + " != " + rightValue);
			question.negativeGuardExps.put(node, leftValue + " = " + rightValue);
		}

		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAGreaterNumericBinaryExp(
			AGreaterNumericBinaryExp node, CMLModelcheckerContext question)
			throws AnalysisException {

		PExp left = node.getLeft();
		PExp right = node.getRight();
		String leftValue = "";
		String rightValue = "";
		if(left instanceof AIntLiteralExp){
			leftValue = ((AIntLiteralExp) left).getValue().toString();
			if(right instanceof AIntLiteralExp){
				rightValue = ((AIntLiteralExp) right).getValue().toString();
				if(ExpressionEvaluator.evaluate(node)){
					question.positiveGuardExps.put(node, leftValue + " > " + rightValue);
				}else{
					question.negativeGuardExps.put(node, leftValue + " <= " + rightValue);
				}
			}else if(right instanceof AVariableExp){
				rightValue = ((AVariableExp) right).toString();
				question.positiveGuardExps.put(node, leftValue + " > " + rightValue);
				question.negativeGuardExps.put(node, leftValue + " <= " + rightValue);
			}
		} else if (left instanceof AVariableExp){
			leftValue = ((AVariableExp) left).toString();
			if(right instanceof AIntLiteralExp){
				rightValue = ((AIntLiteralExp) right).getValue().toString();
			}else if(right instanceof AVariableExp){
				rightValue = ((AVariableExp) right).toString();
			}
			question.positiveGuardExps.put(node, leftValue + " > " + rightValue);
			question.negativeGuardExps.put(node, leftValue + " <= " + rightValue);
		}
		
		
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAGreaterEqualNumericBinaryExp(
			AGreaterEqualNumericBinaryExp node, CMLModelcheckerContext question)
			throws AnalysisException {
		PExp left = node.getLeft();
		PExp right = node.getRight();
		String leftValue = "";
		String rightValue = "";
		if(left instanceof AIntLiteralExp){
			leftValue = ((AIntLiteralExp) left).getValue().toString();
			if(right instanceof AIntLiteralExp){
				rightValue = ((AIntLiteralExp) right).getValue().toString();
				if(ExpressionEvaluator.evaluate(node)){
					question.positiveGuardExps.put(node, leftValue + " >= " + rightValue);
				}else{
					question.negativeGuardExps.put(node, leftValue + " < " + rightValue);
				}
			}else if(right instanceof AVariableExp){
				rightValue = ((AVariableExp) right).toString();
				question.positiveGuardExps.put(node, leftValue + " >= " + rightValue);
				question.negativeGuardExps.put(node, leftValue + " < " + rightValue);
			}
		} else if (left instanceof AVariableExp){
			leftValue = ((AVariableExp) left).toString();
			if(right instanceof AIntLiteralExp){
				rightValue = ((AIntLiteralExp) right).getValue().toString();
			}else if(right instanceof AVariableExp){
				rightValue = ((AVariableExp) right).toString();
			}
			question.positiveGuardExps.put(node, leftValue + " >= " + rightValue);
			question.negativeGuardExps.put(node, leftValue + " < " + rightValue);
		}

		
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseALessEqualNumericBinaryExp(
			ALessEqualNumericBinaryExp node, CMLModelcheckerContext question)
			throws AnalysisException {
		
		PExp left = node.getLeft();
		PExp right = node.getRight();
		String leftValue = "";
		String rightValue = "";
		if(left instanceof AIntLiteralExp){
			leftValue = ((AIntLiteralExp) left).getValue().toString();
			if(right instanceof AIntLiteralExp){
				rightValue = ((AIntLiteralExp) right).getValue().toString();
				if(ExpressionEvaluator.evaluate(node)){
					question.positiveGuardExps.put(node, leftValue + " <= " + rightValue);
				}else{
					question.negativeGuardExps.put(node, leftValue + " > " + rightValue);
				}
			}else if(right instanceof AVariableExp){
				rightValue = ((AVariableExp) right).toString();
				question.positiveGuardExps.put(node, leftValue + " <= " + rightValue);
				question.negativeGuardExps.put(node, leftValue + " > " + rightValue);
			}
		} else if (left instanceof AVariableExp){
			leftValue = ((AVariableExp) left).toString();
			if(right instanceof AIntLiteralExp){
				rightValue = ((AIntLiteralExp) right).getValue().toString();
			}else if(right instanceof AVariableExp){
				rightValue = ((AVariableExp) right).toString();
			}
			question.positiveGuardExps.put(node, leftValue + " <= " + rightValue);
			question.negativeGuardExps.put(node, leftValue + " > " + rightValue);
		}

		
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseALessNumericBinaryExp(ALessNumericBinaryExp node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		PExp left = node.getLeft();
		PExp right = node.getRight();
		String leftValue = "";
		String rightValue = "";
		if(left instanceof AIntLiteralExp){
			leftValue = ((AIntLiteralExp) left).getValue().toString();
			if(right instanceof AIntLiteralExp){
				rightValue = ((AIntLiteralExp) right).getValue().toString();
				if(ExpressionEvaluator.evaluate(node)){
					question.positiveGuardExps.put(node, leftValue + " < " + rightValue);
				}else{
					question.negativeGuardExps.put(node, leftValue + " >= " + rightValue);
				}
			}else if(right instanceof AVariableExp){
				rightValue = ((AVariableExp) right).toString();
				question.positiveGuardExps.put(node, leftValue + " < " + rightValue);
				question.negativeGuardExps.put(node, leftValue + " >= " + rightValue);
			}
		} else if (left instanceof AVariableExp){
			leftValue = ((AVariableExp) left).toString();
			if(right instanceof AIntLiteralExp){
				rightValue = ((AIntLiteralExp) right).getValue().toString();
			}else if(right instanceof AVariableExp){
				rightValue = ((AVariableExp) right).toString();
			}
			question.positiveGuardExps.put(node, leftValue + " < " + rightValue);
			question.negativeGuardExps.put(node, leftValue + " >= " + rightValue);
		}

		
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseABooleanConstExp(ABooleanConstExp node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		if(ExpressionEvaluator.evaluate(node)){
			//nothing todo
		}else{
			question.getScriptContent().append("GUARDNDEF#");
		}
		return super.caseABooleanConstExp(node, question);
	}

	@Override
	public StringBuilder caseAAndBooleanBinaryExp(AAndBooleanBinaryExp node,
			CMLModelcheckerContext question) throws AnalysisException {
		PExp left = node.getLeft();
		PExp right = node.getRight();
		if(ExpressionEvaluator.evaluate(node)){
			question.getScriptContent().append("," + left.toString() + " , "+ right.toString()+" = true");
		}else{
			question.getScriptContent().append("GUARDNDEF#,"+left.toString()+" , "+right.toString()+" = false");
		}

		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseANotUnaryExp(ANotUnaryExp node,
			CMLModelcheckerContext question) throws AnalysisException {
		
	
		if(ExpressionEvaluator.evaluate(node)){
			//nothing todo
		}else{
			question.getScriptContent().append("GUARDNDEF#");
		}

		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAOrBooleanBinaryExp(AOrBooleanBinaryExp node,
			CMLModelcheckerContext question) throws AnalysisException {
		PExp left = node.getLeft();
		PExp right = node.getRight();
		if(ExpressionEvaluator.evaluate(node)){
			question.getScriptContent().append(left.toString() + " or "+ right.toString()+" = true");
		}else{
			question.getScriptContent().append("GUARDNDEF#"+left.toString()+" or "+right.toString()+" = false");
		}

		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseAIntLiteralExp(AIntLiteralExp node,
			CMLModelcheckerContext question) throws AnalysisException {

		question.getScriptContent().append("Int(");
		question.getScriptContent().append(node.getValue());
		question.getScriptContent().append(")");

		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseATimesNumericBinaryExp(
			ATimesNumericBinaryExp node, CMLModelcheckerContext question)
			throws AnalysisException {
		int first = question.getScriptContent().indexOf("#");
		String aux = node.getLeft().toString()+"("+node.getLeft().toString().toLowerCase()+"), "+node.getRight().toString()+"("+node.getRight().toString().toLowerCase()+")";
		String aux2 = node.getLeft().toString().toLowerCase()+" "+node.getOp().toString()+" "+node.getRight().toString().toLowerCase();
		question.getScriptContent().replace(first, first+1, aux);
		int second = question.getScriptContent().indexOf("@");
		question.getScriptContent().replace(second, second+1, aux2);
		return question.getScriptContent();
	}


	@Override
	public StringBuilder createNewReturnValue(INode node,
			CMLModelcheckerContext question) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public StringBuilder createNewReturnValue(Object node,
			CMLModelcheckerContext question) throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
