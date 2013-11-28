package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAActionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAExplicitCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAProcessDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAValueDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCSCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCASBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class ProblemDomainBuilder {
	
	public String buildProblemDomain(){
		
		StringBuilder content = new StringBuilder();
		String option = MCNode.DEFAULT;
		
		
		//generate value definitions
		generateValueDefinitions(content,option);
				
		//generate auxiliary definitions (actions, operationDefs, iocommdefs, guardDefs, etc.) 
		generateAuxiliaryActions(content,option);
		
		//generates process definitions 
		generateProcessDefinitions(content, option);
		
		//generate the initial state
		generateInitialState(content,option);
		
		//generates guard definitions
		generateGuardDefinitions(content,option);
		
		
		//generates assignment definitions
		generateAssignDefinitions(content, option);
		
		
		//generates operation definitions
		generateOperationDefinitions(content,option);
		
		//generate iocom defs
		generateIOCommDefs(content,option);
		
		
		//generate all facts related to set manipulation (required by the toolkit)
		generateSetFacts(content,option);
		
		//generate conforms clause
		generateConforms(content,option);
		
		return content.toString();
	}
	
	private void generateAuxiliaryActions(StringBuilder content, String option){
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		for (MCAActionDefinition localAction : context.localActions) {
			content.append(localAction.toFormula(option));
		}
	}

	private void generateProcessDefinitions(StringBuilder content, String option){
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		for (MCAProcessDefinition processDef : context.processDefinitions) {
			content.append(processDef.toFormula(option));
		}
	}
	private void generateInitialState(StringBuilder content, String option){
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		content.append("  State(");
		content.append(context.maximalBinding.toFormula(option));
		content.append(",pBody)  :- GivenProc(np), ProcDef(np,nopar,pBody).\n");
	}
	
	private void generateGuardDefinitions(StringBuilder content, String option){
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
	
		for (Iterator<Entry<MCPCMLExp,LinkedList<MCGuardDef>>> iterator = context.guardDefs.entrySet().iterator(); iterator.hasNext();) {
			Entry<MCPCMLExp,LinkedList<MCGuardDef>> item = (Entry<MCPCMLExp,LinkedList<MCGuardDef>>) iterator.next();
			LinkedList<MCGuardDef> guardDefList = item.getValue();
			for (MCGuardDef mcGuardDef : guardDefList) {
				content.append(mcGuardDef.toFormula(option));
			}
		}
		
		for (Iterator<Entry<MCPCMLExp,LinkedList<NewMCGuardDef>>> iterator = context.stmGuardDefs.entrySet().iterator(); iterator.hasNext();) {
			Entry<MCPCMLExp,LinkedList<NewMCGuardDef>> item = (Entry<MCPCMLExp,LinkedList<NewMCGuardDef>>) iterator.next();
			LinkedList<NewMCGuardDef> guardDefList = item.getValue();
			for (NewMCGuardDef mcGuardDef : guardDefList) {
				content.append(mcGuardDef.toFormula(option));
			}
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
	
	private void generateSetFacts(StringBuilder content, String option){
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		for (MCASBinaryExp setExp : context.setExpressioFacts) {
			content.append(setExp.toFormula(option));
			content.append("\n");
		}
	}
	
	private void generateConforms(StringBuilder content, String option){
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		content.append("  conforms := " + context.propertiesDomain.getName() + "." + context.propertyToCheck + ".\n");
	}
	
}
