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
import eu.compassresearch.ast.definitions.SCmlOperationDefinition;
import eu.compassresearch.ast.expressions.AEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AFatEnumVarsetExpression;
import eu.compassresearch.ast.expressions.ANameChannelExp;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.SingleBind;

public class MCActionVisitor extends
		QuestionAnswerCMLAdaptor<CMLModelcheckerContext, StringBuilder> {

	final private QuestionAnswerAdaptor<CMLModelcheckerContext, StringBuilder> rootVisitor;
	
	public MCActionVisitor(QuestionAnswerAdaptor<CMLModelcheckerContext, StringBuilder> parentVisitor){
		this.rootVisitor = parentVisitor;
	}
	
	//FOR THE CASE OF ACTIONS WHOSE VISIT METHOD IS NOT IMPLEMENTED
	@Override
	public StringBuilder defaultPAction(PAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		throw new ModelcheckerRuntimeException(ModelcheckerErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
	}
	
	///BASIC ACTIONS
	@Override
	public StringBuilder caseAStopAction(AStopAction node,
			CMLModelcheckerContext question) throws AnalysisException {

		question.getScriptContent().append(node.toString());

		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAChaosAction(AChaosAction node,
			CMLModelcheckerContext question) throws AnalysisException {

		question.getScriptContent().append(node.toString());

		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseADivAction(ADivAction node,
			CMLModelcheckerContext question) throws AnalysisException {

		question.getScriptContent().append("Div");

		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseASkipAction(ASkipAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		question.getScriptContent().append(node.toString());

		return question.getScriptContent();

	}

	///COMMON ACTIONS
	@Override
	public StringBuilder caseAHidingAction(AHidingAction node,
			CMLModelcheckerContext question) throws AnalysisException {

		//it puts the event set in the context so the internal process can access and generate lieIn
		question.setStack.add(node.getChansetExpression());
		
		// "actions\ {ev}" hide(actions,"{ev}")
		question.getScriptContent().append("hide(");
		
		//the internal action must check if there is some event set to generate lieInEvents
		node.getLeft().apply(this, question);
		question.getScriptContent().append(",");
		//node.getChansetExpression().apply(this, question);
		node.getChansetExpression().apply(rootVisitor, question);
		question.getScriptContent().append(")");
		
		//it remover the event set from the context at the end
		question.setStack.pop();
		
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseACommunicationAction(ACommunicationAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		//if the communication action does not involves values
		
		LinkedList<PCommunicationParameter> parameters = node.getCommunicationParameters();
		if(parameters.size() == 0){
			question.getScriptContent().append(
				"Prefix(BasicEv(\"" + node.getIdentifier() + "\"), ");

			//it applies recursivelly in the internal structure
			node.getAction().apply(this, question);

			question.getScriptContent().append(")");
			
			//if there is some set of event in the context we must generate lieIn events.
			SetStack chanSetStack = question.setStack.copy();
			while(!chanSetStack.isEmpty()){
				
				PVarsetExpression setExp = (PVarsetExpression)chanSetStack.pop();
				LinkedList<ANameChannelExp> chanNames = null;
				if(setExp instanceof AEnumVarsetExpression){
					chanNames = ((AEnumVarsetExpression) setExp).getChannelNames();
				}
				if(setExp instanceof AFatEnumVarsetExpression){
					chanNames = ((AFatEnumVarsetExpression) setExp).getChannelNames();
				}
				if(chanNames != null){
					boolean generateLieIn = false;
					for (ANameChannelExp aNameChannelExp : chanNames) {
						if(aNameChannelExp.getIdentifier().toString().equals(node.getIdentifier().toString())){
							generateLieIn = true;
							break;
						}
					}
					if(!generateLieIn && chanSetStack.size()==0){
						break;
					}else{
						StringBuilder lieIn = new StringBuilder();
						lieIn.append("lieIn(");
						lieIn.append("BasicEv(\"" + node.getIdentifier().toString() + "\")");
						lieIn.append(",");
						lieIn.append("\"");
						lieIn.append(setExp.toString());
						lieIn.append("\"");
						lieIn.append(")");
						
						if(!question.lieIn.contains(lieIn.toString())){
							question.lieIn.add(lieIn.toString());
						}
					}
				}
				
			}
			
		}//else if(parameters.size() == 1){
		else { //there are parameters
			
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
						ChannelTypeDefinition channDef = question.getChannelDefinition(node.getIdentifier().toString());
						//question.getScriptContent().append(" :- ");
						//copyCtxt.scriptContent.append(" :- ");
						//aux.getFirst().apply(this, question);
						if(channDef != null){
							//channDef.getChanDef().apply(this, copyCtxt);
							//int i = question.getScriptContent().lastIndexOf("_");
							int i = copyCtxt.getScriptContent().lastIndexOf("_");
							//question.getScriptContent().replace(i, i+1, parameters.getFirst().toString());
							copyCtxt.getScriptContent().replace(i, i+1, param.toString());
							//question.getScriptContent().append(".\n");
						
							//puts the information in the main context to be recovered at the end
							question.channelDependencies.add(copyCtxt.getScriptContent().toString());
						}
					}
					
				}
			}
				//if(question.info.containsKey(Utilities.STATES_KEY)){
				//if(question.stateVariables.size() > 0){
					//StringBuilder channelsStr = new StringBuilder(); 
					//ppppppppp
				//	Set stateVariables = question.stateVariables;
					//question.getScriptContent().append(")");
					//ArrayList<StringBuilder> states = (ArrayList<StringBuilder>) question.info.get(Utilities.STATES_KEY);
					//question.getScriptContent().append(") :- " + states.get(0));
					//channelsStr.append(") :- "+states.get(0));
					/*
					for(int i = 1; i < states.size(); i++){
						question.getScriptContent().append(",");
						//channelsStr.append(",");
						question.getScriptContent().append(states.get(i));
						//channelsStr.append(states.get(i));
					}
					question.getScriptContent().append(".\n");
					*/
				//} //else if (question.info.containsKey(Utilities.CHANNEL_DEFINITIONS_KEY)){
				/*
				 else if (question.info.containsKey(Utilities.CHANNEL_DEFINITIONS_KEY)){
				 
					PCommunicationParameter param = parameters.getFirst();
					if(param instanceof ASignalCommunicationParameter){
						PExp exp = param.getExpression();
						if(exp instanceof AVariableExp){
							CMLModelcheckerContext copyCtxt = question.copy();
							copyCtxt.scriptContent = new StringBuilder();
							LinkedList<AChannelNameDefinition> aux = (LinkedList<AChannelNameDefinition>) question.info.get(Utilities.CHANNEL_DEFINITIONS_KEY);
							//question.getScriptContent().append(" :- ");
							//copyCtxt.scriptContent.append(" :- ");
							//aux.getFirst().apply(this, question);
							aux.getFirst().apply(this, copyCtxt);
							//int i = question.getScriptContent().lastIndexOf("_");
							int i = copyCtxt.getScriptContent().lastIndexOf("_");
							//question.getScriptContent().replace(i, i+1, parameters.getFirst().toString());
							copyCtxt.getScriptContent().replace(i, i+1, param.toString());
							//question.getScriptContent().append(".\n");
							
							//puts the information in the main context to be recovered at the end
							question.channelDependencies.add(copyCtxt.getScriptContent().toString());
						}
						
					}
				}
				*/
				
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
	}
	
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
	public StringBuilder caseAExternalChoiceAction(AExternalChoiceAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		// it writes the left process into the buffer
		question.getScriptContent().append("eChoice(");
		node.getLeft().apply(this, question);
		question.getScriptContent().append(",");
		// it writes the right process into the buffer
		node.getRight().apply(this, question);
		question.getScriptContent().append(")");

		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAInternalChoiceAction(AInternalChoiceAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		// it writes the left process into the buffer
		question.getScriptContent().append("iChoice(");
		node.getLeft().apply(this, question);
		question.getScriptContent().append(",");
		// it writes the right process into the buffer
		node.getRight().apply(this, question);
		question.getScriptContent().append(")");

		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseABlockStatementAction(ABlockStatementAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		if(node.getDeclareStatement() != null){
			//node.getDeclareStatement().apply(this, question);
			node.getDeclareStatement().apply(rootVisitor, question);
		}
		node.getAction().apply(this, question);
		if(node.getDeclareStatement() != null){
			question.getScriptContent().append(")");
		}
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
	public StringBuilder caseASequentialCompositionAction(
			ASequentialCompositionAction node, CMLModelcheckerContext question)
			throws AnalysisException {
		// it writes the sequential composition constructor
		question.getScriptContent().append("seqC(");

		// it writes the first action
		node.getLeft().apply(this, question);
		question.getScriptContent().append(",");

		// it writes the second action
		node.getRight().apply(this, question);
		//if the action does not depend on the state.
		question.getScriptContent().append(")");
		//if(!question.info.containsKey(Utilities.STATES_KEY)){
		//	question.getScriptContent().append(")");
		//}

		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAReferenceAction(AReferenceAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		// the parameters also need to be written
		question.getScriptContent().append(
				"proc(\"" + node.getName() + "\", nopar)");

		// Adding auxiliary definitions
		// LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>)
		// question.info.get(node); ppppppppppppppp mudar para pegar de localActions
		LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>) question.info
				.get(Utilities.LOCAL_DEFINITIONS_KEY);

		CMLModelcheckerContext auxCtxt = new CMLModelcheckerContext();
		if (localDefinitions != null) {
			for (PDefinition pDefinition : localDefinitions) {
				pDefinition.apply(this, auxCtxt);
			}
		}
		int auxIndex = question.getScriptContent().indexOf(
				"#AUXILIARY_PROCESSES#");
		if (auxIndex != -1) {
			question.getScriptContent().replace(auxIndex,
					auxIndex + "#AUXILIARY_PROCESSES#".length(),
					auxCtxt.getScriptContent().toString());
			if(auxCtxt.getVariables().size() != 0){
				question.setVariables(auxCtxt.getVariables());
				question.copyVarDeclarationInfo(auxCtxt);
				question.copyVarDelInfo(auxCtxt);
				question.copyAssignmentDefInfo(auxCtxt);
			}
		}
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseADeclareStatementAction(
			ADeclareStatementAction node, CMLModelcheckerContext question)
			throws AnalysisException {
		node.getAssignmentDefs().getFirst().apply(rootVisitor,question);
		return question.getScriptContent();
	}
	
	///PARALLEL ACTIONS
	@Override
	public StringBuilder caseAInterleavingParallelAction(
			AInterleavingParallelAction node, CMLModelcheckerContext question)
			throws AnalysisException {
		
		// it writes the interleave as a generalised parallelism with am empty synchronisation set
		// it writes the left process into the buffer
		question.getScriptContent().append("genPar(");
		node.getLeftAction().apply(this, question);
		question.getScriptContent().append(",");
		question.getScriptContent().append("\"{}\"");
		question.getScriptContent().append(",");
		// it writes the right process into the buffer
		node.getRightAction().apply(this, question);
		question.getScriptContent().append(")");

		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseAGeneralisedParallelismParallelAction(
			AGeneralisedParallelismParallelAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		//it puts the event set in the context so the internal process can access and generate lieIn
		question.setStack.add(node.getChansetExpression());
	
		// it writes the left process into the buffer
		question.getScriptContent().append("genPar(");
		node.getLeftAction().apply(this, question);
		question.getScriptContent().append(",");
		node.getChansetExpression().apply(rootVisitor, question);
		question.getScriptContent().append(",");
		// it writes the right process into the buffer
		node.getRightAction().apply(this, question);
		question.getScriptContent().append(")");

		question.setStack.pop();
		
		return question.getScriptContent();
	}
	
	
	/////REPLICATED ACTIONS
	@Override
	public StringBuilder caseASequentialCompositionReplicatedAction(
			ASequentialCompositionReplicatedAction node,
			CMLModelcheckerContext question) throws AnalysisException {

		PSingleDeclaration sDecl = node.getReplicationDeclaration().getFirst();
		LinkedList<PExp> indexes = new LinkedList<PExp>();
		if (sDecl instanceof AExpressionSingleDeclaration) {
			PExp pExp = ((AExpressionSingleDeclaration) sDecl).getExpression();
			if (pExp instanceof ASetEnumSetExp) {
				indexes = ((ASetEnumSetExp) pExp).getMembers();
			}
		}
		// building combination of processses based on simple constructs
		StringBuilder replicatedAction = buildReplicatedAction(node, question, 
				node.getReplicatedAction(), Utilities.SEQUENTIAL_COMPOSITION,
				indexes.size());
		question.getScriptContent().append(replicatedAction.toString());

		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseAGeneralisedParallelismReplicatedAction(
			AGeneralisedParallelismReplicatedAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		PSingleDeclaration sDecl = node.getReplicationDeclaration().getFirst();
		LinkedList<PExp> indexes = new LinkedList<PExp>();
		if (sDecl instanceof AExpressionSingleDeclaration) {
			PExp pExp = ((AExpressionSingleDeclaration) sDecl).getExpression();
			if (pExp instanceof ASetEnumSetExp) {
				indexes = ((ASetEnumSetExp) pExp).getMembers();
			}
		}
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
		
		//it must put the sinchronisation set in the context before visiting the definitions
		//to generate lieIn
		//PVarsetExpression chanSet = node.getChansetExpression();
		//question.setStack.add(chanSet);
		
		//LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>) question.info
		//		.get(Utilities.LOCAL_DEFINITIONS_KEY);
		//CMLModelcheckerContext auxCtxt = new CMLModelcheckerContext();
		//if(localDefinitions != null){
		//	for (PDefinition pDefinition : localDefinitions) {
		//		pDefinition.apply(this, auxCtxt);
		//	}
		//}
		//question.setStack.pop();
		
		//int auxIndex = question.getScriptContent().indexOf(
		//		"#AUXILIARY_PROCESSES#");
		//if (auxIndex != -1) {
		//	question.getScriptContent().replace(auxIndex,
		//			auxIndex + "#AUXILIARY_PROCESSES#".length(),
		//			auxCtxt.getScriptContent().toString());
		//}

		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseAExternalChoiceReplicatedAction(
			AExternalChoiceReplicatedAction node,
			CMLModelcheckerContext question) throws AnalysisException {

		PSingleDeclaration sDecl = node.getReplicationDeclaration().getFirst();
		LinkedList<PExp> indexes = new LinkedList<PExp>();
		if (sDecl instanceof AExpressionSingleDeclaration) {
			PExp pExp = ((AExpressionSingleDeclaration) sDecl).getExpression();
			if (pExp instanceof ASetEnumSetExp) {
				indexes = ((ASetEnumSetExp) pExp).getMembers();
			}
		}
		// building combination of processses based on simple constructs
		StringBuilder replicatedAction = buildReplicatedAction( node, question,
				node.getReplicatedAction(), Utilities.EXTERNAL_CHOICE,
				indexes.size());
		question.getScriptContent().append(replicatedAction.toString());

		
		//LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>) question.info
		//		.get(Utilities.LOCAL_DEFINITIONS_KEY);
		//CMLModelcheckerContext auxCtxt = new CMLModelcheckerContext();
		//if(localDefinitions != null){
		//	for (PDefinition pDefinition : localDefinitions) {
		//		pDefinition.apply(this, auxCtxt);
		//	}
		//}
		//int auxIndex = question.getScriptContent().indexOf(
		//		"#AUXILIARY_PROCESSES#");
		//if (auxIndex != -1) {
		//	question.getScriptContent().replace(auxIndex,
		//			auxIndex + "#AUXILIARY_PROCESSES#".length(),
		//			auxCtxt.getScriptContent().toString());
		//}

		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseAInternalChoiceReplicatedAction(
			AInternalChoiceReplicatedAction node,
			CMLModelcheckerContext question) throws AnalysisException {

		PSingleDeclaration sDecl = node.getReplicationDeclaration().getFirst();
		LinkedList<PExp> indexes = new LinkedList<PExp>();
		if (sDecl instanceof AExpressionSingleDeclaration) {
			PExp pExp = ((AExpressionSingleDeclaration) sDecl).getExpression();
			if (pExp instanceof ASetEnumSetExp) {
				indexes = ((ASetEnumSetExp) pExp).getMembers();
			}
		}
		// building combination of processses based on simple constructs
		StringBuilder replicatedAction = buildReplicatedAction(node, question, 
				node.getReplicatedAction(), Utilities.INTERNAL_CHOICE,
				indexes.size());
		question.getScriptContent().append(replicatedAction.toString());

		return question.getScriptContent();
	}

	private StringBuilder buildReplicatedAction(SReplicatedActionBase replicatedAction, 
			CMLModelcheckerContext context, PAction action,
			int kindOfAction, int times) throws AnalysisException {

		CMLModelcheckerContext localCtxt = new CMLModelcheckerContext();
		localCtxt.localActions = context.localActions;
		if (times == 1) {
			action.apply(this, localCtxt);
		} else if (times > 1) {
			if (kindOfAction == Utilities.EXTERNAL_CHOICE) {
				localCtxt.getScriptContent().append("eChoice(");
			} else if (kindOfAction == Utilities.INTERNAL_CHOICE) {
				localCtxt.getScriptContent().append("iChoice(");
			} else if (kindOfAction == Utilities.SEQUENTIAL_COMPOSITION) {
				localCtxt.getScriptContent().append("seqC(");
			} else if (kindOfAction == Utilities.GEN_PARALLELISM) {
				localCtxt.getScriptContent().append("genPar(");
			}
			action.apply(this, localCtxt);
			localCtxt.getScriptContent().append(",");
			if(replicatedAction instanceof AGeneralisedParallelismReplicatedAction){
				((AGeneralisedParallelismReplicatedAction) replicatedAction).getChansetExpression().apply(rootVisitor, localCtxt);
				localCtxt.getScriptContent().append(",");
			}
			StringBuilder rest = buildReplicatedAction(replicatedAction, context,action, kindOfAction,
					times - 1);
			localCtxt.getScriptContent().append(rest.toString());
			localCtxt.getScriptContent().append(")");
		}
		return localCtxt.getScriptContent();
	}
	
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
