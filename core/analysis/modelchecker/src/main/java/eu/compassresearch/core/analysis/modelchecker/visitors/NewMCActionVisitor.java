package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.ast.statements.AIfStm;

import eu.compassresearch.ast.actions.ACallAction;
import eu.compassresearch.ast.actions.AChaosAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.ADivAction;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AExternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismReplicatedAction;
import eu.compassresearch.ast.actions.AGuardedAction;
import eu.compassresearch.ast.actions.AHidingAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.AInterleavingReplicatedAction;
import eu.compassresearch.ast.actions.AInternalChoiceAction;
import eu.compassresearch.ast.actions.AInternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AInterruptAction;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASequentialCompositionReplicatedAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AStmAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.actions.ATimedInterruptAction;
import eu.compassresearch.ast.actions.ATimeoutAction;
import eu.compassresearch.ast.actions.AUntimedTimeoutAction;
import eu.compassresearch.ast.actions.AWaitAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCABlockStatementAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCACallAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCACallStatementAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAChaosAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCACommunicationAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCADeclareStatementAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCADivAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAExternalChoiceAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAExternalChoiceReplicatedAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAGeneralisedParallelismParallelAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAGeneralisedParallelismReplicatedAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAGuardedAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAHidingAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAIfStatementAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAInterleavingParallelAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAInterleavingReplicatedAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAInternalChoiceAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAInternalChoiceReplicatedAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAInterruptAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAReferenceAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCASequentialCompositionAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCASequentialCompositionReplicatedAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCASkipAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAStmAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAStopAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCATimedInterruptAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCATimeoutAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAUntimedTimeoutAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAWaitAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPCommunicationParameter;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ActionChannelDependency;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCAssignDef;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.GuardDefGenerator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCCondition;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCGuardDef;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCIOCommDef;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.NewMCGuardDef;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCPSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCPCMLDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.ast.statements.MCPCMLStm;

