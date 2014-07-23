package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCActionCall;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCGenericCall;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCOperationCall;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ParameterDependency;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ParameterFact;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAActionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAExplicitCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCALocalDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAProcessDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCPCMLDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCSCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAReferenceAction implements MCPAction {

	private String name;
	private LinkedList<MCPCMLExp> args;
	protected String parentDefinitionName;
	
	
	public MCAReferenceAction(String name, LinkedList<MCPCMLExp> args, String parentDefinitionName) {
		super();
		this.name = name;
		this.args = args;
		this.parentDefinitionName = parentDefinitionName;
	}


	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		
		String nameToSearch = context.reverseNameMapping.get(this.name);
		Stack<MCPCMLDefinition> copy = new Stack<MCPCMLDefinition>();
		copy.addAll(context.mcProcOrActionsStack);
		if(!copy.isEmpty()){
			MCPCMLDefinition currentDef = copy.peek();
			if(currentDef instanceof MCAActionDefinition){
				//it has to get the process definition 
				copy.pop();
				currentDef = copy.peek();
				
			} 
			if(currentDef instanceof MCAProcessDefinition){
				nameToSearch = this.name + ((MCAProcessDefinition) currentDef).getName();
			}
		}
		
		ArrayList<MCAActionDefinition> localActions = context.localActions;
		boolean callResolved = false;
		MCGenericCall call = null;
		
		if(localActions.size() > 0){ //if there are auxiliary actions
			for (MCAActionDefinition localAction : localActions) {
				if(localAction.getName().toString().equals(nameToSearch)){
					callResolved = true;
					call = new MCActionCall(nameToSearch, args, null);
					
					result.append(call.toFormula(option));
					if(args.size() == 1){ //there is one parameter being used
						MCAActionDefinition actionDef = context.getActionByName(name);
						LinkedList<MCPParametrisation> parameters = actionDef.getDeclarations();
						MCALocalDefinition localDef = new MCALocalDefinition(parameters.getFirst().toFormula(option), null);
						MCAValParametrisation param = new MCAValParametrisation(localDef);
						ParameterDependency paramDep = new ParameterDependency(this.name,param,this.parentDefinitionName); 
						context.parameterDependencies.add(paramDep);
						MCPCMLType parType = ((MCAValParametrisation)localAction.getDeclarations().getFirst()).getDeclaration().getType();
						ParameterFact paramFact = new ParameterFact(this.name, args.getFirst(),parType);
						context.parameterFacts.add(paramFact);
					}
					break;
					
				}
			}
		}
		
		if (!callResolved) {
			for (MCAProcessDefinition pDefinition : context.processDefinitions) {
					if(((MCAProcessDefinition) pDefinition).getName().toString().equals(this.name)){
						callResolved = true;
						call = new MCActionCall(name, args,null);
						result.append(call.toFormula(option));
						break;
					}
			}
		}
		
		if (!callResolved) {
			for (MCSCmlOperationDefinition pDefinition : context.operations) {
				if(pDefinition instanceof MCAExplicitCmlOperationDefinition){
					if(((MCAExplicitCmlOperationDefinition) pDefinition).getName().toString().equals(this.name)){
						call = new MCOperationCall(name, args, null, ((MCAExplicitCmlOperationDefinition) pDefinition).getOperationType());
						result.append(call.toFormula(option));
					}
				}
			}
		}
		
		
		//result.append(call.toFormula(option));

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
