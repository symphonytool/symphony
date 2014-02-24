package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.analysis.intf.IQuestionAnswer;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.ATypeMultipleBind;
import org.overture.ast.patterns.PPattern;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.ast.actions.ASignalCommunicationParameter;
import eu.compassresearch.ast.actions.AValParametrisation;
import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAReadCommunicationParameter;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCASignalCommunicationParameter;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAValParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAWriteCommunicationParameter;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ExpressionEvaluator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.TypeManipulator;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAChannelDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCALocalDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAIntLiteralExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAUndefinedExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCVoidValue;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCAIdentifierPattern;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCATypeMultipleBind;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCPCMLPattern;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAChannelType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAProductType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public class NewMCParameterAndPatternVisitor extends QuestionAnswerCMLAdaptor<NewCMLModelcheckerContext, MCNode> {

	final private QuestionAnswerAdaptor<NewCMLModelcheckerContext, MCNode> rootVisitor;
	
	
	public NewMCParameterAndPatternVisitor(QuestionAnswerAdaptor<NewCMLModelcheckerContext, MCNode> parentVisitor) {
		this.rootVisitor = parentVisitor;
	}

	
	@Override
	public MCNode defaultPParametrisation(PParametrisation node,
			NewCMLModelcheckerContext question) throws AnalysisException {


		throw new ModelcheckerRuntimeException(ModelcheckerErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
	}


	@Override
	public MCNode defaultPPattern(PPattern node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		throw new ModelcheckerRuntimeException(ModelcheckerErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));	}


	@Override
	public MCNode defaultINode(INode node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		throw new ModelcheckerRuntimeException(ModelcheckerErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
	}

	@Override
	public MCNode caseAIdentifierPattern(AIdentifierPattern node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCAIdentifierPattern result = new MCAIdentifierPattern(node.getName().toString());
		
		return result;
	}

	
	@Override
	public MCNode defaultPCommunicationParameter(PCommunicationParameter node,
			NewCMLModelcheckerContext question) throws AnalysisException {


		throw new ModelcheckerRuntimeException(ModelcheckerErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
	}


	@Override
	public MCNode caseAReadCommunicationParameter(
			AReadCommunicationParameter node, NewCMLModelcheckerContext question)
			throws AnalysisException {
		
		MCPCMLExp expression = null;
		
		if(node.getExpression() != null){
			expression = (MCPCMLExp) node.getExpression().apply(rootVisitor, question);
		}

		MCPCMLPattern pattern = (MCPCMLPattern) node.getPattern().apply(rootVisitor, question);
		
		MCAReadCommunicationParameter result = new MCAReadCommunicationParameter(expression,pattern); 
		
		String name = node.getPattern().toString();
		
		if(expression == null){
			INode parent = node.parent();
			if(parent instanceof ACommunicationAction){
				String channelName = ((ACommunicationAction) parent).getIdentifier().getName();
				MCAChannelDefinition chanDef = question.getChannelDefinition(channelName);
				MCPCMLType realType = chanDef.getType();
				if(realType instanceof MCAChannelType){
					realType = ((MCAChannelType) realType).getType();
					ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
					//this is a solution to avoid dealing with product type
					//becaus only one parameter of the communication can be a read parameter
					if(realType instanceof MCAProductType){
						//simply use the defaul value for integers
						expression = new MCAIntLiteralExp("0");
					}else{
						expression = evaluator.getDefaultValue(realType);
					}
				}
			} 
			
		}
		
		question.maximalBinding = question.maximalBinding.addBinding("nP", name, expression);

		return result;
	}


	@Override
	public MCNode caseASignalCommunicationParameter(
			ASignalCommunicationParameter node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCPCMLExp expression = (MCPCMLExp) node.getExpression().apply(rootVisitor, question);
		
		MCASignalCommunicationParameter result = new MCASignalCommunicationParameter(expression);
		
		return result;
	}


	
	@Override
	public MCNode caseAWriteCommunicationParameter(
			AWriteCommunicationParameter node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCPCMLExp expression = (MCPCMLExp) node.getExpression().apply(rootVisitor, question);
		MCAWriteCommunicationParameter result = new MCAWriteCommunicationParameter(expression);
		
		return result;
	}

	@Override
	public MCNode caseAValParametrisation(AValParametrisation node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCALocalDefinition declaration = (MCALocalDefinition) node.getDeclaration().apply(rootVisitor, question);
		MCAValParametrisation result = new MCAValParametrisation(declaration);
		
		String valName = declaration.getName();
		MCPCMLType valType = declaration.getType();
		ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
		MCPCMLExp expression = evaluator.getDefaultValue(valType);
		question.maximalBinding = question.maximalBinding.addBinding("nP", valName, expression);
		
		return result;
	}
	
	

	@Override
	public MCNode caseATypeMultipleBind(ATypeMultipleBind node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCPCMLType type = (MCPCMLType) node.getType().apply(rootVisitor, question);
		LinkedList<MCPCMLPattern> pList = new LinkedList<MCPCMLPattern>();
		for (PPattern p : node.getPlist()) {
			pList.add((MCPCMLPattern) p.apply(rootVisitor, question));
		}
		MCATypeMultipleBind result = new MCATypeMultipleBind(pList, type);
		
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
