package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.Iterator;
import java.util.LinkedList;

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
import org.overture.ast.expressions.ASeqEnumSeqExp;
import org.overture.ast.expressions.ASetEnumSetExp;
import org.overture.ast.expressions.ASubtractNumericBinaryExp;
import org.overture.ast.expressions.ATimesNumericBinaryExp;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.ABracketedExp;
import eu.compassresearch.ast.expressions.AEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AFatEnumVarsetExpression;
import eu.compassresearch.ast.expressions.ANameChannelExp;
import eu.compassresearch.ast.expressions.PCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCCondition;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCNegGuardDef;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCPosGuardDef;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCType;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAEnumVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAEqualsBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAFatEnumVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAGreaterEqualNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAGreaterNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAIntLiteralExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCALessEqualNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCALessNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCANameChannelExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCANotEqualsBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCASeqEnumSeqExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCASetEnumSetExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class NewMCExpressionVisitor extends
QuestionAnswerCMLAdaptor<NewCMLModelcheckerContext, MCNode> {
	
	final private QuestionAnswerAdaptor<NewCMLModelcheckerContext, MCNode> rootVisitor;

	public NewMCExpressionVisitor(QuestionAnswerAdaptor<NewCMLModelcheckerContext, MCNode> parentVisitor){
		this.rootVisitor = parentVisitor;
	}
	
	
	//FOR THE CASE OF EXPRESSIONS WHOSE VISIT METHOD IS NOT IMPLEMENTED
	@Override
	public MCNode defaultPCMLExp(PCMLExp node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		throw new ModelcheckerRuntimeException(ModelcheckerErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
	}
	
	/*
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
	*/
	
	@Override
	public MCNode caseASeqEnumSeqExp(ASeqEnumSeqExp node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		LinkedList<PExp> members = node.getMembers();
		LinkedList<MCPCMLExp> memb = new LinkedList<MCPCMLExp>();
		LinkedList<PType> typ = node.getTypes();
		LinkedList<MCType> types = new LinkedList<MCType>();
		for(PExp e: members){
			memb.add((MCPCMLExp)e.apply(this,question));
		}
		for(PType t: typ){
			types.add((MCType)t.apply(this, question));
		}
		return new MCASeqEnumSeqExp(memb, types);
	}
	
	@Override
	public MCNode caseASetEnumSetExp(ASetEnumSetExp node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		LinkedList<PExp> memb = node.getMembers();
		LinkedList<MCPCMLExp> members = new LinkedList<MCPCMLExp>();
		for(PExp e: memb){
			members.add((MCPCMLExp)e.apply(this,question));
		}		
		return new MCASetEnumSetExp(members);
	}


	@Override
	public MCNode caseAEnumVarsetExpression(AEnumVarsetExpression node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		LinkedList<ANameChannelExp> nameExp = node.getChannelNames();
		LinkedList<MCANameChannelExp> mcNameExp = new LinkedList<MCANameChannelExp>();
		for(ANameChannelExp n : nameExp){
			mcNameExp.add((MCANameChannelExp)n.apply(this, question));
		}
		return new MCAEnumVarsetExpression(mcNameExp);
	}
	
	
	@Override
	public MCNode caseAEqualsBinaryExp(AEqualsBinaryExp node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCPCMLExp left = (MCPCMLExp) node.getLeft().apply(this, question);
		MCPCMLExp right = (MCPCMLExp) node.getRight().apply(this, question);
		MCAEqualsBinaryExp result = new MCAEqualsBinaryExp(left, right);
		
		return result;
	}
	
	@Override
	public MCNode caseANotEqualBinaryExp(ANotEqualBinaryExp node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		MCPCMLExp left = (MCPCMLExp) node.getLeft().apply(this, question);
		MCPCMLExp right = (MCPCMLExp) node.getRight().apply(this, question);
		MCANotEqualsBinaryExp result = new MCANotEqualsBinaryExp(left, right);
		
		return result;
	}
	
	@Override
	public MCNode caseAGreaterEqualNumericBinaryExp(
			AGreaterEqualNumericBinaryExp node, NewCMLModelcheckerContext question)
			throws AnalysisException {
		
		MCPCMLExp left = (MCPCMLExp) node.getLeft().apply(this, question);
		MCPCMLExp right = (MCPCMLExp) node.getRight().apply(this, question);
		MCAGreaterEqualNumericBinaryExp result = new MCAGreaterEqualNumericBinaryExp(left, right);
		
		return result;
	}
	
	@Override
	public MCNode caseALessNumericBinaryExp(ALessNumericBinaryExp node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCPCMLExp left = (MCPCMLExp) node.getLeft().apply(this, question);
		MCPCMLExp right = (MCPCMLExp) node.getRight().apply(this, question);
		MCALessNumericBinaryExp result = new MCALessNumericBinaryExp(left, right);
		
		return result;
	}
	
	@Override
	public MCNode caseAGreaterNumericBinaryExp(
			AGreaterNumericBinaryExp node, NewCMLModelcheckerContext question)
			throws AnalysisException {
		MCPCMLExp left = (MCPCMLExp) node.getLeft().apply(this, question);
		MCPCMLExp right = (MCPCMLExp) node.getRight().apply(this, question);
		MCAGreaterNumericBinaryExp result = new MCAGreaterNumericBinaryExp(left, right);
		
		return result;
		
	}

	@Override
	public MCNode caseALessEqualNumericBinaryExp(
			ALessEqualNumericBinaryExp node, NewCMLModelcheckerContext question)
			throws AnalysisException {
	
		MCPCMLExp left = (MCPCMLExp) node.getLeft().apply(this, question);
		MCPCMLExp right = (MCPCMLExp) node.getRight().apply(this, question);
		MCALessEqualNumericBinaryExp result = new MCALessEqualNumericBinaryExp(left, right);
		
		return result;
		
	}
	
	@Override
	public MCNode caseAFatEnumVarsetExpression(
			AFatEnumVarsetExpression node, NewCMLModelcheckerContext question)
			throws AnalysisException {

		
		LinkedList<MCANameChannelExp> channelNames = new LinkedList<MCANameChannelExp>();
		for (ANameChannelExp chanNameExp : node.getChannelNames()) {
			channelNames.add((MCANameChannelExp) chanNameExp.apply(rootVisitor, question));
		}
		
		MCAFatEnumVarsetExpression result = new MCAFatEnumVarsetExpression(channelNames);
		
		return result;
	}
	
	/*
	

	@Override
	public StringBuilder caseAVariableExp(AVariableExp node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		question.getScriptContent().append("Int("+node.getName()+")");
		
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

	*/

	@Override
	public MCNode caseAIntLiteralExp(AIntLiteralExp node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		return new MCAIntLiteralExp(node.getValue().toString()); 
	}
	
	@Override
	public MCNode caseABracketedExp(ABracketedExp node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		return node.getExpression().apply(this,question);
	}

	@Override
	public MCNode caseANameChannelExp(ANameChannelExp node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		String name = node.getIdentifier().toString();
		LinkedList<PExp> exp = node.getExpressions();
		LinkedList<MCPCMLExp> mcExp = new LinkedList<MCPCMLExp>();
		for(PExp e: exp){
			mcExp.add((MCPCMLExp)e);
		}
		return new MCANameChannelExp(name, mcExp);
	}


	@Override
	public MCNode createNewReturnValue(INode node,
			NewCMLModelcheckerContext question) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public MCNode createNewReturnValue(Object node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
