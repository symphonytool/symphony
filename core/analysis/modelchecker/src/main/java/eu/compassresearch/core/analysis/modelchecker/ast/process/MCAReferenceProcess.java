package eu.compassresearch.core.analysis.modelchecker.ast.process;

import java.util.ArrayList;
import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAValParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCActionCall;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCGenericCall;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCOperationCall;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCProcessCall;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ParameterDependency;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ParameterFact;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAActionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAExplicitCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCALocalDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAProcessDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCSCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.ArrayListSet;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAReferenceProcess implements MCPProcess {

	protected String name;
	protected LinkedList<MCPCMLExp> args;
	protected MCAProcessDefinition proc;
	protected String parentDefinitionName;
	
	public MCAReferenceProcess(String name, LinkedList<MCPCMLExp> args, MCAProcessDefinition procDef, String parentDefinitionName) {
		this.name = name;
		this.args = args;
		this.proc = procDef;
		this.parentDefinitionName = parentDefinitionName;
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
					call = new MCActionCall(name, args,null);
					result.append(call.toFormula(option));
					if(args.size() == 1){ //there is one parameter being used
						MCPCMLType parType = ((MCAValParametrisation)localAction.getDeclarations().getFirst()).getDeclaration().getType();
						ParameterFact paramFact = new ParameterFact(this.name, args.getFirst(),parType);
						context.parameterFacts.add(paramFact);
					}
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
					if(args.size() == 1){ //there is one parameter being used
						MCAProcessDefinition procDef = context.getProcessByName(name);
						LinkedList<MCPParametrisation> parameters = procDef.getLocalState();
						MCALocalDefinition localDef = new MCALocalDefinition(parameters.getFirst().toFormula(option), null);
						MCAValParametrisation param = new MCAValParametrisation(localDef);
						ParameterDependency paramDep = new ParameterDependency(this.name,param,this.parentDefinitionName); 
						context.parameterDependencies.add(paramDep);
						
						ParameterFact paramFact = new ParameterFact(this.name, args.getFirst(),parameters.getFirst().getDeclaration().getType());
						context.parameterFacts.add(paramFact);
					}
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

	public String getParentDefinitionName() {
		return parentDefinitionName;
	}

	public void setParentDefinitionName(String parentDefinitionName) {
		this.parentDefinitionName = parentDefinitionName;
	}
	
}
