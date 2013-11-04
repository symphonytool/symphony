package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.Iterator;
import java.util.Map.Entry;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.Domain;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCAssignDef;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCGuardDef;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCIOCommDef;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCLieInFact;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAActionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAChannelNameDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAExplicitCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAProcessDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAValueDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCSCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class ProblemDomainBuilder {
	
	public void buildProblemDomain(Domain problemDomain){
		StringBuilder content = new StringBuilder();
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
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
	
	
}
