package eu.compassresearch.core.analysis.modelchecker.ast.process;

import java.util.ArrayList;
import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCActionCall;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCGenericCall;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCOperationCall;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCProcessCall;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAActionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAExplicitCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAProcessDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCSCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.visitors.ArrayListSet;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAReferenceProcess implements MCPProcess {

	protected String name;
	protected LinkedList<MCPCMLExp> args;
	protected MCAProcessDefinition proc;
	
	public MCAReferenceProcess(String name, LinkedList<MCPCMLExp> args, MCAProcessDefinition procDef) {
		this.name = name;
		this.args = args;
		this.proc = procDef;
	}
	
	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		ArrayList<MCAActionDefinition> localActions = context.localActions;
		boolean callResolved = false;
		MCGenericCall call = null;
		
		if(localActions.size() > 0){ //if there are auxiliary actions
			for (MCAActionDefinition localAction : localActions) {
				if(localAction.getName().toString().equals(this.name.toString())){
					callResolved = true;
					call = new MCActionCall(name, args);
					result.append(call.toFormula(option));
					break;
				}
			}
		}
		ArrayListSet<MCAProcessDefinition> procDefs = context.processDefinitions;
		if(!callResolved){
			for (MCAProcessDefinition mcaProcessDefinition : procDefs) {
				if(mcaProcessDefinition.getName().equals(this.name)){
					callResolved = true;
					call = new MCProcessCall(name, args, null);
					result.append(call.toFormula(option));
					break;
				}
			}
		}
		if (!callResolved) {
			for (MCSCmlOperationDefinition pDefinition : context.operations) {
				if(pDefinition instanceof MCAExplicitCmlOperationDefinition){
					//((MCAExplicitCmlOperationDefinition) pDefinition).setParentAction(this);
					if(((MCAExplicitCmlOperationDefinition) pDefinition).getName().toString().equals(this.name)){
						call = new MCOperationCall(name, args, null);
						result.append(call.toFormula(option));
						break;
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

	public MCAProcessDefinition getProc() {
		return proc;
	}

	public void setProc(MCAProcessDefinition proc) {
		this.proc = proc;
	}
}
