package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.AEqualsBinaryExp;
import org.overture.ast.expressions.AIntLiteralExp;
import org.overture.ast.expressions.ASetEnumSetExp;
import org.overture.ast.expressions.PExp;

import eu.compassresearch.ast.actions.ABlockStatementAction;
import eu.compassresearch.ast.actions.ACallStatementAction;
import eu.compassresearch.ast.actions.AChaosAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.ADivAction;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AExternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AGuardedAction;
import eu.compassresearch.ast.actions.AHidingAction;
import eu.compassresearch.ast.actions.AInternalChoiceAction;
import eu.compassresearch.ast.actions.AInternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASequentialCompositionReplicatedAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.actions.AValParametrisation;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.AExpressionSingleDeclaration;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AActionsDefinition;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChannelsDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.expressions.AEnumVarsetExpression;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrator;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaResult;
import eu.compassresearch.core.analysis.modelchecker.api.IFormulaIntegrator;

@SuppressWarnings("serial")
public class CMLModelcheckerVisitor extends
		QuestionAnswerCMLAdaptor<CMLModelcheckerContext, StringBuilder> {

	private List<PSource> sources;
	private final static String ANALYSIS_NAME = "Model Checker Visitor";
	private String propertyToCheck = Utilities.DEADLOCK_PROPERTY;

	public CMLModelcheckerVisitor(List<PSource> sources) {
		this.sources = sources;
	}

	public CMLModelcheckerVisitor(PSource singleSource) {
		this.sources = new LinkedList<PSource>();
		this.sources.add(singleSource);
	}

	public String getPropertyToCheck() {
		return propertyToCheck;
	}

	public void setPropertyToCheck(String propertyToCheck) {
		this.propertyToCheck = propertyToCheck;
	}

	@Override
	public StringBuilder caseAProcessDefinition(AProcessDefinition node,
			CMLModelcheckerContext question) throws AnalysisException {

		question.getScriptContent().append(
				"domain StartProcDomain includes CML_PropertiesSpec {\n");
		// .append("domain StartProcDomain includes CSP_Properties at \"./basic_formula_script.fml\" {\n");
		// .append("domain StartProcDomain includes CSP_Properties at \"../resources/basic_formula_script.fml\" {\n");

		// it converts all structures defined in the process body (auxiliary
		// actions, etc).
		node.getProcess().apply(this, question);
		
		int startIndex = question.getScriptContent().indexOf("#");
		int endIndex = question.getScriptContent().lastIndexOf("#");
		question.getScriptContent().replace(startIndex, endIndex + 1,
				"\"" + node.getName().toString() + "\"");
		// question.re
		// it converts the top level process
		// question.append("  ProcDef(");
		// question.append("\"" + node.getName() + "\", ");
		// ///PPPPPPPPPPPPPPPP
		// it applies recursivelly in the internal structure (the process body)
		// question.append(").\n");

		question.getScriptContent().append(
				"  conforms := CML_PropertiesSpec." + this.propertyToCheck + ".\n");
		question.getScriptContent().append("}\n\n");
		question.getScriptContent().append(
				"partial model StartProcModel of StartProcDomain{\n");
		if(question.info.containsKey(Utilities.CHANNEL_DEFINITIONS_KEY)){
			LinkedList<AChannelNameDefinition> aux = (LinkedList<AChannelNameDefinition>) question.info.get(Utilities.CHANNEL_DEFINITIONS_KEY);
			question.getScriptContent().append("  ");
			for (AChannelNameDefinition aChannelNameDefinition : aux) {
				aChannelNameDefinition.apply(this, question);
				question.getScriptContent().append("\n");
			}
			
		}
		question.getScriptContent().append(
				"  GivenProc(\"" + node.getName() + "\")\n");
		question.getScriptContent().append("}");

		// System.out.println("AProcessDefinition: " + node.toString());
		// System.out.println("name: " + node.getName());
		// System.out.println("_process: " + node.getProcess());

		// System.out.println(": " + node.getProcess().);

		// return node.getName() .apply(this, question);
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAActionProcess(AActionProcess node,
			CMLModelcheckerContext question) throws AnalysisException {
		// it applies to each definition of this action process
		// for (PDefinition definition : node.getDefinitionParagraphs()) {
		// definition.apply(this, question);
		// }
		// question.info.put(node.getAction(), node.getDefinitionParagraphs());
		question.info.put(Utilities.LOCAL_DEFINITIONS_KEY,
				node.getDefinitionParagraphs());
		
		//node.getDefinitionParagraphs().getFirst().apply(this, question);

		// Auxiliary processes will be written here
		question.getScriptContent().append("#AUXILIARY_PROCESSES#\n");

		// Iocommdefs will be written here
		question.getScriptContent().append("#IOCOMM_DEFS#\n");
		
		// it converts the top level process
		question.getScriptContent().append("  ProcDef(");
		question.getScriptContent().append("#PROCNAME#");
		question.getScriptContent().append(",");
		// parameters
		question.getScriptContent().append("nopar");
		question.getScriptContent().append(",");
		// the body
		node.getAction().apply(this, question);
		question.getScriptContent().append(").\n");

		// if there is no auxiliary definitions, then remove the TAG
		int index = question.getScriptContent()
				.indexOf("#AUXILIARY_PROCESSES#");
		if (index != -1) {
			question.getScriptContent().replace(index,
					index + "#AUXILIARY_PROCESSES#".length(), "");
		}
		
		//putting the initial state to be generated
		question.getScriptContent().append("  State(0,nBind,np,pBody)  :- GivenProc(np), ProcDef(np,_,pBody).\n");
		
		
		int indexIoCommDef = question.getScriptContent().indexOf("#IOCOMM_DEFS#");
		if(indexIoCommDef != -1){
			if(question.info.containsKey(Utilities.IOCOMM_DEFINITIONS_KEY)){
				question.getScriptContent().replace(indexIoCommDef, indexIoCommDef + "#IOCOMM_DEFS#".length(), question.info.get(Utilities.IOCOMM_DEFINITIONS_KEY).toString());
			}else{
				question.getScriptContent().replace(indexIoCommDef, indexIoCommDef + "#IOCOMM_DEFS#".length(), "");
			}
		}	
		

		if(question.info.containsKey(Utilities.CONDITION_KEY)){
			PExp condition = (PExp)question.info.get(Utilities.CONDITION_KEY);
			CMLModelcheckerContext auxCtxt = new CMLModelcheckerContext();
			condition.apply(this,auxCtxt);
			StringBuilder condResult = auxCtxt.getScriptContent();
			int indexGuardNDefHash = condResult.indexOf("#");
			if(indexGuardNDefHash != -1){
				question.getScriptContent().append("guardNDef(l,occ,b) :- State(l,b,_,condChoice(occ,_,_)),");
				condResult = condResult.replace(0,indexGuardNDefHash+1,"");
				question.getScriptContent().append(condResult.toString());
				question.getScriptContent().append(".");
			}else{
				question.getScriptContent().append("guardDef(l,occ,b) :- State(l,b,_,condChoice(occ,_,_)),");
				question.getScriptContent().append(condResult.toString());
				question.getScriptContent().append(".");
			}
			question.getScriptContent().append("\n");
			
		}
		
		return question.getScriptContent();
	}

	// Visitor methods for actions
	@Override
	public StringBuilder caseAActionsDefinition(AActionsDefinition node,
			CMLModelcheckerContext question) throws AnalysisException {
		// it applies to each internal definition
		LinkedList<AActionDefinition> actions = node.getActions();
		for (AActionDefinition currentActionDefinition : actions) {
			currentActionDefinition.apply(this, question);
		}
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
		StringBuilder replicatedAction = buildReplicatedAction(
				node.getReplicatedAction(), Utilities.EXTERNAL_CHOICE,
				indexes.size());
		question.getScriptContent().append(replicatedAction.toString());

		// for (PExp indexValue : indexes) {
		// CMLModelcheckerContext ctxt = new CMLModelcheckerContext();
		// StringBuilder txtToBeReplicated =
		// actionToBeReplicated.apply(this,ctxt);
		// String variableToBeReplaced = new
		// String((String)ctxt.info.get(actionToBeReplicated.getName()));

		// question.getScriptContent().append(txtToBeReplicated.toString().replace(variableToBeReplaced,
		// indexValue.toString()));
		// }

		// Adding auxiliary definitions
		// LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>)
		// question.info.get(node);
		LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>) question.info
				.get(Utilities.LOCAL_DEFINITIONS_KEY);
		CMLModelcheckerContext auxCtxt = new CMLModelcheckerContext();
		for (PDefinition pDefinition : localDefinitions) {
			pDefinition.apply(this, auxCtxt);
		}
		int auxIndex = question.getScriptContent().indexOf(
				"#AUXILIARY_PROCESSES#");
		if (auxIndex != -1) {
			question.getScriptContent().replace(auxIndex,
					auxIndex + "#AUXILIARY_PROCESSES#".length(),
					auxCtxt.getScriptContent().toString());
		}

		return question.getScriptContent();
	}

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
		StringBuilder replicatedAction = buildReplicatedAction(
				node.getReplicatedAction(), Utilities.SEQUENTIAL_COMPOSITION,
				indexes.size());
		question.getScriptContent().append(replicatedAction.toString());

		// for (PExp indexValue : indexes) {
		// CMLModelcheckerContext ctxt = new CMLModelcheckerContext();
		// StringBuilder txtToBeReplicated =
		// actionToBeReplicated.apply(this,ctxt);
		// String variableToBeReplaced = new
		// String((String)ctxt.info.get(actionToBeReplicated.getName()));

		// question.getScriptContent().append(txtToBeReplicated.toString().replace(variableToBeReplaced,
		// indexValue.toString()));
		// }

		// Adding auxiliary definitions
		// LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>)
		// question.info.get(node);
		LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>) question.info
				.get(Utilities.LOCAL_DEFINITIONS_KEY);
		CMLModelcheckerContext auxCtxt = new CMLModelcheckerContext();
		for (PDefinition pDefinition : localDefinitions) {
			pDefinition.apply(this, auxCtxt);
		}
		int auxIndex = question.getScriptContent().indexOf(
				"#AUXILIARY_PROCESSES#");
		if (auxIndex != -1) {
			question.getScriptContent().replace(auxIndex,
					auxIndex + "#AUXILIARY_PROCESSES#".length(),
					auxCtxt.getScriptContent().toString());
		}

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
		StringBuilder replicatedAction = buildReplicatedAction(
				node.getReplicatedAction(), Utilities.INTERNAL_CHOICE,
				indexes.size());
		question.getScriptContent().append(replicatedAction.toString());

		// for (PExp indexValue : indexes) {
		// CMLModelcheckerContext ctxt = new CMLModelcheckerContext();
		// StringBuilder txtToBeReplicated =
		// actionToBeReplicated.apply(this,ctxt);
		// String variableToBeReplaced = new
		// String((String)ctxt.info.get(actionToBeReplicated.getName()));

		// question.getScriptContent().append(txtToBeReplicated.toString().replace(variableToBeReplaced,
		// indexValue.toString()));
		// }

		// Adding auxiliary definitions
		// LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>)
		// question.info.get(node);
		LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>) question.info
				.get(Utilities.LOCAL_DEFINITIONS_KEY);
		CMLModelcheckerContext auxCtxt = new CMLModelcheckerContext();
		for (PDefinition pDefinition : localDefinitions) {
			pDefinition.apply(this, auxCtxt);
		}
		int auxIndex = question.getScriptContent().indexOf(
				"#AUXILIARY_PROCESSES#");
		if (auxIndex != -1) {
			question.getScriptContent().replace(auxIndex,
					auxIndex + "#AUXILIARY_PROCESSES#".length(),
					auxCtxt.getScriptContent().toString());
		}

		return question.getScriptContent();
	}

	private StringBuilder buildReplicatedAction(PAction action,
			int kindOfAction, int times) throws AnalysisException {

		CMLModelcheckerContext localCtxt = new CMLModelcheckerContext();
		if (times == 1) {
			action.apply(this, localCtxt);
		} else if (times > 1) {
			if (kindOfAction == Utilities.EXTERNAL_CHOICE) {
				localCtxt.getScriptContent().append("eChoice(");
			} else if (kindOfAction == Utilities.INTERNAL_CHOICE) {
				localCtxt.getScriptContent().append("iChoice(");
			} else if (kindOfAction == Utilities.SEQUENTIAL_COMPOSITION) {
				localCtxt.getScriptContent().append("seqC(");
			}
			action.apply(this, localCtxt);
			localCtxt.getScriptContent().append(",");
			StringBuilder rest = buildReplicatedAction(action, kindOfAction,
					times - 1);
			localCtxt.getScriptContent().append(rest.toString());
			localCtxt.getScriptContent().append(")");
		}
		return localCtxt.getScriptContent();
	}

	@Override
	public StringBuilder caseAHidingAction(AHidingAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		// "acoes \ {ev}" hide(acoes,{ev})
		question.getScriptContent().append("hide(");
		node.getLeft().apply(this, question);
		question.getScriptContent().append(",");
		node.getChansetExpression().apply(this, question);
		question.getScriptContent().append(")");
		// gerar os lieIn. fixo tem que mudar
		// question.append("lieIn(Sigma(\"b\"),\"[b]\").\n");
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAEnumVarsetExpression(AEnumVarsetExpression node,
			CMLModelcheckerContext question) throws AnalysisException {
		// question.append("\"" + node.toString() + "\"");
		question.getScriptContent().append("\"X\"");

		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAActionDefinition(AActionDefinition node,
			CMLModelcheckerContext question) throws AnalysisException {
		question.getScriptContent().append("  ProcDef(");
		question.getScriptContent().append("\"" + node.getName() + "\",");
		// parameters
		LinkedList<PParametrisation> parameters = node.getDeclarations();
		if(parameters.size()==0){
			question.getScriptContent().append("nopar");
			question.getScriptContent().append(",");
			// it converts the internal action (body)
			node.getAction().apply(this, question);
			question.getScriptContent().append(").\n");
		} else if(parameters.size()==1){
			question.getScriptContent().append("SPar(");
			node.getDeclarations().getFirst().apply(this, question);
			question.getScriptContent().append("),");
			node.getAction().apply(this, question);
			//question.getScriptContent().append(") ");
			//if there are channel declaration for the same action name it must be put here.
			//if(question.info.containsKey(Utilities.CHANNEL_DEFINITIONS_KEY)){
			//	LinkedList<AChannelNameDefinition> channels = (LinkedList<AChannelNameDefinition>)question.info.get(Utilities.CHANNEL_DEFINITIONS_KEY);
			//	for (AChannelNameDefinition aChannelNameDefinition : channels) {
					//aChannelNameDefinition.getName().toString().equals(node.get)
			//	}
			//}
		}

		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseAValParametrisation(AValParametrisation node,
			CMLModelcheckerContext question) throws AnalysisException {
		question.getScriptContent().append("Int(");
		question.getScriptContent().append(node.getDeclaration().getIdentifiers().getFirst().toString());
		question.getScriptContent().append(")");
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseATypeSingleDeclaration(
			ATypeSingleDeclaration node, CMLModelcheckerContext question)
			throws AnalysisException {
		question.getScriptContent().append("Int(");
		question.getScriptContent().append(node.getIdentifiers().getFirst().getName());
		question.getScriptContent().append(")");
		return question.getScriptContent();
	}

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
		}else if(parameters.size() == 1){
			question.getScriptContent().append(
					"Prefix(IOComm(0,\"" + node.getIdentifier()+"."+parameters.getFirst().toString() + "\",");
			
			question.getScriptContent().append("Int(" + parameters.getFirst().toString() + ")");
			question.getScriptContent().append("),");
			
				
				//it applies recursivelly in the internal structure
				node.getAction().apply(this, question);

				question.getScriptContent().append("))");
				
				if(question.info.containsKey(Utilities.CHANNEL_DEFINITIONS_KEY)){
					LinkedList<AChannelNameDefinition> aux = (LinkedList<AChannelNameDefinition>) question.info.get(Utilities.CHANNEL_DEFINITIONS_KEY);
					question.getScriptContent().append(" :- ");
					aux.getFirst().apply(this, question);
					int i = question.getScriptContent().lastIndexOf("_");
					question.getScriptContent().replace(i, i+1, parameters.getFirst().toString());
					question.getScriptContent().append(".\n");
				}
				
				CMLModelcheckerContext aux = new CMLModelcheckerContext();
				aux.getScriptContent().append("  IOCommDef(0,0,");
				aux.getScriptContent().append("Int(" + parameters.getFirst().toString() + ")");
				aux.getScriptContent().append(",nBind,nBind) :- State(0,_,_,");
				aux.getScriptContent().append("Prefix(IOComm(0,\"" + node.getIdentifier()+"."+parameters.getFirst().toString() + "\",");
				aux.getScriptContent().append("Int(" + parameters.getFirst().toString() + ")");
				aux.getScriptContent().append("),");
				node.getAction().apply(this, aux);
				aux.getScriptContent().append(")).\n");
				//aux.getScriptContent().append("  State(0,nBind,np,pBody)  :- GivenProc(np), ProcDef(np,_,pBody).\n");
				
				
				question.info.put(Utilities.IOCOMM_DEFINITIONS_KEY, aux.getScriptContent().toString());
		}

		return question.getScriptContent();
	}
	
	
	
	@Override
	public StringBuilder caseACallStatementAction(ACallStatementAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		LinkedList<PExp> args = node.getArgs();
		if(args.size() == 1){
			question.getScriptContent().append("proc(\"");
			question.getScriptContent().append(node.getName()+"\",SPar(");
			node.getArgs().getFirst().apply(this, question);
			question.getScriptContent().append("))");
		}
		
		// Adding auxiliary definitions
				// LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>)
				// question.info.get(node);
				LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>) question.info
						.get(Utilities.LOCAL_DEFINITIONS_KEY);

				CMLModelcheckerContext auxCtxt = new CMLModelcheckerContext();
				auxCtxt.copyChannelInfo(question);
				if (localDefinitions != null) {
					for (PDefinition pDefinition : localDefinitions) {
						pDefinition.apply(this, auxCtxt);
					}
				}
				question.copyIOCommDefInfo(auxCtxt);
				int auxIndex = question.getScriptContent().indexOf(
						"#AUXILIARY_PROCESSES#");
				if (auxIndex != -1) {
					question.getScriptContent().replace(auxIndex,
							auxIndex + "#AUXILIARY_PROCESSES#".length(),
							auxCtxt.getScriptContent().toString());
				}
				//if(question.info.containsKey(Utilities.IOCOMM_DEFINITIONS_KEY)){
				//	question.getScriptContent().append(question.info.get(Utilities.IOCOMM_DEFINITIONS_KEY));
				//}
				
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseASkipAction(ASkipAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		question.getScriptContent().append(node.toString());

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
	public StringBuilder caseAChannelsDefinition(AChannelsDefinition node,
			CMLModelcheckerContext question) throws AnalysisException {
		//question.getScriptContent().append("Channel(0,\"");
		//node.getChannelNameDeclarations().getFirst().apply(this, question);
		//question.getScriptContent().append(")");
		question.info.put(Utilities.CHANNEL_DEFINITIONS_KEY, node.getChannelNameDeclarations());		
		
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
		node.getAction().apply(this, question);

		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAGuardedAction(AGuardedAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		// it writes the conditional choice constructor
		question.getScriptContent().append("condChoice(");
		// it writes the condition as an integer and puts the expression
		//to be evaluated in the context
		question.info.put(Utilities.CONDITION_KEY, node.getExpression());
		//node.getExpression().apply(this, question);
		question.getScriptContent().append(Utilities.OCCUR_COUNT++ + ",");
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
		question.getScriptContent().append(")");

		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseAChannelNameDefinition(
			AChannelNameDefinition node, CMLModelcheckerContext question)
			throws AnalysisException {
		if( ((AChannelType)node.getSingleType().getType()).getType() != null){
			question.getScriptContent().append("Channel(0,\"");
			String type = node.getSingleType().getType().toString();
			type = type.substring(0,1).toUpperCase().concat(type.substring(1));
			question.getScriptContent().append(node.getSingleType().getIdentifiers().getFirst().getName()+"\",");
			question.getScriptContent().append(type);
			//if there is a fixed initial value it has to be used here
			question.getScriptContent().append("(_))");
		}
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
		// question.info.get(node);
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
		}
		return question.getScriptContent();
	}

	// Visitor methods for expressions
	@Override
	public StringBuilder caseAEqualsBinaryExp(AEqualsBinaryExp node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		PExp left = node.getLeft();
		PExp right = node.getRight();
		if(left instanceof AIntLiteralExp){
			if(right instanceof AIntLiteralExp){
				String leftValue = ((AIntLiteralExp) left).getValue().toString();
				String rightValue = ((AIntLiteralExp) right).getValue().toString();
				if(!leftValue.equals(rightValue)){
					question.getScriptContent().append("GUARDNDEF#"+leftValue + " != " + rightValue);
				}else{
					question.getScriptContent().append(leftValue + " = " + rightValue);
				}
			}
		}
		//question.getScriptContent().append("EQ(");
		//node.getLeft().apply(this, question);
		//question.getScriptContent().append(",");
		//node.getRight().apply(this, question);
		//question.getScriptContent().append(")");

		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAIntLiteralExp(AIntLiteralExp node,
			CMLModelcheckerContext question) throws AnalysisException {

		question.getScriptContent().append("Int(");
		question.getScriptContent().append(node.getValue());
		question.getScriptContent().append(")");

		return question.getScriptContent();
	}

	// to be used by the command line tool
	public String getAnalysisName() {
		return ANALYSIS_NAME;
	}

	public String[] generateFormulaCodeForAll() throws IOException,
			AnalysisException {
		// StringBuilder basicContent = new StringBuilder();
		String[] codes = new String[0];
		if (sources.size() > 0) {
			codes = new String[sources.size()];
			// basicContent =
			// Utilities.readScriptFromFile(Utilities.BASIC_FORMULA_SCRIPT);
		}

		for (PSource source : sources) {
			CMLModelcheckerContext content = new CMLModelcheckerContext();
			// content.getScriptContent().append(basicContent.toString());
			for (PDefinition paragraph : source.getParagraphs()) {
				try {
					paragraph.apply(this, content);
				} catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
				}
			}
			codes[sources.indexOf(source)] = content.getScriptContent()
					.toString();
			// it saves the current generated formula file
			// String formulaFileName =
			// Utilities.generateFormulaFileName(((AFileSource)source).getFile().getPath());
			// System.out.println(formulaFileName);
			// Utilities.writeScriptToFile(formulaFileName,content);

		}
		return codes;
	}

	/**
	 * @param args
	 * @throws Throwable
	 */
	public static void main(String[] args) throws Throwable {
		String cml_example = "src/test/resources/action-reference-parametrised.cml";
		System.out.println("Testing on " + cml_example);
		
		// List<PSource> sources = new LinkedList<PSource>();
		PSource source = Utilities.makeSourceFromFile(cml_example);
		// sources.add(source);
		CMLModelcheckerVisitor visitor = new CMLModelcheckerVisitor(source);
		visitor.setPropertyToCheck(Utilities.DEADLOCK_PROPERTY);
		String[] codes = visitor.generateFormulaCodeForAll();
		for (int i = 0; i < codes.length; i++) {
			System.out.println(codes[i]);
		}
		System.out.println("Analysing generated FORMULA specification");
		IFormulaIntegrator mc = FormulaIntegrator.getInstance();
		FormulaResult mcResult = mc.analyse(codes[0]);

		System.out.println("File " + cml_example + " is "
				+ (mcResult.isSatisfiable() ? "SAT" : "UNSAT") + "\n");
		double loadTime = mcResult.getElapsedTimeLoad();
		double solveTime = mcResult.getElapsedTimeSolve();
		System.out.println("Analysis time (load + solve) = " + "(" + loadTime
				+ " + " + solveTime + ") = " + (loadTime + solveTime)
				+ " seconds\n");
		System.out.println("Base of Facts: \n");
		System.out.println(mcResult.getFacts());
		mc.finalize();
	}

}