public class NewMCActionVisitor extends
		QuestionAnswerCMLAdaptor<NewCMLModelcheckerContext, MCNode> {

	final private QuestionAnswerAdaptor<NewCMLModelcheckerContext, MCNode> rootVisitor;
	
	public NewMCActionVisitor(QuestionAnswerAdaptor<NewCMLModelcheckerContext, MCNode> parentVisitor){
		this.rootVisitor = parentVisitor;
	}
	
	//FOR THE CASE OF ACTIONS WHOSE VISIT METHOD IS NOT IMPLEMENTED
	@Override
	public MCNode defaultPAction(PAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		throw new ModelcheckerRuntimeException(ModelcheckerErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
	}
	
	///BASIC ACTIONS
	@Override
	public MCNode caseAStopAction(AStopAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		return new MCAStopAction();
	}

	
	@Override
	public MCNode caseAChaosAction(AChaosAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		return new MCAChaosAction();
	}

	
	@Override
	public MCNode caseADivAction(ADivAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		return new MCADivAction();
	}

	
	@Override
	public MCNode caseASkipAction(ASkipAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		return new MCASkipAction();

	}

	
	///COMMON ACTIONS
	@Override
	public MCNode caseAHidingAction(AHidingAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		MCPAction action = (MCPAction) node.getLeft().apply(rootVisitor, question);
		MCPVarsetExpression chanSetExp = (MCPVarsetExpression) node.getChansetExpression().apply(rootVisitor, question);
		MCAHidingAction result = new MCAHidingAction(action, chanSetExp);
		
		question.setStack.add(chanSetExp);
		
		return result;
	}
	
	@Override
	public MCNode caseAExternalChoiceAction(AExternalChoiceAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCPAction left = (MCPAction) node.getLeft().apply(rootVisitor, question);
		MCPAction right = (MCPAction) node.getRight().apply(rootVisitor, question);
		MCAExternalChoiceAction result = new MCAExternalChoiceAction(left, right);
		
		return result;
	}

	@Override
	public MCNode caseAInternalChoiceAction(AInternalChoiceAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCPAction left = (MCPAction) node.getLeft().apply(rootVisitor, question);
		MCPAction right = (MCPAction) node.getRight().apply(rootVisitor, question);
		MCAInternalChoiceAction result = new MCAInternalChoiceAction(left, right);
		
		return result;
		
	}
	
	
	
	@Override
	public MCNode caseAInterruptAction(AInterruptAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCPAction left = (MCPAction) node.getLeft().apply(rootVisitor, question);
		MCPAction right = (MCPAction) node.getRight().apply(rootVisitor, question);
		MCAInterruptAction result = new MCAInterruptAction(left,right);
		
		return result;
	}

	
	@Override
	public MCNode caseAUntimedTimeoutAction(AUntimedTimeoutAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		MCPAction left = (MCPAction) node.getLeft().apply(rootVisitor, question);
		MCPAction right = (MCPAction) node.getRight().apply(rootVisitor, question);
		MCAUntimedTimeoutAction result = new MCAUntimedTimeoutAction(left,right);

		return result;
	}

	@Override
	public MCNode caseAStmAction(AStmAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCPCMLStm statement = (MCPCMLStm) node.getStatement().apply(rootVisitor, question);
		MCAStmAction result = new MCAStmAction(statement);
		
		return result;
	}

	/*
	@Override
	public MCNode caseABlockStatementAction(ABlockStatementAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCPAction action = (MCPAction) node.getAction().apply(this, question);
		MCADeclareStatementAction declareStatement = null;
		if(node.getDeclareStatement() != null) {
			declareStatement = (MCADeclareStatementAction) node.getDeclareStatement().apply(rootVisitor, question);
		}
		MCABlockStatementAction result  = new MCABlockStatementAction(declareStatement, action);
		
		return result;
	}
	
	*/
	@Override
	public MCNode caseASequentialCompositionAction(
			ASequentialCompositionAction node, NewCMLModelcheckerContext question)
			throws AnalysisException {
		
		MCPAction left = (MCPAction) node.getLeft().apply(rootVisitor, question);
		MCPAction right = (MCPAction) node.getRight().apply(rootVisitor, question);
		MCASequentialCompositionAction result = new MCASequentialCompositionAction(left, right);
		
		return result;
	}
	
	/*
	@Override
	public MCNode caseADeclareStatementAction(
			ADeclareStatementAction node, NewCMLModelcheckerContext question)
			throws AnalysisException {
		
		LinkedList<MCPCMLDefinition>  mcAssignDefs = new LinkedList<MCPCMLDefinition>();
		
		LinkedList<PDefinition>  assignDefs = node.getAssignmentDefs();
		for (PDefinition pDefinition : assignDefs) {
			MCPCMLDefinition mcPDef =  (MCPCMLDefinition) pDefinition.apply(rootVisitor, question);
			mcAssignDefs.add(mcPDef);
		}

		MCADeclareStatementAction result = new MCADeclareStatementAction(mcAssignDefs);
		return result;
	}
	*/
	@Override
	public MCNode caseAInterleavingParallelAction(
			AInterleavingParallelAction node, NewCMLModelcheckerContext question)
			throws AnalysisException {
		
		MCPAction left = (MCPAction) node.getLeftAction().apply(rootVisitor, question);
		MCPAction right = (MCPAction) node.getRightAction().apply(rootVisitor, question);
		MCAInterleavingParallelAction result = new MCAInterleavingParallelAction(left, right);
		
		return result;
		
	}
	
	@Override
	public MCNode caseAGeneralisedParallelismParallelAction(
			AGeneralisedParallelismParallelAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		MCPAction left = (MCPAction) node.getLeftAction().apply(rootVisitor, question);
		MCPVarsetExpression chanSetExpression = (MCPVarsetExpression) node.getChansetExpression().apply(rootVisitor, question);
		MCPAction right = (MCPAction) node.getRightAction().apply(rootVisitor, question);
		MCAGeneralisedParallelismParallelAction result = new MCAGeneralisedParallelismParallelAction(left, chanSetExpression, right);
		
		question.globalChanSets.add(chanSetExpression);
		
		return result;
		
	}
	
	@Override
	public MCNode caseASequentialCompositionReplicatedAction(
			ASequentialCompositionReplicatedAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		LinkedList<PSingleDeclaration> replicationDecls = node.getReplicationDeclaration();
		LinkedList<MCPSingleDeclaration> replicationDeclarations = new LinkedList<MCPSingleDeclaration>();
		for (PSingleDeclaration pSingleDecl : replicationDecls) {
			replicationDeclarations.add((MCPSingleDeclaration) pSingleDecl.apply(rootVisitor, question));
		}
		MCPAction replicatedAction = (MCPAction) node.getReplicatedAction().apply(rootVisitor, question);
		MCASequentialCompositionReplicatedAction result = 
				new MCASequentialCompositionReplicatedAction(replicationDeclarations, replicatedAction);
		
		return result;
	}

	@Override
	public MCNode caseAGeneralisedParallelismReplicatedAction(
			AGeneralisedParallelismReplicatedAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		LinkedList<PSingleDeclaration> replicationDecls = node.getReplicationDeclaration();
		LinkedList<MCPSingleDeclaration> replicationDeclarations = new LinkedList<MCPSingleDeclaration>();
		for (PSingleDeclaration pSingleDecl : replicationDecls) {
			replicationDeclarations.add((MCPSingleDeclaration) pSingleDecl.apply(rootVisitor, question));
		}
		MCPAction replicatedAction = (MCPAction) node.getReplicatedAction().apply(rootVisitor, question);
		MCPVarsetExpression chansetExpression =  (MCPVarsetExpression) node.getChansetExpression().apply(rootVisitor, question);
		MCAGeneralisedParallelismReplicatedAction result = 
				new MCAGeneralisedParallelismReplicatedAction(replicationDeclarations, replicatedAction,chansetExpression);
		
		question.globalChanSets.add(chansetExpression);
		
		return result;
		
	}
	
	
	
	@Override
	public MCNode caseAInterleavingReplicatedAction(
			AInterleavingReplicatedAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		
		LinkedList<PSingleDeclaration> replicationDecls = node.getReplicationDeclaration();
		LinkedList<MCPSingleDeclaration> replicationDeclarations = new LinkedList<MCPSingleDeclaration>();
		for (PSingleDeclaration pSingleDecl : replicationDecls) {
			replicationDeclarations.add((MCPSingleDeclaration) pSingleDecl.apply(rootVisitor, question));
		}
		MCPAction replicatedAction = (MCPAction) node.getReplicatedAction().apply(rootVisitor, question);
		MCAInterleavingReplicatedAction result = 
				new MCAInterleavingReplicatedAction(replicationDeclarations, replicatedAction);
		
		return result;
	}

	@Override
	public MCNode caseAExternalChoiceReplicatedAction(
			AExternalChoiceReplicatedAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		LinkedList<PSingleDeclaration> replicationDecls = node.getReplicationDeclaration();
		LinkedList<MCPSingleDeclaration> replicationDeclarations = new LinkedList<MCPSingleDeclaration>();
		for (PSingleDeclaration pSingleDecl : replicationDecls) {
			replicationDeclarations.add((MCPSingleDeclaration) pSingleDecl.apply(rootVisitor, question));
		}
		MCPAction replicatedAction = (MCPAction) node.getReplicatedAction().apply(rootVisitor, question);
		MCAExternalChoiceReplicatedAction result = 
				new MCAExternalChoiceReplicatedAction(replicationDeclarations, replicatedAction);
		
		return result;

	}
	
	@Override
	public MCNode caseAInternalChoiceReplicatedAction(
			AInternalChoiceReplicatedAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		LinkedList<PSingleDeclaration> replicationDecls = node.getReplicationDeclaration();
		LinkedList<MCPSingleDeclaration> replicationDeclarations = new LinkedList<MCPSingleDeclaration>();
		for (PSingleDeclaration pSingleDecl : replicationDecls) {
			replicationDeclarations.add((MCPSingleDeclaration) pSingleDecl.apply(rootVisitor, question));
		}
		MCPAction replicatedAction = (MCPAction) node.getReplicatedAction().apply(rootVisitor, question);
		MCAInternalChoiceReplicatedAction result = 
				new MCAInternalChoiceReplicatedAction(replicationDeclarations, replicatedAction);
		
		return result;
	}
	
	
	@Override
	public MCNode caseACommunicationAction(ACommunicationAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		LinkedList<PCommunicationParameter> parameters = node.getCommunicationParameters();
				LinkedList<MCPCommunicationParameter> mcParameters = new LinkedList<MCPCommunicationParameter>();
		for (PCommunicationParameter pCommunicationParameter : parameters) {
			mcParameters.add((MCPCommunicationParameter) pCommunicationParameter.apply(rootVisitor, question));
		}
		String identifier = node.getIdentifier().getName().toString();
		if(parameters.size() > 0){ //the action definition to which this communication action belongs depends on a specific channel.
			if(!question.actionOrProcessDefStack.isEmpty()){ //there is an action definition in a wider context of this communication action
				INode parentAction = question.actionOrProcessDefStack.peek();
				if(parentAction instanceof AActionDefinition){
					String actionName = Utilities.extractFunctionName(((AActionDefinition) parentAction).getName().toString());
					ActionChannelDependency actionChanDep = new ActionChannelDependency(actionName, identifier, mcParameters);
					question.channelDependencies.add(actionChanDep);
				} else if (parentAction instanceof AProcessDefinition){
					String processName = Utilities.extractFunctionName(((AProcessDefinition) parentAction).getName().toString());
					ActionChannelDependency actionChanDep = new ActionChannelDependency(processName, identifier, mcParameters);
					question.channelDependencies.add(actionChanDep);
				}
			}
		}

		MCPAction action = (MCPAction) node.getAction().apply(rootVisitor, question);
		MCACommunicationAction result = new MCACommunicationAction(identifier, mcParameters, action);
		
		//fr the moment iocomm do not depend on channel. This means that formula wont instantiate communicated values
		MCIOCommDef ioCommDef = new MCIOCommDef(result.getCounterId(), result);

		question.ioCommDefs.add(ioCommDef);
		
		return result;
		
	}
	
	@Override
	public MCNode caseAReferenceAction(AReferenceAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		String name = Utilities.extractFunctionName(node.getName().toString());
		LinkedList<PExp> args = node.getArgs();
		LinkedList<MCPCMLExp> mcArgs = new LinkedList<MCPCMLExp>();
		for (PExp pExp : args) {
			mcArgs.add((MCPCMLExp) pExp.apply(rootVisitor, question));
		}
		
		MCAReferenceAction result = new MCAReferenceAction(name, mcArgs);
	
		return result;
	}
	
	@Override
	public MCNode caseAGuardedAction(AGuardedAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCPCMLExp expression = (MCPCMLExp) node.getExpression().apply(rootVisitor, question);
		
		MCPAction action = (MCPAction) node.getAction().apply(rootVisitor, question);
		MCAGuardedAction result = new MCAGuardedAction(expression, action);
		
		LinkedList<MCGuardDef> gDefs = GuardDefGenerator.generateGuardDefs(expression, result.getCounterId(), result);
		question.guardDefs.put(expression, gDefs);
		
		//for (MCGuardDef mcGuardDef : guarDefs) {
			//question.actionGuardDefs.put(expression, mcGuardDef);
		//}
		
	
		return result;
	}
	
	
	
	/*
	@Override
	public MCNode caseAIfStatementAction(AIfStatementAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		MCPCMLExp expression = (MCPCMLExp) node.getIfExp().apply(rootVisitor, question);
		MCPAction thenStmt = (MCPAction) node.getThenStm().apply(this, question);
		MCPAction elseStmt = (MCPAction) node.getElseStm().apply(this, question);
		MCAIfStatementAction result = new MCAIfStatementAction(expression,thenStmt,elseStmt);

		LinkedList<MCGuardDef> guarDefs = GuardDefGenerator.generateGuardDefs(expression, result.getCounterId(), result);
		
		for (MCGuardDef mcGuardDef : guarDefs) {
			question.guardDefs.put(expression, mcGuardDef);
		}

		return result;

		
	}
	*/
	
	
	/*
	@Override
	public MCNode caseACallStatementAction(ACallStatementAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		String name = node.getName().toString();
		LinkedList<PExp> args = node.getArgs();
		LinkedList<MCPCMLExp> mcArgs = new LinkedList<MCPCMLExp>();
		for (PExp pExp : args) {
			mcArgs.add((MCPCMLExp) pExp.apply(rootVisitor, question));
		}
		MCACallStatementAction result = new MCACallStatementAction(name,mcArgs);
		
		return result;
		
	}
	*/
	
	@Override
	public MCNode caseACallAction(ACallAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		String name = node.getName().getSimpleName();
		LinkedList<MCPCMLExp> args = new LinkedList<MCPCMLExp>();
		for (PExp pExp : node.getArgs()) {
			args.add((MCPCMLExp) pExp.apply(rootVisitor, question));
		}
		MCACallAction result = new MCACallAction(name, args);
		
		return result;
	}

	/////TIMED ACTIONS
	@Override
	public MCNode caseATimedInterruptAction(ATimedInterruptAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCPAction left = (MCPAction) node.getLeft().apply(rootVisitor, question);
		MCPAction right = (MCPAction) node.getRight().apply(rootVisitor, question);
		MCPCMLExp timeExpression = (MCPCMLExp) node.getTimeExpression().apply(rootVisitor, question);
		MCATimedInterruptAction result = new MCATimedInterruptAction(left, right, timeExpression);
		
		//we need to update the maximum value for clock in the context 
		question.updateMaxClock(timeExpression);
		
		return result;
	}


	@Override
	public MCNode caseATimeoutAction(ATimeoutAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCPAction left = (MCPAction) node.getLeft().apply(rootVisitor, question);
		MCPAction right = (MCPAction) node.getRight().apply(rootVisitor, question);
		MCPCMLExp timeoutExpression = (MCPCMLExp) node.getTimeoutExpression().apply(rootVisitor, question);
		MCATimeoutAction result = new MCATimeoutAction(left, right, timeoutExpression);
		
		//we need to update the maximum value for clock in the context 
		question.updateMaxClock(timeoutExpression);
		
		return result;
	}
	
	@Override
	public MCNode caseAWaitAction(AWaitAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		MCPCMLExp timeoutExpression = (MCPCMLExp) node.getExpression().apply(rootVisitor, question);
		MCAWaitAction result = new MCAWaitAction(timeoutExpression);
		
		//we need to update the maximum value for clock in the context 
		question.updateMaxClock(timeoutExpression);
				
		return result;
	}
	
	
	
	/////REPLICATED ACTIONS
	
	

	

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
