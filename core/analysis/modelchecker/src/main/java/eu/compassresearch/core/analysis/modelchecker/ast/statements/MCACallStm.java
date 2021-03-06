package eu.compassresearch.core.analysis.modelchecker.ast.statements;

import java.util.ArrayList;
import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAValParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCActionCall;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCFunctionCall;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCGenericCall;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCOperationCall;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ParameterDependency;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ParameterFact;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAActionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAExplicitCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAExplicitFunctionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCALocalDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCSCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCSFunctionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCPCMLPattern;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAOperationType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCACallStm implements MCPCMLStm {

	private String name;
	private LinkedList<MCPCMLExp> args;
	private String parentDefinitionName;
	
	
	public MCACallStm(String name, LinkedList<MCPCMLExp> args, String parentDefinitionName) {
		this.name = name;
		this.args = args;
		this.parentDefinitionName = parentDefinitionName;
	}


	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		
		String nameToSearch = context.reverseNameMapping.get(this.name);
		ArrayList<MCAActionDefinition> localActions = context.localActions;
		boolean callResolved = false;
		MCGenericCall call = null;
		
		if(localActions.size() > 0){ //if there are auxiliary actions
			for (MCAActionDefinition localAction : localActions) {
				//if(localAction.getName().toString().equals(this.name.toString())){
				if(localAction.getName().toString().equals(nameToSearch)){
					callResolved = true;
					call = new MCActionCall(nameToSearch, args,null);
					result.append(call.toFormula(option));
					if(args.size() == 1){ //there is one parameter being used
						MCAActionDefinition actionDef = context.getActionByName(nameToSearch);
						MCALocalDefinition localDef = new MCALocalDefinition(null, null);
						//if the variable is a communication variable the it is present on the bindings
						if(context.maximalBinding.containsVariable(args.getFirst().toString())){
							localDef.setName(args.getFirst().toString());
						}else{
							LinkedList<MCPParametrisation> parameters = actionDef.getDeclarations();
							localDef.setName(parameters.getFirst().toFormula(option));
						}
						
						MCAValParametrisation param = new MCAValParametrisation(localDef);
						ParameterDependency paramDep = new ParameterDependency(nameToSearch,param,this.parentDefinitionName); 
						context.parameterDependencies.add(paramDep);
						MCPCMLType parType = ((MCAValParametrisation)localAction.getDeclarations().getFirst()).getDeclaration().getType();
						ParameterFact paramFact = new ParameterFact(nameToSearch, args.getFirst(),parType);
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
						callResolved = true;
						call = new MCOperationCall(name, args, null,((MCAExplicitCmlOperationDefinition) pDefinition).getOperationType());
						result.append(call.toFormula(option));
						if(args.size() == 1){ //there is one parameter being used
							LinkedList<MCPCMLPattern> paramPatterns = ((MCAExplicitCmlOperationDefinition) pDefinition).getParamPatterns();
							MCALocalDefinition localDef = new MCALocalDefinition(null, null);
							//if the variable is a communication variable the it is present on the bindings
							if(context.maximalBinding.containsVariable(args.getFirst().toString())){
								localDef.setName(args.getFirst().toString());
							}else{
								//LinkedList<MCPParametrisation> parameters = param.getDeclarations();
								localDef.setName(paramPatterns.getFirst().toFormula(option));
							}
							/*
							MCAValParametrisation param = new MCAValParametrisation(localDef);
							ParameterDependency paramDep = new ParameterDependency(nameToSearch,param,this.parentDefinitionName); 
							context.parameterDependencies.add(paramDep);
							MCPCMLType parType = ((MCAOperationType) ((MCAExplicitCmlOperationDefinition) pDefinition).getOperationType()).getParameters().getFirst();
							ParameterFact paramFact = new ParameterFact(nameToSearch, args.getFirst(),parType);
							context.parameterFacts.add(paramFact);
							*/
						}
						break;
					}
				}
			}
		}
		if (!callResolved) {
			for (MCSFunctionDefinition pDefinition : context.functions) {
				if(pDefinition instanceof MCAExplicitFunctionDefinition){
					if(((MCAExplicitFunctionDefinition) pDefinition).getName().toString().equals(this.name)){
						callResolved = true;
						call = new MCFunctionCall(name, args, null);
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


	public String getParentDefinitionName() {
		return parentDefinitionName;
	}


	public void setParentDefinitionName(String parentDefinitionName) {
		this.parentDefinitionName = parentDefinitionName;
	}
	
	
}
