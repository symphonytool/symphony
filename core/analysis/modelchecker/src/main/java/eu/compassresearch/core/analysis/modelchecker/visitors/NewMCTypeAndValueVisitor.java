package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.node.INode;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.ANatNumericBasicType;
import org.overture.ast.types.AProductType;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.ast.types.PCMLType;
import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCANatNumericBasicType;

public class NewMCTypeAndValueVisitor extends
		QuestionAnswerCMLAdaptor<NewCMLModelcheckerContext, MCNode> {

	final private QuestionAnswerAdaptor<NewCMLModelcheckerContext, MCNode> rootVisitor;
	
	public NewMCTypeAndValueVisitor(QuestionAnswerAdaptor<NewCMLModelcheckerContext, MCNode> parentVisitor){
		this.rootVisitor = parentVisitor;
	}
	
	//FOR THE CASE OF TYPES WHOSE VISIT METHOD IS NOT IMPLEMENTED
	@Override
	public MCNode defaultPCMLType(PCMLType node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		throw new ModelcheckerRuntimeException(ModelcheckerErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
	}
	
	///// TYPES
	@Override
	public MCNode caseANatNumericBasicType(ANatNumericBasicType node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		return new MCANatNumericBasicType();
	}

	/*
	@Override
	public StringBuilder caseAIntNumericBasicType(AIntNumericBasicType node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		//question.getScriptContent().append("int");
		question.getScriptContent().append("Natural");
		
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseANamedInvariantType(ANamedInvariantType node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		//question.getScriptContent().append("primitive ");
		question.getScriptContent().append(node.getName());
		//question.getScriptContent().append("  ::= (");
		//node.getType().apply(this, question);
		//question.getScriptContent().append(")");
		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseAChannelType(AChannelType node,
			CMLModelcheckerContext question) throws AnalysisException {
		if(node.getType() != null){
			node.getType().apply(this, question);
		}
			
		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseAProductType(AProductType node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		LinkedList<PType> types = new LinkedList<PType>(node.getTypes());
		do{
			PType auxType = types.pollFirst();
			question.getScriptContent().append(auxType.toString());
			if(!types.isEmpty()){
				question.getScriptContent().append(",");
			}
		}while(!types.isEmpty());
		
		
		return question.getScriptContent();
	}

	*/
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
	/////  VALUES

}
