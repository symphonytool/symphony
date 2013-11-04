package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexLocation;

import eu.compassresearch.ast.actions.ASingleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ActionChannelDependency;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ExpressionEvaluator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCAssignDef;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCChannel;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCCondition;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCGuardDef;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCIOCommDef;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCLieInFact;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCNegGuardDef;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCPosGuardDef;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCValueDef;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCATypeSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCPProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.Binding;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.NullBinding;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Int;
import eu.compassresearch.core.analysis.modelchecker.visitors.CMLModelcheckerContext;
import eu.compassresearch.core.analysis.modelchecker.visitors.Condition;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAProcessDefinition implements MCPCMLDefinition {

	private String name;
	private LinkedList<MCATypeSingleDeclaration> localState = new LinkedList<MCATypeSingleDeclaration>();
	private MCPProcess process;
	
	
	public MCAProcessDefinition(String name,
			LinkedList<MCATypeSingleDeclaration> localState, MCPProcess process) {
		
		this.name = name;
		this.localState = localState;
		this.process = process;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		//generateUserTypeDefinitions(question);
		//generateChannelTypes(question);
		
		result.append("domain StartProcDomain includes CML_PropertiesSpec {\n");
		
		//generate value definitions
		generateValueDefinitions(result,option);
		
		//generate auxiliary definitions (actions, operationDefs, iocommdefs, guardDefs, etc.) 
		generateAuxiliaryActions(result,option);
		
		result.append("  ProcDef(\"");
		result.append(this.name);
		result.append("\",");
		//parameters (local state)
		ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
		
		MCPCMLType decls =  evaluator.instantiateMCTypeFromDecls(this.localState);
		result.append(decls.toFormula(MCNode.NAMED));
		result.append(",");
		result.append(this.process.toFormula(option));
		result.append(")");
		
		//if the action has dependencies we get them from the context
		LinkedList<ActionChannelDependency> dependencies = context.getActionChannelDependendies(this.name);
		if(dependencies.size() > 0){
			result.append(" :- ");
			for (Iterator<ActionChannelDependency> iterator = dependencies.iterator(); iterator.hasNext();) {
				ActionChannelDependency actionChannelDependency = (ActionChannelDependency) iterator.next();
				result.append(actionChannelDependency.toFormula(option));
				if(iterator.hasNext()){
					result.append(",");
				}
			}
		}
		
		generateInitialState(result,option);
		
		generateGuardDefinitions(result,option);
		
		generateAssignDefinitions(result, option);
		
		generateOperationDefinitions(result,option);
		
		generateIOCommDefs(result,option);
		
		
		//GENERATION OF THE PARTIAL MODEL
		
		result.append("  conforms := CML_PropertiesSpec." + context.propertyToCheck + ".\n");
		result.append("}\n\n");
		result.append("partial model StartProcModel of StartProcDomain{\n");

		generateLieInFacts(result,option);

		generateChannels(result,option);
		
		//pppp
		//generate fetch facts
		//generate del facts
		//generate upd facts (if necessary)

		//generateBindingFacts(question);

		//generateChannelDefinitions(question);

		result.append("  GivenProc(\"" + this.name + "\")\n");
		result.append("}");


		
		//String content = question.getScriptContent().toString();
		//Binding maxBinding = question.getMaxBinding();
		//content = content.replaceAll(Utilities.MAX_BIND, maxBinding.toFormula());
		//StringBuilder newContent = new StringBuilder();
		//newContent.append(content);
		//question.setScriptContent(newContent);

		//return question.getScriptContent();
		
		return result.toString();
	}

	private void generateAuxiliaryActions(StringBuilder content, String option){
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		for (MCAActionDefinition localAction : context.localActions) {
			content.append(localAction.toFormula(option));
		}
	}

	private void generateInitialState(StringBuilder content, String option){
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		content.append("  State(0,");
		content.append(context.maximalBinding.toFormula(option));
		content.append(",np,pBody)  :- GivenProc(np), ProcDef(np,nopar,pBody).\n");
	}
	
	private void generateGuardDefinitions(StringBuilder content, String option){
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
	
		for (Iterator<Entry<MCPCMLExp,MCGuardDef>> iterator = context.guardDefs.entrySet().iterator(); iterator.hasNext();) {
			Entry<MCPCMLExp,MCGuardDef> item = (Entry<MCPCMLExp,MCGuardDef>) iterator.next();
			MCGuardDef guardDef = item.getValue();
			content.append(guardDef.toFormula(option));
		}
	}
	
	private void generateAssignDefinitions(StringBuilder content, String option){
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		if(context.assignDefs.size() != 0){
			for (MCAssignDef assingnDef : context.assignDefs) {
				content.append(assingnDef.toFormula(option) + "\n");
			}
		}
	}
	
	private void generateOperationDefinitions(StringBuilder content, String option){
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		for (MCSCmlOperationDefinition opDef : context.operations) {
			if(opDef instanceof MCAExplicitCmlOperationDefinition){
				MCAExplicitCmlOperationDefinition op = (MCAExplicitCmlOperationDefinition) opDef;
				content.append(op.toFormula(MCNode.NAMED));
			}
		}
	}
	
	private void generateIOCommDefs(StringBuilder content, String option){
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		for (MCIOCommDef ioCommDef : context.ioCommDefs) {
			content.append(ioCommDef.toFormula(option));
			content.append("\n");
		}
	}
	private void generateValueDefinitions(StringBuilder content, String option){
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		for (MCAValueDefinition valueDef : context.valueDefinitions) {
			content.append(valueDef.toFormula(option));
			content.append("\n");
		}
	}
	
	private void generateLieInFacts(StringBuilder content, String option){
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		if(context.lieIn.size() != 0){
			for (MCLieInFact lieIn : context.lieIn) {
				content.append(lieIn.toFormula(option) + "\n");
			}
		}
	}
	
	private void generateChannels(StringBuilder content, String option){
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		if(context.channelDefs.size() != 0){
			for (MCAChannelNameDefinition chanDef : context.channelDefs) {
				if(chanDef.isTyped()){
					content.append(chanDef.toFormula(option) + "\n");
				}
			}
		}
	}
	public LinkedList<MCATypeSingleDeclaration> getLocalState() {
		return localState;
	}



	public void setLocalState(LinkedList<MCATypeSingleDeclaration> localState) {
		this.localState = localState;
	}



	public MCPProcess getProcess() {
		return process;
	}



	public void setProcess(MCPProcess process) {
		this.process = process;
	}

	
}
