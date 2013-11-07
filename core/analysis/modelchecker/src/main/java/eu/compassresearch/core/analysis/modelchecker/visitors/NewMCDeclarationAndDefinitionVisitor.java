package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AExplicitOperationDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AUntypedDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SOperationDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.PPattern;






import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.AExpressionSingleDeclaration;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AActionsDefinition;
import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.definitions.AChannelsDefinition;
import eu.compassresearch.ast.definitions.AOperationsDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.ATypesDefinition;
import eu.compassresearch.ast.definitions.AValuesDefinition;
import eu.compassresearch.ast.definitions.PCMLDefinition;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCChannel;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCAExpressionSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCATypeSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAActionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAActionsDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAAssignmentDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAChannelNameDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAChannelsDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAExplicitCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAExplicitFunctionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAOperationsDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAProcessDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAStateDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCATypeDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCATypesDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAUntypedDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAValueDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAValuesDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCPCMLDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCSCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCPCMLPattern;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCPProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAFieldField;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAIntNumericBasicType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLNumericType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCVoidType;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ExpressionEvaluator;

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
				
		LinkedList<MCATypeSingleDeclaration> localState = new LinkedList<MCATypeSingleDeclaration>();
		for (PParametrisation aTypeSingleDeclaration : node.getLocalState()) {
			localState.add((MCATypeSingleDeclaration) aTypeSingleDeclaration.apply(this, question));
		}
		MCPProcess process = (MCPProcess) node.getProcess().apply(rootVisitor, question); 
		String name = node.getName().toString();
		MCAProcessDefinition result = new MCAProcessDefinition(name,localState, process);
		
		question.processDefinitions.add(result);

		//it removes the visited action definition
		question.actionOrProcessDefStack.pop();
				
		return result;
		
	}

	
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
		String name = node.getName().toString();
		MCAActionDefinition result = new MCAActionDefinition(name, mcParameters, action);
		
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
		MCPCMLExp expr = null;
		if(node.getExpression() != null) {
			expr = (MCPCMLExp) node.getExpression().apply(rootVisitor, question);
		}
				
		String varName = node.getName().toString();
		MCPCMLType varValue = new MCVoidType();
		if(expr != null){
			//this has to be improved to instantiate values of the suitable type of the expression.
			ExpressionEvaluator evaluator = new ExpressionEvaluator();
			varValue = evaluator.instantiateMCType(expr);
			//from type we have to get the correct type instantiation
		}
		question.maximalBinding = question.maximalBinding.addBinding("nP", varName, varValue);
		
		return new MCAAssignmentDefinition(varName, expr, type);
	}

	@Override
	public MCNode caseAStateDefinition(AStateDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		LinkedList<MCPCMLDefinition> stateDefs = new LinkedList<MCPCMLDefinition>(); 
		
		for (PDefinition pDefinition : node.getStateDefs()) {
			stateDefs.add((MCPCMLDefinition) pDefinition.apply(this, question));
		}
		MCAStateDefinition result = new MCAStateDefinition(stateDefs, null, null, null, null, null);
		
		return result;
	}

	@Override
	public MCNode caseAChannelsDefinition(AChannelsDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		LinkedList<MCAChannelNameDefinition> chanNameDecls = new LinkedList<MCAChannelNameDefinition>(); 
		for (AChannelDefinition chanDef : node.getChannelDeclarations()) {
			chanNameDecls.add((MCAChannelNameDefinition) chanDef.apply(this, question));
		}
		MCAChannelsDefinition result = new MCAChannelsDefinition(chanNameDecls);
		
		return result;
	}
	@Override
	public MCNode caseAChannelDefinition(
			AChannelDefinition node, NewCMLModelcheckerContext question)
			throws AnalysisException {
		
		String name = node.getName().toString();
		MCATypeSingleDeclaration singleType = (MCATypeSingleDeclaration) node.getType().apply(rootVisitor, question);
		MCAChannelNameDefinition result = new MCAChannelNameDefinition(name,singleType);
		
		question.channelDefs.add(result);
		
		return result;
		
	}

	@Override
	public MCNode caseATypeSingleDeclaration(
			ATypeSingleDeclaration node, NewCMLModelcheckerContext question)
			throws AnalysisException {
		
		MCPCMLType type = null;
		String identifier = node.getIdentifier().toString();
		if(node.getType() != null){
			type = (MCPCMLType) node.getType().apply(rootVisitor, question);
		}
		MCATypeSingleDeclaration result = new MCATypeSingleDeclaration(identifier, type);

		return result;
	}

	@Override
	public MCNode caseAOperationsDefinition(AOperationsDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		LinkedList<SOperationDefinition> operations = node.getOperations();
		LinkedList<MCSCmlOperationDefinition> mcOperations = new LinkedList<MCSCmlOperationDefinition>();
		for (SOperationDefinition currentOperationDefinition : operations) {
			mcOperations.add((MCAExplicitCmlOperationDefinition) currentOperationDefinition.apply(this, question));
		}
		MCAOperationsDefinition result = new MCAOperationsDefinition(mcOperations);
		
		return result;
	}

	@Override
	public MCNode caseAExplicitOperationDefinition(
			AExplicitOperationDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		String name = node.getName().toString();
		MCPAction body = (MCPAction) node.getBody().apply(rootVisitor, question);
		
		LinkedList<MCPCMLPattern> mcParamPatterns = new LinkedList<MCPCMLPattern>();
		for (PPattern pPattern : node.getParameterPatterns()) {
			mcParamPatterns.add((MCPCMLPattern) pPattern.apply(rootVisitor, question));
		}
		
		MCAExplicitCmlOperationDefinition result = 
				new MCAExplicitCmlOperationDefinition(name,body,null,null,null,null,mcParamPatterns,null,null);
		
		question.operations.add(result);
		
		return result;
		
	}
	
	@Override
	public MCNode caseAValuesDefinition(AValuesDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		LinkedList<MCPCMLDefinition> mcValueDefs = new LinkedList<MCPCMLDefinition>();
		for (PDefinition pDef : node.getValueDefinitions()) {
			mcValueDefs.add((MCPCMLDefinition) pDef.apply(this, question));
		}
		  
		MCAValuesDefinition result = new MCAValuesDefinition(mcValueDefs);
		
		return result;
	}
	
	@Override
	public MCNode caseAValueDefinition(AValueDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		String name = node.getPattern().toString();
		LinkedList<MCPCMLDefinition> definitions = new LinkedList<MCPCMLDefinition>();
		for (PDefinition pDef : node.getDefs()) {
			definitions.add((MCPCMLDefinition) pDef.apply(this, question));
		}
		MCPCMLType type = (MCPCMLType) node.getType().apply(rootVisitor, question);
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
	public MCNode caseATypesDefinition(ATypesDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		LinkedList<MCATypeDefinition> mcTypes = new LinkedList<MCATypeDefinition>();
		for (ATypeDefinition aTypeDef : node.getTypes()) {
			mcTypes.add((MCATypeDefinition) aTypeDef.apply(this, question));
		}
		MCATypesDefinition result = new MCATypesDefinition(mcTypes);
		
		return result;
	}

	@Override
	public MCNode caseATypeDefinition(ATypeDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		
		String name = node.getName().toString();
		MCPCMLExp invExpression = (MCPCMLExp) node.getInvExpression().apply(rootVisitor, question);
		MCATypeDefinition result = new MCATypeDefinition(name, invExpression);
		
		question.typeDefinitions.add(result);
		
	
		return result;
	}
	
	
	/*
	private void generateIOCommDefs(CMLModelcheckerContext context){
		//if(question.info.containsKey(Utilities.IOCOMM_DEFINITIONS_KEY)){
		if(context.ioCommDefs.size() > 0){
			Binding maximalBinding = context.getMaxBinding();
			//if(context.info.containsKey(Utilities.STATES_KEY)){
			//if(context.stateVariables.size() > 0){
			//	StringBuilder s = new StringBuilder(context.getScriptContent().toString());
			//	s.replace(0, s.indexOf("nopar,"), "");
			//	String ss = s.substring("nopar,".length(), s.indexOf(" :"));

			//	StringBuilder io = new StringBuilder(context.info.get(Utilities.IOCOMM_DEFINITIONS_KEY).toString());
			//	io.append(ss+".\n");
			//context.getScriptContent().replace(indexIoCommDef, indexIoCommDef + "#IOCOMM_DEFS#".length(), io.toString());
			//context.getScriptContent().append(io.toString());
			//} else{
			StringBuilder ioCommDefs = new StringBuilder();
			for (String ioCommDef : context.ioCommDefs) {
				ioCommDef = ioCommDef.replaceAll(Utilities.MAX_BIND, maximalBinding.toFormula());
				ioCommDefs.append(ioCommDef + "\n");
			}
			//question.getScriptContent().replace(indexIoCommDef, indexIoCommDef + "#IOCOMM_DEFS#".length(), question.info.get(Utilities.IOCOMM_DEFINITIONS_KEY).toString());
			//context.getScriptContent().replace(indexIoCommDef, indexIoCommDef + "#IOCOMM_DEFS#".length(), ioCommDefs.toString());
			context.getScriptContent().append(ioCommDefs.toString());
			//}

		}

	}
	
	private void generateBindingFacts(CMLModelcheckerContext context){
		//generate fetch facts
		Binding maxBinding = context.getMaxBinding();
		context.getScriptContent().append(maxBinding.generateAllFetchFacts(context.numberOfFetchFacts));
		
		//generate upd facts
		
		
		//generate del facts
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
}
