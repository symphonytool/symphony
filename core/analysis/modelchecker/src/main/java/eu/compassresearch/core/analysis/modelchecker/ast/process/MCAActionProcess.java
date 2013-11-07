package eu.compassresearch.core.analysis.modelchecker.ast.process;

import java.util.LinkedList;

import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCPCMLDefinition;

public class MCAActionProcess implements MCPProcess {

	private MCPCMLDefinition definition;
	private MCPAction action;
	
	
	public MCAActionProcess(MCPCMLDefinition definition,
			MCPAction action) {
		this.definition = definition;
		this.action = action;
	}


	@Override
	public String toFormula(String option) {
		/*
		PAction mainAction = node.getAction();
		 
		
		if(mainAction instanceof AGeneralisedParallelismReplicatedAction){
			PVarsetExpression chanSet = ((AGeneralisedParallelismReplicatedAction) mainAction).getChansetExpression();
			question.setStack.add(chanSet);
		}
		
		if(mainAction instanceof AGeneralisedParallelismParallelAction){
			PVarsetExpression chanSet = ((AGeneralisedParallelismParallelAction) mainAction).getChansetExpression();
			question.setStack.add(chanSet);
		}
		*/
		
		return this.action.toFormula(option);
	}

	
	public MCPCMLDefinition getDefinition() {
		return definition;
	}


	public void setDefinition(MCPCMLDefinition definition) {
		this.definition = definition;
	}


	public MCPAction getAction() {
		return action;
	}


	public void setAction(MCPAction action) {
		this.action = action;
	}

	
}
