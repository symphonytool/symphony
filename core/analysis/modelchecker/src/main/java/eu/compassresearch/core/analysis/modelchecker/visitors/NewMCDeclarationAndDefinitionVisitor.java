package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AExplicitOperationDefinition;
import org.overture.ast.definitions.AImplicitOperationDefinition;
import org.overture.ast.definitions.AInstanceVariableDefinition;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AUntypedDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.AOperationType;
import org.overture.ast.types.AUnionType;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.AExpressionSingleDeclaration;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionClassDefinition;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.PCMLDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ExpressionEvaluator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCAssignDef;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCAExpressionSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCATypeSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAActionClassDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAActionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAAssignmentDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAChannelDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAChansetDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAExplicitCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAExplicitFunctionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAInstanceVariableDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCALocalDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAProcessDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAStateDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCATypeDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAUntypedDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAValueDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCPCMLDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAUndefinedExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCVoidValue;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCPCMLPattern;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCPProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.statements.MCPCMLStm;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public class NewMCDeclarationAndDefinitionVisitor extends
		QuestionAnswerCMLAdaptor<NewCMLModelcheckerContext, MCNode> {

	final private QuestionAnswerAdaptor<NewCMLModelcheckerContext, MCNode> rootVisitor;
	
	public NewMCDeclarationAndDefinitionVisitor(QuestionAnswerAdaptor<NewCMLModelcheckerContext, MCNode> parentVisitor){
		this.rootVisitor = parentVisitor;
	}
	
	//FOR THE CASE OF DEFINITIONS AND DECLARATIONS WHOSE VISIT METHOD IS NOT IMPLEMENTED
	@Override
	public MCNode defaultPCMLDefinition(PCMLDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		throw new ModelcheckerRuntimeException(ModelcheckerErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
	}
		
	@Override
	public MCNode defaultPSingleDeclaration(PSingleDeclaration node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		throw new ModelcheckerRuntimeException(ModelcheckerErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
	}

	//// DEFINITIONS
	@Override
	public MCNode caseAProcessDefinition(AProcessDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		//it first puts the action definition into a stack so we can detect dependencies between 
		//it and some channel that communicates values
		question.actionOrProcessDefStack.push(node);
				
		LinkedList<MCPParametrisation> localState = new LinkedList<MCPParametrisation>();
		
		for (PParametrisation aTypeSingleDeclaration : node.getLocalState()) {
			localState.add((MCPParametrisation) aTypeSingleDeclaration.apply(rootVisitor, question));
		}
		MCPProcess process = (MCPProcess) node.getProcess().apply(rootVisitor, question); 
		String name = node.getName().toString();
		MCAProcessDefinition result = new MCAProcessDefinition(name,localState, process);
		
		question.processDefinitions.add(result);

		//it removes the visited action definition
		question.actionOrProcessDefStack.pop();
				
		return result;
		
	}

	/*
	@Override
	public MCNode caseAActionsDefinition(AActionsDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		// it applies to each internal definition
		LinkedList<AActionDefinition> actions = node.getActions();
		LinkedList<MCAActionDefinition> mcActions = new LinkedList<MCAActionDefinition>();
		for (AActionDefinition currentActionDefinition : actions) {
			MCAActionDefinition mcActionDef = (MCAActionDefinition) currentActionDefinition.apply(this, question);
			question.localActions.add(mcActionDef);
			mcActions.add(mcActionDef);
		}
		
		MCAActionsDefinition result = new MCAActionsDefinition(mcActions);
		return result;
	}
	*/
	
	@Override
	public MCNode caseAActionDefinition(AActionDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		//it first puts the action definition into a stack so we can detect dependencies between 
		//it and some channel that communicates values
		question.actionOrProcessDefStack.push(node);
		
		LinkedList<PParametrisation> parameters = node.getDeclarations();
		LinkedList<MCPParametrisation> mcParameters = new LinkedList<MCPParametrisation>();
		for (PParametrisation pParametrisation : parameters) {
			mcParameters.add((MCPParametrisation) pParametrisation.apply(rootVisitor, question));
		}
		MCPAction action = (MCPAction) node.getAction().apply(rootVisitor, question);
		String name = Utilities.extractFunctionName(node.getName().toString());
		MCAActionDefinition result = new MCAActionDefinition(name, mcParameters, action);
		
		question.localActions.add(result);
		
		//it removes the visited action definition
		question.actionOrProcessDefStack.pop();
		
		return result;
	}
	
	@Override
	public MCNode caseAExpressionSingleDeclaration(
			AExpressionSingleDeclaration node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		String identifier = node.getIdentifier().toString();
		PExp expr = node.getExpression();
		MCPCMLExp expression = (MCPCMLExp) expr.apply(rootVisitor, question);
		return new MCAExpressionSingleDeclaration(identifier, expression);
	}

	
	
	@Override
	public MCNode caseAAssignmentDefinition(AAssignmentDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCPCMLType type = (MCPCMLType) node.getType().apply(rootVisitor, question);;
		MCPCMLExp expression = null;
		if(node.getExpression() != null) {
			expression = (MCPCMLExp) node.getExpression().apply(rootVisitor, question);
		}
				
		String varName = node.getName().toString();
		MCPCMLExp varValue = new MCVoidValue();
		if(expression != null){
			varValue = expression;
			if(expression instanceof MCAUndefinedExp){
				//this has to be improved to instantiate values of the suitable type of the expression.
				ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
				varValue = evaluator.getDefaultValue(type);
			} 
		}
		question.maximalBinding = question.maximalBinding.addBinding("nP", varName, varValue);
		
		MCAAssignmentDefinition result = new MCAAssignmentDefinition(varName, expression, type);
				
		return result;
	}

	@Override
	public MCNode caseATypeSingleDeclaration(ATypeSingleDeclaration node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		String identifier = node.getIdentifier().getName();
		MCPCMLType type = (MCPCMLType) node.getType().apply(rootVisitor, question);
		MCATypeSingleDeclaration result = new MCATypeSingleDeclaration(identifier, type);
		return result;
	}

	@Override
	public MCNode caseAStateDefinition(AStateDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		LinkedList<MCPCMLDefinition> stateDefs = new LinkedList<MCPCMLDefinition>(); 
		
		for (PDefinition pDefinition : node.getStateDefs()) {
			stateDefs.add((MCPCMLDefinition) pDefinition.apply(rootVisitor, question));
		}
		MCAStateDefinition result = new MCAStateDefinition(stateDefs, null, null, null, null, null);
		
		return result;
	}

	@Override
	public MCNode caseAChannelDefinition(
			AChannelDefinition node, NewCMLModelcheckerContext question)
			throws AnalysisException {
		
		String name = node.getName().toString();
		MCPCMLType singleType = (MCPCMLType) node.getType().apply(rootVisitor, question);
		MCAChannelDefinition result = new MCAChannelDefinition(name,singleType);
		
		question.channelDefs.add(result);
		
		return result;
		
	}
	
	

	@Override
	public MCNode caseAChansetDefinition(AChansetDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		String identifier = node.getIdentifier().getName().toString();
		MCPVarsetExpression chansetExpression = (MCPVarsetExpression) node.getChansetExpression().apply(rootVisitor, question);
		
		MCAChansetDefinition result = new MCAChansetDefinition(identifier, chansetExpression);
		
		question.chansetDefs.add(result);
		
		//maybe only the bellow insertion is enough
		question.globalChanSets.add(chansetExpression);
		
		return result;
	}

	@Override
	public MCNode caseALocalDefinition(
			ALocalDefinition node, NewCMLModelcheckerContext question)
			throws AnalysisException {
		
		MCPCMLType type = null;
		String name = node.getName().getName();
		if(node.getType() != null){
			type = (MCPCMLType) node.getType().apply(rootVisitor, question);
		}
		MCALocalDefinition result = new MCALocalDefinition(name, type);
		
		return result;
	}

	@Override
	public MCNode caseAExplicitOperationDefinition(
			AExplicitOperationDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		String name = Utilities.extractFunctionName(node.getName().toString());
		MCPCMLStm body = (MCPCMLStm) node.getBody().apply(rootVisitor, question);
		
		LinkedList<MCPCMLPattern> mcParamPatterns = new LinkedList<MCPCMLPattern>();
		int patternPosition = 0;
		for (PPattern pPattern : node.getParameterPatterns()) {
			MCPCMLPattern pattern = (MCPCMLPattern) pPattern.apply(rootVisitor, question);
			PType opType = node.getType();
			MCPCMLExp varValue = null;
			ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
			if(opType instanceof AOperationType){
				MCPCMLType patternType = (MCPCMLType) ((AOperationType) opType).getParameters().get(patternPosition).apply(rootVisitor, question);
				//TypeValue patternValue = typeHandler.getValues(patternType).getFirst();
				//evaluator.ge
				//question.maximalBinding = question.maximalBinding.addBinding("nP",pattern.toFormula(MCNode.NAMED), new MCAVa);
			}
				
			mcParamPatterns.add(pattern);
			patternPosition++;
		}
		
		MCAExplicitCmlOperationDefinition result = 
				new MCAExplicitCmlOperationDefinition(name,body,null,null,null,null,mcParamPatterns,null,null);
		
		question.operations.add(result);
		
		return result;
		
	}
	
	
	
	@Override
	public MCNode caseAImplicitOperationDefinition(
			AImplicitOperationDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		return super.caseAImplicitOperationDefinition(node, question);
	}

	@Override
	public MCNode caseAExplicitFunctionDefinition(
			AExplicitFunctionDefinition node, NewCMLModelcheckerContext question)
			throws AnalysisException {
		
		String name = Utilities.extractFunctionName(node.getName().toString());
		MCPCMLExp body = (MCPCMLExp) node.getBody().apply(rootVisitor, question);
		
		LinkedList<MCPCMLPattern> mcParamPatterns = new LinkedList<MCPCMLPattern>();
		for (List<PPattern> pPatternList : node.getParamPatternList()) {
			for (PPattern pPattern : pPatternList) {
				MCPCMLPattern pattern = (MCPCMLPattern) pPattern.apply(rootVisitor, question);
				mcParamPatterns.add(pattern);
			}
		}
		
		MCPCMLType type = (MCPCMLType) node.getType().apply(rootVisitor, question);
		MCAExplicitFunctionDefinition result = 
				new MCAExplicitFunctionDefinition(name,type, body,mcParamPatterns);
		
		question.functions.add(result);
		
		return result;
		
	}

	@Override
	public MCNode caseAValueDefinition(AValueDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		String name = node.getPattern().toString();
		LinkedList<MCPCMLDefinition> definitions = new LinkedList<MCPCMLDefinition>();
		for (PDefinition pDef : node.getDefs()) {
			definitions.add((MCPCMLDefinition) pDef.apply(rootVisitor, question));
		}
		MCPCMLType type = null;
		if (node.getType() != null){
			type = (MCPCMLType) node.getType().apply(rootVisitor, question);
		}
		MCPCMLExp expression = (MCPCMLExp) node.getExpression().apply(rootVisitor, question);
		MCAValueDefinition result = new MCAValueDefinition(name, definitions, expression,type);

		question.valueDefinitions.add(result);
		
		return result;
	}
	
	
	
	@Override
	public MCNode caseAUntypedDefinition(AUntypedDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCAUntypedDefinition result = new MCAUntypedDefinition(node.getName().toString());
		
		return result;
	}
	
	@Override
	public MCNode caseATypeDefinition(ATypeDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		
		String name = node.getName().toString();
		MCPCMLExp invExpression = null;
		MCPCMLType type = null;
		if( node.getInvExpression() != null) {
			invExpression = (MCPCMLExp) node.getInvExpression().apply(rootVisitor, question);
		} else {
			PType pType = node.getInvType(); 
			if( pType instanceof ANamedInvariantType){
				pType = ((ANamedInvariantType) pType).getType();
				type = (MCPCMLType) pType.apply(rootVisitor, question);
			}
			
		}
		
		MCATypeDefinition result = new MCATypeDefinition(name, invExpression, type);
		
		question.typeDefinitions.add(result);
		
	
		return result;
	}
	
	@Override
	public MCNode caseAActionClassDefinition(AActionClassDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		//String name = node.getName().toString();
		LinkedList<MCPCMLDefinition> definitions = new LinkedList<MCPCMLDefinition>();
		for (PDefinition pDefinition : node.getDefinitions()) {
			definitions.add((MCPCMLDefinition) pDefinition.apply(rootVisitor, question));
		}
		MCAActionClassDefinition result = new MCAActionClassDefinition(definitions);
		
		return result;
	}
	
	

	@Override
	public MCNode caseAInstanceVariableDefinition(
			AInstanceVariableDefinition node, NewCMLModelcheckerContext question)
			throws AnalysisException {
		
		String name = node.getName().toString();
		MCPCMLType type = (MCPCMLType) node.getType().apply(rootVisitor, question);
		MCPCMLExp expression = (MCPCMLExp) node.getExpression().apply(rootVisitor, question);
		MCAInstanceVariableDefinition result = new MCAInstanceVariableDefinition(name, type, expression);

		MCPCMLExp varValue = null;
		if(expression != null){
			varValue = expression;
			if(expression instanceof MCAUndefinedExp){
				//this has to be improved to instantiate values of the suitable type of the expression.
				ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
				varValue = evaluator.getDefaultValue(type);
				//from type we have to get the correct type instantiation
			} 
			
		}
		
		question.maximalBinding = question.maximalBinding.addBinding("nP", name, varValue);
		
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
