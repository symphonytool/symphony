package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import eu.compassresearch.ast.definitions.SCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAActionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAExplicitCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCSCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCACallStatementAction implements MCPAction {

	private String name;
	private LinkedList<MCPCMLExp> args;

	public MCACallStatementAction(String name, LinkedList<MCPCMLExp> args) {
		this.name = name;
		this.args = args;
	}

	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		
		ArrayList<MCAActionDefinition> localActions = context.localActions;
		boolean callResolved = false;
		if(localActions.size() > 0){ //if there are auxiliary actions
			for (MCAActionDefinition localAction : localActions) {
				if(localAction.getName().toString().equals(this.name.toString())){
					callResolved = true;
					result.append("proc(\"" + localAction.getName().toString() + "\",");
					if(args.size()==0){
						result.append("void");
					} else if(args.size() == 1){
						result.append(args.getFirst().toFormula(option));
					} else if(args.size() > 1){
						//TODO
					}
					result.append(")");
				}
			}
		}
		if (!callResolved) {
			for (MCSCmlOperationDefinition pDefinition : context.operations) {
				if(pDefinition instanceof MCAExplicitCmlOperationDefinition){
					((MCAExplicitCmlOperationDefinition) pDefinition).setParentAction(this);
					if(((MCAExplicitCmlOperationDefinition) pDefinition).getName().toString().equals(this.name)){
						result.append("operation(\"" + this.name + "\",");
						if(args.size()==0){
							result.append("void");
						} else if(args.size()==1){
							result.append(args.getFirst().toFormula(option));
						} else if(args.size() > 1){
							//TODO
						}
						result.append(")");
					}
				}
			}
		}
		
		return result.toString();
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedList<MCPCMLExp> getArgs() {
		return args;
	}

	public void setArgs(LinkedList<MCPCMLExp> args) {
		this.args = args;
	}

}
