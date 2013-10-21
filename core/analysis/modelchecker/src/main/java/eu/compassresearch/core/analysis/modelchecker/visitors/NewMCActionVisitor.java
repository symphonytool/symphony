package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.ArrayList;
import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.ASetEnumSetExp;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.actions.ABlockStatementAction;
import eu.compassresearch.ast.actions.ACallStatementAction;
import eu.compassresearch.ast.actions.AChaosAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.ADeclareStatementAction;
import eu.compassresearch.ast.actions.ADivAction;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AExternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismReplicatedAction;
import eu.compassresearch.ast.actions.AGuardedAction;
import eu.compassresearch.ast.actions.AHidingAction;
import eu.compassresearch.ast.actions.AIfStatementAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.AInternalChoiceAction;
import eu.compassresearch.ast.actions.AInternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASequentialCompositionReplicatedAction;
import eu.compassresearch.ast.actions.ASignalCommunicationParameter;
import eu.compassresearch.ast.actions.ASingleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.actions.SReplicatedActionBase;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.AExpressionSingleDeclaration;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.SCmlOperationDefinition;
import eu.compassresearch.ast.expressions.AEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AFatEnumVarsetExpression;
import eu.compassresearch.ast.expressions.ANameChannelExp;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCABlockStatementAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAChaosAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCACommunicationAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCADeclareStatementAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCADivAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAExternalChoiceAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAExternalChoiceReplicatedAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAGeneralisedParallelismParallelAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAGeneralisedParallelismReplicatedAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAHidingAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAInterleavingParallelAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAInternalChoiceAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAInternalChoiceReplicatedAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAReferenceAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCASequentialCompositionAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCASequentialCompositionReplicatedAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCASkipAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAStopAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPCommunicationParameter;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCSReplicatedActionBase;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCPSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCPCMLDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.SingleBind;

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

		MCPAction action = (MCPAction) node.getLeft().apply(this, question);
		MCPVarsetExpression chanSetExp = (MCPVarsetExpression) node.getChansetExpression().apply(rootVisitor, question);
		MCAHidingAction result = new MCAHidingAction(action, chanSetExp);
		
		return result;
	}
	
	@Override
	public MCNode caseAExternalChoiceAction(AExternalChoiceAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCPAction left = (MCPAction) node.getLeft().apply(this, question);
		MCPAction right = (MCPAction) node.getRight().apply(this, question);
		MCAExternalChoiceAction result = new MCAExternalChoiceAction(left, right);
		
		return result;
	}

	@Override
	public MCNode caseAInternalChoiceAction(AInternalChoiceAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		MCPAction left = (MCPAction) node.getLeft().apply(this, question);
		MCPAction right = (MCPAction) node.getRight().apply(this, question);
		MCAInternalChoiceAction result = new MCAInternalChoiceAction(left, right);
		
		return result;
		
	}
	
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
	
	@Override
	public MCNode caseASequentialCompositionAction(
			ASequentialCompositionAction node, NewCMLModelcheckerContext question)
			throws AnalysisException {
		
		MCPAction left = (MCPAction) node.getLeft().apply(this, question);
		MCPAction right = (MCPAction) node.getRight().apply(this, question);
		MCASequentialCompositionAction result = new MCASequentialCompositionAction(left, right);
		
		return result;
	}
	
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
	
	@Override
	public MCNode caseAInterleavingParallelAction(
			AInterleavingParallelAction node, NewCMLModelcheckerContext question)
			throws AnalysisException {
		
		MCPAction left = (MCPAction) node.getLeftAction().apply(this, question);
		MCPAction right = (MCPAction) node.getRightAction().apply(this, question);
		MCAInterleavingParallelAction result = new MCAInterleavingParallelAction(left, right);
		
		return result;
		
	}
	
	@Override
	public MCNode caseAGeneralisedParallelismParallelAction(
			AGeneralisedParallelismParallelAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		MCPAction left = (MCPAction) node.getLeftAction().apply(this, question);
		MCPVarsetExpression chanSetExpression = (MCPVarsetExpression) node.getChansetExpression().apply(rootVisitor, question);
		MCPAction right = (MCPAction) node.getRightAction().apply(this, question);
		MCAGeneralisedParallelismParallelAction result = new MCAGeneralisedParallelismParallelAction(left, chanSetExpression, right);
		
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
		MCPAction replicatedAction = (MCPAction) node.getReplicatedAction().apply(this, question);
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
		MCPAction replicatedAction = (MCPAction) node.getReplicatedAction().apply(this, question);
		MCPVarsetExpression chansetExpression =  (MCPVarsetExpression) node.getChansetExpression().apply(rootVisitor, question);
		MCAGeneralisedParallelismReplicatedAction result = 
				new MCAGeneralisedParallelismReplicatedAction(replicationDeclarations, replicatedAction,chansetExpression);
		
		return result;
		

		/*
		// building combination of processses based on simple constructs
		StringBuilder replicatedActionBuilder = buildReplicatedAction(node, question,
				node.getReplicatedAction(), Utilities.GEN_PARALLELISM,
				indexes.size());
		String replicatedActionString = replicatedActionBuilder.toString();
		for (PExp pExp : indexes) {
			CMLModelcheckerContext argCtxt = new CMLModelcheckerContext();
			StringBuilder argValue = pExp.apply(rootVisitor, argCtxt);
			PAction replicatedAction = node.getReplicatedAction();
			if(replicatedAction instanceof ACallStatementAction){
				PExp arg0 = ((ACallStatementAction) replicatedAction).getArgs().getFirst();
				if(arg0 instanceof AVariableExp){
					replicatedActionString = replicatedActionString.replaceFirst("(" +  ((AVariableExp) arg0).getName().toString() + ")", argValue.toString());
				}
			} 
		}
		question.getScriptContent().append(replicatedActionString);
		*/
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
		MCPAction replicatedAction = (MCPAction) node.getReplicatedAction().apply(this, question);
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
		MCPAction replicatedAction = (MCPAction) node.getReplicatedAction().apply(this, question);
		MCAInternalChoiceReplicatedAction result = 
				new MCAInternalChoiceReplicatedAction(replicationDeclarations, replicatedAction);
		
		return result;
	}
	
	
	@Override
	public MCNode caseACommunicationAction(ACommunicationAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		//if the communication action does not involves parameters
		LinkedList<PCommunicationParameter> parameters = node.getCommunicationParameters();
		LinkedList<MCPCommunicationParameter> mcParameters = new LinkedList<MCPCommunicationParameter>();
		for (PCommunicationParameter pCommunicationParameter : parameters) {
			mcParameters.add((MCPCommunicationParameter) pCommunicationParameter.apply(rootVisitor, question));
		}
		String identifier = node.getIdentifier().getName().toString(); 
		MCPAction action = (MCPAction) node.getAction().apply(this, question);
		MCACommunicationAction result = new MCACommunicationAction(identifier, mcParameters, action);
		
		return result;
		
		/*else { //there are parameters
		
			
			question.getScriptContent().append(
					//"Prefix(IOComm(" + question.IOCOMM_COUNTER + ",\"" + node.getIdentifier()+"."+parameters.getFirst().toString() + "\",");
					"Prefix(IOComm(" + question.IOCOMM_COUNTER + ",\"" + node.getIdentifier()+"."+ parameters.toString() + "\",");
			String varName = parameters.getFirst().toString();
			if(parameters.size() == 1){
				//we assume that only integers are communicated on channels
				question.getScriptContent().append("Int(" + varName + ")");
			}
			question.getScriptContent().append("),");
			
				
			//it applies recursivelly in the internal structure
			node.getAction().apply(this, question);

				//question.getScriptContent().append("))");
				question.getScriptContent().append(")");
			
			//if this communication depends on a state variable. then we must add a code for fetching it in the bindings
			SingleBind bind = question.getBindByVariable(varName);
			if(bind != null){
				question.getScriptContent().append(" :- ");
				question.getScriptContent().append("fetch(" + "\"" + varName + "\"," + Utilities.MAX_BIND + "," + "Int(" + varName + ")");
			}else{
				//if this communication depends on a communicated variable (channel)
				PCommunicationParameter param = parameters.getFirst();
				if(param instanceof ASignalCommunicationParameter){
					PExp exp = param.getExpression();
					if(exp instanceof AVariableExp){
						CMLModelcheckerContext copyCtxt = question.copy();
						copyCtxt.scriptContent = new StringBuilder();
						
						//LinkedList<ChannelTypeDefinition> channelDefs = question.channelDefinitions;
						ChannelTypeDefinition channDef = copyCtxt.getChannelDefinition(node.getIdentifier().toString());
						//question.getScriptContent().append(" :- ");
						//copyCtxt.scriptContent.append(" :- ");
						//aux.getFirst().apply(this, question);
						if(channDef != null){
							AChannelNameDefinition c = channDef.getChanDef();
							c.apply(this.rootVisitor, copyCtxt);
							//int i = question.getScriptContent().lastIndexOf("_");
							int i = copyCtxt.getScriptContent().lastIndexOf("_");
							//question.getScriptContent().replace(i, i+1, parameters.getFirst().toString());
							if( i != -1){
								copyCtxt.getScriptContent().replace(i, i+1, param.toString());
							}
							//question.getScriptContent().append(".\n");
						
							//puts the information in the main context to be recovered at the end
							question.channelDependencies.add(copyCtxt.getScriptContent().toString());
						}
					}
					
				}
			}
				
				
				CMLModelcheckerContext aux = new CMLModelcheckerContext();
				aux.getScriptContent().append("  IOCommDef(0," + question.IOCOMM_COUNTER + ",");
				aux.getScriptContent().append("Int(" + parameters.getFirst().toString() + "),");
				//if(question.info.containsKey(Utilities.STATES_KEY)){
				if(question.stateVariables.size() > 0){
					aux.getScriptContent().append(question.getMaxBindingWithStates().toFormulaWithState()+","+question.getMaxBindingWithStates().toFormulaWithState());
					aux.getScriptContent().append(") :- State(0,_,np,");					
					
				} else {
					aux.getScriptContent().append(Utilities.MAX_BIND + "," + Utilities.MAX_BIND + ") :- State(0,_,_,");
					
					//aux.getScriptContent().append("Prefix(IOComm(0,\"" + node.getIdentifier()+"."+parameters.getFirst().toString() + "\",");
					aux.getScriptContent().append("Prefix(IOComm("+ question.IOCOMM_COUNTER +",\"" + node.getIdentifier()+"."+parameters.toString() + "\",");
					aux.getScriptContent().append("Int(" + parameters.getFirst().toString() + ")");
					aux.getScriptContent().append("),");
					node.getAction().apply(this, aux);
					aux.getScriptContent().append(")).\n");
				}
				question.IOCOMM_COUNTER++;
				//aux.getScriptContent().append("  State(0,nBind,np,pBody)  :- GivenProc(np), ProcDef(np,_,pBody).\n");
				
				//question.info.put(Utilities.IOCOMM_DEFINITIONS_KEY, aux.getScriptContent().toString());
				question.ioCommDefs.add(aux.getScriptContent().toString());
		}
		
		return question.getScriptContent();
		*/
	}
	
	@Override
	public MCNode caseAReferenceAction(AReferenceAction node,
			NewCMLModelcheckerContext question) throws AnalysisException {

		String name = node.getName().toString();
		LinkedList<PExp> args = node.getArgs();
		LinkedList<MCPCMLExp> mcArgs = new LinkedList<MCPCMLExp>();
		for (PExp pExp : args) {
			mcArgs.add((MCPCMLExp) pExp.apply(rootVisitor, question));
		}
		
		MCAReferenceAction result = new MCAReferenceAction(name, mcArgs);
	
		return result;
	}
	
	/*
	@Override
	public StringBuilder caseACallStatementAction(ACallStatementAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		LinkedList<PExp> args = node.getArgs();
		
		StringBuilder callStr = new StringBuilder();
		ArrayList<AActionDefinition> localActions = question.localActions;
		boolean callResolved = false;
		if(localActions != null){ //if there are auxiliary actions
			for (AActionDefinition localAction : localActions) {
				if(localAction.getName().toString().equals(node.getName().toString())){
					callResolved = true;
					callStr.append("proc(\"" + localAction.getName().toString() + "\",");
					if(args.size()==0){
						callStr.append("nopar");
					}
					if(args.size()==1){
						CMLModelcheckerContext newCtxt = new CMLModelcheckerContext();
						newCtxt.getScriptContent().append("SPar(");
						//args.getFirst().apply(this, newCtxt);
						args.getFirst().apply(rootVisitor, newCtxt);
						newCtxt.getScriptContent().append(")");
						callStr.append(newCtxt.getScriptContent().toString());
					}
					callStr.append(")");
				}
			}
		}
		if (!callResolved) {
			for (SCmlOperationDefinition pDefinition : question.operations) {
				if(pDefinition.getName().toString().equals(node.getName().toString())){
					callStr.append("operation(\"" + pDefinition.getName().toString() + "\",");
					if(args.size()==0){
						callStr.append("nopar");
					}
					if(args.size()==1){
						CMLModelcheckerContext newCtxt = new CMLModelcheckerContext(); 
						//String argStr = args.getFirst().apply(this, newCtxt).toString();
						String argStr = args.getFirst().apply(rootVisitor, newCtxt).toString();
						callStr.append(argStr);
					}
					callStr.append(")");
				}
			}
		}
		
		question.getScriptContent().append(callStr);
		
		// Adding auxiliary definitions
				// LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>)
				// question.info.get(node);
				//LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>) question.info
				//		.get(Utilities.LOCAL_DEFINITIONS_KEY);

				//CMLModelcheckerContext auxCtxt = new CMLModelcheckerContext();
				//auxCtxt.copyChannelInfo(question);
				//if (localActions != null) {
				//	for (PDefinition pDefinition : localActions) {
				//		pDefinition.apply(this, auxCtxt);
				//	}
				//}
				//question.copyIOCommDefInfo(auxCtxt);
				//int auxIndex = question.getScriptContent().indexOf(
				//		"#AUXILIARY_PROCESSES#");
				//if (auxIndex != -1) {
				//	question.getScriptContent().replace(auxIndex,
				//			auxIndex + "#AUXILIARY_PROCESSES#".length(),
				//			auxCtxt.getScriptContent().toString());
				//}
				//if(question.info.containsKey(Utilities.IOCOMM_DEFINITIONS_KEY)){
				//	question.getScriptContent().append(question.info.get(Utilities.IOCOMM_DEFINITIONS_KEY));
				//}
				
		return question.getScriptContent();
	}

	

	
	
	
	
	@Override
	public StringBuilder caseAGuardedAction(AGuardedAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		// it writes the conditional choice constructor
		question.getScriptContent().append("condChoice(");
		// it writes the condition as an integer and puts the expression
		//to be evaluated in the context
		question.getScriptContent().append(CMLModelcheckerContext.GUARD_COUNTER + ",");
		//question.info.put(Utilities.CONDITION_KEY, node.getExpression());
		Condition newCondition = new Condition(node.getExpression(),CMLModelcheckerContext.GUARD_COUNTER++);
		question.guards.add(newCondition);
		//node.getExpression().apply(this, question);
		//question.getScriptContent().append(Utilities.OCCUR_COUNT++ + ",");
		//question.getScriptContent().append(CMLModelcheckerContext.GUARD_COUNTER++ + ",");
		// it writes the behaviour in the if-true branch
		node.getAction().apply(this, question);
		question.getScriptContent().append(",Stop)"); // the else branch of a
														// guarded action is
														// stop

		return question.getScriptContent();
	}

	
	
	@Override
	public StringBuilder caseAIfStatementAction(AIfStatementAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		// it writes the conditional choice constructor
				question.getScriptContent().append("condChoice(");
				// it writes the condition as an integer and puts the expression
				//to be evaluated in the context
				question.getScriptContent().append(CMLModelcheckerContext.GUARD_COUNTER + ",");
				//question.info.put(Utilities.CONDITION_KEY, node.getExpression());
				Condition newCondition = new Condition(node.getIfExp(),CMLModelcheckerContext.GUARD_COUNTER++);
				question.guards.add(newCondition);
				//node.getExpression().apply(this, question);
				//question.getScriptContent().append(Utilities.OCCUR_COUNT++ + ",");
				//question.getScriptContent().append(CMLModelcheckerContext.GUARD_COUNTER++ + ",");
				// it writes the behaviour in the if-true branch
				node.getThenStm().apply(this, question);
				question.getScriptContent().append(","); 
				node.getElseStm().apply(this, question);
				
				return question.getScriptContent();
	}

	

	

	
	
	///PARALLEL ACTIONS
	
	
	
	
	
	/////REPLICATED ACTIONS
	
	@Override
	public StringBuilder caseASingleGeneralAssignmentStatementAction(
			ASingleGeneralAssignmentStatementAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		question.getScriptContent().append("assign("+CMLModelcheckerContext.ASSIGN_COUNTER +")");
		//StringBuilder str = new StringBuilder();
		//this line is generated for this assignment. Each assignment must have a line like this
		//probably the maximal binding is not ready to be put here and you need to put the assignment information into the context 
		//str.append("  assignDef(0, "+CMLModelcheckerContext.ASSIGN_COUNTER +", st, st_)  :- State(0,st,name,assign("+CMLModelcheckerContext.ASSIGN_COUNTER+")), st = OLD_BINDING, st_ = NEW_BINDING");
		//str.append(".\n");
		
		question.putVarAttInBinding(Utilities.ASSIGNMENT_DEFINITION_KEY, ""+CMLModelcheckerContext.ASSIGN_COUNTER, node.getStateDesignator().toString(), node.getExpression().toString());
		
		CMLModelcheckerContext.ASSIGN_COUNTER++;
		
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
}
