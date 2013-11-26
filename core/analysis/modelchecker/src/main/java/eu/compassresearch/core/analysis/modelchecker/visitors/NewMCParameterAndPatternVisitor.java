package eu.compassresearch.core.analysis.modelchecker.visitors;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.analysis.intf.IQuestionAnswer;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.PPattern;

import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.ast.actions.ASignalCommunicationParameter;
import eu.compassresearch.ast.actions.AValParametrisation;
import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAReadCommunicationParameter;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCASignalCommunicationParameter;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAValParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAWriteCommunicationParameter;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ExpressionEvaluator;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCALocalDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAUndefinedExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCVoidValue;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCAIdentifierPattern;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCPCMLPattern;

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
			expression = new MCVoidValue();
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
