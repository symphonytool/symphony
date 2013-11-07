package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.expressions.AEqualsBinaryExp;
import org.overture.ast.expressions.AGreaterEqualNumericBinaryExp;
import org.overture.ast.expressions.AGreaterNumericBinaryExp;
import org.overture.ast.expressions.AInSetBinaryExp;
import org.overture.ast.expressions.AIntLiteralExp;
import org.overture.ast.expressions.ALessEqualNumericBinaryExp;
import org.overture.ast.expressions.ALessNumericBinaryExp;
import org.overture.ast.expressions.ANotEqualBinaryExp;
import org.overture.ast.expressions.APlusNumericBinaryExp;
import org.overture.ast.expressions.ASeqEnumSeqExp;
import org.overture.ast.expressions.ASetEnumSetExp;
import org.overture.ast.expressions.ASetRangeSetExp;
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
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAEnumVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAEqualsBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAFatEnumVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAGreaterEqualNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAGreaterNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAInSetBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAIntLiteralExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCALessEqualNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCALessNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCANameChannelExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCANotEqualsBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAPlusNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCASeqEnumSeqExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCASetEnumSetExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCASetRangeSetExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCASubtractNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCATimesNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAVariableExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

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
	
	
	@Override
	public MCNode caseAPlusNumericBinaryExp(APlusNumericBinaryExp node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCPCMLExp left = (MCPCMLExp) node.getLeft().apply(this, question);
		MCPCMLExp right = (MCPCMLExp) node.getRight().apply(this, question);
		MCAPlusNumericBinaryExp result = new MCAPlusNumericBinaryExp(left,right);
		
		return result;
	}
		

	@Override
	public MCNode caseASubtractNumericBinaryExp(
			ASubtractNumericBinaryExp node, NewCMLModelcheckerContext question)
			throws AnalysisException {
		
		MCPCMLExp left = (MCPCMLExp) node.getLeft().apply(this, question);
		MCPCMLExp right = (MCPCMLExp) node.getRight().apply(this, question);
		MCASubtractNumericBinaryExp result = new MCASubtractNumericBinaryExp(left,right);
		
		return result;
		
	}
	
	@Override
	public MCNode caseASeqEnumSeqExp(ASeqEnumSeqExp node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		LinkedList<PExp> members = node.getMembers();
		LinkedList<MCPCMLExp> memb = new LinkedList<MCPCMLExp>();
		LinkedList<PType> typ = node.getTypes();
		LinkedList<MCPCMLType> types = new LinkedList<MCPCMLType>();
		for(PExp e: members){
			memb.add((MCPCMLExp)e.apply(this,question));
		}
		for(PType t: typ){
			types.add((MCPCMLType)t.apply(this, question));
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
	

	@Override
	public MCNode caseAVariableExp(AVariableExp node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		return new MCAVariableExp(node.getName().toString());
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


	*/

	@Override
	public MCNode caseAIntLiteralExp(AIntLiteralExp node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		return new MCAIntLiteralExp(node.getValue().toString()); 
	}

	@Override
	public MCNode caseATimesNumericBinaryExp(
			ATimesNumericBinaryExp node, NewCMLModelcheckerContext question)
			throws AnalysisException {
		
		MCPCMLExp left = (MCPCMLExp) node.getLeft().apply(this, question);
		MCPCMLExp right = (MCPCMLExp) node.getRight().apply(this, question);
		MCATimesNumericBinaryExp result = new MCATimesNumericBinaryExp(left, right);
			
		return result;
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
	public MCNode caseAInSetBinaryExp(AInSetBinaryExp node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCPCMLExp left = (MCPCMLExp) node.getLeft().apply(this, question);
		MCPCMLExp right = (MCPCMLExp) node.getRight().apply(this, question);
		MCAInSetBinaryExp result = new MCAInSetBinaryExp(left,right);
		
		return result;
	}

	

	@Override
	public MCNode caseASetRangeSetExp(ASetRangeSetExp node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		MCPCMLExp first = (MCPCMLExp) node.getFirst().apply(this, question);
		MCPCMLExp last = (MCPCMLExp) node.getLast().apply(this, question);
		
		MCASetRangeSetExp result = new MCASetRangeSetExp(first, last);
		
		return result;
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
